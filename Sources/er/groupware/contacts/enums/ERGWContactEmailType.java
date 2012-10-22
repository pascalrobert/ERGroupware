package er.groupware.contacts.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.zimbra.common.mailbox.ContactConstants;

import er.groupware.calendar.enums.ERGWICalendarProperty;

public enum ERGWContactEmailType implements ERGWICalendarProperty {

  HOME("Home","HOME",ContactConstants.A_email),
  WORK("Work","WORK",ContactConstants.A_email2),
  OTHER("Other","OTHER",ContactConstants.A_email3);
  
  private String localizedDescription;
  private String rfc2445Value;
  private String zimbraValue;

  private static final Map<String,ERGWContactEmailType> zimbraLookup = new HashMap<String,ERGWContactEmailType>();
  private static final Map<String,ERGWContactEmailType> rfc2445Lookup = new HashMap<String,ERGWContactEmailType>();
  
  static {
    for(ERGWContactEmailType s : EnumSet.allOf(ERGWContactEmailType.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  private ERGWContactEmailType(String localizedDescription, String rfc2445Value, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
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
    return null;
  }
  
  public static ERGWContactEmailType getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
  // TODO: should be rfc2426, or just rfc
  public static ERGWContactEmailType getByRFC2445Value(String zimbraValue) { 
    return rfc2445Lookup.get(zimbraValue); 
  }
}
