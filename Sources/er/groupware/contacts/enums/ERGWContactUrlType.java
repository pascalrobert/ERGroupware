package er.groupware.contacts.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.zimbra.common.mailbox.ContactConstants;

public enum ERGWContactUrlType {

  HOME("Home","HOME",ContactConstants.A_homeURL),
  WORK("Work","WORK",ContactConstants.A_workURL),
  OTHER("Other","OTHER",ContactConstants.A_otherURL);  
  
  private String localizedDescription;
  private String rfc2445Value;
  private String zimbraValue;

  private static final Map<String,ERGWContactUrlType> zimbraLookup = new HashMap<String,ERGWContactUrlType>();
  private static final Map<String,ERGWContactUrlType> rfc2445Lookup = new HashMap<String,ERGWContactUrlType>();
  
  static {
    for(ERGWContactUrlType s : EnumSet.allOf(ERGWContactUrlType.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  private ERGWContactUrlType(String localizedDescription, String rfc2445Value, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }

  public static ERGWContactUrlType getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
  // TODO: should be rfc2426, or just rfc
  public static ERGWContactUrlType getByRFC2445Value(String zimbraValue) { 
    return rfc2445Lookup.get(zimbraValue); 
  }

  public String localizedDescription() {
    return localizedDescription;
  }

  public String zimbraValue() {
    return zimbraValue;
  }

  public String rfc2445Value() {
    return rfc2445Value;
  }

  public Object ewsValue() {
    // TODO Auto-generated method stub
    return null;
  }
  
}
