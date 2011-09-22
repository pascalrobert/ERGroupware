package er.groupware.calendar.enums;


import com.webobjects.foundation.NSArray;

/*
 * http://tools.ietf.org/html/rfc2445#section-4.3.10
 * 
freq       = "SECONDLY" / "MINUTELY" / "HOURLY" / "DAILY"
                / "WEEKLY" / "MONTHLY" / "YEARLY"
 */

public enum ERGWFrequence {

  SECONDLY("Aux secondes", "SECONDLY","SEC"),
  MINUTELY("Aux minutes", "MINUTELY","MIN"),
  HOURLY("Aux heures", "HOURLY","HOU"),
  DAILY("Aux journées", "DAILY","DAI"),
  MONTHLY("Aux mois", "MONTHLY","MON"),
  YEARLY("Aux années", "YEARLY","YEA"),
  WEEKLY("Aux semaines", "WEEKLY","WEE");
  
  private String description;
  private String rfc2445Value;
  private String zimbraValue;

  private ERGWFrequence(String description, String rfc2445Value, String zimbraValue) {
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
  
  public static NSArray<ERGWFrequence> frequences() {
    return new NSArray<ERGWFrequence>(ERGWFrequence.values());
  }
  
  private ERGWFrequence() {
  }
  
}
