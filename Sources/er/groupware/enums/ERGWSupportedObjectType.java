package er.groupware.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import zswi.objects.dav.enums.SupportedData;

public enum ERGWSupportedObjectType {

  ICALENDAR_V2("Calendar and tasks", SupportedData.ICALENDAR_2_0),
  VCARD_V3("Contacts", SupportedData.VCARD_3_0);
  
  private String localizedDescription;
  private SupportedData rfc2445Value;

  private static final Map<SupportedData,ERGWSupportedObjectType> rfc2445Lookup = new HashMap<SupportedData,ERGWSupportedObjectType>();

  static {
    for(ERGWSupportedObjectType s : EnumSet.allOf(ERGWSupportedObjectType.class)) {
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  private ERGWSupportedObjectType(String localizedDescription, SupportedData rfc2445Value) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
  }
  
  public static ERGWSupportedObjectType getByRFC2445Value(SupportedData zimbraValue) { 
    return rfc2445Lookup.get(zimbraValue); 
  }

  public SupportedData rfc2445Value() {
    return rfc2445Value;
  }
  
  public String localizedDescription() {
    return localizedDescription;
  }
  
}
