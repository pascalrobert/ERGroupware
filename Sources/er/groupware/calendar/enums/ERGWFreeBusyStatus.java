package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import microsoft.exchange.webservices.data.LegacyFreeBusyStatus;
import net.fortuna.ical4j.model.property.BusyType;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.mailbox.calendar.IcalXmlStrMap;

import er.groupware.calendar.ERGWCalendarPrincipal;

public enum ERGWFreeBusyStatus implements ERGWICalendarProperty {

  FREE("Libre", new BusyType("FREE"), IcalXmlStrMap.FBTYPE_FREE, LegacyFreeBusyStatus.Free),
  BUSY("Occupé", BusyType.BUSY, IcalXmlStrMap.FBTYPE_BUSY, LegacyFreeBusyStatus.Busy),
  BUSY_TENTATIVE("Tentatif", BusyType.BUSY_TENTATIVE, IcalXmlStrMap.FBTYPE_BUSY_TENTATIVE, LegacyFreeBusyStatus.Tentative),
  BUSY_UNAVAILABLE("Non disponible", BusyType.BUSY_UNAVAILABLE, IcalXmlStrMap.FBTYPE_BUSY_UNAVAILABLE, LegacyFreeBusyStatus.OOF);
  
  private String localizedDescription;
  private BusyType rfc2445Value;
  private String zimbraValue;
  private LegacyFreeBusyStatus ewsValue;

  private ERGWFreeBusyStatus(String localizedDescription, BusyType rfc2445Value, String zimbraValue, LegacyFreeBusyStatus ewsValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
    this.ewsValue = ewsValue;
  }
  
  private static final Map<String,ERGWFreeBusyStatus> zimbraLookup = new HashMap<String,ERGWFreeBusyStatus>();
  private static final Map<BusyType,ERGWFreeBusyStatus> rfc2445Lookup = new HashMap<BusyType,ERGWFreeBusyStatus>();

  static {
    for(ERGWFreeBusyStatus s : EnumSet.allOf(ERGWFreeBusyStatus.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
    
  public String localizedDescription() {
    return ERGWCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }

  public String zimbraValue() {
    return zimbraValue;
  }
  
  public BusyType rfc2445Value() {
    return rfc2445Value;
  }
  
  public LegacyFreeBusyStatus ewsValue() {
    return ewsValue;
  }
  
  public static NSArray<ERGWFreeBusyStatus> statuses() {
    return new NSArray<ERGWFreeBusyStatus>(ERGWFreeBusyStatus.values());
  }
  
  private ERGWFreeBusyStatus() {
  }
  
  public static ERGWFreeBusyStatus getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
}
