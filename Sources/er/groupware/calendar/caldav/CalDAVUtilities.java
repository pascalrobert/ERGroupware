package er.groupware.calendar.caldav;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.jackrabbit.webdav.property.DavPropertySet;

import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.connector.dav.CalDavCalendarStore;
import net.fortuna.ical4j.connector.dav.PathResolver;
import net.fortuna.ical4j.model.ConstraintViolationException;

import com.webobjects.foundation.NSArray;

import er.groupware.calendar.ERGWCalendar;
import er.groupware.calendar.ERGWCalendarCollection;

public class CalDAVUtilities {

  private CalDavCalendarStore store;

  public void connect(String username, String password, URL url, PathResolver pathResolver) throws ObjectStoreException {
    store = new CalDavCalendarStore("-//OACIQ//TA2MKS//EN", url, pathResolver);
    store.connect(username, password.toCharArray());
  }

  public void disconnect() {
    store.disconnect();
  }

  /**
   * @return An array of CalDAV collections found in to the store. Delegated collections are not returned by this.
   * If no collections are found, the array will be empty.
   */
  public NSArray<CalDavCalendarCollection> getCollections() {
    NSArray<CalDavCalendarCollection> collections = new NSArray<CalDavCalendarCollection>();
    try {
      collections = new NSArray<CalDavCalendarCollection>(store.getCollections());
    } catch (ObjectStoreException e) {
      e.printStackTrace();
    } catch (ObjectNotFoundException e) {
      e.printStackTrace();
    }
    return collections;
  }
  
  public void createCollection(String id, ERGWCalendarCollection collection) throws ObjectStoreException, ParserConfigurationException {
    store.addCollection(id, ERGWCalendarCollection.convertPropertiesToDavPropertySet(collection));
  }

  public void addCalendarObject(ERGWCalendar calendar, CalDavCalendarCollection collection) throws ConstraintViolationException, ObjectNotFoundException, ObjectStoreException, SocketException, ParseException, URISyntaxException {
    net.fortuna.ical4j.model.Calendar icalCalendar = ERGWCalendar.transformToICalObject(calendar);
    collection.addCalendar(icalCalendar);
  }
}
