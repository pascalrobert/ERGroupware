package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import microsoft.exchange.webservices.data.Importance;

import com.webobjects.foundation.NSArray;

public enum ERGWPriority implements ERGWICalendarProperty {
  
  UNDEFINED("Non défini",net.fortuna.ical4j.model.property.Priority.UNDEFINED, "0", Importance.Normal),
  NORMAL("Normal",net.fortuna.ical4j.model.property.Priority.MEDIUM, "5", Importance.Normal),
  HIGH("Haute",net.fortuna.ical4j.model.property.Priority.HIGH, "1", Importance.High),
  LOW("Basse",net.fortuna.ical4j.model.property.Priority.LOW, "9", Importance.Low);

  private String description;
  private net.fortuna.ical4j.model.property.Priority rfc2445Value;
  private String zimbraValue;
  private Importance ewsValue;

  private ERGWPriority(String description, net.fortuna.ical4j.model.property.Priority rfc2445Value, String zimbraValue, Importance ewsValue) {
    this.description = description;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
    this.ewsValue = ewsValue;
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

  public Importance ewsValue() {
    return ewsValue;
  }

}
