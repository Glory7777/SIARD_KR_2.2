package ch.admin.bar.siardsuite.model;

import ch.admin.bar.siard2.api.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
}
