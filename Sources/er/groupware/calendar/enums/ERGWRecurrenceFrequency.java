package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.Recur;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.zclient.ZInvite.ZFrequency;

public enum ERGWRecurrenceFrequency implements ERGWICalendarProperty {

  DAILY("Chaque jour", Recur.DAILY, ZFrequency.DAI),
  HOURLY("Hourly", Recur.HOURLY, ZFrequency.HOU),
  MINUTELY("MINUTELY", Recur.MINUTELY, ZFrequency.MIN),
  SECONDLY("SECONDLY", Recur.SECONDLY, ZFrequency.SEC),
  WEEKLY("Chaque semaine", Recur.WEEKLY, ZFrequency.WEE),
  MONTHLY("Chaque mois", Recur.MONTHLY, ZFrequency.MON),
  YEARLY("Chaque année", Recur.YEARLY, ZFrequency.YEA);
    
  private String localizedDescription;
  private String rfc2445Value;
  private ZFrequency zimbraValue;

  private static final Map<ZFrequency,ERGWRecurrenceFrequency> zimbraLookup = new HashMap<ZFrequency,ERGWRecurrenceFrequency>();
  private static final Map<String,ERGWRecurrenceFrequency> rfc2445Lookup = new HashMap<String,ERGWRecurrenceFrequency>();
  
  static {
    for(ERGWRecurrenceFrequency s : EnumSet.allOf(ERGWRecurrenceFrequency.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  private ERGWRecurrenceFrequency(String localizedDescription, String rfc2445Value, ZFrequency zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }
    
  public static NSArray<ERGWRecurrenceFrequency> recurrences() {
    return new NSArray<ERGWRecurrenceFrequency>(ERGWRecurrenceFrequency.values());
  }
  
  private ERGWRecurrenceFrequency() {
  }

  public String localizedDescription() {
    return localizedDescription;
  }

  public ZFrequency zimbraValue() {
    return zimbraValue;
  }

  public String rfc2445Value() {
    return rfc2445Value;
  }

  public Object ewsValue() {
    return null;
  }
  
  public static ERGWRecurrenceFrequency getByZimbraValue(ZFrequency zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
  public static ERGWRecurrenceFrequency getByRFC2445Value(String rfc2455Value) { 
    return rfc2445Lookup.get(rfc2455Value); 
  }
}
