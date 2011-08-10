package er.groupware.calendar;

import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.property.Duration;

import com.webobjects.foundation.NSTimestamp;

import er.extensions.eof.ERXKey;
import er.groupware.calendar.enums.AlarmAction;
import er.groupware.calendar.enums.AlarmDurationType;

public class ERAlarm {
  
  private AlarmAction action;
  private int duration;
  private int repeatCount;
  private boolean isNegativeDuration;
  private String description;
  private boolean isAbsolute;
  private NSTimestamp alarmDate;
  private AlarmDurationType durationType;
  private String emailAddress;
  private String emailSubject;
  
  public static final ERXKey<AlarmAction> ACTION = new ERXKey<AlarmAction>("action");
  public static final ERXKey<Integer> DURATION = new ERXKey<Integer>("duration");
  public static final ERXKey<Integer> REPEAT_COUNT = new ERXKey<Integer>("repeatCount");
  public static final ERXKey<Boolean> IS_NEGATIVE_DURATION = new ERXKey<Boolean>("isNegativeDuration");
  public static final ERXKey<String> DESCRIPTION = new ERXKey<String>("description");
  public static final ERXKey<Boolean> IS_ABSOLUTE = new ERXKey<Boolean>("isAbsolute");
  public static final ERXKey<NSTimestamp> ALARM_DATE = new ERXKey<NSTimestamp>("alarmDate");
  public static final ERXKey<AlarmDurationType> DURATION_TYPE = new ERXKey<AlarmDurationType>("durationType");
  public static final ERXKey<String> EMAIL_ADDRESS = new ERXKey<String>("emailAddress");
  public static final ERXKey<String> EMAIL_SUBJECT = new ERXKey<String>("emailSubject");
  
  public ERAlarm() {
  }
  
  public ERAlarm(ERCalendar calendar) {
    calendar.addAlarm(this);
  }

  public AlarmAction action() {
    return action;
  }

  public void setAction(AlarmAction _action) {
    this.action = _action;
  }

  public int duration() {
    return duration;
  }

  public void setDuration(int _duration) {
    this.duration = _duration;
  }

  public int repeatCount() {
    return repeatCount;
  }

  public void setRepeatCount(int _repeatCount) {
    this.repeatCount = _repeatCount;
  }

  public boolean isNegativeDuration() {
    return isNegativeDuration;
  }

  public void setNegativeDuration(boolean _isNegativeDuration) {
    this.isNegativeDuration = _isNegativeDuration;
  }

  public String description() {
    return description;
  }

  public void setDescription(String _description) {
    this.description = _description;
  }

  public boolean isAbsolute() {
    return isAbsolute;
  }

  public void setAbsolute(boolean _isAbsolute) {
    this.isAbsolute = _isAbsolute;
  }

  public NSTimestamp alarmDate() {
    return alarmDate;
  }

  public void setAlarmDate(NSTimestamp _alarmDate) {
    this.alarmDate = _alarmDate;
  }

  public AlarmDurationType durationType() {
    return durationType;
  }

  public void setDurationType(AlarmDurationType _durationType) {
    this.durationType = _durationType;
  }

  public String emailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String _emailAddress) {
    this.emailAddress = _emailAddress;
  }

  public String emailSubject() {
    return emailSubject;
  }

  public void setEmailSubject(String _emailSubject) {
    this.emailSubject = _emailSubject;
  }
  
  public static VAlarm transformToICalObject(ERAlarm alarm) {
    VAlarm vAlarm = new VAlarm();
    vAlarm.getProperties().add(alarm.action().rfc2445Value());
    
    Dur duration = null;
    switch (alarm.durationType()) {
    case DAYS:
      duration = new Dur(alarm.duration(), 0, 0, 0); 
      break;
    case HOURS:
      duration = new Dur(0, alarm.duration(), 0, 0); 
      break;
    case MINUTES:
      duration = new Dur(0, 0, alarm.duration(), 0); 
      break;
    case SECONDS:
      duration = new Dur(0, 0, 0, alarm.duration()); 
      break;
    case WEEKS:
      duration = new Dur(alarm.duration()); 
      break;
    default:
      break;
    }
    vAlarm.getProperties().add(new Duration(duration));
    
    switch (alarm.action()) {
    case AUDIO:
      break;
    case DISPLAY:
      break;
    case EMAIL:
      break;
    case PROCEDURE:
      break;
    default:
      break;
    }
    return vAlarm;
  }

  
}
