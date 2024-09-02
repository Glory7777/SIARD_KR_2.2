package ch.admin.bar.siard2.api.meta;
import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.MetaData;
import ch.admin.bar.siard2.api.MetaPrivilege;
import ch.admin.bar.siard2.api.MetaRole;
import ch.admin.bar.siard2.api.MetaSchema;
import ch.admin.bar.siard2.api.MetaSearch;
import ch.admin.bar.siard2.api.MetaUser;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.generated.MessageDigestType;
import ch.admin.bar.siard2.api.generated.PrivilegeType;
import ch.admin.bar.siard2.api.generated.PrivilegesType;
import ch.admin.bar.siard2.api.generated.RoleType;
import ch.admin.bar.siard2.api.generated.RolesType;
import ch.admin.bar.siard2.api.generated.SchemaType;
import ch.admin.bar.siard2.api.generated.SchemasType;
import ch.admin.bar.siard2.api.generated.SiardArchive;
import ch.admin.bar.siard2.api.generated.UserType;
import ch.admin.bar.siard2.api.generated.UsersType;
import ch.enterag.utils.DU;
import ch.enterag.utils.SU;
import ch.enterag.utils.xml.XU;
import java.io.IOException;
import java.net.URI;
import java.util.GregorianCalendar;
import java.util.Objects;

public class MetaDataImpl extends MetaSearchImpl implements MetaData {
  private static DU _du = DU.getInstance("en", "yyyy-MM-dd"); public static final String _sURI_SCHEME_FILE = "file";
  private static ObjectFactory _of = new ObjectFactory();
  private Map<String, MetaUser> _mapMetaUsers = new HashMap<>();
  private Map<String, MetaRole> _mapMetaRoles = new HashMap<>();
  private List<MetaPrivilege> _listMetaPrivileges = new ArrayList<>();
  
  private Archive _archive = null;
  
  public Archive getArchive() {
    return this._archive; } private ArchiveImpl getArchiveImpl() {
    return (ArchiveImpl)getArchive();
  }
  private SiardArchive _sa = null;

  
  public SiardArchive getSiardArchive() throws IOException {
    for (int iSchema = 0; iSchema < getMetaSchemas(); iSchema++) {
      
      MetaSchema ms = getMetaSchema(iSchema);
      if (ms != null)
        ((MetaSchemaImpl)ms).getSchemaType(); 
    } 
    return this._sa;
  }
  
  private SiardArchive _saTemplate = null; public SiardArchive getTemplate() {
    return this._saTemplate;
  }



  
  public void setTemplate(MetaData md) throws IOException {
    if (md != null) {
      setTemplate(((MetaDataImpl)md).getSiardArchive());
    }
  }







  
  public void setTemplate(SiardArchive saTemplate) throws IOException {
    this._saTemplate = saTemplate;
    if (!SU.isNotEmpty(getDbName()) || getDbName().equals("(...)"))
      setDbName(XU.fromXml(this._saTemplate.getDbname())); 
    if (!SU.isNotEmpty(getDescription()))
      setDescription(XU.fromXml(this._saTemplate.getDescription())); 
    if (!SU.isNotEmpty(getArchiver()))
      setArchiver(XU.fromXml(this._saTemplate.getArchiver())); 
    if (!SU.isNotEmpty(getArchiverContact()))
      setArchiverContact(XU.fromXml(this._saTemplate.getArchiverContact())); 
    if (!SU.isNotEmpty(getDataOwner()) || getDataOwner().equals("(...)"))
      setDataOwner(XU.fromXml(this._saTemplate.getDataOwner())); 
    if (!SU.isNotEmpty(getDataOriginTimespan()) || getDataOriginTimespan().equals("(...)"))
      setDataOriginTimespan(XU.fromXml(this._saTemplate.getDataOriginTimespan())); 
    if (getLobFolder() == null && SU.isNotEmpty(this._saTemplate.getLobFolder()))
      setLobFolder(URI.create(XU.fromXml(this._saTemplate.getLobFolder()))); 
    SchemasType sts = this._saTemplate.getSchemas();
    if (sts != null)
    {
      for (int iSchema = 0; iSchema < sts.getSchema().size(); iSchema++) {
        
        SchemaType stTemplate = sts.getSchema().get(iSchema);
        String sName = XU.fromXml(stTemplate.getName());
        MetaSchema ms = getMetaSchema(sName);
        if (ms != null) {
          
          MetaSchemaImpl msi = (MetaSchemaImpl)ms;
          msi.setTemplate(stTemplate);
        } 
      } 
    }
    UsersType uts = this._saTemplate.getUsers();
    if (uts != null)
    {
      for (int iUser = 0; iUser < uts.getUser().size(); iUser++) {
        
        UserType utTemplate = uts.getUser().get(iUser);
        String sName = XU.fromXml(utTemplate.getName());
        MetaUser mu = getMetaUser(sName);
        if (mu != null) {
          
          MetaUserImpl mui = (MetaUserImpl)mu;
          mui.setTemplate(utTemplate);
        } 
      } 
    }
    RolesType rts = this._saTemplate.getRoles();
    if (rts != null)
    {
      for (int iRole = 0; iRole < rts.getRole().size(); iRole++) {
        
        RoleType rtTemplate = rts.getRole().get(iRole);
        String sName = XU.fromXml(rtTemplate.getName());
        MetaRole mr = getMetaRole(sName);
        if (mr != null) {
          
          MetaRoleImpl mri = (MetaRoleImpl)mr;
          mri.setTemplate(rtTemplate);
        } 
      } 
    }
    PrivilegesType pts = this._saTemplate.getPrivileges();
    if (pts != null)
    {
      for (int iPrivilege = 0; iPrivilege < pts.getPrivilege().size(); iPrivilege++) {
        
        PrivilegeType ptTemplate = pts.getPrivilege().get(iPrivilege);
        String sType = XU.fromXml(ptTemplate.getType());
        String sObject = XU.fromXml(ptTemplate.getObject());
        String sGrantor = XU.fromXml(ptTemplate.getGrantor());
        String sGrantee = XU.fromXml(ptTemplate.getGrantee());
        MetaPrivilege mp = getMetaPrivilege(sType, sObject, sGrantor, sGrantee);
        if (mp != null) {
          
          MetaPrivilegeImpl mpi = (MetaPrivilegeImpl)mp;
          mpi.setTemplate(ptTemplate);
        } 
      } 
    }
  }





  
  public static SiardArchive createSiardArchive() {
    SiardArchive sa = _of.createSiardArchive();
    sa.setVersion(XU.toXml("2.2"));
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTimeZone(TZ.getUtcTimeZone());
    sa.setArchivalDate(_du.toXmlGregorianCalendar(gc));
    sa.setSchemas(_of.createSchemasType());
    sa.setUsers(_of.createUsersType());
    return sa;
  }








  
  private MetaDataImpl(Archive archive, SiardArchive sa) throws IOException {
    this._archive = archive;
    this._sa = sa;

    
    UsersType uts = this._sa.getUsers();
    if (uts != null)
    {
      for (int iUser = 0; iUser < uts.getUser().size(); iUser++) {
        
        UserType ut = uts.getUser().get(iUser);
        MetaUser mu = MetaUserImpl.newInstance(this, ut);
        this._mapMetaUsers.put(XU.fromXml(ut.getName()), mu);
      } 
    }
    
    RolesType rts = this._sa.getRoles();
    if (rts != null)
    {
      for (int iRole = 0; iRole < rts.getRole().size(); iRole++) {
        
        RoleType rt = rts.getRole().get(iRole);
        MetaRole mr = MetaRoleImpl.newInstance(this, rt);
        this._mapMetaRoles.put(XU.fromXml(rt.getName()), mr);
      } 
    }
    
    PrivilegesType pts = this._sa.getPrivileges();
    if (pts != null)
    {
      for (int iPrivilege = 0; iPrivilege < pts.getPrivilege().size(); iPrivilege++) {
        
        PrivilegeType pt = pts.getPrivilege().get(iPrivilege);
        MetaPrivilege mp = MetaPrivilegeImpl.newInstance(this, pt);
        this._listMetaPrivileges.add(mp);
      } 
    }
  }









  
  public static MetaData newInstance(Archive archive, SiardArchive sa) throws IOException {
    return new MetaDataImpl(archive, sa);
  }




  
  public String getVersion() {
    return ((ArchiveImpl)getArchive()).getPreviousMetaDataVersion();
  }




  
  public void setDbName(String sDbname) {
    if (getArchiveImpl().isMetaDataDifferent(getDbName(), sDbname))
      this._sa.setDbname(XU.toXml(sDbname)); 
  }
  
  public String getDbName() {
    return XU.fromXml(this._sa.getDbname());
  }




  
  public void setDescription(String sDescription) {
    if (getArchiveImpl().isMetaDataDifferent(getDescription(), sDescription))
      this._sa.setDescription(XU.toXml(sDescription)); 
  }
  
  public String getDescription() {
    return XU.fromXml(this._sa.getDescription());
  }



  
  public void setArchiver(String sArchiver) {
    if (getArchiveImpl().isMetaDataDifferent(getArchiver(), sArchiver))
      this._sa.setArchiver(XU.toXml(sArchiver)); 
  }
  
  public String getArchiver() {
    return XU.fromXml(this._sa.getArchiver());
  }



  
  public void setArchiverContact(String sArchiverContact) {
    if (getArchiveImpl().isMetaDataDifferent(getArchiverContact(), sArchiverContact))
      this._sa.setArchiverContact(XU.toXml(sArchiverContact)); 
  }
  
  public String getArchiverContact() {
    return XU.fromXml(this._sa.getArchiverContact());
  }



  
  public void setDataOwner(String sDataOwner) {
    if (getArchiveImpl().isMetaDataDifferent(getDataOwner(), sDataOwner))
      this._sa.setDataOwner(XU.toXml(sDataOwner)); 
  }
  
  public String getDataOwner() {
    return XU.fromXml(this._sa.getDataOwner());
  }



  
  public void setDataOriginTimespan(String sDataOriginTimespan) {
    if (getArchiveImpl().isMetaDataDifferent(getDataOriginTimespan(), sDataOriginTimespan))
      this._sa.setDataOriginTimespan(XU.toXml(sDataOriginTimespan)); 
  }
  
  public String getDataOriginTimespan() {
    return XU.fromXml(this._sa.getDataOriginTimespan());
  }




  
  public void setLobFolder(URI uriLobFolder) throws IOException {
    boolean bMayBeSet = false;
    if (getLobFolder() == null) {
      
      if (getArchive().isEmpty()) {
        bMayBeSet = true;
      }
    } else {
      bMayBeSet = true;
    }  if (bMayBeSet) {
      
      if (getArchiveImpl().isMetaDataDifferent(getLobFolder(), uriLobFolder))
      {
        if (uriLobFolder != null) {
          
          if (uriLobFolder.getPath().endsWith("/")) {
            
            if (uriLobFolder.isAbsolute()) {
              
              if (uriLobFolder.getScheme() == null) {
                try {
                  uriLobFolder = new URI("file", "", uriLobFolder.getPath(), null);
                } catch (URISyntaxException uRISyntaxException) {}
              }
              if (!uriLobFolder.getScheme().equals("file")) {
                throw new IllegalArgumentException("Only URIs with scheme \"file\" allowed for LOB folder!");
              
              }
            }
            else if (!uriLobFolder.getPath().startsWith("../")) {
              throw new IllegalArgumentException("Relative LOB folder URIs must start with \"..\"!");
            } 
            this._sa.setLobFolder(XU.toXml(uriLobFolder.toString()));
          } else {
            
            throw new IllegalArgumentException("Path of LOB folder URI must denote a folder (end with \"/\")!");
          } 
        } else {
          throw new IllegalArgumentException("LOB folder URI must not be null!");
        } 
      }
    } else {
      throw new IOException("LOB folder value cannot be set!");
    } 
  }



  
  public URI getLobFolder() {
    URI uriLobFolder = null;
    String sLobFolder = XU.fromXml(this._sa.getLobFolder());
    if (sLobFolder != null) {
      try {
        uriLobFolder = new URI(sLobFolder);
      } catch (URISyntaxException uRISyntaxException) {}
    }
    return uriLobFolder;
  }






  
  public URI getAbsoluteUri(URI uriLobFolder) {
    URI uriAbsolute = null;
    if (!uriLobFolder.isAbsolute()) {
      
      if (uriLobFolder.getPath().startsWith("..")) {
        
        String sPathRelativeToSiard = uriLobFolder.getPath().substring(3);
        if (sPathRelativeToSiard.length() == 0) {
          sPathRelativeToSiard = "./";
        }
        try {
          uriLobFolder = new URI(sPathRelativeToSiard);
          URI uriResolver = getArchive().getFile().getAbsoluteFile().getParentFile().toURI();
          uriAbsolute = uriResolver.resolve(uriLobFolder);
        }
        catch (URISyntaxException uRISyntaxException) {}
      } else {
        
        throw new IllegalArgumentException("LOB folder is relative and does not start with \"../\"!");
      } 
    } else {
      uriAbsolute = uriLobFolder;
    }  return uriAbsolute;
  }




  
  public URI getAbsoluteLobFolder() {
    URI uriAbsolute = null;
    URI uriLobFolder = getLobFolder();
    if (uriLobFolder != null)
      uriAbsolute = getAbsoluteUri(uriLobFolder); 
    return uriAbsolute;
  }





  
  public void setProducerApplication(String sProducerApplication) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getProducerApplication(), sProducerApplication)) {
        this._sa.setProducerApplication(XU.toXml(sProducerApplication));
      }
    } else {
      throw new IOException("Producer application value cannot be set!");
    } 
  }
  public String getProducerApplication() {
    return XU.fromXml(this._sa.getProducerApplication());
  }
  
  public Calendar getArchivalDate() {
    return _du.toGregorianCalendar(this._sa.getArchivalDate());
  }
  
  public List<MessageDigestType> getMessageDigest() {
    return this._sa.getMessageDigest();
  }
  
  public void setMessageDigest(MessageDigestType md) {
    getArchiveImpl().isMetaDataDifferent(null, md);
    this._sa.getMessageDigest().clear();
    this._sa.getMessageDigest().add(md);
  }





  
  public void setClientMachine(String sClientMachine) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getClientMachine(), sClientMachine)) {
        this._sa.setClientMachine(XU.toXml(sClientMachine));
      }
    } else {
      throw new IOException("Client machine name cannot be set!");
    } 
  }
  public String getClientMachine() {
    return XU.fromXml(this._sa.getClientMachine());
  }




  
  public void setDatabaseProduct(String sDatabaseProduct) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getDatabaseProduct(), sDatabaseProduct)) {
        this._sa.setDatabaseProduct(XU.toXml(sDatabaseProduct));
      }
    } else {
      throw new IOException("Database product name cannot be set!");
    } 
  }
  public String getDatabaseProduct() {
    return XU.fromXml(this._sa.getDatabaseProduct());
  }




  
  public void setConnection(String sConnection) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getConnection(), sConnection)) {
        this._sa.setConnection(XU.toXml(sConnection));
      }
    } else {
      throw new IOException("Connection string cannot be set!");
    } 
  }
  public String getConnection() {
    return XU.fromXml(this._sa.getConnection());
  }




  
  public void setDatabaseUser(String sDatabaseUser) throws IOException {
    if (getArchive().canModifyPrimaryData()) {
      
      if (getArchiveImpl().isMetaDataDifferent(getDatabaseUser(), sDatabaseUser)) {
        this._sa.setDatabaseUser(XU.toXml(sDatabaseUser));
      }
    } else {
      throw new IOException("Database user name cannot be set!");
    } 
  }
  public String getDatabaseUser() {
    return XU.fromXml(this._sa.getDatabaseUser());
  }



  
  public int getMetaSchemas() {
    SchemasType sts = this._sa.getSchemas();
    int iMetaSchemas = sts.getSchema().size();
    return iMetaSchemas;
  }




  
  public MetaSchema getMetaSchema(int iSchema) {
    String sName = XU.fromXml(((SchemaType)this._sa.getSchemas().getSchema().get(iSchema)).getName());
    return getMetaSchema(sName);
  }




  
  public MetaSchema getMetaSchema(String sName) {
    MetaSchema ms = null;
    Schema schema = getArchive().getSchema(sName);
    if (schema != null)
      ms = schema.getMetaSchema(); 
    return ms;
  }




  
  public int getMetaUsers() {
    return this._mapMetaUsers.size();
  }




  
  public MetaUser getMetaUser(int iUser) {
    MetaUser mu = null;
    UsersType uts = this._sa.getUsers();
    if (uts != null) {
      
      UserType ut = uts.getUser().get(iUser);
      String sName = ut.getName();
      mu = getMetaUser(sName);
    } 
    return mu;
  }




  
  public MetaUser getMetaUser(String sName) {
    return this._mapMetaUsers.get(sName);
  }





  
  public MetaUser createMetaUser(String sName) throws IOException {
    MetaUser mu = null;
    if (getArchive().canModifyPrimaryData()) {
      
      if (getMetaUser(sName) == null) {
        
        UsersType uts = this._sa.getUsers();
        if (uts == null) {
          
          uts = _of.createUsersType();
          this._sa.setUsers(uts);
        } 
        UserType ut = _of.createUserType();
        ut.setName(XU.toXml(sName));
        uts.getUser().add(ut);
        mu = MetaUserImpl.newInstance(this, ut);
        this._mapMetaUsers.put(sName, mu);
        getArchiveImpl().isMetaDataDifferent(null, mu);
        if (this._saTemplate != null) {
          
          UsersType utsTemplate = this._saTemplate.getUsers();
          if (utsTemplate != null) {
            
            UserType utTemplate = null;
            for (int iUser = 0; utTemplate == null && iUser < utsTemplate.getUser().size(); iUser++) {
              
              UserType utTry = utsTemplate.getUser().get(iUser);
              if (sName.equals(utTry.getName()))
                utTemplate = utTry; 
            } 
            if (utTemplate != null) {
              
              MetaUserImpl mui = (MetaUserImpl)mu;
              mui.setTemplate(utTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one user with the same name allowed!");
      } 
    } else {
      throw new IOException("Users can only be created if archive is open for modification of primary data.");
    }  return mu;
  }




  
  public int getMetaRoles() {
    return this._mapMetaRoles.size();
  }




  
  public MetaRole getMetaRole(int iRole) {
    MetaRole mr = null;
    RolesType rts = this._sa.getRoles();
    if (rts != null) {
      
      RoleType rt = rts.getRole().get(iRole);
      String sName = rt.getName();
      mr = getMetaRole(sName);
    } 
    return mr;
  }




  
  public MetaRole getMetaRole(String sName) {
    return this._mapMetaRoles.get(sName);
  }





  
  public MetaRole createMetaRole(String sName, String sAdmin) throws IOException {
    MetaRole mr = null;
    if (getArchive().canModifyPrimaryData()) {
      
      if (getMetaRole(sName) == null) {
        
        RolesType rts = this._sa.getRoles();
        if (rts == null) {
          
          rts = _of.createRolesType();
          this._sa.setRoles(rts);
        } 
        RoleType rt = _of.createRoleType();
        rt.setName(XU.toXml(sName));
        rt.setAdmin(XU.toXml(sAdmin));
        rts.getRole().add(rt);
        mr = MetaRoleImpl.newInstance(this, rt);
        this._mapMetaRoles.put(sName, mr);
        getArchiveImpl().isMetaDataDifferent(null, mr);
        if (this._saTemplate != null) {
          
          RolesType rtsTemplate = this._saTemplate.getRoles();
          if (rtsTemplate != null) {
            
            RoleType rtTemplate = null;
            for (int iRole = 0; rtTemplate == null && iRole < rtsTemplate.getRole().size(); iRole++) {
              
              RoleType rtTry = rtsTemplate.getRole().get(iRole);
              if (sName.equals(rtTry.getName()))
                rtTemplate = rtTry; 
            } 
            if (rtTemplate != null) {
              
              MetaRoleImpl mri = (MetaRoleImpl)mr;
              mri.setTemplate(rtTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one role with the same name allowed!");
      } 
    } else {
      throw new IOException("Roles can only be created if archive is open for modification of primary data.");
    }  return mr;
  }




  
  public int getMetaPrivileges() {
    return this._listMetaPrivileges.size();
  }




  
  public MetaPrivilege getMetaPrivilege(int iIndex) {
    return this._listMetaPrivileges.get(iIndex);
  }




  
  public MetaPrivilege getMetaPrivilege(String sType, String sObject, String sGrantor, String sGrantee) {
    MetaPrivilege mp = null;
    for (int iPrivilege = 0; mp == null && iPrivilege < getMetaPrivileges(); iPrivilege++) {
      
      MetaPrivilege mpTry = getMetaPrivilege(iPrivilege);
      if (Objects.equals(sType, mpTry.getType()) && 
        Objects.equals(sObject, mpTry.getObject()) && 
        Objects.equals(sGrantor, mpTry.getGrantor()) && 
        Objects.equals(sGrantee, mpTry.getGrantee()))
        mp = mpTry; 
    } 
    return mp;
  }





  
  public MetaPrivilege createMetaPrivilege(String sType, String sObject, String sGrantor, String sGrantee) throws IOException {
    MetaPrivilege mp = null;
    if (getArchive().canModifyPrimaryData()) {
      
      if (getMetaPrivilege(sType, sObject, sGrantor, sGrantee) == null) {
        
        PrivilegesType pts = this._sa.getPrivileges();
        if (pts == null) {
          
          pts = _of.createPrivilegesType();
          this._sa.setPrivileges(pts);
        } 
        PrivilegeType pt = _of.createPrivilegeType();
        pt.setType(XU.toXml(sType));
        pt.setObject(XU.toXml(sObject));
        pt.setGrantor(XU.toXml(sGrantor));
        pt.setGrantee(XU.toXml(sGrantee));
        pts.getPrivilege().add(pt);
        mp = MetaPrivilegeImpl.newInstance(this, pt);
        this._listMetaPrivileges.add(mp);
        getArchiveImpl().isMetaDataDifferent(null, mp);
        if (this._saTemplate != null) {
          
          PrivilegesType ptsTemplate = this._saTemplate.getPrivileges();
          if (ptsTemplate != null) {
            
            PrivilegeType ptTemplate = null;
            for (int iPrivilege = 0; ptTemplate == null && iPrivilege < ptsTemplate.getPrivilege().size(); iPrivilege++) {
              
              PrivilegeType ptTry = ptsTemplate.getPrivilege().get(iPrivilege);
              if (Objects.equals(sType, ptTry.getType()) && 
                Objects.equals(sObject, ptTry.getObject()) && 
                Objects.equals(sGrantor, ptTry.getGrantor()) && 
                Objects.equals(sGrantee, ptTry.getGrantee()))
                ptTemplate = ptTry; 
            } 
            if (ptTemplate != null) {
              
              MetaPrivilegeImpl mpi = (MetaPrivilegeImpl)mp;
              mpi.setTemplate(ptTemplate);
            } 
          } 
        } 
      } else {
        
        throw new IOException("Only one privilege with the same type, object, grantor and grantee allowed!");
      } 
    } else {
      throw new IOException("Privileges can only be created if archive is open for modification of primary data.");
    }  return mp;
  }




  
  public boolean isValid() {
    boolean bValid = true;
    
    String metadataVersion = XU.fromXml(this._sa.getVersion());
    if (bValid && 
      !"2.2".equals(metadataVersion) && !"2.1".equals(metadataVersion))
    {
      bValid = false;
    }
    
    if (bValid && 
      !SU.isNotEmpty(this._sa.getDbname())) {
      bValid = false;
    }
    if (bValid && 
      !SU.isNotEmpty(this._sa.getDataOwner())) {
      bValid = false;
    }
    if (bValid && 
      !SU.isNotEmpty(this._sa.getDataOriginTimespan())) {
      bValid = false;
    }
    if (bValid && this._sa
      .getArchivalDate() == null) {
      bValid = false;
    }
    if (bValid && getMetaUsers() <= 0) {
      bValid = false;
    }
    if (bValid && getMetaSchemas() <= 0)
      bValid = false; 
    for (int iSchema = 0; bValid && iSchema < getMetaSchemas(); iSchema++) {
      
      MetaSchema ms = getMetaSchema(iSchema);
      if (ms == null) {
        bValid = false;
      } else if (!ms.isValid()) {
        bValid = false;
      } 
    }  return bValid;
  }





  
  protected MetaSearch[] getSubMetaSearches() throws IOException {
    MetaSearch[] ams = new MetaSearch[getMetaSchemas() + getMetaUsers() + getMetaRoles() + getMetaPrivileges()];
    for (int iSchema = 0; iSchema < getMetaSchemas(); iSchema++)
      ams[iSchema] = (MetaSearch)getMetaSchema(iSchema); 
    for (int iUser = 0; iUser < getMetaUsers(); iUser++)
      ams[getMetaSchemas() + iUser] = (MetaSearch)getMetaUser(iUser); 
    for (int iRole = 0; iRole < getMetaRoles(); iRole++)
      ams[getMetaSchemas() + getMetaUsers() + iRole] = (MetaSearch)getMetaRole(iRole); 
    for (int iPrivilege = 0; iPrivilege < getMetaPrivileges(); iPrivilege++)
      ams[getMetaSchemas() + getMetaUsers() + getMetaRoles() + iPrivilege] = (MetaSearch)getMetaPrivilege(iPrivilege); 
    return ams;
  }





  
  public String[] getSearchElements(DU du) throws IOException {
    return new String[] { 
        getVersion(), 
        getDbName(), 
        getDescription(), 
        getArchiver(), 
        getArchiverContact(), 
        getDataOwner(), 
        getDataOriginTimespan(), 
        (getLobFolder() == null) ? "" : getLobFolder().toString(), 
        getProducerApplication(), du
        .fromGregorianCalendar((GregorianCalendar)getArchivalDate()), 
        getClientMachine(), 
        getDatabaseProduct(), 
        getConnection(), 
        getDatabaseUser() };
  }









  
  public String toString() {
    return getArchive().getFile().getName();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaDataImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */