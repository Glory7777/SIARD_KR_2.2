package ch.admin.bar.siard2.api.convertableSiardArchive.Siard22;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21Archive;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21AttributeType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21ColumnType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21FieldType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21ForeignKeyTypes;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21Parameter;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21PriviligeType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21Routine;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21SchemaType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21Table;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21TriggerType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21TypeType;
import ch.admin.bar.siard2.api.convertableSiardArchive.Siard21.ConvertableSiard21ViewType;
import java.util.List;
import java.util.function.Function;

public class Siard21ToSiard22Transformer implements Siard21Transformer {
  public SiardArchive visit(ConvertableSiard21Archive siard21Archive) {
    return new ConvertableSiard22Archive("2.2", siard21Archive
        .getDbname(), siard21Archive
        .getDescription(), siard21Archive
        .getArchiver(), siard21Archive
        .getArchiverContact(), siard21Archive
        .getDataOwner(), siard21Archive
        .getDataOriginTimespan(), siard21Archive
        .getLobFolder(), siard21Archive
        .getProducerApplication(), siard21Archive
        .getArchivalDate(), siard21Archive
        .getClientMachine(), siard21Archive
        .getDatabaseProduct(), siard21Archive
        .getConnection(), siard21Archive
        .getDatabaseUser(), 
        convertElements(siard21Archive.getMessageDigest(), md -> md, ConvertableSiard21MessageDigestType::new, md -> md.accept(this)), 


        
        convertElements(siard21Archive.getSchemas(), SchemasType::getSchema, ConvertableSiard21SchemaType::new, s -> s.accept(this)), 


        
        convertElements(siard21Archive.getUsers(), UsersType::getUser, ConvertableSiard21UserType::new, u -> u.accept(this)), 


        
        convertElements(siard21Archive.getRoles(), RolesType::getRole, ConvertableSiard21RoleType::new, r -> r.accept(this)), 


        
        convertElements(siard21Archive.getPrivileges(), PrivilegesType::getPrivilege, ConvertableSiard21PriviligeType::new, p -> p.accept(this)));
  }




  
  public MessageDigestType visit(ConvertableSiard21MessageDigestType messageDigest) {
    return new ConvertableSiard22MessageDigestType(messageDigest.getDigest(), 
        safeConvert(messageDigest.getDigestType(), md -> md.value(), DigestTypeType::fromValue));
  }



  
  public ConvertableSiard22SchemaType visit(ConvertableSiard21SchemaType siard21Schema) {
    return new ConvertableSiard22SchemaType(siard21Schema.getName(), siard21Schema
        .getDescription(), siard21Schema
        .getFolder(), 
        convertElements(siard21Schema.getTypes(), TypesType::getType, ConvertableSiard21TypeType::new, t -> t.accept(this)), 


        
        convertElements(siard21Schema.getRoutines(), RoutinesType::getRoutine, ConvertableSiard21Routine::new, r -> r.accept(this)), 


        
        convertElements(siard21Schema.getTables(), TablesType::getTable, ConvertableSiard21Table::new, t1 -> t1.accept(this)), 


        
        convertElements(siard21Schema.getViews(), ViewsType::getView, ConvertableSiard21ViewType::new, v -> v.accept(this)));
  }





  
  public ConvertableSiard22TypeType visit(ConvertableSiard21TypeType siard21Type) {
    return new ConvertableSiard22TypeType(siard21Type.getName(), siard21Type
        .getDescription(), siard21Type
        .getBase(), siard21Type
        .getUnderType(), siard21Type
        .getUnderSchema(), siard21Type
        .isFinal(), siard21Type
        .isInstantiable(), 
        safeConvert(siard21Type.getCategory(), c -> c.value(), CategoryType::fromValue), 

        
        convertElements(siard21Type.getAttributes(), AttributesType::getAttribute, ConvertableSiard21AttributeType::new, a -> a.accept(this)));
  }




  
  public ConvertableSiard22AttributeType visit(ConvertableSiard21AttributeType siard21Attribute) {
    return new ConvertableSiard22AttributeType(siard21Attribute.getName(), siard21Attribute
        .getDescription(), siard21Attribute
        .getType(), siard21Attribute
        .getTypeSchema(), siard21Attribute
        .getTypeName(), siard21Attribute
        .getCardinality(), siard21Attribute
        .getDefaultValue(), siard21Attribute
        .isNullable(), siard21Attribute
        .getTypeOriginal());
  }

  
  public ConvertableSiard22RoutineType visit(ConvertableSiard21Routine siard21Routine) {
    return new ConvertableSiard22RoutineType(siard21Routine.getName(), siard21Routine
        .getDescription(), siard21Routine
        .getBody(), siard21Routine
        .getCharacteristic(), siard21Routine
        .getReturnType(), siard21Routine
        .getSpecificName(), siard21Routine
        .getSource(), 
        convertElements(siard21Routine.getParameters(), ParametersType::getParameter, ConvertableSiard21Parameter::new, p -> p.accept(this)));
  }





  
  public ConvertablSiard22Parameter visit(ConvertableSiard21Parameter siard21Parameter) {
    return new ConvertablSiard22Parameter(siard21Parameter.getName(), siard21Parameter
        .getDescription(), siard21Parameter
        .getCardinality(), siard21Parameter
        .getMode(), siard21Parameter
        .getType(), siard21Parameter
        .getTypeName(), siard21Parameter
        .getTypeSchema(), siard21Parameter
        .getTypeOriginal());
  }

  
  public ConvertableSiard22TableType visit(ConvertableSiard21Table siard21Table) {
    return new ConvertableSiard22TableType(siard21Table.getName(), siard21Table
        .getDescription(), siard21Table
        .getFolder(), siard21Table
        .getRows(), 
        convertPrimaryKey(siard21Table.getPrimaryKey()), 
        convertElements(siard21Table.getColumns(), ColumnsType::getColumn, ConvertableSiard21ColumnType::new, c -> c.accept(this)), 


        
        convertElements(siard21Table.getCandidateKeys(), CandidateKeysType::getCandidateKey, ConvertableSiard21UniqueKeyType::new, ck -> ck.accept(this)), 


        
        convertElements(siard21Table.getCheckConstraints(), CheckConstraintsType::getCheckConstraint, ConvertableSiard21CheckConstraintType::new, c1 -> c1.accept(this)), 


        
        convertElements(siard21Table.getForeignKeys(), ForeignKeysType::getForeignKey, ConvertableSiard21ForeignKeyTypes::new, f -> f.accept(this)), 


        
        convertElements(siard21Table.getTriggers(), TriggersType::getTrigger, ConvertableSiard21TriggerType::new, t -> t.accept(this)));
  }





  
  public ConvertableSiard22UniqueKeyType visit(ConvertableSiard21UniqueKeyType siard21UniqueKey) {
    return new ConvertableSiard22UniqueKeyType(siard21UniqueKey.getName(), siard21UniqueKey
        .getDescription(), siard21UniqueKey
        .getColumn());
  }

  
  public ConvertableSiard22CheckConstraintType visit(ConvertableSiard21CheckConstraintType siard21CheckConstraint) {
    return new ConvertableSiard22CheckConstraintType(siard21CheckConstraint.getName(), siard21CheckConstraint
        .getDescription(), siard21CheckConstraint
        .getCondition());
  }

  
  public ConvertableSiard22ForeignKeyTypes visit(ConvertableSiard21ForeignKeyTypes siard21ForeignKey) {
    return new ConvertableSiard22ForeignKeyTypes(siard21ForeignKey.getName(), siard21ForeignKey
        .getDescription(), 
        safeConvert(siard21ForeignKey.getMatchType(), MatchTypeType::value, MatchTypeType::fromValue), 

        
        safeConvert(siard21ForeignKey.getDeleteAction(), ReferentialActionType::value, ReferentialActionType::fromValue), 

        
        safeConvert(siard21ForeignKey.getUpdateAction(), ReferentialActionType::value, ReferentialActionType::fromValue), siard21ForeignKey

        
        .getReferencedSchema(), siard21ForeignKey
        .getReferencedTable(), 
        convertElements(siard21ForeignKey.getReference(), r -> r, ConvertableSiard21ReferenceType::new, r -> r.accept(this)));
  }




  
  public ConvertableSiard22ReferenceType visit(ConvertableSiard21ReferenceType siard21Reference) {
    return new ConvertableSiard22ReferenceType(siard21Reference.getReferenced(), siard21Reference.getColumn());
  }

  
  public ConvertableSiard22ViewType visit(ConvertableSiard21ViewType siard21View) {
    return new ConvertableSiard22ViewType(siard21View.getName(), siard21View
        .getDescription(), siard21View
        .getRows(), siard21View
        .getQuery(), siard21View
        .getQueryOriginal(), 
        convertElements(siard21View.getColumns(), ColumnsType::getColumn, ConvertableSiard21ColumnType::new, c -> c.accept(this)));
  }





  
  public ConvertableSiard22ColumnType visit(ConvertableSiard21ColumnType siard21Column) {
    return new ConvertableSiard22ColumnType(siard21Column.getName(), siard21Column
        .getDescription(), siard21Column
        .getDefaultValue(), siard21Column
        .getLobFolder(), siard21Column
        .getMimeType(), siard21Column
        .getType(), siard21Column
        .getTypeName(), siard21Column
        .getTypeSchema(), siard21Column
        .getTypeOriginal(), siard21Column
        .getCardinality(), siard21Column
        .isNullable(), 
        convertElements(siard21Column.getFields(), FieldsType::getField, ConvertableSiard21FieldType::new, field -> field.accept(this)));
  }





  
  public ConvertableSiard22FieldType visit(ConvertableSiard21FieldType siard21Field) {
    return new ConvertableSiard22FieldType(siard21Field.getName(), siard21Field
        .getDescription(), siard21Field
        .getMimeType(), siard21Field
        .getLobFolder(), 
        convertElements(siard21Field.getFields(), FieldsType::getField, ConvertableSiard21FieldType::new, field -> field.accept(this)));
  }




  
  public ConvertableSiard22UserType visit(ConvertableSiard21UserType siard21User) {
    return new ConvertableSiard22UserType(siard21User.getName(), siard21User.getDescription());
  }

  
  public ConvertableSiard22RoleType visit(ConvertableSiard21RoleType role) {
    return new ConvertableSiard22RoleType(role.getName(), role.getDescription(), role.getAdmin());
  }

  
  public ConvertableSiard22PriviligeType visit(ConvertableSiard21PriviligeType privilige) {
    return new ConvertableSiard22PriviligeType(privilige.getType(), privilige
        .getDescription(), privilige
        .getGrantee(), privilige
        .getGrantor(), privilige
        .getObject(), 
        safeConvert(privilige.getOption(), PrivOptionType::value, PrivOptionType::fromValue));
  }



  
  public ConvertableSiard22TriggerType visit(ConvertableSiard21TriggerType trigger) {
    return new ConvertableSiard22TriggerType(trigger.getName(), trigger
        .getDescription(), trigger
        .getAliasList(), trigger
        .getTriggeredAction(), trigger
        .getTriggerEvent(), 
        safeConvert(trigger.getActionTime(), t -> t.value(), ActionTimeType::fromValue));
  }





















  
  private <I, T, V, R> List<R> convertElements(I container, Function<I, List<T>> getElements, Function<T, V> make, Function<V, R> accept) {
    if (container == null) return Collections.emptyList(); 
    return (List<R>)((List<T>)getElements.apply(container)).stream().<V>map(make).<R>map(accept).collect(Collectors.toList());
  }











  
  private <I, R> R safeConvert(I thing, Function<I, String> toValue, Function<String, R> from) {
    if (thing == null) return null; 
    return from.apply(toValue.apply(thing));
  }

  
  private ConvertableSiard22UniqueKeyType convertPrimaryKey(UniqueKeyType primaryKey) {
    if (primaryKey == null) return null; 
    return (new ConvertableSiard21UniqueKeyType(primaryKey)).accept(this);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard22\Siard21ToSiard22Transformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */