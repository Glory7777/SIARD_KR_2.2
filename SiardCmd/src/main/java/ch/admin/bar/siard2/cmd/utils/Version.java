package ch.admin.bar.siard2.cmd.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class Version implements Comparable<Version>, Serializable {

    private final String databaseName;
    private final int version;
    private final int subVersion;

    public String toString() {
        return this.databaseName + ' ' +
                this.version + '.' +
                this.subVersion + '.';
    }

    @Override
    public int compareTo(@NotNull Version other) {
        if (this == other) {
            return 0;
        } else {
            int diff = databaseName.compareTo(other.databaseName);
            if (diff == 0) {
                diff = this.version - other.version;
                if (diff == 0) {
                    diff = this.subVersion - other.subVersion;
                }
            }
            return diff;
        }
    }
}
