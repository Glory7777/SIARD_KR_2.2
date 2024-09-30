package ch.admin.bar.siard2.api.convertableSiardArchive.Siard22;

import ch.admin.bar.siard2.api.generated.MessageDigestType;
import ch.admin.bar.siard2.api.generated.PrivilegesType;
import ch.admin.bar.siard2.api.generated.RolesType;
import ch.admin.bar.siard2.api.generated.SchemasType;
import ch.admin.bar.siard2.api.generated.SiardArchive;
import ch.admin.bar.siard2.api.generated.old21.*;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;



public class ConvertableSiard22Archive extends SiardArchive {
  ConvertableSiard22Archive(String version,
                            String dbName,
                            String description,
                            String archiver,
                            String archiverContact,
                            String dataOwner,
                            String dataOriginTimespan,
                            String lobFolder,
                            String producerApplication,
                            XMLGregorianCalendar archivalDate,
                            String clientMachine,
                            String databaseProduct,
                            String connection,
                            String databaseUser,
                            List<MessageDigestType> messageDigests,
                            List<ch.admin.bar.siard2.api.generated.SchemaType> schemas,
                            List<ch.admin.bar.siard2.api.generated.UserType> users,
                            List<ch.admin.bar.siard2.api.generated.RoleType> roles,
                            List<ch.admin.bar.siard2.api.generated.PrivilegeType> privileges) {
    this.version = version;
    this.dbname = dbName;
    this.description = description;
    this.archiver = archiver;
    this.archiverContact = archiverContact;
    this.dataOwner = dataOwner;
    this.dataOriginTimespan = dataOriginTimespan;
    this.lobFolder = lobFolder;
    this.producerApplication = producerApplication;
    this.archivalDate = archivalDate;
    this.clientMachine = clientMachine;
    this.databaseProduct = databaseProduct;
    this.connection = connection;
    this.databaseUser = databaseUser;
    this.messageDigest = messageDigests;
    
    if (schemas.size() > 0) {
      this.schemas = new SchemasType();
      this.schemas.getSchema().addAll(schemas);
    } 
    
    if (users.size() > 0) {
      this.users = new ch.admin.bar.siard2.api.generated.UsersType();
      this.users.getUser().addAll(users);
    } 
    
    if (roles.size() > 0) {
      this.roles = new ch.admin.bar.siard2.api.generated.RolesType();
      this.roles.getRole().addAll(roles);
    } 
    
    if (privileges.size() > 0) {
      this.privileges = new ch.admin.bar.siard2.api.generated.PrivilegesType();
      this.privileges.getPrivilege().addAll(privileges);
    } 
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\convertableSiardArchive\Siard22\ConvertableSiard22Archive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */