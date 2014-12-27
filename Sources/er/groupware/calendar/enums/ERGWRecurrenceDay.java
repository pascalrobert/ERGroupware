package er.groupware.calendar.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import microsoft.exchange.webservices.data.DayOfTheWeek;
import net.fortuna.ical4j.model.WeekDay;

public enum ERGWRecurrenceDay implements ERGWICalendarProperty {

  SUNDAY("Sunday",WeekDay.SU.getDay(), "SU", DayOfTheWeek.Sunday),
  MONDAY("Monday",WeekDay.MO.getDay(), "MO", DayOfTheWeek.Monday),
  TUESDAY("Tuesday",WeekDay.TU.getDay(), "TU", DayOfTheWeek.Tuesday),
  WEDNESDAY("Wednesday",WeekDay.WE.getDay(), "WE", DayOfTheWeek.Wednesday),
  THURSDAY("Thursday",WeekDay.TH.getDay(), "TH", DayOfTheWeek.Thursday),
  FRIDAY("Friday",WeekDay.FR.getDay(), "FR", DayOfTheWeek.Friday),
  SATURDAY("Saturday",WeekDay.SA.getDay(), "SA", DayOfTheWeek.Saturday);
  
  private String description;
  private String rfc2445Value;
  private String zimbraValue;
  private DayOfTheWeek ewsValue;

  private static final Map<String,ERGWRecurrenceDay> zimbraLookup = new HashMap<String,ERGWRecurrenceDay>();
  private static final Map<String,ERGWRecurrenceDay> rfc2445Lookup = new HashMap<String,ERGWRecurrenceDay>();
  private static final Map<DayOfTheWeek,ERGWRecurrenceDay> ewsLookup = new HashMap<DayOfTheWeek,ERGWRecurrenceDay>();
  
  static {
    for(ERGWRecurrenceDay s : EnumSet.allOf(ERGWRecurrenceDay.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
      ewsLookup.put(s.ewsValue(), s);
    }
  }
  
  private ERGWRecurrenceDay(String description, String rfc2445Value, String zimbraValue, DayOfTheWeek ewsValue) {
    this.description = description;
    this.zimbraValue = zimbraValue;
    this.rfc2445Value = rfc2445Value;
    this.ewsValue = ewsValue;
  }

  public String localizedDescription() {
    return description;
  }

  public String zimbraValue() {
    return zimbraValue;
  }

  public String rfc2445Value() {
    return rfc2445Value;
  }

  public DayOfTheWeek ewsValue() {
    return ewsValue;
  }
  
  public static ERGWRecurrenceDay getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
  public static ERGWRecurrenceDay getByRFC2445Value(String rfc2455Value) { 
    return rfc2445Lookup.get(rfc2455Value); 
  }
  
  public static ERGWRecurrenceDay getByEWSValue(DayOfTheWeek ews2455Value) { 
    return ewsLookup.get(ews2455Value); 
  }
  
}
