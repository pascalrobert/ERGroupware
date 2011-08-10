package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.property.Transp;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.mailbox.calendar.IcalXmlStrMap;

import er.groupware.calendar.ERCalendarPrincipal;

public enum Transparency implements ICalendarProperty {

  OPAQUE("calendar.transparency.opaque", Transp.OPAQUE, IcalXmlStrMap.TRANSP_OPAQUE),
  TRANSPARENT("calendar.transparency.transparent", Transp.TRANSPARENT, IcalXmlStrMap.TRANSP_TRANSPARENT);
  
  private String localizedDescription;
  private Transp transpObject;
  private String zimbraValue;
  
  private Transparency(String localizedDescription, Transp transpObject, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.transpObject = transpObject;
    this.zimbraValue = zimbraValue;
  }
  
  private static final Map<String,Transparency> zimbraLookup = new HashMap<String,Transparency>();
  private static final Map<Transp,Transparency> rfc2445Lookup = new HashMap<Transp,Transparency>();
  
  static {
    for(Transparency s : EnumSet.allOf(Transparency.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
    
  public String localizedDescription() {
    return ERCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }

  public Transp rfc2445Value() {
    return transpObject;
  }
  
  public String zimbraValue() {
    return zimbraValue;
  }
      
  public static NSArray<Transparency> transparencies() {
    return new NSArray<Transparency>(Transparency.values());
  }
  
  private Transparency() {
  }
  
  public static Transparency getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }

}
