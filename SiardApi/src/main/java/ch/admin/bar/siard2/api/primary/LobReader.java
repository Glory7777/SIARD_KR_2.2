package ch.admin.bar.siard2.api.primary;

import ch.enterag.utils.zip.FileEntry;
import ch.enterag.utils.zip.Zip64File;
import lombok.SneakyThrows;
import org.apache.tika.Tika;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LobReader implements AutoCloseable {

    private final Zip64File zip64File;
    private static final Tika tika = new Tika(); // Tika 인스턴스는 한 번만 생성하여 재사용

    public LobReader(File siardFile) throws IOException {
        this.zip64File = new Zip64File(siardFile);
    }

    @SneakyThrows
    public String readLob(String cPath) {
        FileEntry entry = zip64File.getFileEntry(cPath);
        if (entry == null) {
            throw new IOException("File not found in SIARD archive: " + cPath);
        }

        try (InputStream inputStream = zip64File.openEntryInputStream(entry.getName())) {
            byte[] data = inputStream.readAllBytes();

            if (cPath.toLowerCase().endsWith(".txt")) {
                return new String(data, StandardCharsets.UTF_8);
            } else {
                // 바이너리 파일: Tika를 사용해 텍스트 추출 시도
                try {
                    return tika.parseToString(new ByteArrayInputStream(data));
                } catch (Exception tikaException) {
                    // Tika 파싱 실패 시, 16진수 문자열로 변환하여 반환
                    return DatatypeConverter.printHexBinary(data);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading record from SIARD file at: " + cPath, e);
        }
    }

    @Override
    public void close() throws IOException {
        if (zip64File != null) {
            zip64File.close();
        }
    }
}
