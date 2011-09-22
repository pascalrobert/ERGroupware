package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.property.Clazz;

import com.webobjects.foundation.NSArray;

import er.groupware.calendar.ERGWCalendarPrincipal;

public enum ERGWClassification implements ERGWICalendarProperty {

  PUBLIC("Public", Clazz.PUBLIC, "PUB"),
  PRIVATE("Privé", Clazz.PRIVATE, "PRI"),
  CONFIDENTIAL("Confidentiel", Clazz.CONFIDENTIAL, "CON");

  private String localizedDescription;
  private Clazz rfc2445Value;
  private String zimbraValue;

  private ERGWClassification(String localizedDescription, Clazz rfc2445Value, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }

  private static final Map<String,ERGWClassification> zimbraLookup = new HashMap<String,ERGWClassification>();
  private static final Map<Clazz,ERGWClassification> rfc2445Lookup = new HashMap<Clazz,ERGWClassification>();

  static {
    for(ERGWClassification s : EnumSet.allOf(ERGWClassification.class)) {
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

  public static NSArray<ERGWClassification> clazzes() {
    return new NSArray<ERGWClassification>(ERGWClassification.values());
  }

  private ERGWClassification() {
  }

  public static ERGWClassification getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }

}
