package er.groupware.caldav;

import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ConstraintViolationException;
import net.fortuna.ical4j.model.DateTime;

import org.apache.jackrabbit.webdav.DavException;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import er.groupware.calendar.ERGWCalendar;
import er.groupware.calendar.ERGWCalendarCollection;

public class CalDAVCollection extends ERGWCalendarCollection {

  protected NSArray<ERGWCalendar> events;
  protected NSArray<ERGWCalendar> tasks;
  protected CalDavCalendarCollection originalCollection;
  
  public CalDAVCollection() {
    super();
  }
  
  public CalDAVCollection(CalDavCalendarCollection caldavcollection) {
    super();
    this.originalCollection = caldavcollection;
  }

  // Move to CalDAVCollection?
  public void addCalendarObject(ERGWCalendar calendar, CalDavCalendarCollection collection) throws ConstraintViolationException, ObjectNotFoundException, ObjectStoreException, SocketException, ParseException, URISyntaxException {
    net.fortuna.ical4j.model.Calendar icalCalendar = ERGWCalendar.transformToICalObject(calendar);
    collection.addCalendar(icalCalendar);
  }

  public void setEvents(NSArray<ERGWCalendar> events) {
    this.events = events;
  }
  
  public NSArray<ERGWCalendar> events() {
    return events;
  }
  
  public void setTasks(NSArray<ERGWCalendar> tasks) {
    this.tasks = tasks;
  }
  
  public NSArray<ERGWCalendar> tasks() {
    return tasks;
  }
  
  public NSArray<ERGWCalendar> eventsForTimePeriod(java.util.Date startTime, java.util.Date endTime) {
    NSMutableArray<ERGWCalendar> events = new NSMutableArray<ERGWCalendar>();
    try {
      Calendar[] calendarObjects = originalCollection.getEventsForTimePeriod(originalCollection, new DateTime(startTime), new DateTime(endTime));
      for (Calendar calendarObject: calendarObjects) {
        events.addObject(ERGWCalendar.transformFromICalResponse(calendarObject));
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (DavException e) {
      e.printStackTrace();
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    catch (ParserException e) {
      e.printStackTrace();
    }
    return events;
  }
  
  // proppatch to change collection proprietes
  // remove calendar object
  // modify calendar object
  // report (find events/tasks, find journal) per collection + per store
  // share collection
  // set acl on collection
  // move calendar object from collection to another
  // copy calendar object from collection to another
  // finding supported operations 
  // localization support for displayname and description
  // if-none-match handling when updating object
  // better handling of 207 status code when creating collection
  // support of etag of objects and collections
  // find pending tasks

}
