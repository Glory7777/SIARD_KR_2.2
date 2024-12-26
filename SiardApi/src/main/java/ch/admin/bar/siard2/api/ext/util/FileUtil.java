package ch.admin.bar.siard2.api.ext.util;

import ch.admin.bar.siard2.api.ext.FileDownloadPathHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Objects;

public class FileUtil {

    private final FileDownloadPathHolder fileDownloadPathHolder;

    private FileUtil(FileDownloadPathHolder fileDownloadPathHolder) {
        this.fileDownloadPathHolder = fileDownloadPathHolder;
    }

    public static FileUtil create(FileDownloadPathHolder fileDownloadPathHolder) {
        return new FileUtil(fileDownloadPathHolder);
    }

    public void copy() {

        String sourceFilePath = fileDownloadPathHolder.getSourceFilePath().replace("/","\\").trim();
        String targetFilePath = fileDownloadPathHolder.getTargetFilePath().replace("/","\\").trim();
//        String test = "C:\\Users\\lenovo\\Downloads\\test\\테스트텍스트.txt";
        File sourceFile = new File(sourceFilePath);
//        File sourceFile = new File(test);
        if (!sourceFile.exists()) {
            System.out.println("File does not exist :: " + sourceFilePath);
            return;
        }

        makeDirectoryIfNotExists();
        String remainingPath = fileDownloadPathHolder.getRemainingSourcePath();
        remainingPath = remainingPath.replace("/", "\\");
        System.out.println("targetFile Path: " + targetFilePath);
        System.out.println("sourceFile Path: " + sourceFilePath);
        System.out.println("Remaining Path: " + remainingPath);

        // 최종 파일 저장 경로
        String finalTargetPath = targetFilePath;
        if (!remainingPath.isEmpty()) {
            finalTargetPath += "\\" + remainingPath;
            System.out.println("FinalTargetFile Path: " + finalTargetPath);
        }

        makeFullDirectoryPath(targetFilePath, remainingPath);

        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(finalTargetPath + File.separator + sourceFile.getName());
             FileChannel fcin = fis.getChannel();
             FileChannel fcout = fos.getChannel();
        ) {
            long size = fcin.size();
            fcout.transferFrom(fcin, 0, size);
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    private void makeDirectoryIfNotExists() {
        String targetPathToMakeDirectory = fileDownloadPathHolder.getTargetPathToMakeDirectory();
        File file = new File(targetPathToMakeDirectory);
        if (file.isDirectory() && !file.exists()) {
            file.mkdirs();
        }
    }


    private void makeFullDirectoryPath(String targetFilePath, String remainingPath) {
        // remainingPath가 null이거나 비어있으면 아무 작업도 하지 않음
        if (remainingPath == null || remainingPath.isEmpty()) {
            return;
        }
        // targetFilePath가 슬래시로 끝나지 않으면 추가
        if (!targetFilePath.endsWith("\\")) {
            targetFilePath += "\\";
        }
        // remainingPath에서 슬래시를 통일하고 경로 결합
        remainingPath = remainingPath.replace("/", "\\");
        String fullPath = targetFilePath + remainingPath;
        System.out.println("Directory created: " + fullPath);
        // 최종 경로의 디렉토리 객체 생성
        File directory = new File(fullPath);
        // 디렉토리 존재 여부 확인 후 생성
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created: " + fullPath);
            } else {
                System.err.println("Failed to create directory: " + fullPath);
            }
        } else {
            System.out.println("Directory already exists: " + fullPath);
        }
    }

}
