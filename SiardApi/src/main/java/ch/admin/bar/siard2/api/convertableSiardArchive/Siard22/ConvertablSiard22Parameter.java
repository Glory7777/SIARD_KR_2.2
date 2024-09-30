package ch.admin.bar.siard2.api.convertableSiardArchive.Siard22;

import ch.admin.bar.siard2.api.generated.ParameterType;
import java.math.BigInteger;


public class ConvertablSiard22Parameter
  extends ParameterType
{
  public ConvertablSiard22Parameter(String name, String description, BigInteger cardinality, String mode, String type, String typeName, String typeSchema, String typeOriginal) {
    this.name = name;
    this.description = description;
    this.cardinality = cardinality;
    this.mode = mode;
    this.type = type;
    this.typeName = typeName;
    this.typeSchema = typeSchema;
    this.typeOriginal = typeOriginal;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard22\ConvertablSiard22Parameter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */