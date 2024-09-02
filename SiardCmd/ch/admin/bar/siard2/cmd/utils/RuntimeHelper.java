package ch.admin.bar.siard2.cmd.utils;

import java.util.Enumeration;
import java.util.Properties;


public class RuntimeHelper
{
  public static String getRuntimeInformation() {
    Runtime rt = Runtime.getRuntime();




    
    StringBuilder stringBuilder = (new StringBuilder()).append("free memory: ").append(rt.freeMemory()).append("\n").append("total memory: ").append(rt.totalMemory()).append("\n").append("maximum memory: ").append(rt.maxMemory()).append("\n").append("System properties: ").append(rt.maxMemory()).append("\n");
    
    Properties systemProperties = System.getProperties();
    Enumeration<?> systemPropertiesEnumeration = systemProperties.propertyNames();
    while (systemPropertiesEnumeration.hasMoreElements()) {
      String key = systemPropertiesEnumeration.nextElement().toString();
      stringBuilder
        .append("  ")
        .append(key)
        .append(": ")
        .append(systemProperties.getProperty(key))
        .append("\n");
    } 
    
    return stringBuilder.toString();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cm\\utils\RuntimeHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */