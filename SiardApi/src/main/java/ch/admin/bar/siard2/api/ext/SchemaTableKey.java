package ch.admin.bar.siard2.api.ext;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

@EqualsAndHashCode
@Getter
@Value(staticConstructor = "of")
public class SchemaTableKey {

    String schema;
    String table;

}