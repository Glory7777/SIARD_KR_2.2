package ch.admin.bar.siard2.api.primary;

import lombok.SneakyThrows;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LobReader {

    private static ZipFile zipFile = null;

    @SneakyThrows
    public static void openSiardForReadLob(File file) throws IOException {
        System.out.println("Attempting to open SIARD file: " + file.getAbsolutePath());

        zipFile = new ZipFile(file);
        System.out.println("SIARD file opened successfully: " + file.getAbsolutePath());
    }

    @SneakyThrows
    public static String readRecordByCPath(String filePath, String cPath) {
        // SIARD 파일 열기 확인
        if (zipFile == null) {
            openSiardForReadLob(new File(filePath));
        }

        System.out.println("before clear cPath: " + cPath);
        cPath = cPath.replace("\\", "/").trim();
        System.out.println("clear cPath: " + cPath);

        ZipEntry entry = zipFile.getEntry(cPath);
        if (entry == null) {
            throw new IOException("File not found in SIARD archive: " + cPath);
        }

        try (InputStream inputStream = zipFile.getInputStream(entry)) {
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
        if (zipFile != null) {
            try {
                zipFile.close();
                System.out.println("SIARD archive closed successfully.");
            } catch (IOException e) {
                System.err.println("Error closing SIARD archive: " + e.getMessage());
            }
        }
    }
}
