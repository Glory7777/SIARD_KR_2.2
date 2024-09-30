package ch.admin.bar.siard2.api.convertableSiardArchive.Siard21;

import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22FieldType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.Siard21ToSiard22Transformer;
import ch.admin.bar.siard2.api.generated.old21.FieldType;

public class ConvertableSiard21FieldType
  extends FieldType
{
  public ConvertableSiard21FieldType(FieldType field) {
    this.name = field.getName();
    this.description = field.getDescription();
    this.mimeType = field.getMimeType();
    this.lobFolder = field.getLobFolder();
    this.fields = field.getFields();
  }
  
  public ConvertableSiard22FieldType accept(Siard21ToSiard22Transformer visitor) {
    return visitor.visit(this);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard21\ConvertableSiard21FieldType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */