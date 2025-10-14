package ch.admin.bar.siardsuite.service;

import ch.admin.bar.siard2.api.*;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siardsuite.ui.presenter.archive.browser.forms.utils.ListAssembler;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStreamWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import ch.enterag.utils.BU;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import ch.admin.bar.siard2.api.generated.CategoryType;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Builder
public class TableExporterService {
    private static final Logger log = LoggerFactory.getLogger(TableExporterService.class);

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

    @NonNull
    @Builder.Default
    private final String archiveFileName = "all_tables";

    @SneakyThrows
    public void export() {
        String htmlFileName = archiveFileName.replace(".siard", ".html");
        File destination = new File(exportDir.getAbsolutePath(), htmlFileName);
        File lobFolder = new File(exportDir, lobsDirName);

        if (destination.exists()) {
            boolean deleted = destination.delete();
        }
        
        if (!lobFolder.exists()) {
            boolean ok = lobFolder.mkdirs();
        }

        try (OutputStream os = Files.newOutputStream(destination.toPath());
             OutputStreamWriter w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {

            String pageTitle = htmlFileName.replace(".html", "");
            w.write("<!DOCTYPE html>\r\n<html lang=\"ko\">\r\n<head>\r\n<meta charset=\"utf-8\"/>\r\n");
            w.write("<title>" + escapeHtml(pageTitle) + "</title>\r\n<style>");
            w.write("body{font-family:Arial,sans-serif;margin:20px;}h2{margin-top:30px;border-bottom:1px solid #ccc;padding-bottom:6px;}");
            w.write("table{border-collapse:collapse;width:100%;margin:12px 0 28px;}th,td{border:1px solid #ddd;padding:6px;text-align:left;}th{background:#f5f5f5;}tr:nth-child(even){background:#fafafa;}");
            w.write("</style>\r\n</head>\r\n<body>\r\n<h1>" + escapeHtml(pageTitle) + "</h1>\r\n");

            for (val schema : schemas) {
        val filtered = ListAssembler.assemble(schema.getTables(), schema::getTable).stream()
                .filter(shouldBeExportedFilter)
                .collect(Collectors.toList());

                if (!filtered.isEmpty()) {
                    for (val table : filtered) {
                        val mt = table.getMetaTable();

                        String schemaName = schema.getMetaSchema().getName();
                        String tableName = mt.getName();
                        w.write("<h2>" + escapeHtml(schemaName + "." + tableName) + "</h2>\r\n<table>\r\n<tr>\r\n");
                        for (int i = 0; i < mt.getMetaColumns(); i++) {
                            w.write("<th>" + escapeHtml(mt.getMetaColumn(i).getName()) + "</th>\r\n");
                        }
                        w.write("</tr>\r\n");

                        RecordDispenser rd = table.openRecords();
                        try {
                            long totalRows = mt.getRows();
                            for (long r = 0; r < totalRows; r++) {
                                Record rec = rd.get();
                                w.write("<tr>\r\n");
                                for (int c = 0; c < rec.getCells(); c++) {
                                    Cell cell = rec.getCell(c);
                                    w.write("<td>");
                                    writeCellValue(w, cell, lobFolder);
                                    w.write("</td>\r\n");
                                }
                                w.write("</tr>\r\n");
                            }
                        } finally { rd.close(); }

                        copyLobFiles(table, lobFolder);

                        w.write("</table>\r\n");
                    }
                }
            }
            w.write("</body>\r\n</html>\r\n");
        }
    }

    private void export(Schema schema) throws IOException { /* no-op: single-file export only */ }

    private void export(Table table) throws IOException { /* no-op: single-file export only */ }

    private void copyLobFiles(Table table, File lobFolder) throws IOException {
        MetaTable mt = table.getMetaTable();
        RecordDispenser rd = table.openRecords();
        try {
            for (long r = 0; r < mt.getRows(); r++) {
                Record rec = rd.get();
                for (int c = 0; c < rec.getCells(); c++) {
                    Cell cell = rec.getCell(c);
                    String filename = null;
                    try { filename = cell.getFilename(); } catch (IOException ignore) {}
                    
                    if (filename != null) {
                        File target = new File(lobFolder, filename);
                        File parent = target.getParentFile();
                        if (parent != null && !parent.exists()) parent.mkdirs();
                        
                        if (!target.exists()) {
                            InputStream is = null;
                            try { is = cell.getInputStream(); } catch (Exception ignored) {}
                            if (is != null) {
                                try (OutputStream os = new FileOutputStream(target)) {
                                    byte[] buf = new byte[8192];
                                    for (int read = is.read(buf); read != -1; read = is.read(buf)) {
                                        os.write(buf, 0, read);
                                    }
                                } finally { try { is.close(); } catch (Exception e) { /* ignore */ } }
                            } else {
                                Reader rdr = null;
                                try { rdr = cell.getReader(); } catch (Exception ignored) {}
                                if (rdr != null) {
                                    try (Writer out = new FileWriter(target)) {
                                        char[] cbuf = new char[8192];
                                        for (int read = rdr.read(cbuf); read != -1; read = rdr.read(cbuf)) {
                                            out.write(cbuf, 0, read);
                                        }
                                    } finally { try { rdr.close(); } catch (Exception e) { /* ignore */ } }
                                }
                            }
                        }
                    }
                }
            }
        } finally { rd.close(); }
    }

    private void writeCellValue(OutputStreamWriter w, Cell cell, File lobFolder) throws IOException {
        if (cell.isNull()) { w.write("&nbsp;"); return; }
        
        String filename = null;
        try { filename = cell.getFilename(); } catch (IOException ignore) {}
        
        if (filename != null) {
            String displayName = new File(filename).getName();
            String href = "lobs/" + filename.replace('\\', '/');
            w.write("<a href=\"" + escapeHtml(href) + "\">" + escapeHtml(displayName) + "</a>");
            return;
        }
        int preType = cell.getMetaValue().getPreType();
        if (preType == -3 || preType == -2 || preType == 2004 || preType == 2005 || preType == 2009 || preType == 2011) {
            w.write("[LOB Data]");
            return;
        }
        String text = getValueAsString(cell);
        w.write(escapeHtml(text));
    }

    private String getValueAsString(Value v) {
        try {
            int t = v.getMetaValue().getPreType();
            switch (t) {
                case -15: case -9: case 1: case 12: case 70: case 2005: case 2009: case 2011: return v.getString();
                case -3: case -2: case 2004: return "0x" + BU.toHex(v.getBytes());
                case 2: case 3: return v.getBigDecimal().toPlainString();
                case 5: return v.getInt().toString();
                case 4: return v.getLong().toString();
                case -5: return v.getBigInteger().toString();
                case 6: case 8: return v.getDouble().toString();
                case 7: return v.getFloat().toString();
                case 16: return v.getBoolean().toString();
                case 91: return v.getDate().toString();
                case 92: return v.getTime().toString();
                case 93: return v.getTimestamp().toString();
                case 1111: return v.getDuration().toString();
                default: return v.toString();
            }
        } catch (Exception e) {
            return "[Error: " + e.getMessage() + "]";
        }
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
                .replace("\"","&quot;").replace("'","&#39;");
    }
}
