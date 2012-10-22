package er.groupware.contacts.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.zimbra.common.mailbox.ContactConstants;

import er.groupware.calendar.enums.ERGWICalendarProperty;

public enum ERGWContactPhysicalAddressType implements ERGWICalendarProperty {

  HOME("Home","HOME",ContactConstants.A_homeAddress),
  WORK("Work","WORK",ContactConstants.A_workAddress),
  OTHER("Other","OTHER",ContactConstants.A_otherAddress);
 
  private String localizedDescription;
  private String rfc2445Value;
  private String zimbraValue;

  private static final Map<String,ERGWContactPhysicalAddressType> zimbraLookup = new HashMap<String,ERGWContactPhysicalAddressType>();
  private static final Map<String,ERGWContactPhysicalAddressType> rfc2445Lookup = new HashMap<String,ERGWContactPhysicalAddressType>();
  
  static {
    for(ERGWContactPhysicalAddressType s : EnumSet.allOf(ERGWContactPhysicalAddressType.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  private ERGWContactPhysicalAddressType(String localizedDescription, String rfc2445Value, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }

  public static ERGWContactPhysicalAddressType getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
  // TODO: should be rfc2426, or just rfc
  public static ERGWContactPhysicalAddressType getByRFC2445Value(String zimbraValue) { 
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
