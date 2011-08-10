package er.groupware.calendar;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;

import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.cs.zclient.ZInvite.ZStatus;

import er.groupware.calendar.enums.EventStatus;
import er.groupware.calendar.enums.IStatus;
import er.groupware.calendar.enums.Transparency;

public class EREvent extends ERCalendarObject {

  private Transparency transparency;
  private EventStatus status;

  public EREvent(ERCalendar calendar) {
    calendar.addEvent(this);
  }

  @Override
  public EventStatus status() {
    return status;
  }

  @Override
  public void setStatus(IStatus status) {
    this.status = (EventStatus)status;
  }

  public Transparency transparency() {
    return transparency;
  }

  public void setTransparency(Transparency transparency) {
    this.transparency = transparency;
  }

  public static CalendarComponent transformToICalObject(EREvent event) throws SocketException, ParseException, URISyntaxException {
    VEvent vEvent = (VEvent)ERCalendarObject.transformToICalObject(event, new VEvent());
    if (event.transparency != null) {
      vEvent.getProperties().add(event.transparency.rfc2445Value());
    }
    if (event.status != null) {
      vEvent.getProperties().add(event.status.rfc2445Value());
    }
    return vEvent;
  }

  public static Element transformToZimbraObject(EREvent event) {
    Element invite = ERCalendarObject.transformToZimbraObject(event);
    invite.addAttribute(MailConstants.A_CAL_STATUS, event.status().zimbraValue().toString());
    invite.addAttribute(MailConstants.A_APPT_TRANSPARENCY, event.transparency().zimbraValue());
    return invite;
  }

  public static void transformFromZimbraResponse(Element e, EREvent newObject) throws ServiceException {
    ERCalendarObject.transformFromZimbraResponse(e, newObject);
    newObject.setStatus(EventStatus.getByZimbraValue(ZStatus.fromString(e.getAttribute(MailConstants.A_CAL_STATUS))));
    newObject.setTransparency(Transparency.getByZimbraValue(e.getAttribute(MailConstants.A_APPT_TRANSPARENCY)));
  }

}
