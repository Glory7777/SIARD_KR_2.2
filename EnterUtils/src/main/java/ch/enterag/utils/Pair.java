package ch.enterag.utils;

import java.util.Objects;
















public class Pair<CLFIRST, CLSECOND>
{
  private CLFIRST _f = null; public CLFIRST getFirst() {
    return this._f;
  } private CLSECOND _s = null; public CLSECOND getSecond() {
    return this._s;
  }





  
  public Pair(CLFIRST f, CLSECOND s) {
    this._f = f;
    this._s = s;
  }





  
  public int hashCode() {
    return Objects.hash(new Object[] { this._f, this._s });
  }







  
  public static boolean areEqual(Object o1, Object o2) {
    boolean bEqual = false;
    if (o1 != null) {
      
      if (o1.equals(o2)) {
        bEqual = true;
      }
    } else if (o2 == null) {
      bEqual = true;
    }  return bEqual;
  }









  
  public static boolean areEqual(Pair<?, ?> p1, Pair<?, ?> p2) {
    boolean bEqual = false;
    if (p1 != null) {
      
      if (p2 != null) {
        bEqual = (areEqual(p1._f, p2._f) && areEqual(p1._s, p2._s));
      }
    } else if (p2 == null) {
      bEqual = true;
    }  return bEqual;
  }




  
  public boolean equals(Object o) {
    boolean bEqual = false;
    if (o != null && o instanceof Pair) {
      
      Pair<?, ?> pair = (Pair<?, ?>)o;
      bEqual = areEqual(this, pair);
    } 
    return bEqual;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\Pair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */