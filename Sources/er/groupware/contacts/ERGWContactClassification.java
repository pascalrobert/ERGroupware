package er.groupware.contacts;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.vcard.property.Clazz;

import com.microsoft.schemas.exchange.services._2006.types.SensitivityChoicesType;
import com.webobjects.foundation.NSArray;

import er.groupware.calendar.ERGWCalendarPrincipal;
import er.groupware.calendar.enums.ERGWICalendarProperty;

public enum ERGWContactClassification implements ERGWICalendarProperty {

  PUBLIC("Public", Clazz.PUBLIC, "PUB", SensitivityChoicesType.NORMAL),
  PRIVATE("Privé", Clazz.PRIVATE, "PRI", SensitivityChoicesType.PRIVATE),
  CONFIDENTIAL("Confidentiel", Clazz.CONFIDENTIAL, "CON", SensitivityChoicesType.CONFIDENTIAL),
  PERSONAL("Personnel", Clazz.PRIVATE, "CON", SensitivityChoicesType.PERSONAL);

  private String localizedDescription;
  private Clazz rfc2445Value;
  private String zimbraValue;
  private SensitivityChoicesType ewsValue;
  
  private ERGWContactClassification(String localizedDescription, Clazz rfc2445Value, String zimbraValue, SensitivityChoicesType ewsValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }

  private static final Map<String,ERGWContactClassification> zimbraLookup = new HashMap<String,ERGWContactClassification>();
  private static final Map<Clazz,ERGWContactClassification> rfc2445Lookup = new HashMap<Clazz,ERGWContactClassification>();

  static {
    for(ERGWContactClassification s : EnumSet.allOf(ERGWContactClassification.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value, s);
    }
  }

  public String localizedDescription() {
    return ERGWCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }

  public Clazz rfc2445Value() {
    return rfc2445Value;
  }

  public String zimbraValue() {
    return zimbraValue;
  }

  public static NSArray<ERGWContactClassification> clazzes() {
    return new NSArray<ERGWContactClassification>(ERGWContactClassification.values());
  }

  private ERGWContactClassification() {
  }

  public static ERGWContactClassification getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
  public static ERGWContactClassification getByRFC2445Value(Clazz rfcValue) { 
    return rfc2445Lookup.get(rfcValue); 
  }

  public SensitivityChoicesType ewsValue() {
    return ewsValue;
  }

}
