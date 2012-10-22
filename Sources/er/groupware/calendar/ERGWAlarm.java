package er.groupware.calendar;

import java.net.MalformedURLException;
import java.util.GregorianCalendar;
import java.util.List;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.Repeat;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Trigger;

import java.util.Calendar;
import com.webobjects.foundation.NSTimestamp;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.cs.zclient.ZAlarm;
import com.zimbra.cs.zclient.ZAlarm.ZTriggerType;
import com.zimbra.cs.zclient.ZInvite;

import er.extensions.eof.ERXKey;
import er.groupware.calendar.enums.ERGWAlarmAction;
import er.groupware.calendar.enums.ERGWAlarmDurationType;

public class ERGWAlarm {

  private ERGWAlarmAction action;
  private int duration;
  private int repeatCount;
  private boolean isNegativeDuration;
  private String description;
  private boolean isAbsolute;
  private NSTimestamp alarmDate;
  private ERGWAlarmDurationType durationType;
  private String emailAddress;
  private String emailSubject;

  public static final ERXKey<ERGWAlarmAction> ACTION = new ERXKey<ERGWAlarmAction>("action");
  public static final ERXKey<Integer> DURATION = new ERXKey<Integer>("duration");
  public static final ERXKey<Integer> REPEAT_COUNT = new ERXKey<Integer>("repeatCount");
  public static final ERXKey<Boolean> IS_NEGATIVE_DURATION = new ERXKey<Boolean>("isNegativeDuration");
  public static final ERXKey<String> DESCRIPTION = new ERXKey<String>("description");
  public static final ERXKey<Boolean> IS_ABSOLUTE = new ERXKey<Boolean>("isAbsolute");
  public static final ERXKey<NSTimestamp> ALARM_DATE = new ERXKey<NSTimestamp>("alarmDate");
  public static final ERXKey<ERGWAlarmDurationType> DURATION_TYPE = new ERXKey<ERGWAlarmDurationType>("durationType");
  public static final ERXKey<String> EMAIL_ADDRESS = new ERXKey<String>("emailAddress");
  public static final ERXKey<String> EMAIL_SUBJECT = new ERXKey<String>("emailSubject");

  public ERGWAlarm() {
  }

  public ERGWAlarm(ERGWCalendar calendar) {
    calendar.addAlarm(this);
  }

  public ERGWAlarmAction action() {
    return action;
  }

  public void setAction(ERGWAlarmAction _action) {
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

  public ERGWAlarmDurationType durationType() {
    return durationType;
  }

  public void setDurationType(ERGWAlarmDurationType _durationType) {
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

  public static VAlarm transformToICalObject(ERGWAlarm alarm) {
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

  public static ERGWAlarm transformFromICalObject(CalendarComponent calComponent, CalendarComponent parentComponent) {
    ERGWAlarm alarm = new ERGWAlarm();
    VAlarm vAlarm = (VAlarm)calComponent;

    if (vAlarm.getAction() != null) {
      ERGWAlarmAction action = ERGWAlarmAction.getByRFC2455Value(vAlarm.getAction());
      if (action != null) {
        alarm.setAction(action);
      }
    }

    Dur duration = vAlarm.getDuration().getDuration();
    if (duration != null) {
      if (duration.getDays() > 0) {
        alarm.setDuration(duration.getDays());
        alarm.setDurationType(ERGWAlarmDurationType.DAYS);

      } else if (duration.getHours() > 0) {
        alarm.setDuration(duration.getHours());
        alarm.setDurationType(ERGWAlarmDurationType.HOURS);

      } else if (duration.getMinutes() > 0) {
        alarm.setDuration(duration.getMinutes());
        alarm.setDurationType(ERGWAlarmDurationType.MINUTES);

      } else if (duration.getSeconds() > 0) {
        alarm.setDuration(duration.getSeconds());
        alarm.setDurationType(ERGWAlarmDurationType.SECONDS);

      } else if (duration.getWeeks() > 0) {
        alarm.setDuration(duration.getWeeks());
        alarm.setDurationType(ERGWAlarmDurationType.WEEKS);

      }
      alarm.setNegativeDuration(duration.isNegative());      
    }

    Trigger trigger = vAlarm.getTrigger();
    if (trigger != null) {
      if ((trigger.getParameter(Parameter.VALUE) != null) && (trigger.getParameter(Parameter.VALUE).getValue().equals("DATE-TIME"))) {
        DateTime triggerTime = trigger.getDateTime();
        alarm.setAlarmDate(new NSTimestamp(triggerTime));
        alarm.setAbsolute(triggerTime.isUtc());
      } else {
        Dur triggerDuration = trigger.getDuration();
        alarm.setNegativeDuration(triggerDuration.isNegative());
        alarm.setAbsolute(false);
        
        java.util.Calendar dateStart = GregorianCalendar.getInstance();
        Property parentStartDate = parentComponent.getProperty(Property.DTSTART);
        if (parentStartDate != null) {
          dateStart.setTime(((DtStart)parentStartDate).getDate());
          if (triggerDuration.getDays() > 0) {
            if (alarm.isNegativeDuration()) {
              dateStart.add(java.util.Calendar.DAY_OF_MONTH, 0 - triggerDuration.getDays());
            } else {
              dateStart.add(java.util.Calendar.DAY_OF_MONTH, triggerDuration.getDays());
            }

          } else if (duration.getHours() > 0) {
            if (alarm.isNegativeDuration()) {
              dateStart.add(java.util.Calendar.HOUR, 0 - triggerDuration.getHours());
            } else {
              dateStart.add(java.util.Calendar.HOUR, triggerDuration.getHours());
            }

          } else if (duration.getMinutes() > 0) {
            if (alarm.isNegativeDuration()) {
              dateStart.add(java.util.Calendar.MINUTE, 0 - triggerDuration.getMinutes());
            } else {
              dateStart.add(java.util.Calendar.MINUTE, triggerDuration.getMinutes());
            }

          } else if (duration.getSeconds() > 0) {
            if (alarm.isNegativeDuration()) {
              dateStart.add(java.util.Calendar.SECOND, 0 - triggerDuration.getSeconds());
            } else {
              dateStart.add(java.util.Calendar.SECOND, triggerDuration.getSeconds());
            }

          } else if (duration.getWeeks() > 0) {
            if (alarm.isNegativeDuration()) {
              dateStart.add(java.util.Calendar.WEEK_OF_YEAR, 0 - triggerDuration.getWeeks());
            } else {
              dateStart.add(java.util.Calendar.WEEK_OF_YEAR, triggerDuration.getWeeks());
            }

          }
          alarm.setAlarmDate(new NSTimestamp(dateStart.getTime()));
        }
      }
    }

    Description alarmDescription = vAlarm.getDescription();
    if (alarmDescription != null) {
      alarm.setDescription(alarmDescription.getValue());      
    }

    if (alarm.action.equals(ERGWAlarmAction.EMAIL)) {
      Property attendeeForAlarm = vAlarm.getProperty(Property.ATTENDEE);
      if (attendeeForAlarm != null) {
        try {
          alarm.setEmailAddress(((Attendee)attendeeForAlarm).getCalAddress().toURL().toExternalForm());
        }
        catch (MalformedURLException e) {
        }      
      }
      Summary alarmSummary = vAlarm.getSummary();
      alarm.setEmailSubject(alarmSummary.getValue());
    }

    Repeat repeatAlarm = vAlarm.getRepeat();
    if (repeatAlarm != null) {
      alarm.setRepeatCount(repeatAlarm.getCount());
    }

    return alarm;
  }

  public static void transformToZimbraObject(ERGWAlarm alarm, Element inviteComponent) {
    Element alarme = inviteComponent.addElement(MailConstants.E_CAL_ALARM);
    alarme.addAttribute(MailConstants.A_CAL_ALARM_ACTION, alarm.action().toString());
    Element declencheur = alarme.addElement(MailConstants.E_CAL_ALARM_TRIGGER);
    if (alarm.isAbsolute()) {
      Element abs = declencheur.addElement(MailConstants.E_CAL_ALARM_ABSOLUTE);
      abs.addAttribute(MailConstants.A_DATE, new DateTime(alarm.alarmDate().getTime()).toString());
    } else {
      Element rel = declencheur.addElement(MailConstants.E_CAL_ALARM_RELATIVE);   
      if (alarm.isNegativeDuration()) {
        rel.addAttribute(MailConstants.A_CAL_DURATION_NEGATIVE, "1");
      } else {
        rel.addAttribute(MailConstants.A_CAL_DURATION_NEGATIVE, "0");        
      }
      rel.addAttribute(MailConstants.A_CAL_ALARM_RELATED, "START");
      if (alarm.durationType() != null) {
        rel.addAttribute(alarm.durationType().zimbraValue(), alarm.duration());          
      } else {
        rel.addAttribute(ERGWAlarmDurationType.MINUTES.zimbraValue(), alarm.duration());
      }
    }
    alarme.addAttribute(MailConstants.E_CAL_ALARM_DESCRIPTION, alarm.description());
    Element repeat = alarme.addElement(MailConstants.E_CAL_ALARM_REPEAT);
    repeat.addAttribute(MailConstants.A_CAL_ALARM_COUNT, alarm.repeatCount());
    if (alarm.action().equals(ERGWAlarmAction.EMAIL)) {
      alarme.addAttribute(MailConstants.E_CAL_ALARM_SUMMARY,alarm.emailSubject());
      Element xmlAttendee = alarme.addElement(MailConstants.E_CAL_ATTENDEE);
      xmlAttendee.addAttribute(MailConstants.A_ADDRESS, alarm.emailSubject());
    }
  }

  public static void transformFromZimbraResponse(Element e, ERGWCalendar newObject) throws ServiceException {
    List<Element> alarms = e.listElements(MailConstants.A_CAL_ALARM);
    for (Element xmlAlarm: alarms) {
      ERGWAlarm newAlarm = new ERGWAlarm();
      ZAlarm zAlarm = new ZAlarm(xmlAlarm);
      newAlarm.setAction(ERGWAlarmAction.getByZimbraValue(zAlarm.getAction()));
      if (zAlarm.getTriggerType().equals(ZTriggerType.RELATIVE)) {
        newAlarm.setAbsolute(false);
        newAlarm.setDuration(zAlarm.getTriggerRelated().getMins());
      } else  {
        newAlarm.setAbsolute(true);
        //newAlarm.setAlarmDate(endTime);
      }
      newAlarm.setDescription(zAlarm.getDescription());
      List<ZInvite.ZAttendee> alarmAttendees = zAlarm.getAttendees();
      if (alarmAttendees != null) {
        for (ZInvite.ZAttendee alarmAttendee: alarmAttendees) {
          newAlarm.setEmailAddress(alarmAttendee.getAddress());
        }
      }
      newAlarm.setEmailSubject(zAlarm.getSummary());
      newAlarm.setRepeatCount(zAlarm.getRepeatCount());
      newObject.addAlarm(newAlarm);
    }
  }

}
