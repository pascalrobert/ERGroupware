package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.property.Clazz;

import com.webobjects.foundation.NSArray;

import er.groupware.calendar.ERCalendarPrincipal;

public enum Classification implements ICalendarProperty {

  PUBLIC("Public", Clazz.PUBLIC, "PUB"),
  PRIVATE("Privé", Clazz.PRIVATE, "PRI"),
  CONFIDENTIAL("Confidentiel", Clazz.CONFIDENTIAL, "CON");

  private String localizedDescription;
  private Clazz rfc2445Value;
  private String zimbraValue;

  private Classification(String localizedDescription, Clazz rfc2445Value, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }

  private static final Map<String,Classification> zimbraLookup = new HashMap<String,Classification>();
  private static final Map<Clazz,Classification> rfc2445Lookup = new HashMap<Clazz,Classification>();

  static {
    for(Classification s : EnumSet.allOf(Classification.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value, s);
    }
  }

  public String localizedDescription() {
    return ERCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }

  public Clazz rfc2445Value() {
    return rfc2445Value;
  }

  public String zimbraValue() {
    return zimbraValue;
  }

  public static NSArray<Classification> clazzes() {
    return new NSArray<Classification>(Classification.values());
  }

  private Classification() {
  }

  public static Classification getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }

}
