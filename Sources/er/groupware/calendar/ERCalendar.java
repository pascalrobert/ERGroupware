package er.groupware.calendar;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.Element.XMLElement;
import com.zimbra.common.soap.MailConstants;

import er.groupware.calendar.exceptions.ZimbraTooManyObjectsException;

public class ERCalendar {

  private NSMutableArray<EREvent> events;
  private NSMutableArray<ERTask> tasks;
  private NSMutableArray<ERAlarm> alarms;
  private ProdId productId;
  protected Version version;
  protected CalScale scale;
  public static ProdId defaultProdId = new ProdId("-//Project Wonder//ERCalendar2//EN"); // TODO: that should go in a property

  public ERCalendar() {
    productId = defaultProdId;
    version = Version.VERSION_2_0;
    scale = CalScale.GREGORIAN;
    events = new NSMutableArray<EREvent>();
    tasks = new NSMutableArray<ERTask>();
    alarms = new NSMutableArray<ERAlarm>();
  }

  public ProdId productId() {
    return productId;
  }

  public void setProductId(ProdId _productId) {
    this.productId = _productId;
  }

  public NSArray<EREvent> events() {
    return events.immutableClone();
  }
  
  public NSArray<EREvent> getEvents() {
    return events();
  }

  public void setEvents(NSArray<EREvent> _events) {
    this.events = _events.mutableClone();
  }

  public void addEvent(EREvent event) {
    this.events.addObject(event);
  }

  public NSArray<ERTask> tasks() {
    return tasks.immutableClone();
  }

  public void setTasks(NSArray<ERTask> _tasks) {
    this.tasks = _tasks.mutableClone();
  }

  public void addTask(ERTask task) {
    this.tasks.addObject(task);
  }
  
  public NSArray<ERAlarm> alarms() {
    return alarms.immutableClone();
  }

  public void setAlarms(NSArray<ERAlarm> _alarms) {
    this.alarms = _alarms.mutableClone();
  }

  public void addAlarm(ERAlarm _alarm) {
    this.alarms.addObject(_alarm);
  }

  public Version version() {
    return version;
  }

  public void setVersion(Version _version) {
    this.version = _version;
  }

  public CalScale scale() {
    return scale;
  }

  public void setScale(CalScale _scale) {
    this.scale = _scale;
  }

  public static Calendar transformToICalObject(ERCalendar calendar) throws SocketException, ParseException, URISyntaxException {
    net.fortuna.ical4j.model.Calendar icalCalendar = new net.fortuna.ical4j.model.Calendar();

    icalCalendar.getProperties().add(calendar.productId());
    icalCalendar.getProperties().add(calendar.version);
    icalCalendar.getProperties().add(calendar.scale);
    for (EREvent event: calendar.events()) {
      VEvent vEvent = (VEvent)EREvent.transformToICalObject(event);
      icalCalendar.getComponents().add(vEvent);
    }
    for (ERTask task: calendar.tasks()) {
      VToDo vTodo = (VToDo)ERTask.transformToICalObject(task);
      icalCalendar.getComponents().add(vTodo);
    }
    for (ERAlarm alarm: calendar.alarms()) {
      VAlarm vAlarm = (VAlarm)ERAlarm.transformToICalObject(alarm);
      icalCalendar.getComponents().add(vAlarm);
    }
    return icalCalendar;
  }

  public static XMLElement transformToZimbraObject(ERCalendar calendar) throws ZimbraTooManyObjectsException {
    if ((calendar.events().count() > 1) || (calendar.tasks().count() > 1)) {
      throw new ZimbraTooManyObjectsException("Calendar objects in Zimbra can only have one event or task");
    }
    if ((calendar.events().count() == 1) && (calendar.tasks().count() == 1)) {
      throw new ZimbraTooManyObjectsException("Calendar objects in Zimbra can only have one event or task, they can't have both");
    }
    XMLElement invitation = new XMLElement(MailConstants.E_INVITE);

    if (calendar.events().count() == 1) {
      Element vEvent = EREvent.transformToZimbraObject(calendar.events().objectAtIndex(0));
      for (ERAlarm alarm: calendar.alarms()) {
        ERAlarm.transformToZimbraObject(alarm, vEvent);
      }
      invitation.addElement(vEvent);
    }
    if (calendar.tasks().count() == 1) {
      Element vTodo = ERTask.transformToZimbraObject(calendar.tasks().objectAtIndex(0));
      for (ERAlarm alarm: calendar.alarms()) {
        ERAlarm.transformToZimbraObject(alarm, vTodo);
      }
      invitation.addElement(vTodo);
    }

    return invitation;
  }

  public static ERCalendar transformFromZimbraResponse(XMLElement je) throws ServiceException {
    ERCalendar newCalendar = new ERCalendar();
    newCalendar.setProductId(defaultProdId);
    Element appt = je.getElement("appt");

    NSMutableArray<EREvent> events = new NSMutableArray<EREvent>();

    for (Element inviteEl : appt.listElements(MailConstants.E_INVITE)) {
      for (Element component : inviteEl.listElements(MailConstants.A_CAL_COMP)) {
        EREvent event = new EREvent(newCalendar);
        EREvent.transformFromZimbraResponse(component, event);
        events.addObject(event);
        ERAlarm.transformFromZimbraResponse(component, newCalendar);
      }
    }
    newCalendar.setEvents(events);
    return newCalendar;
  }

}
