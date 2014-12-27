package er.groupware.contacts;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import microsoft.exchange.webservices.data.Sensitivity;
import net.fortuna.ical4j.vcard.property.Clazz;

import com.webobjects.foundation.NSArray;

import er.groupware.calendar.ERGWCalendarPrincipal;
import er.groupware.calendar.enums.ERGWICalendarProperty;

public enum ERGWContactClassification implements ERGWICalendarProperty {

  PUBLIC("Public", Clazz.PUBLIC, "PUB", Sensitivity.Normal),
  PRIVATE("Privé", Clazz.PRIVATE, "PRI", Sensitivity.Private),
  CONFIDENTIAL("Confidentiel", Clazz.CONFIDENTIAL, "CON", Sensitivity.Confidential),
  PERSONAL("Personnel", Clazz.PRIVATE, "CON", Sensitivity.Personal);

  private String localizedDescription;
  private Clazz rfc2445Value;
  private String zimbraValue;
  private Sensitivity ewsValue;
  
  private ERGWContactClassification(String localizedDescription, Clazz rfc2445Value, String zimbraValue, Sensitivity ewsValue) {
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

  public Sensitivity ewsValue() {
    return ewsValue;
  }

}
