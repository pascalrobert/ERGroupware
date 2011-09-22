package er.groupware.calendar.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.parameter.Role;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.zclient.ZInvite.ZRole;

import er.extensions.eof.ERXKey;

public enum AttendeeRole implements ICalendarProperty {

  CHAIR("Meeting Leader", Role.CHAIR, ZRole.CHA),
  REQ_PARTICIPANT("Requis", Role.REQ_PARTICIPANT, ZRole.REQ),
  OPT_PARTICIPANT("Optionnel", Role.OPT_PARTICIPANT, ZRole.OPT),
  NON_PARTICIPANT("Non participant", Role.NON_PARTICIPANT, ZRole.NON);

  private String description;
  private ZRole zimbraValue;
  private Role rfc2445Value;

  public static final ERXKey<String> DESCRIPTION = new ERXKey<String>("description");

  private AttendeeRole(String description, Role rfc2445Value, ZRole zimbraValue) {
    this.description = description;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }
  
  private static final Map<ZRole,AttendeeRole> zimbraLookup = new HashMap<ZRole,AttendeeRole>();
  private static final Map<Role,AttendeeRole> rfc2445Lookup = new HashMap<Role,AttendeeRole>();
  
  static {
    for(AttendeeRole s : EnumSet.allOf(AttendeeRole.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }

  public String localizedDescription() {
    return description;
  }

  public ZRole zimbraValue() {
    return zimbraValue;
  }

  public static NSArray<AttendeeRole> roles() {
    return new NSArray<AttendeeRole>(AttendeeRole.values());
  }

  private AttendeeRole() {
  }

  public Role rfc2445Value() {
    return rfc2445Value;
  }
  
  public static AttendeeRole getByZimbraValue(ZRole zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }

}
