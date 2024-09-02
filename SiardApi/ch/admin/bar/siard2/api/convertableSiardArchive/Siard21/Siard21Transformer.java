package ch.admin.bar.siard2.api.convertableSiardArchive.Siard21;

import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertablSiard22Parameter;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22AttributeType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22CheckConstraintType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22ColumnType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22FieldType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22ForeignKeyTypes;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22PriviligeType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22ReferenceType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22RoleType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22RoutineType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22SchemaType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22TableType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22TriggerType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22TypeType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22UniqueKeyType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22UserType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard22.ConvertableSiard22ViewType;
import ch.admin.bar.siard2.api.generated.MessageDigestType;
import ch.admin.bar.siard2.api.generated.SiardArchive;

public interface Siard21Transformer {
  SiardArchive visit(ConvertableSiard21Archive paramConvertableSiard21Archive);
  
  MessageDigestType visit(ConvertableSiard21MessageDigestType paramConvertableSiard21MessageDigestType);
  
  ConvertableSiard22SchemaType visit(ConvertableSiard21SchemaType paramConvertableSiard21SchemaType);
  
  ConvertableSiard22TypeType visit(ConvertableSiard21TypeType paramConvertableSiard21TypeType);
  
  ConvertableSiard22AttributeType visit(ConvertableSiard21AttributeType paramConvertableSiard21AttributeType);
  
  ConvertableSiard22RoutineType visit(ConvertableSiard21Routine paramConvertableSiard21Routine);
  
  ConvertablSiard22Parameter visit(ConvertableSiard21Parameter paramConvertableSiard21Parameter);
  
  ConvertableSiard22TableType visit(ConvertableSiard21Table paramConvertableSiard21Table);
  
  ConvertableSiard22UniqueKeyType visit(ConvertableSiard21UniqueKeyType paramConvertableSiard21UniqueKeyType);
  
  ConvertableSiard22CheckConstraintType visit(ConvertableSiard21CheckConstraintType paramConvertableSiard21CheckConstraintType);
  
  ConvertableSiard22ForeignKeyTypes visit(ConvertableSiard21ForeignKeyTypes paramConvertableSiard21ForeignKeyTypes);
  
  ConvertableSiard22ReferenceType visit(ConvertableSiard21ReferenceType paramConvertableSiard21ReferenceType);
  
  ConvertableSiard22ViewType visit(ConvertableSiard21ViewType paramConvertableSiard21ViewType);
  
  ConvertableSiard22ColumnType visit(ConvertableSiard21ColumnType paramConvertableSiard21ColumnType);
  
  ConvertableSiard22FieldType visit(ConvertableSiard21FieldType paramConvertableSiard21FieldType);
  
  ConvertableSiard22UserType visit(ConvertableSiard21UserType paramConvertableSiard21UserType);
  
  ConvertableSiard22RoleType visit(ConvertableSiard21RoleType paramConvertableSiard21RoleType);
  
  ConvertableSiard22PriviligeType visit(ConvertableSiard21PriviligeType paramConvertableSiard21PriviligeType);
  
  ConvertableSiard22TriggerType visit(ConvertableSiard21TriggerType paramConvertableSiard21TriggerType);
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard21\Siard21Transformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */