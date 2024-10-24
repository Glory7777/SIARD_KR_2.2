package ch.admin.bar.siard2.api.ext.form;

import ch.admin.bar.siard2.api.ext.SchemaTableKey;
import ch.admin.bar.siard2.api.ext.SftpConnection;
import lombok.*;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FormData {

    @EqualsAndHashCode.Include
    @Builder.Default
    private SftpConnection sftpConnection = new SftpConnection();
    @EqualsAndHashCode.Include
    private SchemaTableKey schemaTableKey;
    private String targetDirectory;
    private boolean sftp;
    private boolean copy;
    private final Map<SchemaTableKey, LinkedHashSet<String>> downloadableTableMap = new HashMap<>();

    public void updateConnectionData(FormData newFormData) {
        this.setSftpConnection(newFormData.getSftpConnection());
        this.setTargetDirectory(newFormData.getTargetDirectory());
        this.setSftp(newFormData.isSftp());
        this.setCopy(newFormData.isCopy());
    }

    public void updateTableData(String schema, String table, String column) {
        this.setSchemaTableKey(SchemaTableKey.of(schema, table));
        LinkedHashSet<String> columnSet = downloadableTableMap.computeIfAbsent(schemaTableKey, k -> new LinkedHashSet<>());
        columnSet.add(column);
    }

}
