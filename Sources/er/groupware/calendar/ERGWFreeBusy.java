package er.groupware.calendar;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;

import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VFreeBusy;
import net.fortuna.ical4j.model.property.XProperty;
import er.groupware.calendar.enums.ERGWIStatus;

/*
 * BEGIN:VCALENDAR
CALSCALE:GREGORIAN
VERSION:2.0
METHOD:REQUEST
PRODID:-//Apple Inc.//Mac OS X 10.8.2//EN
BEGIN:VFREEBUSY
UID:91ACFAFA-7BDB-45FD-9F15-0A855EAEEC64
DTEND:20130122T050000Z
X-CALENDARSERVER-EXTENDED-FREEBUSY:T
ATTENDEE:mailto:calendriers@conatus.lan
ATTENDEE:urn:uuid:ABA855F4-D9E5-4C6F-AD5E-DDB553E15E2C
ATTENDEE:urn:uuid:08617720-C26F-495C-ADB5-6AC1B58D34C1
DTSTART:20130121T050000Z
X-CALENDARSERVER-MASK-UID:C77BF812-4113-4CC8-AD0F-F160B3E0995D
DTSTAMP:20130121T133143Z
ORGANIZER:mailto:calendriers@conatus.lan
END:VFREEBUSY
END:VCALENDAR
 */

/*
PRODID:-//Project Wonder//ERCalendar2//EN
VERSION:2.0
CALSCALE:GREGORIAN
X-WR-CALNAME:
BEGIN:VFREEBUSY
DTSTAMP:20130121T153721Z
UID:20130121T153645Z-outbox@fdb4:5bac:8d1e:35af:3cc0:771d:5b13:f57d
ATTENDEE;ROLE=REQ-PARTICIPANT;CN=;RSVP=FALSE;CUTYPE=UNKNOWN:mailto:calendriers@conatus.lan
ATTENDEE;ROLE=REQ-PARTICIPANT;CN=;RSVP=FALSE;CUTYPE=UNKNOWN:urn:uuid:08617720-C26F-495C-ADB5-6AC1B58D34C1
DESCRIPTION:
DTEND:20100611T080000
DTSTART:20100611T070000
ORGANIZER;CN=;CUTYPE=UNKNOWN:mailto:calendriers@conatus.lan
SUMMARY:
X-CALENDARSERVER-EXTENDED-FREEBUSY:T
X-CALENDARSERVER-MASK-UID:00000-43542-0000002910
END:VFREEBUSY
END:VCALENDAR
 */

public class ERGWFreeBusy extends ERGWCalendarObject {

  // X-CALENDARSERVER-MASK-UID:C77BF812-4113-4CC8-AD0F-F160B3E0995D
  protected String maskUid;
  // X-CALENDARSERVER-EXTENDED-FREEBUSY:T
  protected String extendedFreeBusy;
  
  public ERGWFreeBusy(ERGWCalendar calendar) {
    calendar.setFreeBusy(this);
  }

  @Override
  public ERGWIStatus status() {
    return null;
  }

  @Override
  public void setStatus(ERGWIStatus status) {
    // DOES NOTHING
  }

  public String maskUid() {
    return maskUid;
  }

  public void setMaskUid(String maskUid) {
    this.maskUid = maskUid;
  }

  public String extendedFreeBusy() {
    return extendedFreeBusy;
  }

  public void setExtendedFreeBusy(String extendedFreeBusy) {
    this.extendedFreeBusy = extendedFreeBusy;
  }
  
  public static CalendarComponent transformToICalObject(ERGWFreeBusy freeBusy) throws SocketException, ParseException, URISyntaxException {
    // TODO The useUtc parameter should be stored somewhere
    VFreeBusy vFreeBusy = (VFreeBusy)ERGWCalendarObject.transformToICalObject(freeBusy, new VFreeBusy(), true);
    if (freeBusy.extendedFreeBusy() != null) {
      XProperty xCalServerExtFreeBusy = new XProperty("X-CALENDARSERVER-EXTENDED-FREEBUSY");
      xCalServerExtFreeBusy.setValue(freeBusy.extendedFreeBusy());
      vFreeBusy.getProperties().add(xCalServerExtFreeBusy);
    }
    if (freeBusy.maskUid() != null) {
      XProperty xCalServerMaskUid = new XProperty("X-CALENDARSERVER-MASK-UID");
      xCalServerMaskUid.setValue(freeBusy.maskUid());
      vFreeBusy.getProperties().add(xCalServerMaskUid);
    }
    return vFreeBusy;
  }
  

}
