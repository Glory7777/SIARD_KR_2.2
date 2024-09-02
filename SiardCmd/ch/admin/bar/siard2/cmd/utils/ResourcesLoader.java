package ch.admin.bar.siard2.cmd.utils;

import java.io.InputStream;
import java.util.Optional;













public class ResourcesLoader
{
  public static InputStream loadResource(String resource) {
    Optional<InputStream> urlToResource = Optional.ofNullable(ResourcesLoader.class
        .getClassLoader()
        .getResourceAsStream(resource));
    
    return urlToResource
      .<Throwable>orElseThrow(() -> new IllegalArgumentException(String.format("Resource \"%s\" not found", new Object[] { resource })));
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cm\\utils\ResourcesLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */