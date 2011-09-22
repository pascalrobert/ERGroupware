package er.groupware.calendar.enums;


import com.webobjects.foundation.NSArray;
import com.zimbra.common.soap.MailConstants;

public enum ERGWAlarmDurationType {

  MINUTES("minutes", MailConstants.A_CAL_DURATION_MINUTES),
  HOURS("hour" ,MailConstants.A_CAL_DURATION_HOURS),
  DAYS("jour", MailConstants.A_CAL_DURATION_DAYS),
  SECONDS("seconds", MailConstants.A_CAL_DURATION_SECONDS),
  WEEKS("week", MailConstants.A_CAL_DURATION_WEEKS);
    
  private String localizedDescription;
  private String zimbraValue;

  private ERGWAlarmDurationType(String localizedDescription, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.zimbraValue = zimbraValue;
  }
  
  public String localizedDescription() {
    return localizedDescription;
  }

  public String zimbraValue() {
    return zimbraValue;
  }
  
  public static NSArray<ERGWAlarmDurationType> types() {
    return new NSArray<ERGWAlarmDurationType>(ERGWAlarmDurationType.values());
  }
  
  private ERGWAlarmDurationType() {
  }
  
}
