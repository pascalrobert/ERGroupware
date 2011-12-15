package er.groupware.calendar.caldav;

import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.connector.dav.CalDavCalendarStore;
import net.fortuna.ical4j.connector.dav.PathResolver;

import com.webobjects.foundation.NSArray;

import er.groupware.calendar.ERGWCalendar;
import er.groupware.calendar.ERGWCalendarCollection;

// Rename to CalDAVStore?
public class CalDAVStore {

  private CalDavCalendarStore store;
  
  public CalDAVStore(String username, String password, URL url, PathResolver pathResolver) throws ObjectStoreException {
    store = new CalDavCalendarStore(ERGWCalendar.defaultProdId.getValue(), url, pathResolver);
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

  // delete collection
  // report (find events/tasks, find journal) per collection + per store
  // find delegated collections
  // free-busy query
  // finding supported operations 
  // if-none-match handling when updating object
  // find pending tasks
  // finding principals and calendar-home-set and using genericpathresolver
  // finding invitations
}
