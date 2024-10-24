package ch.admin.bar.siard2.api;

import ch.admin.bar.siard2.api.ext.form.FormData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Archive {
    String sSIARD2_META_DATA_XSD_RESOURCE = "/ch/admin/bar/siard2/api/res/metadata.xsd";

    String sSIARD2_META_DATA_NAMESPACE = "http://www.bar.admin.ch/xmlns/siard/2/metadata.xsd";

    String sSIARD2_GENERIC_TABLE_XSD_RESOURCE = "/ch/admin/bar/siard2/api/res/table.xsd";

    String sSIARD2_TABLE_NAMESPACE = "http://www.bar.admin.ch/xmlns/siard/2/table.xsd";

    String sSIARD_DEFAULT_EXTENSION = "siard";

    String sMETA_DATA_VERSION_1_0 = "1.0";

    String sMETA_DATA_VERSION_2_0 = "2.0";

    String sMETA_DATA_VERSION_2_1 = "2.1";

    String sMETA_DATA_VERSION = "2.2";

    int iDEFAULT_MAX_INLINE_SIZE = 4000;

    File getFile();

    boolean canModifyPrimaryData();

    void setMaxInlineSize(int paramInt) throws IOException;

    int getMaxInlineSize();

    void setMaxLobsPerFolder(int paramInt) throws IOException;

    int getMaxLobsPerFolder();

    void exportMetaDataSchema(OutputStream paramOutputStream) throws IOException;

    void exportGenericTableSchema(OutputStream paramOutputStream) throws IOException;

    void exportMetaData(OutputStream paramOutputStream) throws IOException;

    void importMetaDataTemplate(InputStream paramInputStream) throws IOException;

    void open(File paramFile) throws IOException;

    void create(File paramFile) throws IOException;

    void close() throws IOException;

    MetaData getMetaData();

    void loadMetaData() throws IOException;

    void saveMetaData() throws IOException;

    int getSchemas();

    Schema getSchema(int paramInt);

    Schema getSchema(String paramString);

    Schema createSchema(String paramString) throws IOException;

    boolean isEmpty();

    boolean isValid();

    boolean isPrimaryDataUnchanged() throws IOException;

    boolean isMetaDataUnchanged();

    void replaceWithSelectedSchemas(Map<String, List<String>> selectedSchemaTableMap);

    void replaceWithSelectedSchemaMap(Map<String, Schema> selectedSchemaMap);

    Map<String, Schema> getSelectedSchemaMap();

    boolean hasSelectedTables();

    Map<String, Schema> getSchemaMap();

    void setSelectedSchemaTableMap(Map<String, List<String>> selectedSchemaTableMap);

    Map<String, List<String>> getSelectedSchemaTableMap();

    Set<FormData> getFormDataSet();

    void setFormDataSet(Set<FormData> formDataSet);

}