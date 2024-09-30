package ch.enterag.utils.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


















public abstract class Glue
{
  public static Object getPrivate(Object oOwner, String sFieldName) {
    Object oResult = null;

    
    try { Field field = null;
      Class<?> cls = null;
      if (oOwner.getClass() != Class.class) {
        cls = oOwner.getClass();
      } else {
        
        cls = (Class)oOwner;
        oOwner = null;
      } 
      for (; field == null && cls != null; cls = cls.getSuperclass()) {
        try {
          field = cls.getDeclaredField(sFieldName);
        } catch (NoSuchFieldException noSuchFieldException) {}
      } 
      if (field != null) {
        
        field.setAccessible(true);
        oResult = field.get(oOwner);
      } else {
        
        throw new IllegalArgumentException("Field " + sFieldName + " not found!");
      }  }
    catch (IllegalAccessException iae) { throw new IllegalArgumentException(iae.getClass().getName() + ": " + iae.getMessage()); }
     return oResult;
  }












  
  public static void setPrivate(Object oOwner, String sFieldName, Object oValue) {
    
    try { Field field = null;
      Class<?> cls = null;
      if (oOwner.getClass() != Class.class) {
        cls = oOwner.getClass();
      } else {
        
        cls = (Class)oOwner;
        oOwner = null;
      } 
      for (; field == null && cls != null; cls = cls.getSuperclass()) {
        try {
          field = cls.getDeclaredField(sFieldName);
        } catch (NoSuchFieldException noSuchFieldException) {}
      } 
      if (field != null) {
        
        field.setAccessible(true);
        if ((field.getModifiers() & 0x10) != 0) {
          
          try {
            
            Field modifiersField = field.getClass().getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
          }
          catch (NoSuchFieldException noSuchFieldException) {}
        }
        field.set(oOwner, oValue);
      } else {
        
        throw new IllegalArgumentException("Field " + sFieldName + " not found!");
      }  }
    catch (IllegalAccessException iae) { throw new IllegalArgumentException(iae.getClass().getName() + ": " + iae.getMessage()); }
  
  }






  
  public static Constructor<?> getConstructor(Class<?> cls, Class<?>... vaTypes) {
    Constructor<?> constructor = null;
    
    try {
      constructor = cls.getDeclaredConstructor(vaTypes);
      constructor.setAccessible(true);
    }
    catch (NoSuchMethodException noSuchMethodException) {}
    return constructor;
  }









  
  public static Object invokePrivate(Object oInstance, String sMethodName, Class<?>[] aoTypes, Object[] aoArguments) {
    Object oResult = null;

    
    try { Method method = oInstance.getClass().getDeclaredMethod(sMethodName, aoTypes);
      method.setAccessible(true);
      oResult = method.invoke(oInstance, aoArguments); }
    catch (NoSuchMethodException nsme)
    { throw new IllegalArgumentException("Method " + sMethodName + " does not exist for this object and argument types!"); }
    catch (InvocationTargetException ite) { throw new IllegalArgumentException(ite.getClass().getName() + ": " + ite.getMessage()); }
    catch (IllegalAccessException iae) { throw new IllegalArgumentException(iae.getClass().getName() + ": " + iae.getMessage()); }
     return oResult;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\enterutils.jar!\ch\entera\\utils\reflect\Glue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */