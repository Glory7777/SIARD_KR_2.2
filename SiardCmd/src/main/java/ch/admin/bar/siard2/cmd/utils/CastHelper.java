package ch.admin.bar.siard2.cmd.utils;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;








public final class CastHelper
{
  public static <T> Optional<T> tryCast(Object o, Class<T> type) {
    if (type.isInstance(o)) {
      return Optional.of((T)o);
    }
    return Optional.empty();
  }



  
  public static <T> Stream<T> tryCastWithStream(Object o, Class<T> type) {
    if (type.isInstance(o)) {
      return Stream.of((T)o);
    }
    return Stream.empty();
  }
  
  public static <T, R> Function<T, Stream<R>> tryCast(Class<R> type) {
    return t -> tryCastWithStream(t, type);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cm\\utils\CastHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */