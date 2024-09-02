package ch.admin.bar.siard2.api;

import java.io.IOException;

public interface MetaField extends MetaValue {
  MetaColumn getParentMetaColumn();
  
  MetaField getParentMetaField();
  
  MetaAttribute getMetaAttribute() throws IOException;
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardapi.jar!\ch\admin\bar\siard2\api\MetaField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */