package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.property.Status;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.zclient.ZInvite.ZStatus;

import er.groupware.calendar.ERGWCalendarPrincipal;

public enum ERGWEventStatus implements ERGWIStatus, ERGWICalendarProperty {

  TENTATIVE("Tentatif", Status.VEVENT_TENTATIVE, ZStatus.TENT),
  CANCELLED("Annulé", Status.VEVENT_CANCELLED, ZStatus.CANC),
  CONFIRMED("Confirmé", Status.VEVENT_CONFIRMED, ZStatus.CONF);
  
  private String localizedDescription;
  private Status statusObject;
  private ZStatus zimbraValue;

  private ERGWEventStatus(String description, Status rfc2445Value, ZStatus zimbraValue) {
    this.localizedDescription = description;
    this.statusObject = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }
    
  private static final Map<ZStatus,ERGWEventStatus> zimbraLookup = new HashMap<ZStatus,ERGWEventStatus>();
  private static final Map<Status,ERGWEventStatus> rfc2445Lookup = new HashMap<Status,ERGWEventStatus>();
  
  static {
    for(ERGWEventStatus s : EnumSet.allOf(ERGWEventStatus.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  public String localizedDescription() {
    return ERGWCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }
  
  public Status rfc2445Value() {
    return statusObject;
  }
  
  public ZStatus zimbraValue() {
    return zimbraValue;
  }
  
  public static NSArray<ERGWEventStatus> statuses() {
    return new NSArray<ERGWEventStatus>(ERGWEventStatus.values());
  }
  
  private ERGWEventStatus() {
  }
  
  public static ERGWEventStatus getByZimbraValue(ZStatus zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }

  public Object ewsValue() {
    return null;
  }
  
}
