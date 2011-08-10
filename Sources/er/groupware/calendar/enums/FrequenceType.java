package er.groupware.calendar.enums;


import com.webobjects.foundation.NSArray;

/*
 * http://tools.ietf.org/html/rfc2445#section-4.3.10
 * 
BYSECOND, BYMINUTE, BYHOUR, BYDAY, BYMONTHDAY, BYYEARDAY, BYWEEKNO, BYSETPOS

The BYSETPOS rule part specifies a COMMA character (US-ASCII decimal
   44) separated list of values which corresponds to the nth occurrence
   within the set of events specified by the rule. Valid values are 1 to
   366 or -366 to -1. It MUST only be used in conjunction with another
   BYxxx rule part. For example "the last work day of the month" could
   be represented as:

     RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR;BYSETPOS=-1
 */

public enum FrequenceType {

  BYSECOND("Aux secondes", "BYSECOND","SEC"),
  BYMINUTE("Aux minutes", "BYHOUR","MIN"),
  BYHOUR("Aux heures", "BYHOUR","HOU"),
  BYDAY("Aux journées", "BYDAY","DAI"),
  BYMONTHDAY("Aux mois", "BYMONTHDAY","MON"),
  BYYEARDAY("Aux années", "BYYEARDAY","YEA"),
  BYWEEKNO("Aux semaines", "BYWEEKNO","WEE");
  
  private String description;
  private String rfc2445Value;
  private String zimbraValue;

  private FrequenceType(String description, String rfc2445Value, String zimbraValue) {
    this.description = description;
    this.zimbraValue = zimbraValue;
    this.rfc2445Value = rfc2445Value;
  }
    
  public String description() {
    return description;
  }
  
  public String zimbraValue() {
    return zimbraValue;
  }
  
  public String rfc2445Value() {
    return rfc2445Value;
  }
  
  public static NSArray<FrequenceType> types() {
    return new NSArray<FrequenceType>(FrequenceType.values());
  }
  
  private FrequenceType() {
  }
  
}
