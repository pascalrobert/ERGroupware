package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.property.BusyType;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.mailbox.calendar.IcalXmlStrMap;

import er.groupware.calendar.ERCalendarPrincipal;

public enum FreeBusyStatus implements ICalendarProperty {

  FREE("Libre", new BusyType("FREE"), IcalXmlStrMap.FBTYPE_FREE),
  BUSY("Occupé", BusyType.BUSY, IcalXmlStrMap.FBTYPE_BUSY),
  BUSY_TENTATIVE("Tentatif", BusyType.BUSY_TENTATIVE, IcalXmlStrMap.FBTYPE_BUSY_TENTATIVE),
  BUSY_UNAVAILABLE("Non disponible", BusyType.BUSY_UNAVAILABLE, IcalXmlStrMap.FBTYPE_BUSY_UNAVAILABLE);
  
  private String localizedDescription;
  private BusyType rfc2445Value;
  private String zimbraValue;

  private FreeBusyStatus(String localizedDescription, BusyType rfc2445Value, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }
  
  private static final Map<String,FreeBusyStatus> zimbraLookup = new HashMap<String,FreeBusyStatus>();
  private static final Map<BusyType,FreeBusyStatus> rfc2445Lookup = new HashMap<BusyType,FreeBusyStatus>();

  static {
    for(FreeBusyStatus s : EnumSet.allOf(FreeBusyStatus.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
    
  public String localizedDescription() {
    return ERCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }

  public String zimbraValue() {
    return zimbraValue;
  }
  
  public BusyType rfc2445Value() {
    return rfc2445Value;
  }
  
  public static NSArray<FreeBusyStatus> statuses() {
    return new NSArray<FreeBusyStatus>(FreeBusyStatus.values());
  }
  
  private FreeBusyStatus() {
  }
  
  public static FreeBusyStatus getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
}
