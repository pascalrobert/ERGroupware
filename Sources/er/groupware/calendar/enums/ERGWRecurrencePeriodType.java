package er.groupware.calendar.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.zimbra.common.soap.MailConstants;
import com.zimbra.cs.zclient.ZInvite.ZFrequency;

public enum ERGWRecurrencePeriodType implements ERGWICalendarProperty {

  /*
  The BYSETPOS rule part specifies a COMMA character (US-ASCII decimal
   44) separated list of values which corresponds to the nth occurrence
   within the set of events specified by the rule. Valid values are 1 to
   366 or -366 to -1. It MUST only be used in conjunction with another
   BYxxx rule part. For example "the last work day of the month" could
   be represented as:

     RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-1
   */
  
  INTERVAL("Interval", "INTERVAL", MailConstants.E_CAL_RULE_INTERVAL),
  BYSECOND("By second", "BYSECOND", MailConstants.E_CAL_RULE_BYSECOND),
  BYMINUTE("By minute", "BYMINUTE", MailConstants.E_CAL_RULE_BYMINUTE),
  BYHOUR("By hour", "BYHOUR", MailConstants.E_CAL_RULE_BYHOUR),
  BYDAY("By day", "BYDAY", MailConstants.E_CAL_RULE_BYDAY),
  BYMONTHDAY("By month day", "BYMONTHDAY", MailConstants.E_CAL_RULE_BYMONTHDAY),
  BYYEARDAY("By year day", "BYYEARDAY", MailConstants.E_CAL_RULE_BYYEARDAY),
  BYWEEKNO("By week number", "BYWEEKNO", MailConstants.E_CAL_RULE_BYWEEKNO),
  BYMONTH("By month", "BYMONTH", MailConstants.E_CAL_RULE_BYMONTH),
  BYSETPOS("By occurrence", "BYSETPOS", MailConstants.E_CAL_RULE_BYSETPOS),
  WKST("Workweek", "WKST", MailConstants.E_CAL_RULE_WKST);
  
  private String description;
  private String rfc2445Value;
  private String zimbraValue;

  private static final Map<String,ERGWRecurrencePeriodType> zimbraLookup = new HashMap<String,ERGWRecurrencePeriodType>();
  private static final Map<String,ERGWRecurrencePeriodType> rfc2445Lookup = new HashMap<String,ERGWRecurrencePeriodType>();

  static {
    for(ERGWRecurrencePeriodType s : EnumSet.allOf(ERGWRecurrencePeriodType.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  private ERGWRecurrencePeriodType(String description, String rfc2445Value, String zimbraValue) {
    this.description = description;
    this.zimbraValue = zimbraValue;
    this.rfc2445Value = rfc2445Value;
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

  public Object ewsValue() {
    return null;
  }
  
  public static ERGWRecurrencePeriodType getByZimbraValue(ZFrequency zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
  public static ERGWRecurrencePeriodType getByRFC2445Value(String rfc2455Value) { 
    return rfc2445Lookup.get(rfc2455Value); 
  }

}
