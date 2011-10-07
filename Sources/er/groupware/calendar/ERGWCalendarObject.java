package er.groupware.calendar;

import java.math.BigDecimal;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.CuType;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.parameter.XParameter;
import net.fortuna.ical4j.model.property.BusyType;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.Clazz;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.Geo;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Url;
import net.fortuna.ical4j.util.UidGenerator;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.Element.XMLElement;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.cs.zclient.ZDateTime;
import com.zimbra.cs.zclient.ZInvite.ZAttendee;
import com.zimbra.cs.zclient.ZInvite.ZClass;
import com.zimbra.cs.zclient.ZInvite.ZComponent.ZReply;
import com.zimbra.cs.zclient.ZInvite.ZFreeBusyStatus;
import com.zimbra.cs.zclient.ZInvite.ZOrganizer;
import er.extensions.eof.ERXKey;
import er.extensions.validation.ERXValidationException;
import er.groupware.calendar.enums.ERGWAttendeeRole;
import er.groupware.calendar.enums.ERGWCUType;
import er.groupware.calendar.enums.ERGWClassification;
import er.groupware.calendar.enums.ERGWFreeBusyStatus;
import er.groupware.calendar.enums.ERGWIStatus;
import er.groupware.calendar.enums.ERGWParticipantStatus;
import er.groupware.calendar.enums.ERGWPriority;

public abstract class ERGWCalendarObject {

  private NSMutableArray<ERGWAttendee> attendees;
  private NSMutableArray<ERGWAttendee> resources;
  private ERGWFreeBusyStatus freeBusyStatus;
  private String categories;
  private ERGWClassification classification;
  private String description;
  private NSTimestamp endTime;
  private NSTimestamp startTime;
  private Dur duration;
  private Geo geo;
  private String location;
  private ERGWOrganizer organizer;
  private ERGWPriority priority;
  private String summary;
  private String url;
  private boolean isFullDay;
  private TimeZone timezone;
  private String uid;
  private NSTimestamp lastModifiedDate;
  private NSTimestamp creationDate;
  private String parentId;

  public static final ERXKey<ERGWCalendar> CALENDAR = new ERXKey<ERGWCalendar>("calendar");
  public static final ERXKey<ERGWAttendee> ATTENDEES = new ERXKey<ERGWAttendee>("attendees");
  public static final ERXKey<ERGWAttendee> RESOURCES = new ERXKey<ERGWAttendee>("resources");
  public static final ERXKey<ERGWFreeBusyStatus> FREE_BUSY_STATUS = new ERXKey<ERGWFreeBusyStatus>("freeBusyStatus");
  public static final ERXKey<String> CATEGORIES = new ERXKey<String>("categories");
  public static final ERXKey<ERGWClassification> CLASSIFICATION = new ERXKey<ERGWClassification>("classification");
  public static final ERXKey<String> DESCRIPTION = new ERXKey<String>("description");
  public static final ERXKey<NSTimestamp> END_TIME = new ERXKey<NSTimestamp>("endTime");
  public static final ERXKey<NSTimestamp> START_TIME = new ERXKey<NSTimestamp>("startTime");
  public static final ERXKey<Dur> DURATION = new ERXKey<Dur>("duration");
  public static final ERXKey<Geo> GEO = new ERXKey<Geo>("geo");
  public static final ERXKey<String> LOCATION = new ERXKey<String>("location");
  public static final ERXKey<ERGWOrganizer> ORGANIZER = new ERXKey<ERGWOrganizer>("organizer");
  public static final ERXKey<ERGWPriority> PRIORITY = new ERXKey<ERGWPriority>("priority");
  public static final ERXKey<String> SUMMARY = new ERXKey<String>("summary");
  public static final ERXKey<String> URL = new ERXKey<String>("url");
  public static final ERXKey<Boolean> IS_FULL_DAY = new ERXKey<Boolean>("isFullDay");
  public static final ERXKey<TimeZone> TIMEZONE = new ERXKey<TimeZone>("timezone");
  public static final ERXKey<ERGWAlarm> ALARMS = new ERXKey<ERGWAlarm>("alarms");
  public static final ERXKey<String> UID = new ERXKey<String>("uid");
  public static final ERXKey<NSTimestamp> LAST_MODIFIED_DATE = new ERXKey<NSTimestamp>("lastModifiedDate");
  public static final ERXKey<NSTimestamp> CREATION_DATE = new ERXKey<NSTimestamp>("creationDate");
  public static final ERXKey<String> PARENT_ID = new ERXKey<String>("parentId");

  public ERGWCalendarObject() {
    this.attendees = new NSMutableArray<ERGWAttendee>();
    this.resources = new NSMutableArray<ERGWAttendee>();
  }

  public abstract ERGWIStatus status();
  public abstract void setStatus(ERGWIStatus status);

  /*
  protected CalendarComponent calComponent() {
    return calComponent;
  }

  protected PropertyList properties() {
    return calComponent.getProperties();
  }
  */

  public String uid() {
    return uid;
  }
  
  public void setUid(String uid) {
    this.uid = uid;
  }
  
  public NSArray<ERGWAttendee> attendees() {
    return this.attendees;
  }

  public void setAttendees(NSArray<ERGWAttendee> _attendees) {
    this.attendees = _attendees.mutableClone();
  }

  public void addAttendee(ERGWAttendee attendee) {
    this.attendees.addObject(attendee);
  }

  public NSArray<ERGWAttendee> resources() {
    return this.resources;
  }

  public void setResources(NSArray<ERGWAttendee> _resources) {
    this.resources = _resources.mutableClone();
  }

  public void addResource(ERGWAttendee resource) {
    this.resources.addObject(resource);
  }

  public ERGWFreeBusyStatus freeBusyStatus() {
    return freeBusyStatus;
  }

  public void setFreeBusyStatus(ERGWFreeBusyStatus _freeBusyStatus) {
    this.freeBusyStatus = _freeBusyStatus;
  }

  public String categories() {
    return categories;
  }

  public void setCategories(String _categories) {
    this.categories = _categories;
  }

  public ERGWClassification classification() {
    return classification;
  }

  public void setClassification(ERGWClassification _classification) {
    this.classification = _classification;
  }

  public String description() {
    return description;
  }

  public void setDescription(String _description) {
    this.description = _description;
  }

  public NSTimestamp endTime() {
    return endTime;
  }

  public void setEndTime(NSTimestamp _endTime) {
    this.endTime = _endTime;
  }

  public NSTimestamp startTime() {
    return startTime;
  }

  public void setStartTime(NSTimestamp _startTime) {
    this.startTime = _startTime;
  }

  public Dur duration() {
    return duration;
  }

  public void setDuration(Dur _duration) {
    this.duration = _duration;
  }

  public Geo geo() {
    return geo;
  }

  public void setGeo(Geo _geo) {
    this.geo = _geo;
  }

  public String location() {
    return location;
  }

  public void setLocation(String _location) {
    this.location = _location;
  }

  public ERGWOrganizer organizer() {
    return organizer;
  }

  public void setOrganizer(ERGWOrganizer _organizer) {
    this.organizer = _organizer;
  }

  public ERGWPriority priority() {
    return priority;
  }

  public void setPriority(ERGWPriority _priority) {
    this.priority = _priority;
  }

  public String summary() {
    return summary;
  }

  public void setSummary(String _summary) {
    this.summary = _summary;
  }

  public String url() {
    return url;
  }

  public void setUrl(String _url) {
    this.url = _url;
  }

  public boolean isFullDay() {
    return isFullDay;
  }

  public void setIsFullDay(boolean _isFullDay) {
    this.isFullDay = _isFullDay;
  }

  public TimeZone timezone() {
    return timezone;
  }

  public void setTimezone(TimeZone _timezone) {
    this.timezone = _timezone;
  }
  
  public NSTimestamp lastModifiedDate() {
    return lastModifiedDate;
  }
  
  public void setLastModifiedDate(NSTimestamp _lastModifiedDate) {
    this.lastModifiedDate = _lastModifiedDate;
  }
  
  public NSTimestamp creationDate() {
    return creationDate;
  }
  
  public void setCreationDate(NSTimestamp _creationDate) {
    this.creationDate = _creationDate;
  }
  
  public String parentId() {
    return parentId;
  }
  
  public void setParentId(String _parentId) {
    this.parentId = _parentId;
  }

  public static net.fortuna.ical4j.model.property.Attendee convertAttendee(ERGWAttendee attendee) {
    net.fortuna.ical4j.model.property.Attendee icAttendee = new net.fortuna.ical4j.model.property.Attendee(URI.create("mailto:"  + attendee.emailAddress()));
    
    if (attendee.role() != null) {
      icAttendee.getParameters().add(attendee.role().rfc2445Value());
    } else {
      icAttendee.getParameters().add(Role.REQ_PARTICIPANT);
    }
    
    icAttendee.getParameters().add(new Cn(attendee.name()));
    icAttendee.getParameters().add(new Rsvp(attendee.isRsvp()));        
    icAttendee.getParameters().add(new XParameter("X-SENT", Boolean.toString(attendee.isRsvpSent()).toUpperCase()));   
    icAttendee.getParameters().add(new XParameter("SCHEDULE-STATUS", Boolean.toString(attendee.isRsvpSent()).toUpperCase()));   
    icAttendee.getParameters().add(attendee.partStat().rfc2445Value());
    
    if (attendee.cutype() != null) {
      icAttendee.getParameters().add(attendee.cutype().rfc2445Value());
    } else {
      icAttendee.getParameters().add(ERGWCUType.UNKNOWN.rfc2445Value());        
    }
    
    return icAttendee;
  }
  
  public static net.fortuna.ical4j.model.property.Organizer convertOrganizer(ERGWOrganizer organizer) {
	  net.fortuna.ical4j.model.property.Organizer icOrganizer = new net.fortuna.ical4j.model.property.Organizer(URI.create("mailto:"  + organizer.emailAddress()));
	  icOrganizer.getParameters().add(new Cn(organizer.name()));
	  return icOrganizer;
  }
  
  public static CalendarComponent transformToICalObject(ERGWCalendarObject calendarObject, CalendarComponent calComponent) throws SocketException, URISyntaxException {
    if (calendarObject.uid() == null) {
  	  UidGenerator ug = new UidGenerator("1");
      calendarObject.setUid(ug.generateUid().getValue());
    }
    calComponent.getProperties().add(new Uid(calendarObject.uid()));

    for (ERGWAttendee attendee: calendarObject.attendees) {
      calComponent.getProperties().add(convertAttendee(attendee));      
    }
    
    for (ERGWAttendee resource: calendarObject.resources) {
      calComponent.getProperties().add(convertAttendee(resource));      
    }
    
    if (calendarObject.freeBusyStatus != null) {
      calComponent.getProperties().add(calendarObject.freeBusyStatus.rfc2445Value());
    }
    
    if (calendarObject.categories != null) {
      calComponent.getProperties().add(new Categories(calendarObject.categories));
    }
    
    if (calendarObject.classification != null) {
      calComponent.getProperties().add(calendarObject.classification.rfc2445Value());
    }
    
    calComponent.getProperties().add(new Description(calendarObject.description));
    
    if (calendarObject.endTime != null) {
      if (calendarObject.isFullDay) {
        calComponent.getProperties().add(new DtEnd(new Date(calendarObject.endTime.getTime())));
      } else {        
        calComponent.getProperties().add(new DtEnd(new DateTime(calendarObject.endTime.getTime())));
      }
    }
    
    if (calendarObject.startTime != null) {
      if (calendarObject.isFullDay) {
        calComponent.getProperties().add(new DtStart(new Date(calendarObject.startTime.getTime())));
      } else {        
        calComponent.getProperties().add(new DtStart(new DateTime(calendarObject.startTime.getTime())));
      }
    }
    
    if (calendarObject.duration != null) {
      calComponent.getProperties().add(new Duration(calendarObject.duration));
    }
    
    if (calendarObject.geo != null) {
      calComponent.getProperties().add(calendarObject.geo);
    }
    
    if (calendarObject.location != null) {
      calComponent.getProperties().add(new Location(calendarObject.location));
    }
    
    if (calendarObject.organizer != null) {
      calComponent.getProperties().add(convertOrganizer(calendarObject.organizer));
    }
    
    if (calendarObject.priority != null) {
      calComponent.getProperties().add(calendarObject.priority.rfc2445Value());
    }
    
    calComponent.getProperties().add(new Summary(calendarObject.summary));
    
    if (calendarObject.url != null) {
      calComponent.getProperties().add(new Url(new URI(calendarObject.url)));
    }
    
    return calComponent;
  }
  
  public static ERGWCalendarObject transformFromICalObject(CalendarComponent calComponent, ERGWCalendarObject newObject) throws SocketException, URISyntaxException {
    net.fortuna.ical4j.model.property.Organizer zOrg = (net.fortuna.ical4j.model.property.Organizer)calComponent.getProperty(Property.ORGANIZER);
    net.fortuna.ical4j.model.PropertyList attendees = calComponent.getProperties(Property.ATTENDEE);
    net.fortuna.ical4j.model.property.Clazz classification = (net.fortuna.ical4j.model.property.Clazz)calComponent.getProperty(Property.CLASS);
    net.fortuna.ical4j.model.property.BusyType freeBusy = (net.fortuna.ical4j.model.property.BusyType)calComponent.getProperty(Property.BUSYTYPE);
    net.fortuna.ical4j.model.property.DtStart startTime = (net.fortuna.ical4j.model.property.DtStart)calComponent.getProperty(Property.DTSTART);
    net.fortuna.ical4j.model.property.DtEnd endTime = (net.fortuna.ical4j.model.property.DtEnd)calComponent.getProperty(Property.DTEND);
    net.fortuna.ical4j.model.property.Summary summary = (net.fortuna.ical4j.model.property.Summary)calComponent.getProperty(Property.SUMMARY);
    net.fortuna.ical4j.model.property.Location location = (net.fortuna.ical4j.model.property.Location)calComponent.getProperty(Property.LOCATION);
    net.fortuna.ical4j.model.property.Categories categories = (net.fortuna.ical4j.model.property.Categories)calComponent.getProperty(Property.CATEGORIES);
    net.fortuna.ical4j.model.property.Comment description = (net.fortuna.ical4j.model.property.Comment)calComponent.getProperty(Property.COMMENT);
    net.fortuna.ical4j.model.PropertyList contacts = calComponent.getProperties(Property.CONTACT);
    net.fortuna.ical4j.model.property.Geo geo = (net.fortuna.ical4j.model.property.Geo)calComponent.getProperty(Property.GEO);
    net.fortuna.ical4j.model.property.Url url = (net.fortuna.ical4j.model.property.Url)calComponent.getProperty(Property.URL);
    net.fortuna.ical4j.model.property.Priority priority = (net.fortuna.ical4j.model.property.Priority)calComponent.getProperty(Property.PRIORITY);
    net.fortuna.ical4j.model.PropertyList extras = calComponent.getProperties(Property.EXPERIMENTAL_PREFIX);
    
    ERGWOrganizer organizer = new ERGWOrganizer();
    //organizer.setEmailAddress(zOrg.getParameter(Parameter.VALUE).getValue());
    organizer.setName(zOrg.getParameter(Parameter.CN).getValue());
    newObject.setOrganizer(organizer);
    
    for (Object zAttendee: attendees) {
      net.fortuna.ical4j.model.property.Attendee oldAttendee = (net.fortuna.ical4j.model.property.Attendee)zAttendee;
      ERGWAttendee attendee = new ERGWAttendee();
      CuType type = (CuType)oldAttendee.getParameter(Parameter.CUTYPE);
      if (type == CuType.GROUP) {
        attendee.setCutype(ERGWCUType.GROUP);
      } else if (type == CuType.INDIVIDUAL) {
        attendee.setCutype(ERGWCUType.INDIVIDUAL);
      } else if (type == CuType.RESOURCE) {
        attendee.setCutype(ERGWCUType.RESOURCE);
      } else if (type == CuType.ROOM) {
        attendee.setCutype(ERGWCUType.ROOM);
      } else if (type == CuType.UNKNOWN) {
        attendee.setCutype(ERGWCUType.UNKNOWN);
      }      
      attendee.setName(oldAttendee.getParameter(Parameter.CN).getValue());
      newObject.addAttendee(attendee);
    }

    if (classification == Clazz.PUBLIC) {
      newObject.setClassification(ERGWClassification.PUBLIC);
    } else if (classification == Clazz.PRIVATE) {
      newObject.setClassification(ERGWClassification.PRIVATE);
    } else if (classification == Clazz.CONFIDENTIAL) {
      newObject.setClassification(ERGWClassification.CONFIDENTIAL);
    } else {
      newObject.setClassification(ERGWClassification.PUBLIC);
    }

    if (freeBusy == BusyType.BUSY) {
      newObject.setFreeBusyStatus(ERGWFreeBusyStatus.BUSY);
    } else if (freeBusy == BusyType.BUSY_TENTATIVE) {
      newObject.setFreeBusyStatus(ERGWFreeBusyStatus.BUSY_TENTATIVE);
    } else if (freeBusy == BusyType.BUSY_UNAVAILABLE) {
      newObject.setFreeBusyStatus(ERGWFreeBusyStatus.BUSY_UNAVAILABLE);
    }
    
    newObject.setStartTime(new NSTimestamp(startTime.getDate()));
    newObject.setEndTime(new NSTimestamp(endTime.getDate()));
    newObject.setSummary(summary.getValue());
    newObject.setLocation(location.getValue());
    if (categories != null) {
      newObject.setCategories(categories.getValue());
    }
    if (description != null) {
      newObject.setDescription(description.getValue());
    }
    if (url != null) {
      newObject.setUrl(url.getValue());
    }
    newObject.setGeo(geo);

    if (priority == net.fortuna.ical4j.model.property.Priority.HIGH) {
      newObject.setPriority(ERGWPriority.HIGH);
    } else if (priority == net.fortuna.ical4j.model.property.Priority.LOW) {
      newObject.setPriority(ERGWPriority.LOW);
    } else if (priority == net.fortuna.ical4j.model.property.Priority.MEDIUM) {
      newObject.setPriority(ERGWPriority.NORMAL);
    } else {
      newObject.setPriority(ERGWPriority.UNDEFINED);
    }

    NSLog.out.appendln(extras);
    return newObject;
  }

  public static void transformFromZimbraResponse(Element e, ERGWCalendarObject newObject) throws ServiceException {
    ZOrganizer zOrg = new ZOrganizer(e.getOptionalElement(MailConstants.E_CAL_ORGANIZER));
    ERGWOrganizer organizer = new ERGWOrganizer();
    organizer.setEmailAddress(zOrg.getEmailAddress().getAddress());
    organizer.setLdapUrl(zOrg.getDirectoryUrl());
    organizer.setName(zOrg.getPersonalName());
    organizer.setSentBy(zOrg.getSentBy());
    organizer.setUrl(zOrg.getUrl());
    newObject.setOrganizer(organizer);
    
    List<Element> attendees = e.listElements(MailConstants.E_CAL_ATTENDEE);
    for (Element attendee: attendees) {
      ZAttendee zAttendee = new ZAttendee(attendee);
      ERGWAttendee newAttendee = new ERGWAttendee();
      newAttendee.setEmailAddress(zAttendee.getAddress());
      newAttendee.setCutype(ERGWCUType.getByZimbraValue(zAttendee.getCalendarUserType()));
      //newAttendee.setDelegatedFrom(zAttendee.getDelegatedFrom());
      //newAttendee.setDelegatedTo(zAttendee.getDelegatedTo());
      newAttendee.setLdapUrl(zAttendee.getDirectoryUrl());
      newAttendee.setMemberOf(zAttendee.getMember());
      newAttendee.setPartStat(ERGWParticipantStatus.getByZimbraValue(zAttendee.getParticipantStatus()));
      newAttendee.setName(zAttendee.getPersonalName());
      newAttendee.setRole(ERGWAttendeeRole.getByZimbraValue(zAttendee.getRole()));
      newAttendee.setSentBy(zAttendee.getSentBy());
      newAttendee.setUrl(zAttendee.getUrl());
      newObject.addAttendee(newAttendee);
    }
    
    newObject.setClassification(ERGWClassification.getByZimbraValue(e.getAttribute(MailConstants.A_CAL_CLASS, ZClass.PUB.name())));
    newObject.setFreeBusyStatus(ERGWFreeBusyStatus.getByZimbraValue(e.getAttribute(MailConstants.A_APPT_FREEBUSY, ZFreeBusyStatus.B.name())));
    newObject.setIsFullDay(e.getAttributeBool(MailConstants.A_CAL_ALLDAY, false));
    newObject.setSummary(e.getAttribute(MailConstants.A_NAME, null));
    newObject.setLocation(e.getAttribute(MailConstants.A_CAL_LOCATION, null));

    Iterator<Element> catIter = e.elementIterator(MailConstants.E_CAL_CATEGORY);
    if (catIter.hasNext()) {
      List<String> categories = new ArrayList<String>();
      for (; catIter.hasNext(); ) {
        String cat = catIter.next().getTextTrim();
        categories.add(cat);
      }
      newObject.setCategories(categories.toString());
    }

    Iterator<Element> cmtIter = e.elementIterator(MailConstants.E_CAL_COMMENT);
    if (cmtIter.hasNext()) {
      List<String> comments = new ArrayList<String>();
      for (; cmtIter.hasNext(); ) {
        String cmt = cmtIter.next().getTextTrim();
        comments.add(cmt);
      }
      newObject.setDescription(comments.toString());
    }

    Iterator<Element> cnIter = e.elementIterator(MailConstants.E_CAL_CONTACT);
    if (cnIter.hasNext()) {
      List<String> contacts = new ArrayList<String>();
      for (; cnIter.hasNext(); ) {
        String cn = cnIter.next().getTextTrim();
        contacts.add(cn);
      }
    }

    Element geoElem = e.getOptionalElement(MailConstants.E_CAL_GEO);
    if (geoElem != null) {
      com.zimbra.cs.mailbox.calendar.Geo zGeo = com.zimbra.cs.mailbox.calendar.Geo.parse(geoElem);
      newObject.setGeo(new Geo(new BigDecimal(zGeo.getLatitude()), new BigDecimal(zGeo.getLongitude())));
    }

    newObject.setUrl(e.getAttribute(MailConstants.A_CAL_URL, null));

    newObject.setPriority(ERGWPriority.getByZimbraValue(e.getAttribute(MailConstants.A_CAL_PRIORITY, "0")));

    ArrayList<ZReply> mReplies = new ArrayList<ZReply>();
    Element repliesEl = e.getOptionalElement(MailConstants.E_CAL_REPLIES);
    if (repliesEl != null) {
      for (Element replyEl : repliesEl.listElements(MailConstants.E_CAL_REPLY)) {
        mReplies.add(new ZReply(replyEl));
      }
    }

    Element startEl = e.getOptionalElement(MailConstants.E_CAL_START_TIME);
    if (startEl != null) {
      ZDateTime zDate = new ZDateTime(startEl);
      newObject.setStartTime(new NSTimestamp(zDate.getDate()));
      newObject.setTimezone(TimeZone.getTimeZone(zDate.getTimeZone().getID()));
    }

    Element endEl = e.getOptionalElement(MailConstants.E_CAL_END_TIME);
    if (endEl != null) {
      ZDateTime zDate = new ZDateTime(endEl);
      newObject.setEndTime(new NSTimestamp(zDate.getDate()));
      newObject.setTimezone(TimeZone.getTimeZone(zDate.getTimeZone().getID()));
    }
    
    for (Element el: e.listElements(MailConstants.E_CAL_XPROP)) {
      String xPropName = el.getAttribute(MailConstants.A_NAME);
      String xPropValue = el.getAttribute(MailConstants.A_VALUE);
      if ("X-RELATED-TO".equals(xPropName)) {
        newObject.setParentId(xPropValue);
      }
    }
    
  }

  public static Element transformToZimbraObject(ERGWCalendarObject calendarObject) {

    Element inviteComponent = new XMLElement(MailConstants.E_INVITE_COMPONENT);
    inviteComponent.addAttribute(MailConstants.A_APPT_FREEBUSY, calendarObject.freeBusyStatus.zimbraValue());
    inviteComponent.addAttribute(MailConstants.A_CAL_CLASS, calendarObject.classification.zimbraValue());
    if (calendarObject.isFullDay) {
      inviteComponent.addAttribute(MailConstants.A_CAL_ALLDAY, 1);
    } else {
      inviteComponent.addAttribute(MailConstants.A_CAL_ALLDAY, 0);
    }
    inviteComponent.addAttribute(MailConstants.A_CAL_RULE_XNAME_NAME, calendarObject.summary);

    Element dateDebut = inviteComponent.addElement(MailConstants.E_CAL_START_TIME);
    dateDebut.addAttribute(MailConstants.E_CAL_TZ, calendarObject.timezone.getID());
    dateDebut.addAttribute(MailConstants.A_CAL_DATETIME, new DateTime(calendarObject.startTime.getTime()).toString());

    Element dateFin = inviteComponent.addElement(MailConstants.E_CAL_END_TIME);
    dateFin.addAttribute(MailConstants.E_CAL_TZ, calendarObject.timezone.getID());
    dateFin.addAttribute(MailConstants.A_CAL_DATETIME,new DateTime(calendarObject.endTime.getTime()).toString());

    inviteComponent.addAttribute(MailConstants.A_CAL_LOCATION, calendarObject.location);

    if (calendarObject.organizer != null) {
      Element organisateur = inviteComponent.addElement(MailConstants.E_CAL_ORGANIZER);
      organisateur.addAttribute(MailConstants.A_ADDRESS, calendarObject.organizer.emailAddress());
      organisateur.addAttribute(MailConstants.A_DISPLAY, calendarObject.organizer.name());
    }

    for (ERGWAttendee attendee: calendarObject.attendees) {
      if (attendee.emailAddress() == null) {
        // TODO: localization for the error message
        throw new ERXValidationException("for zimbra servers, emailAddress or url must be specified", attendee, attendee.emailAddress());
      }
      Element xmlAttendee = inviteComponent.addElement(MailConstants.E_CAL_ATTENDEE);
      xmlAttendee.addAttribute(MailConstants.A_ADDRESS, attendee.emailAddress());
      xmlAttendee.addAttribute(MailConstants.A_DISPLAY, attendee.name());
      xmlAttendee.addAttribute(MailConstants.A_CAL_CUTYPE, attendee.cutype().zimbraValue().toString());
    }

    for (ERGWAttendee resource: calendarObject.resources) {
      if (resource.emailAddress() == null) {
        // TODO: localization for the error message
        throw new ERXValidationException("for zimbra servers, emailAddress or url must be specified", resource, resource.emailAddress());
      }
      Element xmlResource = inviteComponent.addElement(MailConstants.E_CAL_ATTENDEE);
      xmlResource.addAttribute(MailConstants.A_ADDRESS, resource.emailAddress());                
      xmlResource.addAttribute(MailConstants.A_DISPLAY, resource.name());
      xmlResource.addAttribute(MailConstants.A_CAL_CUTYPE, resource.cutype().zimbraValue().toString());
    }

    inviteComponent.addAttribute("category", calendarObject.categories);

    inviteComponent.addAttribute(MailConstants.E_FRAG, calendarObject.description);

    if (calendarObject.parentId() != null) {
      Element relatedTo = inviteComponent.addElement(MailConstants.E_CAL_XPROP);
      relatedTo.addAttribute(MailConstants.A_NAME,"X-RELATED-TO");
      relatedTo.addAttribute(MailConstants.A_VALUE, calendarObject.parentId());
    }
    
    return inviteComponent;
  }
  
  public boolean isEnded() {
    NSTimestamp now = new NSTimestamp();
    if (now.after(this.endTime())) {
      return true;
    }
    return false;
  }

}
