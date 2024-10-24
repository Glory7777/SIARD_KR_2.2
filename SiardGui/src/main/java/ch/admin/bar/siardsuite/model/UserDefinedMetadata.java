package ch.admin.bar.siardsuite.model;

import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.ext.form.FormData;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.io.File;
import java.net.URI;
import java.util.*;

@Value
@Builder
public class UserDefinedMetadata {
    @NonNull String dbName;
    @NonNull Optional<String> description;
    @NonNull String owner;
    @NonNull String dataOriginTimespan;
    @NonNull Optional<String> archiverName;
    @NonNull Optional<String> archiverContact;
    @NonNull Optional<URI> lobFolder;

    @NonNull File saveAt;
    @NonNull Boolean exportViewsAsTables;

    @Builder.Default
    Map<String, Schema> selectedSchemaMap = new HashMap<>();

    @Builder.Default
    Map<String, List<String>> selectedSchemaTableMap = new HashMap<>();

    @Builder.Default
    Set<FormData> formDataSet = new LinkedHashSet<>();
}
