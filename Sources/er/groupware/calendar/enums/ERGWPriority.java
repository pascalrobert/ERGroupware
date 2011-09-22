package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXKey;

public enum ERGWPriority implements ERGWICalendarProperty {
  
  UNDEFINED("Non défini",net.fortuna.ical4j.model.property.Priority.UNDEFINED, "0"),
  NORMAL("Normal",net.fortuna.ical4j.model.property.Priority.MEDIUM, "5"),
  HIGH("Haute",net.fortuna.ical4j.model.property.Priority.HIGH, "1"),
  LOW("Basse",net.fortuna.ical4j.model.property.Priority.LOW, "9");

  private String description;
  private net.fortuna.ical4j.model.property.Priority rfc2445Value;
  private String zimbraValue;

  private ERGWPriority(String description, net.fortuna.ical4j.model.property.Priority rfc2445Value, String zimbraValue) {
    this.description = description;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }

  private static final Map<String,ERGWPriority> zimbraLookup = new HashMap<String,ERGWPriority>();
  private static final Map<net.fortuna.ical4j.model.property.Priority,ERGWPriority> rfc2445Lookup = new HashMap<net.fortuna.ical4j.model.property.Priority,ERGWPriority>();

  static {
    for(ERGWPriority s : EnumSet.allOf(ERGWPriority.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  public String localizedDescription() {
    return description;
  }
  
  public net.fortuna.ical4j.model.property.Priority rfc2445Value() {
    return rfc2445Value;
  }
  
  public String zimbraValue() {
    return zimbraValue;
  }

  public static NSArray<ERGWPriority> priorities() {
    return new NSArray<ERGWPriority>(ERGWPriority.values());
  }
  
  private ERGWPriority() {
  }

  public static ERGWPriority getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }

}
