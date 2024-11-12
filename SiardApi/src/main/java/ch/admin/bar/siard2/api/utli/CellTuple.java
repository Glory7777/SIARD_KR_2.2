package ch.admin.bar.siard2.api.utli;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@AllArgsConstructor(staticName = "of")
@Value
public class CellTuple<T1, T2, T3> {
    @NonNull T1 value1;
    @NonNull T2 value2;
    @NonNull T3 value3;
}
