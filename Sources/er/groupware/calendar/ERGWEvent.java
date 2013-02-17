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

import er.groupware.calendar.enums.ERGWEventStatus;
import er.groupware.calendar.enums.ERGWIStatus;
import er.groupware.calendar.enums.ERGWTransparency;

public class ERGWEvent extends ERGWCalendarObject {

  private ERGWTransparency transparency;
  private ERGWEventStatus status;

  public ERGWEvent(ERGWCalendar calendar) {
    calendar.addEvent(this);
  }

  @Override
  public ERGWEventStatus status() {
    return status;
  }

  @Override
  public void setStatus(ERGWIStatus status) {
    this.status = (ERGWEventStatus)status;
  }

  public ERGWTransparency transparency() {
    return transparency;
  }

  public void setTransparency(ERGWTransparency transparency) {
    this.transparency = transparency;
  }

  public static CalendarComponent transformToICalObject(ERGWEvent event) throws SocketException, ParseException, URISyntaxException {
    // TODO The useUtc parameter should be stored somewhere
    VEvent vEvent = (VEvent)ERGWCalendarObject.transformToICalObject(event, new VEvent(), false);
    if (event.transparency != null) {
      vEvent.getProperties().add(event.transparency.rfc2445Value());
    }
    if (event.status != null) {
      vEvent.getProperties().add(event.status.rfc2445Value());
    }
    return vEvent;
  }

  public static Element transformToZimbraObject(ERGWEvent event) {
    Element invite = ERGWCalendarObject.transformToZimbraObject(event);
    invite.addAttribute(MailConstants.A_CAL_STATUS, event.status().zimbraValue().toString());
    invite.addAttribute(MailConstants.A_APPT_TRANSPARENCY, event.transparency().zimbraValue());
    return invite;
  }

  public static void transformFromZimbraResponse(Element e, ERGWEvent newObject) throws ServiceException {
    ERGWCalendarObject.transformFromZimbraResponse(e, newObject);
    newObject.setStatus(ERGWEventStatus.getByZimbraValue(ZStatus.fromString(e.getAttribute(MailConstants.A_CAL_STATUS))));
    newObject.setTransparency(ERGWTransparency.getByZimbraValue(e.getAttribute(MailConstants.A_APPT_TRANSPARENCY)));
  }

}
