package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.property.Status;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.zclient.ZInvite.ZStatus;

import er.groupware.calendar.ERCalendarPrincipal;

public enum EventStatus implements IStatus, ICalendarProperty {

  TENTATIVE("Tentatif", Status.VEVENT_TENTATIVE, ZStatus.TENT),
  CANCELLED("Annulé", Status.VEVENT_CANCELLED, ZStatus.CANC),
  CONFIRMED("Confirmé", Status.VEVENT_CONFIRMED, ZStatus.CONF);
  
  private String localizedDescription;
  private Status statusObject;
  private ZStatus zimbraValue;

  private EventStatus(String description, Status rfc2445Value, ZStatus zimbraValue) {
    this.localizedDescription = description;
    this.statusObject = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }
    
  private static final Map<ZStatus,EventStatus> zimbraLookup = new HashMap<ZStatus,EventStatus>();
  private static final Map<Status,EventStatus> rfc2445Lookup = new HashMap<Status,EventStatus>();
  
  static {
    for(EventStatus s : EnumSet.allOf(EventStatus.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  public String localizedDescription() {
    return ERCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }
  
  public Status rfc2445Value() {
    return statusObject;
  }
  
  public ZStatus zimbraValue() {
    return zimbraValue;
  }
  
  public static NSArray<EventStatus> statuses() {
    return new NSArray<EventStatus>(EventStatus.values());
  }
  
  private EventStatus() {
  }
  
  public static EventStatus getByZimbraValue(ZStatus zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
}
