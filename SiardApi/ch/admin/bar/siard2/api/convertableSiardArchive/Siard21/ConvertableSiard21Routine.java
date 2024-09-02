package ch.admin.bar.siard2.api.convertableSiardArchive.Siard21;

import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22RoutineType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.Siard21ToSiard22Transformer;
import ch.admin.bar.siard2.api.generated.old21.RoutineType;

public class ConvertableSiard21Routine extends RoutineType {
  public ConvertableSiard21Routine(RoutineType routine) {
    this.name = routine.getName();
    this.description = routine.getDescription();
    this.body = routine.getBody();
    this.characteristic = routine.getCharacteristic();
    this.returnType = routine.getReturnType();
    this.specificName = routine.getSpecificName();
    this.source = routine.getSource();
    this.parameters = routine.getParameters();
  }
  
  public ConvertableSiard22RoutineType accept(Siard21ToSiard22Transformer visitor) {
    return visitor.visit(this);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard21\ConvertableSiard21Routine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */