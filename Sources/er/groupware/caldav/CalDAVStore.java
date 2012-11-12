package er.groupware.caldav;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.connector.dav.CalDavCalendarStore;
import net.fortuna.ical4j.connector.dav.PathResolver;
import net.fortuna.ical4j.connector.dav.enums.MediaType;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.groupware.calendar.ERGWCalendar;
import er.groupware.calendar.ERGWCalendarCollection;
import er.groupware.enums.ERGWSupportedObjectType;

public class CalDAVStore {

  protected CalDavCalendarStore store;
  
  public CalDAVStore(String username, String password, URL url, PathResolver pathResolver) throws ObjectStoreException {
    store = new CalDavCalendarStore(ERGWCalendar.defaultProdId.getValue(), url, pathResolver);
    store.connect(username, password.toCharArray());
  }

  public void disconnect() {
    store.disconnect();
  }
  
  public CalDavCalendarStore originalStore() {
    return store;
  }

  /**
   * @return An array of CalDAV collections found in to the store. Delegated collections are not returned by this.
   * If no collections are found, the array will be empty.
   * @throws URISyntaxException 
   * @throws SocketException 
   */
  public NSArray<CalDAVCollection> getCollections() {
    NSMutableArray<CalDAVCollection> collections = new NSMutableArray<CalDAVCollection>();
    try {
      for (CalDavCalendarCollection caldavcollection: store.getCollections()) {
        CalDAVCollection collection = new CalDAVCollection(caldavcollection);
        
        collection.setCalendarOrder(caldavcollection.getOrder());
        collection.setColor(caldavcollection.getColor());
        collection.setTimeZone(null);
        //collection.setDescription(caldavcollection.getDescription());
        collection.setDisplayName(caldavcollection.getDisplayName());
        collection.setId(caldavcollection.getId());
        collection.setSupportedComponents(new NSArray<String>(caldavcollection.getSupportedComponentTypes()));
        
        NSMutableArray<ERGWSupportedObjectType> supportedMediaTypes = new NSMutableArray<ERGWSupportedObjectType>();
        for (MediaType mediaType: caldavcollection.getSupportedMediaTypes()) {
          supportedMediaTypes.addObject(ERGWSupportedObjectType.getByRFC2445Value(mediaType));
        }
        collection.setSupportedObjectTypes(supportedMediaTypes.immutableClone());
        
        NSMutableArray<ERGWCalendar> events = new NSMutableArray<ERGWCalendar>();
        NSMutableArray<ERGWCalendar> tasks = new NSMutableArray<ERGWCalendar>();
        
        for (String componentType: caldavcollection.getSupportedComponentTypes()) {
          if (Component.VEVENT.equals(componentType)) {
            Calendar[] calendars = caldavcollection.getEvents();
            for (Calendar calendar: calendars) {
              events.addObject(ERGWCalendar.transformFromICalResponse(calendar));
            }
          }
          if (Component.VTODO.equals(componentType)) {
            Calendar[] calendars = caldavcollection.getTasks();
            for (Calendar calendar: calendars) {
              tasks.addObject(ERGWCalendar.transformFromICalResponse(calendar));
            }
          }
        }
        
        collection.setEvents(events);
        collection.setTasks(tasks);
       
        collections.add(collection);
      }
    } catch (ObjectStoreException e) {
      e.printStackTrace();
    } catch (ObjectNotFoundException e) {
      e.printStackTrace();
    }
    return collections.immutableClone();
  }
  
  public void createCollection(String id, CalDAVCollection collection) throws ObjectStoreException, ParserConfigurationException {
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
