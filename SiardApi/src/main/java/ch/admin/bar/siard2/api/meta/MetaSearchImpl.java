package ch.admin.bar.siard2.api.meta;

import ch.admin.bar.siard2.api.MetaSearch;
import ch.enterag.utils.DU;
import ch.enterag.utils.logging.IndentLogger;
import java.io.IOException;

















public abstract class MetaSearchImpl
  implements MetaSearch
{
  private static final IndentLogger _il = IndentLogger.getIndentLogger(MetaSearch.class.getName());

  
  protected String _sFindString = null;

  
  public String getFindString() {
    return this._sFindString;
  }

  
  protected boolean _bMatchCase = false;
  
  protected int _iFoundElement = 0;




  
  public int getFoundElement() throws IOException {
    return this._iFoundElement;
  }




  
  public String getFoundString(DU du) throws IOException {
    return getSearchElements(du)[this._iFoundElement];
  }

  
  protected int _iFoundOffset = -1;

  
  public int getFoundOffset() {
    return this._iFoundOffset;
  }







  
  protected abstract String[] getSearchElements(DU paramDU) throws IOException;







  
  protected MetaSearch[] getSubMetaSearches() throws IOException {
    return new MetaSearch[0];
  }








  
  public void find(String sFindString, boolean bMatchCase) throws IOException {
    _il.enter(sFindString, String.valueOf(bMatchCase), getClass().getName());
    this._iFoundElement = 0;
    this._iFoundOffset = -1;
    this._bMatchCase = bMatchCase;
    if (bMatchCase) {
      this._sFindString = sFindString;
    } else {
      this._sFindString = sFindString.toLowerCase();
    } 
    MetaSearch[] amsSubMeta = getSubMetaSearches();
    for (int iSubMeta = 0; iSubMeta < amsSubMeta.length; iSubMeta++) {
      
      if (amsSubMeta[iSubMeta] != null)
        amsSubMeta[iSubMeta].find(this._sFindString, this._bMatchCase); 
    } 
    _il.exit();
  }







  
  public MetaSearch findNext(DU du) throws IOException {
    _il.enter(getClass().getName());
    MetaSearch msFind = null;
    if (this._sFindString != null) {
      
      _il.event("Find string: " + this._sFindString);
      
      this._iFoundOffset++;
      
      String[] asElement = getSearchElements(du);
      while (msFind == null && this._iFoundElement < asElement.length) {
        
        int iPos = -1;
        String sElement = asElement[this._iFoundElement];
        if (sElement != null) {
          
          if (this._bMatchCase) {
            iPos = sElement.indexOf(this._sFindString, this._iFoundOffset);
          } else {
            iPos = sElement.toLowerCase().indexOf(this._sFindString, this._iFoundOffset);
          }  if (iPos >= 0) {
            
            _il.event("Found in \"" + sElement + "\" " + this._iFoundElement + " at position " + iPos);
            msFind = this;
            this._iFoundOffset = iPos;
            
            continue;
          } 
          this._iFoundOffset = 0;
          this._iFoundElement++;
          
          continue;
        } 
        this._iFoundElement++;
      } 
      
      if (msFind == null) {
        
        this._iFoundOffset = -1;
        MetaSearch[] amsSubMeta = getSubMetaSearches();
        while (msFind == null && this._iFoundElement < asElement.length + amsSubMeta.length) {
          
          if (amsSubMeta[this._iFoundElement - asElement.length] != null)
            msFind = amsSubMeta[this._iFoundElement - asElement.length].findNext(du); 
          if (msFind == null) {
            this._iFoundElement++;
          }
        } 
      } 
      if (msFind == null) {
        this._sFindString = null;
      } else {
        _il.event("Element: " + this._iFoundElement + " / Offset: " + this._iFoundOffset);
      } 
    }  _il.exit(String.valueOf(msFind));
    return msFind;
  }




  
  public boolean canFindNext() {
    return (this._sFindString != null);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siardapi.jar!\ch\admin\bar\siard2\api\meta\MetaSearchImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */