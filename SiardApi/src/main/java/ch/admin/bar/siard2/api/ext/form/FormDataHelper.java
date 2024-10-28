package ch.admin.bar.siard2.api.ext.form;

import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.ext.FileDownloadPathHolder;
import ch.admin.bar.siard2.api.ext.SchemaTableKey;
import ch.admin.bar.siard2.api.ext.SftpConnection;
import ch.admin.bar.siard2.api.ext.util.FileUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Optional;

import static ch.admin.bar.siard2.api.ext.SftpSender.buildSftpSender;

@Builder
@Getter
public class FormDataHelper {

    private final FormData formData;
    private final String defaultTargetDirectory;
    private final Cell cell;
    private final Object oValue;
    private final SchemaTableKey schemaTableKey;

    public void send() {
        if (formData.hasData()) {
            if (isSftp()) {
                sendOverSftp();
            } else {
                copyFile();
            }
        }
    }

    private void sendOverSftp() {
        this.findByKey().ifPresent(
                formData -> {
                    String columnName = cell.getMetaColumn().getName();
                    LinkedHashSet<String> columnSet = formData.getDownloadableTableMap().get(schemaTableKey);
                    if (columnSet.contains(columnName)) {
                        SftpConnection sftpConnection = formData.getSftpConnection();
                        FileDownloadPathHolder fileDownloadPathHolder = getFileDownloadPathHolder();
                        buildSftpSender(sftpConnection, fileDownloadPathHolder).send();
                    }
                }
        );
    }

    private void copyFile() {
        FileDownloadPathHolder fileDownloadPathHolder = getFileDownloadPathHolder();
        FileUtil.create(fileDownloadPathHolder).copy();
    }

    private Optional<FormData> findByKey() {
        return Optional.ofNullable(formData).filter(fd -> fd.getSchemaTableKey().equals(schemaTableKey));
    }

    private boolean isSftp() {
        return formData.isSftp();
    }

    private boolean isCopy() {
        return formData.isCopy();
    }

    private FileDownloadPathHolder getFileDownloadPathHolder() {
        String sourceFilePath = oValue.toString();
        String targetFilePath = getOrDefaultTargetDirectory();

        return FileDownloadPathHolder.createInstance(sourceFilePath, targetFilePath);
    }

    private String getOrDefaultTargetDirectory() {
        String targetDirectory = formData.getTargetDirectory();
        return targetDirectory == null || targetDirectory.isBlank() ? defaultTargetDirectory : formData.getTargetDirectory();
    }
}
