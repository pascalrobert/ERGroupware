package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.property.Transp;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.mailbox.calendar.IcalXmlStrMap;

import er.groupware.calendar.ERGWCalendarPrincipal;

public enum ERGWTransparency implements ERGWICalendarProperty {

  OPAQUE("calendar.transparency.opaque", Transp.OPAQUE, IcalXmlStrMap.TRANSP_OPAQUE),
  TRANSPARENT("calendar.transparency.transparent", Transp.TRANSPARENT, IcalXmlStrMap.TRANSP_TRANSPARENT);
  
  private String localizedDescription;
  private Transp transpObject;
  private String zimbraValue;
  
  private ERGWTransparency(String localizedDescription, Transp transpObject, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.transpObject = transpObject;
    this.zimbraValue = zimbraValue;
  }
  
  private static final Map<String,ERGWTransparency> zimbraLookup = new HashMap<String,ERGWTransparency>();
  private static final Map<Transp,ERGWTransparency> rfc2445Lookup = new HashMap<Transp,ERGWTransparency>();
  
  static {
    for(ERGWTransparency s : EnumSet.allOf(ERGWTransparency.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
    
  public String localizedDescription() {
    return ERGWCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }

  public Transp rfc2445Value() {
    return transpObject;
  }
  
  public String zimbraValue() {
    return zimbraValue;
  }
      
  public static NSArray<ERGWTransparency> transparencies() {
    return new NSArray<ERGWTransparency>(ERGWTransparency.values());
  }
  
  private ERGWTransparency() {
  }
  
  public static ERGWTransparency getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }

}
