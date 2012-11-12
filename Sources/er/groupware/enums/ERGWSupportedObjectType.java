package er.groupware.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.connector.dav.enums.MediaType;

public enum ERGWSupportedObjectType {

  ICALENDAR_V2("Calendar and tasks", MediaType.ICALENDAR_2_0),
  VCARD_V4("Contacts", MediaType.VCARD_4_0);
  
  private String localizedDescription;
  private MediaType rfc2445Value;

  private static final Map<MediaType,ERGWSupportedObjectType> rfc2445Lookup = new HashMap<MediaType,ERGWSupportedObjectType>();

  static {
    for(ERGWSupportedObjectType s : EnumSet.allOf(ERGWSupportedObjectType.class)) {
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  private ERGWSupportedObjectType(String localizedDescription, MediaType rfc2445Value) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
  }
  
  public static ERGWSupportedObjectType getByRFC2445Value(MediaType zimbraValue) { 
    return rfc2445Lookup.get(zimbraValue); 
  }

  public MediaType rfc2445Value() {
    return rfc2445Value;
  }
  
  public String localizedDescription() {
    return localizedDescription;
  }
  
}
