package ch.admin.bar.siard2.api.convertableSiardArchive.Siard22;
import ch.admin.bar.siard2.api.generated.RoutineType;
import ch.admin.bar.siard2.api.generated.SchemaType;
import ch.admin.bar.siard2.api.generated.TableType;
import ch.admin.bar.siard2.api.generated.TablesType;
import ch.admin.bar.siard2.api.generated.TypeType;
import ch.admin.bar.siard2.api.generated.TypesType;
import ch.admin.bar.siard2.api.generated.ViewType;
import ch.admin.bar.siard2.api.generated.ViewsType;
import java.util.Collection;

public class ConvertableSiard22SchemaType extends SchemaType {
  public ConvertableSiard22SchemaType(String name, String description, String folder, Collection<TypeType> types, Collection<RoutineType> routines, Collection<TableType> tables, Collection<ViewType> views) {
    this.name = name;
    this.description = description;
    this.folder = folder;
    if (types.size() > 0) {
      this.types = new TypesType();
      this.types.getType().addAll(types);
    } 
    
    if (routines.size() > 0) {
      this.routines = new RoutinesType();
      this.routines.getRoutine().addAll(routines);
    } 
    
    if (tables.size() > 0) {
      this.tables = new TablesType();
      this.tables.getTable().addAll(tables);
    } 
    
    if (views.size() > 0) {
      this.views = new ViewsType();
      this.views.getView().addAll(views);
    } 
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard22\ConvertableSiard22SchemaType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */