package er.groupware.calendar.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.microsoft.schemas.exchange.services._2006.types.DayOfWeekType;
import com.zimbra.cs.zclient.ZInvite.ZFrequency;

import net.fortuna.ical4j.model.WeekDay;

public enum ERGWRecurrenceDay implements ERGWICalendarProperty {

  SUNDAY("Sunday",WeekDay.SU.getDay(), "SU", DayOfWeekType.SUNDAY),
  MONDAY("Monday",WeekDay.MO.getDay(), "MO", DayOfWeekType.MONDAY),
  TUESDAY("Tuesday",WeekDay.TU.getDay(), "TU", DayOfWeekType.TUESDAY),
  WEDNESDAY("Wednesday",WeekDay.WE.getDay(), "WE", DayOfWeekType.WEDNESDAY),
  THURSDAY("Thursday",WeekDay.TH.getDay(), "TH", DayOfWeekType.THURSDAY),
  FRIDAY("Friday",WeekDay.FR.getDay(), "FR", DayOfWeekType.FRIDAY),
  SATURDAY("Saturday",WeekDay.SA.getDay(), "SA", DayOfWeekType.SATURDAY);
  
  private String description;
  private String rfc2445Value;
  private String zimbraValue;
  private DayOfWeekType ewsValue;

  private static final Map<String,ERGWRecurrenceDay> zimbraLookup = new HashMap<String,ERGWRecurrenceDay>();
  private static final Map<String,ERGWRecurrenceDay> rfc2445Lookup = new HashMap<String,ERGWRecurrenceDay>();
  private static final Map<DayOfWeekType,ERGWRecurrenceDay> ewsLookup = new HashMap<DayOfWeekType,ERGWRecurrenceDay>();
  
  static {
    for(ERGWRecurrenceDay s : EnumSet.allOf(ERGWRecurrenceDay.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
      ewsLookup.put(s.ewsValue(), s);
    }
  }
  
  private ERGWRecurrenceDay(String description, String rfc2445Value, String zimbraValue, DayOfWeekType ewsValue) {
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

  public DayOfWeekType ewsValue() {
    return ewsValue;
  }
  
  public static ERGWRecurrenceDay getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
  public static ERGWRecurrenceDay getByRFC2445Value(String rfc2455Value) { 
    return rfc2445Lookup.get(rfc2455Value); 
  }
  
  public static ERGWRecurrenceDay getByEWSValue(DayOfWeekType ews2455Value) { 
    return ewsLookup.get(ews2455Value); 
  }
  
}
