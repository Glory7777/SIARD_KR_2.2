//package ch.admin.bar.siardsuite.ui.presenter.archive.model;
//
//import ch.admin.bar.siard2.api.Archive;
//import ch.admin.bar.siard2.api.MetaData;
//import ch.admin.bar.siard2.api.Schema;
//import ch.admin.bar.siard2.api.Table;
//import ch.admin.bar.siard2.api.generated.DigestTypeType;
//import ch.admin.bar.siard2.api.generated.MessageDigestType;
//import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
//import ch.admin.bar.siardsuite.service.database.model.LoadDatabaseInstruction;
//import ch.enterag.utils.BU;
//import ch.enterag.utils.zip.EntryOutputStream;
//import ch.enterag.utils.zip.FileEntry;
//import ch.enterag.utils.zip.Zip64File;
//import lombok.Getter;
//
//import java.io.*;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Getter
//public class CustomArchiveProxy implements Archive {
//
//    private final Archive delegate;
//    private final List<TreeAttributeWrapper> selectedCheckBoxes;
//    private final Map<Schema, List<Table>> schemaTableMap = new HashMap<>();
//    private final boolean hasNotSelectedItems;
//
//    /**
//     *
//     * */
//    private void initDataToTransfer() {
//        if (selectedCheckBoxes.isEmpty()) return;
//
//        // get corresponding schema
//        KeyValueMapper<TreeAttributeWrapper, Schema> keyMapper = attr -> {
//            String schemaName = attr.getDatabaseTable().getTable().getParentSchema().getMetaSchema().getName();
//            return delegate.getSchema(schemaName);
//        };
//
//        // get table
//        KeyValueMapper<TreeAttributeWrapper, Table> valueMapper = attr -> attr.getDatabaseTable().getTable(); //
//
//        Map<Schema, List<Table>> schemaTableMap = selectedCheckBoxes
//                .stream()
//                .collect(
//                        Collectors.groupingBy(
//                                keyMapper::map,
//                                Collectors.mapping(
//                                        valueMapper::map, Collectors.toList()
//                                )
//                        )
//                );
//
//        this.schemaTableMap.putAll(schemaTableMap);
//    }
//
//    private interface KeyValueMapper<T, K> {
//        K map(T function);
//    }
//
//    private CustomArchiveProxy(Archive archive, List<TreeAttributeWrapper> selectedCheckBoxes) {
//        this.delegate = archive;
//        this.selectedCheckBoxes = selectedCheckBoxes;
//        initDataToTransfer();
//        this.hasNotSelectedItems = schemaTableMap.isEmpty();
//    }
//
//    private CustomArchiveProxy(Archive archive) {
//        this(archive, new ArrayList<>());
//    }
//
//    public static Archive wrap(final Archive archive, List<TreeAttributeWrapper> selectedCheckBoxes) {
//        return new CustomArchiveProxy(archive, selectedCheckBoxes);
//    }
//
//    public static Archive wrap(final Archive archive) {
//        return new CustomArchiveProxy(archive);
//    }
//
//    @Override
//    public File getFile() {
//        return delegate.getFile();
//    }
//
//    @Override
//    public boolean canModifyPrimaryData() {
//        return delegate.canModifyPrimaryData();
//    }
//
//    @Override
//    public void setMaxInlineSize(int i) throws IOException {
//        delegate.setMaxInlineSize(i);
//    }
//
//    @Override
//    public int getMaxInlineSize() {
//        return delegate.getMaxInlineSize();
//    }
//
//    @Override
//    public void setMaxLobsPerFolder(int i) throws IOException {
//        delegate.setMaxLobsPerFolder(i);
//    }
//
//    @Override
//    public int getMaxLobsPerFolder() {
//        return delegate.getMaxLobsPerFolder();
//    }
//
//    @Override
//    public void exportMetaDataSchema(OutputStream outputStream) throws IOException {
//        delegate.exportMetaDataSchema(outputStream);
//    }
//
//    @Override
//    public void exportGenericTableSchema(OutputStream outputStream) throws IOException {
//        delegate.exportGenericTableSchema(outputStream);
//    }
//
//    @Override
//    public void exportMetaData(OutputStream outputStream) throws IOException {
//        delegate.exportMetaData(outputStream);
//    }
//
//    @Override
//    public void importMetaDataTemplate(InputStream inputStream) throws IOException {
//        delegate.importMetaDataTemplate(inputStream);
//    }
//
//    @Override
//    public void open(File file) throws IOException {
//        delegate.open(file);
//    }
//
//    @Override
//    public void create(File file) throws IOException {
//        delegate.create(file);
//    }
//
//    @Override
//    public void close() throws IOException {
//        delegate.close();
//    }
//
//    @Override
//    public MetaData getMetaData() {
//        return delegate.getMetaData();
//    }
//
//    @Override
//    public void loadMetaData() throws IOException {
//        delegate.loadMetaData();
//    }
//
//    @Override
//    public void saveMetaData() throws IOException {
//        delegate.saveMetaData();
//    }
//
//    @Override
//    public int getSchemas() {
//        return hasNotSelectedItems() ? delegate.getSchemas() : getSelectedSchemaSize();
//    }
//
//    @Override
//    public Schema getSchema(int i) {
//        return delegate.getSchema(i);
//    }
//
//    @Override
//    public Schema getSchema(String s) {
//        return delegate.getSchema(s);
//    }
//
//    @Override
//    public Schema createSchema(String s) throws IOException {
//        return delegate.createSchema(s);
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return delegate.isEmpty();
//    }
//
//    @Override
//    public boolean isValid() {
//        return delegate.isValid();
//    }
//
//    @Override
//    public boolean isPrimaryDataUnchanged() throws IOException {
//        return delegate.isPrimaryDataUnchanged();
//    }
//
//    @Override
//    public boolean isMetaDataUnchanged() {
//        return delegate.isMetaDataUnchanged();
//    }
//
//    private boolean hasNotSelectedItems() {
//        return this.hasNotSelectedItems;
//    }
//
//    private int getSelectedSchemaSize() {
//        return schemaTableMap.keySet().size();
//    }
//
//}
