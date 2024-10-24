package ch.admin.bar.siard2.api.ext;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SftpSender implements Sender {

    private final SftpConnection sftpConnection;
    private final FileDownloadPathHolder fileDownloadPathHolder;

    private final JSch jsch = new JSch();
    private Session session;
    private Channel channel;
    private ChannelSftp channelSftp;

    private long sourceFileSize;
    private long executeTime;

    private SftpSender(SftpConnection sftpConnection, FileDownloadPathHolder fileDownloadPathHolder) {
        this.sftpConnection = sftpConnection;
        this.fileDownloadPathHolder = fileDownloadPathHolder;
    }

    public static SftpSender buildSftpSender(SftpConnection sftpConnection, FileDownloadPathHolder fileDownloadPathHolder) {
        return new SftpSender(sftpConnection, fileDownloadPathHolder);
    }

    public static boolean checkConnection(SftpConnection sftpConnection) {
        return new SftpSender(sftpConnection, null).initConnection();
    }

    public void send() {
        initConnection();
        try {
            isFileExists(fileDownloadPathHolder.getSourceFilePath());
        } catch (Exception e) {
            System.out.println("No Such File ! -> " + fileDownloadPathHolder.getSourceFilePath());
            disConnect();
            return;
        }
        downloadFile(fileDownloadPathHolder);
    }

    private void downloadFile(FileDownloadPathHolder fileDownloadPathHolder) {

        String sourceFilePath = fileDownloadPathHolder.getSourceFilePath().substring(0, fileDownloadPathHolder.getSourceFilePath().lastIndexOf("/") + 1);
        File sourceFileObj = new File(fileDownloadPathHolder.getSourceFilePath());
        File targetFileObj = new File(fileDownloadPathHolder.getTargetFilePath() + File.separator + sourceFilePath);

        if (!targetFileObj.exists()) {
            targetFileObj.mkdirs();
        }

        // 시작시간
        long startTime = System.currentTimeMillis();

        changeDirectory(sourceFilePath);
        getSftp(sourceFileObj.getName(), targetFileObj + File.separator + sourceFileObj.getName());
        setSourceFileSize(sourceFileObj.getName());

        // 종료시간
        long endTime = System.currentTimeMillis();
        // 수행시간 = 종료시간 - 시작시간
        executeTime = endTime - startTime;
        log.info("file downloaded :: {}", fileDownloadPathHolder.getSourceFilePath());
        disConnect();
    }

    private void setSourceFileSize(String sourceFile) {
        try {
            sourceFileSize = channelSftp.lstat(sourceFile).getSize();
        } catch (SftpException e) {
            System.out.println("error while setting source file size");
            throw new RuntimeException("cannot set file size");
        }
    }

    /**
     * SFTP 서버에서 파일 내려받기
     * 
     * @param src 내려받을 파일 경로
     * @param dst 저장 경로
     */
    private void getSftp(String src, String dst) {
        try {
            channelSftp.get(src, dst, new SftpProgresMonitorImpl(), ChannelSftp.OVERWRITE);
        } catch (SftpException e) {
            System.out.println("error while getting sftp ");
            throw new RuntimeException("cannot get sftp");
        }
    }

    private void putSftp(String src, String dst, SftpProgressMonitor monitor, int mode) {
        try {
            channelSftp.put(src, dst, new SftpProgresMonitorImpl(), ChannelSftp.OVERWRITE);
        } catch (SftpException e) {
            System.out.println("error while sending sftp ");
            throw new RuntimeException("cannot put sftp");

        }
    }

    /**
     * 디렉토리가 있는 경우 경로로 이동
     * 
     * @param sourceFilePath 
     */
    private void changeDirectory(String sourceFilePath) {
        try {
            channelSftp.cd(sourceFilePath);
            System.out.println("Changed directory to: " + sourceFilePath);  // Add this line
        } catch (SftpException exception) {
            System.out.println("error while changing directory to :" + sourceFilePath);
            throw new RuntimeException("cannot locate the directory...");
        }
    }

    /**
     * 커넥션 설정 및 시도
     */
    private boolean initConnection() {
        log.info("connecting over sftp...");

        String user = sftpConnection.getUser();
        String host = sftpConnection.getHost();
        int port = sftpConnection.getPort();
        String password = sftpConnection.getPassword();

        try {
            session = jsch.getSession(user, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            log.info("error while connecting to SFTP server :: {}", e.getMessage());
            disConnect();
            return false;
        }

        log.info("connection success :: {}", sftpConnection);
        return true;
    }

    private void disConnect() {
        try {
            if (session.isConnected()) {
                System.out.println("disconnecting... " + session.getHost());
                channelSftp.disconnect();
                channel.disconnect();
                session.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void isFileExists(String sourceFile) throws Exception {
        System.out.println("Checking file existence: " + sourceFile);  // Add this line to log the path being checked
        if (isFileNameExistsKor(sourceFile)) {
            setEncoding(sourceFile);
        }
        channelSftp.stat(sourceFile); // => No such file
    }

    private boolean isFileNameExistsKor(String sourceFile) throws Exception {
        String regExp = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
        if (sourceFile.matches(regExp)) {
            return true;
        }
        return false;
    }

    private void setEncoding(String sourceFile) throws Exception {
        boolean fileExistsFlag = false;

        List<String> encodingList = new ArrayList<String>();
        encodingList.add("UTF-8");
        encodingList.add("ASCII");
        encodingList.add("EUC-KR");
        encodingList.add("CP949");
        encodingList.add("MS949");
        encodingList.add("KSC5601");
        encodingList.add("ISO-8859-1");

        for (int i = 0; i < encodingList.size(); i++) {
            channelSftp.setFilenameEncoding(encodingList.get(i));

            try {
                channelSftp.stat(sourceFile);
                fileExistsFlag = true;
                System.out.println(fileExistsFlag + " encoding => " + encodingList.get(i));
                return;
            } catch (Exception e) {

            }
        }
        if (!fileExistsFlag) throw new Exception("No Such File ! -> " + sourceFile);
    }

}
