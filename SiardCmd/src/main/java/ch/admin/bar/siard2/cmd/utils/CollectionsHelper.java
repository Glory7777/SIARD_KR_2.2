package ch.admin.bar.siard2.cmd.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class CollectionsHelper
{
  public static <T> Set<T> setOf(T... entries) {
    HashSet<T> set = new HashSet<>(Arrays.asList(entries));
    return Collections.unmodifiableSet(set);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cm\\utils\CollectionsHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */