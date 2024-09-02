package ch.admin.bar.siard2.api.convertableSiardArchive.Siard22;

import ch.admin.bar.siard2.api.generated.AttributeType;
import ch.admin.bar.siard2.api.generated.AttributesType;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.admin.bar.siard2.api.generated.TypeType;
import java.util.Collection;





public class ConvertableSiard22TypeType
  extends TypeType
{
  public ConvertableSiard22TypeType(String name, String description, String base, String underType, String underSchema, boolean isFinal, boolean isInstantiable, CategoryType category, Collection<AttributeType> attributes) {
    this.name = name;
    this.description = description;
    this.base = base;
    this.underType = underType;
    this.underSchema = underSchema;
    this._final = isFinal;
    this.instantiable = isInstantiable;
    this.category = category;
    if (attributes.size() > 0) {
      this.attributes = new AttributesType();
      this.attributes.getAttribute().addAll(attributes);
    } 
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard22\ConvertableSiard22TypeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */