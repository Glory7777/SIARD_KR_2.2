package ch.admin.bar.siardsuite.service;

import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siardsuite.ui.presenter.archive.browser.forms.utils.ListAssembler;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Builder
public class TableExporterService {

    @NonNull
    private final List<Schema> schemas;

    @NonNull
    @Builder.Default
    private final Predicate<Table> shouldBeExportedFilter = databaseTable -> true;

    @NonNull
    private final File exportDir;

    @NonNull
    @Builder.Default
    private final String lobsDirName = "lobs";
    
    @Builder.Default
    private final int maxRowsPerFile = 10000; // 파일당 최대 행 수

    @SneakyThrows
    public void export() {
        // SIARD 파일명과 현재 날짜시간으로 상위 폴더 생성
        String siardFileName = schemas.get(0).getParentArchive().getFile().getName();
        String siardName = siardFileName.substring(0, siardFileName.lastIndexOf('.'));
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
        String parentFolderName = siardName + "_" + timestamp;
        File parentFolder = new File(exportDir, parentFolderName);
        
        if (!parentFolder.exists()) {
            parentFolder.mkdirs();
        }
        
        for (val schema : schemas) {
            exportSchema(schema, parentFolder);
        }
    }

    private void exportSchema(Schema schema, File parentFolder) throws IOException {
        val filtered = ListAssembler.assemble(schema.getTables(), schema::getTable).stream()
                .filter(shouldBeExportedFilter)
                .collect(Collectors.toList());

        for (val table : filtered) {
            exportTable(table, parentFolder);
        }
    }

    private void exportTable(Table table, File parentFolder) throws IOException {
        // SIARD 파일명에서 확장자를 제거한 이름을 가져옴
        String siardFileName = table.getParentSchema().getParentArchive().getFile().getName();
        String siardName = siardFileName.substring(0, siardFileName.lastIndexOf('.'));
        String tableName = table.getMetaTable().getName();
        
        long totalRows = table.getMetaTable().getRows();
        
        if (totalRows <= maxRowsPerFile) {
            // 행 수가 기준 이하인 경우 단일 파일로 export
            exportSingleTableFile(table, parentFolder, siardName, tableName, 0, totalRows, "");
        } else {
            // 행 수가 기준을 초과하는 경우 분할해서 export
            int fileCount = (int) Math.ceil((double) totalRows / maxRowsPerFile);
            for (int i = 0; i < fileCount; i++) {
                long startRow = i * maxRowsPerFile;
                long endRow = Math.min(startRow + maxRowsPerFile, totalRows);
                String suffix = fileCount > 1 ? "_" + (i + 1) : "";
                exportSingleTableFile(table, parentFolder, siardName, tableName, startRow, endRow, suffix);
            }
        }
    }
    
    private void exportSingleTableFile(Table table, File parentFolder, String siardName, String tableName, 
                                     long startRow, long endRow, String suffix) throws IOException {
        String fileName = siardName + "_" + tableName + suffix + ".html";
        File destination = new File(parentFolder.getAbsolutePath(), fileName);
        
        // LOB 폴더를 SIARD파일명_테이블명 형식으로 생성
        String lobFolderName = siardName + "_" + tableName;
        File lobFolder = new File(parentFolder, lobsDirName + "/content/schema/" + lobFolderName);

        try (OutputStream outPutStream = Files.newOutputStream(destination.toPath())) {
            table.exportAsHtml(outPutStream, lobFolder, startRow, endRow);
        }
    }
}
