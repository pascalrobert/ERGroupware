package er.groupware.calendar;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.TimeZone;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VFreeBusy;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.model.property.XProperty;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.Element.XMLElement;
import com.zimbra.common.soap.MailConstants;

import er.groupware.exceptions.ZimbraTooManyObjectsException;

public class ERGWCalendar {

  private NSMutableArray<ERGWEvent> events;
  private NSMutableArray<ERGWTask> tasks;
  private NSMutableArray<ERGWAlarm> alarms;
  private ProdId productId;
  protected Version version;
  protected CalScale scale;
  private TimeZone timeZone;
  private String calendarName;
  protected ERGWFreeBusy freeBusy;
  
  public static ProdId defaultProdId = new ProdId("-//Project Wonder//ERCalendar2//EN"); // TODO: that should go in a property

  public ERGWCalendar() {
    productId = defaultProdId;
    version = Version.VERSION_2_0;
    scale = CalScale.GREGORIAN;
    events = new NSMutableArray<ERGWEvent>();
    tasks = new NSMutableArray<ERGWTask>();
    alarms = new NSMutableArray<ERGWAlarm>();
  }

  public ProdId productId() {
    return productId;
  }

  public void setProductId(ProdId _productId) {
    this.productId = _productId;
  }

  public NSArray<ERGWEvent> events() {
    return events.immutableClone();
  }
  
  public NSArray<ERGWEvent> getEvents() {
    return events();
  }

  public void setEvents(NSArray<ERGWEvent> _events) {
    this.events = _events.mutableClone();
  }

  public void addEvent(ERGWEvent event) {
    this.events.addObject(event);
  }

  public NSArray<ERGWTask> tasks() {
    return tasks.immutableClone();
  }

  public void setTasks(NSArray<ERGWTask> _tasks) {
    this.tasks = _tasks.mutableClone();
  }

  public void addTask(ERGWTask task) {
    this.tasks.addObject(task);
  }
  
  public ERGWFreeBusy freeBusy() {
    return freeBusy;
  }

  public void setFreeBusy(ERGWFreeBusy _freeBusy) {
    this.freeBusy = _freeBusy;
  }

  public NSArray<ERGWAlarm> alarms() {
    return alarms.immutableClone();
  }

  public void setAlarms(NSArray<ERGWAlarm> _alarms) {
    this.alarms = _alarms.mutableClone();
  }

  public void addAlarm(ERGWAlarm _alarm) {
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

  public TimeZone timeZone() {
    return timeZone;
  }

  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }

  public String calendarName() {
    return calendarName;
  }

  public void setCalendarName(String calendarName) {
    this.calendarName = calendarName;
  }

  public static Calendar transformToICalObject(ERGWCalendar calendar) throws SocketException, ParseException, URISyntaxException {
    net.fortuna.ical4j.model.Calendar icalCalendar = new net.fortuna.ical4j.model.Calendar();

    icalCalendar.getProperties().add(calendar.productId());
    icalCalendar.getProperties().add(calendar.version);
    icalCalendar.getProperties().add(calendar.scale);
    if (calendar.calendarName() != null) {
      icalCalendar.getProperties().add(new XProperty("X-WR-CALNAME", calendar.calendarName()));
    }
    
    if (calendar.timeZone() != null) {
      TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
      VTimeZone tz = registry.getTimeZone(calendar.timeZone().getID()).getVTimeZone();
      icalCalendar.getComponents().add(tz);
    }
    
    for (ERGWEvent event: calendar.events()) {
      VEvent vEvent = (VEvent)ERGWEvent.transformToICalObject(event);
      icalCalendar.getComponents().add(vEvent);
    }
    for (ERGWTask task: calendar.tasks()) {
      VToDo vTodo = (VToDo)ERGWTask.transformToICalObject(task);
      icalCalendar.getComponents().add(vTodo);
    }
    for (ERGWAlarm alarm: calendar.alarms()) {
      VAlarm vAlarm = (VAlarm)ERGWAlarm.transformToICalObject(alarm);
      icalCalendar.getComponents().add(vAlarm);
    }
    if (calendar.freeBusy() != null) {
      VFreeBusy vFreeBusy = (VFreeBusy)ERGWFreeBusy.transformToICalObject(calendar.freeBusy());
      icalCalendar.getComponents().add(vFreeBusy);
    }
    return icalCalendar;
  }
  
  public static ERGWCalendar transformFromICalResponse(net.fortuna.ical4j.model.Calendar je)  {
    ERGWCalendar newCalendar = new ERGWCalendar();
    
    if (je.getProductId() != null) {
      newCalendar.setProductId(je.getProductId());
    } else {
      newCalendar.setProductId(defaultProdId);
    }
    
    if (je.getProperty("X-WR-CALNAME") != null) {
      newCalendar.setCalendarName(je.getProperty("X-WR-CALNAME").getValue());
    }
    
    Component vTimeZone = je.getComponent(Component.VTIMEZONE);
    if (vTimeZone != null) {
      newCalendar.setTimeZone(new net.fortuna.ical4j.model.TimeZone((VTimeZone)vTimeZone));
    }
    
    if (je.getVersion() != null) {
      newCalendar.setVersion(je.getVersion());      
    }
    
    NSMutableArray<ERGWEvent> events = new NSMutableArray<ERGWEvent>();
    NSMutableArray<ERGWTask> tasks = new NSMutableArray<ERGWTask>();

    for (Object component : je.getComponents(Component.VEVENT)) {
      ERGWEvent event = new ERGWEvent(newCalendar);
      ERGWEvent.transformFromICalObject((VEvent)component, event, newCalendar);
      events.addObject(event);
    }
    for (Object component : je.getComponents(Component.VTODO)) {
      ERGWTask task = new ERGWTask(newCalendar);
      ERGWTask.transformFromICalObject((VToDo)component, task, newCalendar);
      tasks.addObject(task);
    }
    newCalendar.setEvents(events);
    newCalendar.setTasks(tasks);
    return newCalendar;
  }

  public static XMLElement transformToZimbraObject(ERGWCalendar calendar) throws ZimbraTooManyObjectsException {
    if ((calendar.events().count() > 1) || (calendar.tasks().count() > 1)) {
      throw new ZimbraTooManyObjectsException("Calendar objects in Zimbra can only have one event or task");
    }
    if ((calendar.events().count() == 1) && (calendar.tasks().count() == 1)) {
      throw new ZimbraTooManyObjectsException("Calendar objects in Zimbra can only have one event or task, they can't have both");
    }
    XMLElement invitation = new XMLElement(MailConstants.E_INVITE);

    if (calendar.events().count() == 1) {
      Element vEvent = ERGWEvent.transformToZimbraObject(calendar.events().objectAtIndex(0));
      for (ERGWAlarm alarm: calendar.alarms()) {
        ERGWAlarm.transformToZimbraObject(alarm, vEvent);
      }
      invitation.addElement(vEvent);
    }
    if (calendar.tasks().count() == 1) {
      Element vTodo = ERGWTask.transformToZimbraObject(calendar.tasks().objectAtIndex(0));
      for (ERGWAlarm alarm: calendar.alarms()) {
        ERGWAlarm.transformToZimbraObject(alarm, vTodo);
      }
      invitation.addElement(vTodo);
    }

    return invitation;
  }

  public static ERGWCalendar transformFromZimbraResponse(XMLElement je) throws ServiceException {
    ERGWCalendar newCalendar = new ERGWCalendar();
    newCalendar.setProductId(defaultProdId);
    Element appt = je.getElement("appt");

    NSMutableArray<ERGWEvent> events = new NSMutableArray<ERGWEvent>();

    for (Element inviteEl : appt.listElements(MailConstants.E_INVITE)) {
      for (Element component : inviteEl.listElements(MailConstants.A_CAL_COMP)) {
        ERGWEvent event = new ERGWEvent(newCalendar);
        ERGWEvent.transformFromZimbraResponse(component, event);
        events.addObject(event);
        ERGWAlarm.transformFromZimbraResponse(component, newCalendar);
      }
    }
    newCalendar.setEvents(events);
    return newCalendar;
  }

}
