package er.groupware.calendar.caldav;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;

import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.model.ConstraintViolationException;
import er.groupware.calendar.ERGWCalendar;

public class CalDAVCollection {

  // Move to CalDAVCollection?
  public void addCalendarObject(ERGWCalendar calendar, CalDavCalendarCollection collection) throws ConstraintViolationException, ObjectNotFoundException, ObjectStoreException, SocketException, ParseException, URISyntaxException {
    net.fortuna.ical4j.model.Calendar icalCalendar = ERGWCalendar.transformToICalObject(calendar);
    collection.addCalendar(icalCalendar);
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
