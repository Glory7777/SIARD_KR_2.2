package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Archive;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LobReader {

    private static Archive archive = null;

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

        System.out.println("Attempting to read cPath: " + cPath);

        ZipEntry entry = zipFile.getEntry(cPath);
        if (entry == null) {
            throw new IOException("File not found in SIARD archive: " + cPath);
        }

        try (InputStream inputStream = zipFile.getInputStream(entry)) {
            System.out.println("File found in archive: " + cPath);
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
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
//    @SneakyThrows
//    public static Archive openSiardForReadLob(File file) throws IOException {
//        // ArchiveImpl 인스턴스를 생성하고 파일 열기
//        val archive = ArchiveImpl.newInstance();
//        archive.open(file);
//        LobReader.archive = archive;  // 열린 Archive를 필드에 저장
//        return archive;
//    }
//
//    @SneakyThrows
//    public static String readRecordByCPath(String filePath, String cPath) {
//
//        // SIARD 파일 열기
//        if (archive == null) {
//            openSiardForReadLob(new File(filePath));
//        }
//        // 아카이브가 열렸는지 확인
//        if (archive != null) {
//            System.out.println("Archive is opened and ready for reading.");
//        } else {
//            throw new IllegalStateException("Failed to open SIARD archive.");
//        }
//
//        System.out.println("Attempting to read cPath: " + cPath);
//
//        // 아카이브에서 cPath에 해당하는 파일 읽기
//        try (InputStream inputStream = archive.getClass().getResourceAsStream(cPath)) {
//            if (inputStream == null) {
//                throw new IOException("File not found in SIARD archive: " + cPath);
//            }
//            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            throw new RuntimeException("Error reading record from SIARD file at: " + cPath, e);
//        }
//    }

}
