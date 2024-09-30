package ch.admin.bar.siard2.api;

import ch.admin.bar.siard2.api.generated.MessageDigestType;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.List;

public interface MetaData extends MetaSearch {
  String sPLACE_HOLDER = "(...)";
  
  Archive getArchive();
  
  void setTemplate(MetaData paramMetaData) throws IOException;
  
  String getVersion();
  
  void setDbName(String paramString);
  
  String getDbName();
  
  void setDescription(String paramString);
  
  String getDescription();
  
  void setArchiver(String paramString);
  
  String getArchiver();
  
  void setArchiverContact(String paramString);
  
  String getArchiverContact();
  
  void setDataOwner(String paramString);
  
  String getDataOwner();
  
  void setDataOriginTimespan(String paramString);
  
  String getDataOriginTimespan();
  
  void setLobFolder(URI paramURI) throws IOException;
  
  URI getLobFolder();
  
  URI getAbsoluteLobFolder();
  
  void setProducerApplication(String paramString) throws IOException;
  
  String getProducerApplication();
  
  Calendar getArchivalDate();
  
  List<MessageDigestType> getMessageDigest();
  
  void setClientMachine(String paramString) throws IOException;
  
  String getClientMachine();
  
  void setDatabaseProduct(String paramString) throws IOException;
  
  String getDatabaseProduct();
  
  void setConnection(String paramString) throws IOException;
  
  String getConnection();
  
  void setDatabaseUser(String paramString) throws IOException;
  
  String getDatabaseUser();
  
  int getMetaSchemas();
  
  MetaSchema getMetaSchema(int paramInt);
  
  MetaSchema getMetaSchema(String paramString);
  
  int getMetaUsers();
  
  MetaUser getMetaUser(int paramInt);
  
  MetaUser getMetaUser(String paramString);
  
  MetaUser createMetaUser(String paramString) throws IOException;
  
  int getMetaRoles();
  
  MetaRole getMetaRole(int paramInt);
  
  MetaRole getMetaRole(String paramString);
  
  MetaRole createMetaRole(String paramString1, String paramString2) throws IOException;
  
  int getMetaPrivileges();
  
  MetaPrivilege getMetaPrivilege(int paramInt);
  
  MetaPrivilege getMetaPrivilege(String paramString1, String paramString2, String paramString3, String paramString4);
  
  MetaPrivilege createMetaPrivilege(String paramString1, String paramString2, String paramString3, String paramString4) throws IOException;
  
  boolean isValid();
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\MetaData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */