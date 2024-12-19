package ch.admin.bar.siard2.api.primary;

import ch.enterag.utils.zip.FileEntry;
import ch.enterag.utils.zip.Zip64File;
import lombok.SneakyThrows;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class LobReader {

    private static Zip64File zip64File = null;
    private static String previousFilePath = null;

    @SneakyThrows
    public static void openSiardForReadLob(File file) throws IOException {

        System.out.println("Attempting to open New SIARD file: " + file.getAbsolutePath());
        zip64File = new Zip64File(file);
        System.out.println("SIARD file opened successfully: " + file.getAbsolutePath());
        System.out.println("SIARD file opened successfully: " + file.getAbsolutePath());
    }

    @SneakyThrows
    public static String readRecordByCPath(String filePath, String cPath) {

        System.out.println("Check previousFilePath : " + previousFilePath);
        System.out.println("Current filePath: " + filePath);

        //콘솔 로그 포함 코드
        if (zip64File == null || !filePath.equals(previousFilePath)) {
            if (zip64File != null) {
                System.out.println("File paths are different. Closing current SIARD file.");
                close(); // 기존 파일 닫기
            } else {
                System.out.println("zip64File is null. Opening new SIARD file.");
            }
            openSiardForReadLob(new File(filePath)); // 새 파일 열기
            previousFilePath = filePath; // previousFilePath 업데이트
        } else {
            System.out.println("File paths are the same. Reusing current SIARD file.");
        }


        //콘솔 로그 제외한 코드(최종 코드)
//     if(zip64File == null || !filePath.equals(previousFilePath)) {
//         if (zip64File != null) {
//             close(); // 기존 파일 닫기
//         }
//     }
//        openSiardForReadLob(new File(filePath)); // 새 파일 열기
//        previousFilePath = filePath; // 이전 filePath 업데이트


        // 엔트리 찾기 (이름이 일치하는 엔트리를 찾기 위해 반복)
        FileEntry entry = zip64File.getFileEntry(cPath);
        if (entry == null) {
            throw new IOException("File not found in SIARD archive: " + cPath);
        }

        try (InputStream inputStream = zip64File.openEntryInputStream(entry.getName())){
            System.out.println("File found in archive: " + cPath);

            //bin 데이터를 읽기 위한 코드 추가
            byte[] data = inputStream.readAllBytes();

            // 파일 확장자로 텍스트/바이너리 구분
            if(cPath.toLowerCase().endsWith(".txt")){
                return new String(data, StandardCharsets.UTF_8);
            } else {
                // 바이너리 파일: byte[]로 반환
                return DatatypeConverter.printHexBinary(data);
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading record from SIARD file at: " + cPath, e);
        }
    }

    public static void close() {
        if (zip64File != null) {
            try {
                zip64File.close();
                System.out.println("SIARD archive closed successfully.");
            } catch (IOException e) {
                System.err.println("Error closing SIARD archive: " + e.getMessage());
            } finally {
                zip64File = null;
                System.out.println("zip64File reset to null.");
            }
        }
    }
    }
