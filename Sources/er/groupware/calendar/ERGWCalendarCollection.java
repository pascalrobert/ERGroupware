package er.groupware.calendar;

import java.math.BigInteger;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.component.VTimeZone;
import zswi.objects.dav.collections.CalendarCollection;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import er.groupware.caldav.CalDAVCollection;
import er.groupware.enums.ERGWFolderType;
import er.groupware.enums.ERGWSupportedObjectType;
import er.groupware.interfaces.IERGWBaseFolder;

public class ERGWCalendarCollection implements IERGWBaseFolder {

  // http://192.168.3.7/calendars/__uids__/C0F07FAF-A20A-4A88-A518-D27E5D3074CA/calendar/
  
  protected String id;
  protected String displayName;  
  protected String description; // Provides a human-readable description of the calendar collection.
  protected NSArray<String> supportedComponents; // VALARM, VEVENT, VAVAILABILITY, VFREEBUSY, VJOURNAL, VTIMEZONE, VTODO, VVENUE
  protected String color;
  protected TimeZone timeZone; // The CALDAV:calendar-timezone property is used to specify the time zone the server should rely on to resolve "date values and "date with local time" values (i.e., floating time) to "date with UTC time" values.
  protected int maxResourceSize; // Provides a numeric value indicating the maximum size of a resource in octets that the server is willing to accept when a calendar object resource is stored in a calendar collection.
  protected NSTimestamp minDateTime; // Provides a DATE-TIME value indicating the earliest date and time (in UTC) that the server is willing to accept for any DATE or DATE-TIME value in a calendar object resource stored in a calendar collection.
  protected NSTimestamp maxDateTime; // Provides a DATE-TIME value indicating the latest date and time (in UTC) that the server is willing to accept for any DATE or DATE-TIME value in a calendar object resource stored in a calendar collection.
  protected int maxInstances; // Provides a numeric value indicating the maximum number of recurrence instances that a calendar object resource stored in a calendar collection can generate.
  protected int maxAttendeesPerInstance; // Provides a numeric value indicating the maximum number of ATTENDEE properties in any instance of a calendar object resource stored in a calendar collection.
  protected int calendarOrder;
  protected ERGWFolderType folderType;
  protected NSArray<ERGWSupportedObjectType> supportedObjectTypes;
  // private NSArray acls;
  // {urn:ietf:params:xml:ns:caldav}schedule-calendar-transp
  
  public ERGWCalendarCollection() {
    this.setFolderType(ERGWFolderType.CALENDAR);
    this.supportedObjectTypes = new NSArray<ERGWSupportedObjectType>();
    this.supportedComponents = new NSArray<String>();
  }
  
  /**
   * rfc4918 (webdav)
   * Name:   displayname
   * Purpose:   Provides a name for the resource that is suitable for
      presentation to a user.
   */
  public String displayName() {
    return displayName;
  }
  
  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }
  
  /**
   * rfc4791
   * Name:  calendar-description
   * Purpose:  Provides a human-readable description of the calendar
      collection.
   */
  public String description() {
    return description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String id() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  /**
   * rfc4791
   * Name:  supported-calendar-component-set
   * Purpose:  Specifies the calendar component types (e.g., VEVENT,
      VTODO, etc.) that calendar object resources can contain in the
      calendar collection.
   */
  public NSArray<String> supportedComponents() {
    return supportedComponents;
  }
  
  public void setSupportedComponents(NSArray<String> supportedComponents) {
    this.supportedComponents = supportedComponents;
  }
  
  /**
   * iCal specific 
   * Name:  calendar-color
   * Purpose: Specifies the color of the calendar as selected by the user in iCal.
   */
  public String color() {
    return color;
  }
  
  public void setColor(String color) {
    this.color = color;
  }
  
  /**
   * rfc4791
   * Name:  calendar-timezone
   * Purpose:  Specifies a time zone on a calendar collection. The 
      CALDAV:calendar-timezone property is used to specify the time zone 
      the server should rely on to resolve "date" values and "date with 
      local time" values (i.e., floating time) to "date with UTC time" values.
   */
  public TimeZone timeZone() {
    return timeZone;
  }
  
  public void setTimeZone(TimeZone timeZone) {
    this.timeZone = timeZone;
  }
  
  /**
   * rfc4791
   * Name:  max-resource-size
   * Read-only property!
   * Purpose:  Provides a numeric value indicating the maximum size of a
      resource in octets that the server is willing to accept when a
      calendar object resource is stored in a calendar collection. In the 
      absence of this property, the client can assume that the server will 
      allow storing a resource of any reasonable size.
   */
  public int maxResourceSize() {
    return maxResourceSize;
  }
  
  /**
   * rfc4791
   * Read-only property!
   * Name:  min-date-time
   * Purpose:  Provides a DATE-TIME value indicating the earliest date and
      time (in UTC) that the server is willing to accept for any DATE or
      DATE-TIME value in a calendar object resource stored in a calendar
      collection. In the absence of this property, the client can
      assume any valid iCalendar date may be used at least up to the
      CALDAV:max-date-time value, if that is defined.
   */
  public NSTimestamp minDateTime() {
    return minDateTime;
  }
  
  /**
   * rfc4791
   * Read-only property!
   * Name:  max-date-time
   * Purpose:  Provides a DATE-TIME value indicating the latest date and
      time (in UTC) that the server is willing to accept for any DATE or
      DATE-TIME value in a calendar object resource stored in a calendar
      collection. In the absence of this property, the client can assume
      any valid iCalendar date may be used at least down to the CALDAV:
      min-date-time value, if that is defined.
   */
  public NSTimestamp maxDateTime() {
    return maxDateTime;
  }
  
  /**
   * rfc4791
   * Read-only property!
   * Name:  max-instances
   * Purpose:  Provides a numeric value indicating the maximum number of
      recurrence instances that a calendar object resource stored in a
      calendar collection can generate. In the absence of
      this property, the client can assume that the server has no limits
      on the number of recurrence instances it can handle or expand.
   */
  public int maxInstances() {
    return maxInstances;
  }
  
  /**
   * rfc4791
   * Read-only property!
   * Name: max-attendees-per-instance
   * Purpose:  Provides a numeric value indicating the maximum number of
      ATTENDEE properties in any instance of a calendar object resource
      stored in a calendar collection.
   */
  public int maxAttendeesPerInstance() {
    return maxAttendeesPerInstance;
  }
  
  /**
   * iCal specific, return the order (per account) in which the collection is in iCal.
   * Useful if you want to display the collections in the same order as iCal.
   */
  public int calendarOrder() {
    return calendarOrder;
  }
  
  public void setCalendarOrder(int calendarOrder) {
    this.calendarOrder = calendarOrder;
  }
  
  public ERGWFolderType folderType() {
    return folderType;
  }

  public void setFolderType(ERGWFolderType folderType) {
    this.folderType = folderType;
  }

  // TODO Declare as not supported
  public <T extends IERGWBaseFolder> IERGWBaseFolder parent() {
    return null;
  }

  // TODO Declare as not supported
  public <T extends IERGWBaseFolder> void setParent(IERGWBaseFolder parent) {    
  }

  public NSArray<ERGWSupportedObjectType> supportedObjectTypes() {
    return this.supportedObjectTypes;
  }

  public void setSupportedObjectTypes(NSArray<ERGWSupportedObjectType> supportedObjectTypes) {
    this.supportedObjectTypes = supportedObjectTypes;
  }

  public void addSupportedObjectType(ERGWSupportedObjectType objectType) {
    NSMutableArray<ERGWSupportedObjectType> array = this.supportedObjectTypes().mutableClone();
    array.addObject(objectType);
    this.setSupportedObjectTypes(array.immutableClone());
  }

  /**
   * @return false, since CalDAV collections can't have children
   */
  public boolean canHaveChildren() {
    return false;
  }

  // TODO return not supported exception
  public NSArray<IERGWBaseFolder> children() {
    return null;
  }

  // TODO return not supported exception
  public void setChildren(NSArray<IERGWBaseFolder> children) {
  }

  // TODO return not supported exception
  public void addChild(IERGWBaseFolder children) {
  }
  
  public static CalendarCollection convertToCalendarCollection(CalDAVCollection collection) {
	CalendarCollection convertedCollection = new CalendarCollection(collection.id());
	convertedCollection.setCalendarColor(collection.color());
	convertedCollection.setCalendarDescription(collection.description());
	convertedCollection.setCalendarOrder(new BigInteger(Integer.valueOf(collection.calendarOrder()).toString()));
	convertedCollection.setDisplayName(collection.displayName());
	//convertedCollection.setScheduleCalendarTransp(collection.);
	//convertedCollection.setSupportedFeatures(collection.supportedObjectTypes());

	CalendarBuilder builder = new CalendarBuilder();
	TimeZoneRegistry registry = builder.getRegistry();
	Calendar calendar = new Calendar();
	VTimeZone tz = registry.getTimeZone(collection.timeZone().getID()).getVTimeZone();
	calendar.getComponents().add(tz);
	convertedCollection.setCalendarTimezone(calendar);

	convertedCollection.setSupportedCalendarComponentSet(collection.supportedComponents().arrayList());
	return convertedCollection;
  }
  

}
