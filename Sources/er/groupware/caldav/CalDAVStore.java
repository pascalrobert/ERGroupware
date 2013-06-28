package er.groupware.caldav;

import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import net.fortuna.ical4j.connector.ObjectNotFoundException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.CalDavCalendarCollection;
import net.fortuna.ical4j.connector.dav.CalDavCalendarStore;
import net.fortuna.ical4j.connector.dav.PathResolver;
import net.fortuna.ical4j.connector.dav.enums.MediaType;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.property.Attendee;

import org.apache.jackrabbit.webdav.DavException;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.groupware.calendar.ERGWAttendee;
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

  public NSArray<CalDAVCollection> getCollections() {
    return getCollections(false);
  }

  
  /**
   * @return An array of CalDAV collections found in to the store. Delegated collections are not returned by this.
   * If no collections are found, the array will be empty.
   * @throws URISyntaxException 
   * @throws SocketException 
   */
  public NSArray<CalDAVCollection> getCollections(boolean fetchObjects) {
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
        
        if (fetchObjects) {
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
  
  protected NSArray<ERGWAttendee> convertAttendeeToERGWAttendee(List<Attendee> list) {
    NSMutableArray<ERGWAttendee> array = new NSMutableArray<ERGWAttendee>();
    if (list != null) {
      for (Attendee room: list) {
        array.addObject(ERGWAttendee.transformFromICalObject(room));
      }
    }
    return array.immutableClone();
  }
  
  public NSArray<ERGWAttendee> findAllRooms() {
    NSArray<ERGWAttendee> array = new NSArray<ERGWAttendee>();
    try {
      array = convertAttendeeToERGWAttendee(store.getAllRooms());
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (DavException e) {
      e.printStackTrace();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return array;
  }
  
  public NSArray<ERGWAttendee> findAllResources() {
    NSArray<ERGWAttendee> array = new NSArray<ERGWAttendee>();
    try {
      array = convertAttendeeToERGWAttendee(store.getAllResources());
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (DavException e) {
      e.printStackTrace();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return array;  
  }

  public NSArray<ERGWAttendee> findIndividuals(String name) {
    NSArray<ERGWAttendee> array = new NSArray<ERGWAttendee>();
    try {
      array = convertAttendeeToERGWAttendee(store.getIndividuals(name));
    }
    catch (ParserConfigurationException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (DavException e) {
      e.printStackTrace();
    }
    catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return array;  
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
