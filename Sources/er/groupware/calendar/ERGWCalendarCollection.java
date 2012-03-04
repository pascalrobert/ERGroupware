package er.groupware.calendar;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.fortuna.ical4j.connector.dav.CalDavConstants;
import net.fortuna.ical4j.connector.dav.property.CalDavPropertyName;
import net.fortuna.ical4j.connector.dav.property.ICalPropertyName;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Version;

import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertySet;
import org.apache.jackrabbit.webdav.property.DefaultDavProperty;
import org.apache.jackrabbit.webdav.xml.DomUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSValidation;

public class ERGWCalendarCollection {

  // http://192.168.3.7/calendars/__uids__/C0F07FAF-A20A-4A88-A518-D27E5D3074CA/calendar/
  
  private String id;
  private String displayName;  
  private String description; // Provides a human-readable description of the calendar collection.
  private NSArray<String> supportedComponents; // VALARM, VEVENT, VAVAILABILITY, VFREEBUSY, VJOURNAL, VTIMEZONE, VTODO, VVENUE
  private NSArray<String> supportedDataTypes; // <C:calendar-data content-type="text/calendar" version="2.0"/>
  private String color;
  private TimeZone timeZone; // The CALDAV:calendar-timezone property is used to specify the time zone the server should rely on to resolve "date values and "date with local time" values (i.e., floating time) to "date with UTC time" values.
  private int maxResourceSize; // Provides a numeric value indicating the maximum size of a resource in octets that the server is willing to accept when a calendar object resource is stored in a calendar collection.
  private NSTimestamp minDateTime; // Provides a DATE-TIME value indicating the earliest date and time (in UTC) that the server is willing to accept for any DATE or DATE-TIME value in a calendar object resource stored in a calendar collection.
  private NSTimestamp maxDateTime; // Provides a DATE-TIME value indicating the latest date and time (in UTC) that the server is willing to accept for any DATE or DATE-TIME value in a calendar object resource stored in a calendar collection.
  private int maxInstances; // Provides a numeric value indicating the maximum number of recurrence instances that a calendar object resource stored in a calendar collection can generate.
  private int maxAttendeesPerInstance; // Provides a numeric value indicating the maximum number of ATTENDEE properties in any instance of a calendar object resource stored in a calendar collection.
  private int calendarOrder;
  // private NSArray acls;
  // {urn:ietf:params:xml:ns:caldav}schedule-calendar-transp
  
  public ERGWCalendarCollection() {
    
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
   * rfc4791
   * Name: supported-calendar-data
   * Purpose:  Specifies what media types are allowed for calendar object
      resources in a calendar collection.
   */
  public NSArray<String> supportedDataTypes() {
    return supportedDataTypes;
  }
  
  public void setSupportedDataTypes(NSArray<String> supportedDataTypes) {
    this.supportedDataTypes = supportedDataTypes;
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
  
  public static DavPropertySet convertPropertiesToDavPropertySet(ERGWCalendarCollection collection) throws ParserConfigurationException {
    if (collection.timeZone() == null) {
      throw new NSValidation.ValidationException("Timezone can't be null");
    }

    DavPropertySet properties = new DavPropertySet();

    properties.add(new DefaultDavProperty(DavPropertyName.DISPLAYNAME, collection.displayName()));
    properties.add(new DefaultDavProperty(CalDavPropertyName.CALENDAR_DESCRIPTION, collection.description()));
    properties.add(new DefaultDavProperty(ICalPropertyName.CALENDAR_COLOR, collection.color()));
    properties.add(new DefaultDavProperty(ICalPropertyName.CALENDAR_ORDER, collection.calendarOrder()));
    
    ArrayList<Element> componentsProperty = new ArrayList<Element>();
    for (String component: collection.supportedComponents()) {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.newDocument();
      Element xmlProp = DomUtil.createElement(document, CalDavPropertyName.COMPONENT.getName(), CalDavConstants.CALDAV_NAMESPACE);
      xmlProp.setAttribute("name", component);
      componentsProperty.add(xmlProp);
    }
    properties.add(new DefaultDavProperty(CalDavPropertyName.SUPPORTED_CALENDAR_COMPONENT_SET, componentsProperty));
    
    Calendar timeZoneCalendar = new Calendar();
    timeZoneCalendar.getProperties().add(Version.VERSION_2_0);
    timeZoneCalendar.getProperties().add(CalScale.GREGORIAN);
    timeZoneCalendar.getProperties().add(ERGWCalendar.defaultProdId);
    timeZoneCalendar.getComponents().add(collection.timeZone().getVTimeZone());
    properties.add(new DefaultDavProperty(CalDavPropertyName.CALENDAR_TIMEZONE, timeZoneCalendar.toString()));
    
    return properties;
  }

  /* 
     {http://calendarserver.org/ns/}allowed-sharing-modes
     http://svn.calendarserver.org/repository/calendarserver/CalendarServer/trunk/doc/Extensions/caldav-sharing-02.txt
     Purpose:  Used to show which modes of sharing are supported on a calendar collection.
     Description:  This WebDAV property is present on a calendar
      collection resource that can been shared or published.  It
      provides a list of options indicating what sharing modes are
      allowed as per Section 5.5.2.
      
      CS:can-be-shared (Section 6.3): when present indicates that the
      calendar collection can be shared.  When not present, the calendar
      collection cannot be shared. 
      
      CS:can-be-published (Section 6.4): when present indicates that the
      calendar collection can be published.  When not present, the calendar 
      collection cannot be published.
   */
  /*
    {http://calendarserver.org/ns/}getctag
    https://trac.calendarserver.org/browser/CalendarServer/trunk/doc/Extensions/caldav-ctag.txt
    Purpose:  Specifies a "synchronization" token used to indicate when
       the contents of a calendar or scheduling Inbox or Outbox
       collection have changed. 
    Description:  The CS:getctag property allows clients to quickly
       determine if the contents of a calendar or scheduling Inbox or
       Outbox collection have changed since the last time a
       "synchronization" operation was done.  The CS:getctag property
       value MUST change each time the contents of the calendar or
       scheduling Inbox or Outbox collection change, and each change MUST
       result in a value that is different from any other used with that
       collection URI.
   */
  /* 
     {http://calendarserver.org/ns/}invite
     http://svn.calendarserver.org/repository/calendarserver/CalendarServer/trunk/doc/Extensions/caldav-sharing-02.txt
     Purpose:  Used to show to whom a calendar has been shared.
     Description:  This WebDAV property is present on a calendar
      collection resource that has been shared by the owner.  It MUST
      NOT appear on the calendar collection resources of the sharees of
      the calendar.  It provides a list of users to whom the calendar
      has been shared, along with the "status" of the sharing invites
      sent to each user.
   */
  // {http://calendarserver.org/ns/}max-resources
  // {http://calendarserver.org/ns/}pushkey
  /* 
     {http://calendarserver.org/ns/}shared-url
     http://svn.calendarserver.org/repository/calendarserver/CalendarServer/trunk/doc/Extensions/caldav-sharing-02.txt
     Purpose:  Indicates the URL of the owner's copy of a shared calendar.
     Description:  This WebDAV property is present on a shared calendar
      collection resource that appears in a sharee's calendar home
      collection.  Its content is a single DAV:href element whose value
      is the URL of the sharer's calendar being shared.
   */
  // {http://me.com/_namespace/}bulk-requests
  /*
<?xml version='1.0' encoding='UTF-8'?>
<bulk-requests xmlns='http://me.com/_namespace/'>
  <simple>
    <max-resources>100</max-resources>
    <max-bytes>10485760</max-bytes>
  </simple>
  <crud>
    <max-resources>100</max-resources>
    <max-bytes>10485760</max-bytes>
  </crud>
</bulk-requests>
   */
  // 
}
