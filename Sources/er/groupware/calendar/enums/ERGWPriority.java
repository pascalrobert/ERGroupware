package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.microsoft.schemas.exchange.services._2006.types.ImportanceChoicesType;
import com.webobjects.foundation.NSArray;

public enum ERGWPriority implements ERGWICalendarProperty {
  
  UNDEFINED("Non défini",net.fortuna.ical4j.model.property.Priority.UNDEFINED, "0", ImportanceChoicesType.NORMAL),
  NORMAL("Normal",net.fortuna.ical4j.model.property.Priority.MEDIUM, "5", ImportanceChoicesType.NORMAL),
  HIGH("Haute",net.fortuna.ical4j.model.property.Priority.HIGH, "1", ImportanceChoicesType.HIGH),
  LOW("Basse",net.fortuna.ical4j.model.property.Priority.LOW, "9", ImportanceChoicesType.LOW);

  private String description;
  private net.fortuna.ical4j.model.property.Priority rfc2445Value;
  private String zimbraValue;
  private ImportanceChoicesType ewsValue;

  private ERGWPriority(String description, net.fortuna.ical4j.model.property.Priority rfc2445Value, String zimbraValue, ImportanceChoicesType ewsValue) {
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

  public ImportanceChoicesType ewsValue() {
    return ewsValue;
  }

}
