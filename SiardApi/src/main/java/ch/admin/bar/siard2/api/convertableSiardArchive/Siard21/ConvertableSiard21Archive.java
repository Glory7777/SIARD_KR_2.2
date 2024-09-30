package ch.admin.bar.siard2.api.convertableSiardArchive.Siard21;

import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.Siard21ToSiard22Transformer;


public class ConvertableSiard21Archive extends ch.admin.bar.siard2.api.generated.old21.SiardArchive {
  public ch.admin.bar.siard2.api.generated.SiardArchive accept(Siard21ToSiard22Transformer visitor) {
    return visitor.visit(this);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard21\ConvertableSiard21Archive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */