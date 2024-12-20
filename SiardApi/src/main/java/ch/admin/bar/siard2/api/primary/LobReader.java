package ch.admin.bar.siard2.api.primary;

import ch.enterag.utils.zip.FileEntry;
import ch.enterag.utils.zip.Zip64File;
import lombok.SneakyThrows;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class LobReader {

    private static Zip64File zip64File = null;
    private static String previousFilePath = null;

    @SneakyThrows
    public static void openSiardForReadLob(File file) throws IOException {
        zip64File = new Zip64File(file);
    }

    @SneakyThrows
    public static String readRecordByCPath(String filePath, String cPath) {

        //저장된 filePath = previousFilePath 가 새로 전달받은 filePath와 다르면 파일 닫아주기
     if(zip64File == null || !filePath.equals(previousFilePath)) {
         if (zip64File != null) {
             close();
         }
     }
        openSiardForReadLob(new File(filePath)); // 새 파일 열기
        previousFilePath = filePath; // filePath 업데이트

        FileEntry entry = zip64File.getFileEntry(cPath);
        if (entry == null) {
            throw new IOException("File not found in SIARD archive: " + cPath);
        }


        try (InputStream inputStream = zip64File.openEntryInputStream(entry.getName())){
            System.out.println("File found in archive: " + cPath);

            //bin 데이터를 읽기 위한 코드 추가
            byte[] data = inputStream.readAllBytes();

            if (cPath.toLowerCase().endsWith(".txt")) {
                return new String(data, StandardCharsets.UTF_8);
            } else {
                // 바이너리 파일: Tika를 사용해 텍스트 추출 시도
                try {
                    Tika tika = new Tika();
                    return tika.parseToString(new ByteArrayInputStream(data));
                } catch (Exception tikaException) {
                    // Tika 실패 시, DatatypeConverter로 16진수 문자열 반환
                    return DatatypeConverter.printHexBinary(data);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading record from SIARD file at: " + cPath, e);
        }
    }

    public static void close() {
        if (zip64File != null) {
            try {
                zip64File.close();
            } catch (IOException e) {
                System.err.println("Error closing SIARD archive: " + e.getMessage());
            } finally {
                zip64File = null;
            }
        }
    }
    }
