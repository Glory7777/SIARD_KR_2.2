package ch.admin.bar.siard2.api.convertableSiardArchive.Siard10;

import ch.admin.bar.siard2.api.generated.old10.SiardArchive;

public class ConvertableSiard10Archive
  extends SiardArchive {
  public <T> T transform(SiardArchive10Transformer transformer) {
    return transformer.transform();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard10\ConvertableSiard10Archive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */