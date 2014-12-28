package er.groupware.exchange.ews;

import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import microsoft.exchange.webservices.data.Appointment;
import microsoft.exchange.webservices.data.ArgumentException;
import microsoft.exchange.webservices.data.ArgumentOutOfRangeException;
import microsoft.exchange.webservices.data.Attendee;
import microsoft.exchange.webservices.data.AutodiscoverLocalException;
import microsoft.exchange.webservices.data.BodyType;
import microsoft.exchange.webservices.data.CalendarFolder;
import microsoft.exchange.webservices.data.ChangeCollection;
import microsoft.exchange.webservices.data.ChangeType;
import microsoft.exchange.webservices.data.Contact;
import microsoft.exchange.webservices.data.ContactsFolder;
import microsoft.exchange.webservices.data.DayOfTheWeek;
import microsoft.exchange.webservices.data.DayOfTheWeekIndex;
import microsoft.exchange.webservices.data.EmailAddress;
import microsoft.exchange.webservices.data.EmailAddressKey;
import microsoft.exchange.webservices.data.ExchangeCredentials;
import microsoft.exchange.webservices.data.ExchangeService;
import microsoft.exchange.webservices.data.ExchangeVersion;
import microsoft.exchange.webservices.data.Folder;
import microsoft.exchange.webservices.data.FolderChange;
import microsoft.exchange.webservices.data.FolderId;
import microsoft.exchange.webservices.data.IAsyncResult;
import microsoft.exchange.webservices.data.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.ImAddressKey;
import microsoft.exchange.webservices.data.Importance;
import microsoft.exchange.webservices.data.Item;
import microsoft.exchange.webservices.data.MessageBody;
import microsoft.exchange.webservices.data.PhoneNumberKey;
import microsoft.exchange.webservices.data.PhysicalAddressEntry;
import microsoft.exchange.webservices.data.PhysicalAddressKey;
import microsoft.exchange.webservices.data.PropertySet;
import microsoft.exchange.webservices.data.Recurrence;
import microsoft.exchange.webservices.data.Recurrence.MonthlyPattern;
import microsoft.exchange.webservices.data.Recurrence.RelativeMonthlyPattern;
import microsoft.exchange.webservices.data.Recurrence.WeeklyPattern;
import microsoft.exchange.webservices.data.SearchFolder;
import microsoft.exchange.webservices.data.SendInvitationsMode;
import microsoft.exchange.webservices.data.Sensitivity;
import microsoft.exchange.webservices.data.StringList;
import microsoft.exchange.webservices.data.Task;
import microsoft.exchange.webservices.data.TasksFolder;
import microsoft.exchange.webservices.data.WebCredentials;
import microsoft.exchange.webservices.data.WellKnownFolderName;

import org.apache.commons.lang.NotImplementedException;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSValidation;

import er.groupware.calendar.ERGWAlarm;
import er.groupware.calendar.ERGWAttendee;
import er.groupware.calendar.ERGWCalendar;
import er.groupware.calendar.ERGWCalendarObject;
import er.groupware.calendar.ERGWEvent;
import er.groupware.calendar.ERGWOrganizer;
import er.groupware.calendar.ERGWRecurrencePeriod;
import er.groupware.calendar.ERGWRecurrenceRule;
import er.groupware.calendar.ERGWTask;
import er.groupware.calendar.enums.ERGWAttendeeRole;
import er.groupware.calendar.enums.ERGWClassification;
import er.groupware.calendar.enums.ERGWFreeBusyStatus;
import er.groupware.calendar.enums.ERGWPriority;
import er.groupware.calendar.enums.ERGWRecurrenceDay;
import er.groupware.calendar.enums.ERGWRecurrenceFrequency;
import er.groupware.calendar.enums.ERGWRecurrencePeriodType;
import er.groupware.contacts.ERGWContact;
import er.groupware.contacts.ERGWContactEmail;
import er.groupware.contacts.ERGWContactPhysicalAddress;
import er.groupware.contacts.ERGWContactTelephone;
import er.groupware.contacts.ERGWContactUrl;
import er.groupware.contacts.enums.ERGWContactEmailType;
import er.groupware.contacts.enums.ERGWContactFileAsStyle;
import er.groupware.contacts.enums.ERGWContactPhysicalAddressType;
import er.groupware.contacts.enums.ERGWContactTelephoneType;
import er.groupware.enums.ERGWFolderType;

/**
 * This is the data store to connect to a Microsoft Exchange 2007 or 2010 service by Exchange Web Services (which uses SOAP).
 * 
 * @author probert
 *
 */
public class ExchangeStore {

  protected ExchangeVersion serverVersionForRequest;
  protected String syncState;
  protected NSArray<ExchangeBaseFolder> folders;
  private String username;
  protected ExchangeService service;

  static {
    Locale.setDefault(Locale.US);
  }
  
  public class AutoDiscover implements IAutodiscoverRedirectionUrl {

    public boolean autodiscoverRedirectionUrlValidationCallback(String arg0) throws AutodiscoverLocalException {
      return true;
    }
    
  }
  
  /**
   * This constructor will connect to a Microsoft Exchange server and authenticate the user.
   * 
   * @param pathToWS Path to exchange.asmx on your Exchange host (should look like https://mydomain.com/EWS/exchange.asmx)
   * @param username User to connect to the Exchange service
   * @param password Password to connect to the Exchange service
   * @param ntmlDomain If your Exchange host is only accepting NTML auth, this parameter is to specify in which domain your user is
   * @throws Exception 
   * 
   */
  public ExchangeStore(String pathToWS, String username, String password, String ntmlDomain, ExchangeVersion serverVersionInResponse, TimeZone timezone) throws Exception {

    if (serverVersionInResponse == null) {
      serverVersionInResponse = ExchangeVersion.Exchange2010_SP1;
    }

    service = new ExchangeService(serverVersionInResponse,timezone);
    ExchangeCredentials credentials = new WebCredentials(username, password);
    service.setCredentials(credentials);

    if (pathToWS != null) {
      service.setUrl(new URI(pathToWS));
    } else {
      service.autodiscoverUrl(username, new AutoDiscover());
    }

    //    if (ntmlDomain != null) {
    //      ExchangeAuthenticator authenticator = new ExchangeAuthenticator(ntmlDomain + "\\" + username, password.toCharArray());
    //      Authenticator.setDefault(authenticator);
    //    }

    syncState = null;

    folders = syncFolders();

    this.username = username;
  }

  /**
   * Return the state of a synchronisation (from a SyncFolderHierarchy operation). Read-only property, you shouldn't set that value.
   * @return
   */
  public String syncState() {
    return this.syncState;
  }

  protected void setSyncState(String syncState) {
    this.syncState = syncState;
  }

  /**
   * Return the list of folder from the store. If you need to get an updated list, call the syncFolders method.
   * @return
   */
  public NSArray<ExchangeBaseFolder> folders() {
    if (this.folders == null) {
      this.folders = new NSArray<ExchangeBaseFolder>();
    }
    return this.folders;
  }

  protected void setFolders(NSArray<ExchangeBaseFolder> folders) {
    this.folders = folders;
  }

  protected void addFolderToList(ExchangeBaseFolder folder) {
    NSMutableArray<ExchangeBaseFolder> array = this.folders().mutableClone();
    array.addObject(folder);
    this.setFolders(array.immutableClone());
  }

  /**
   * This method will call the SyncFolderHierarchy method to get a list of folders from the server.
   * Based on the result of this sync, the local list of folders will be updated.
   * @return
   * @throws Exception 
   */
  public NSArray<ExchangeBaseFolder> syncFolders() throws Exception {
    NSMutableArray<ExchangeBaseFolder> folders = this.folders().mutableClone();

    ChangeCollection<FolderChange> change = service.syncFolderHierarchy(PropertySet.FirstClassProperties, this.syncState());

    this.setSyncState(change.getSyncState());

    for (FolderChange aChange: change) {
      if ("IPF.Appointment".equals(aChange.getFolder().getFolderClass())) {
        ExchangeCalendarFolder calendarFolder = (ExchangeCalendarFolder) ExchangeCalendarFolder.createFromServer(aChange.getFolder());
        if (aChange.getChangeType() == ChangeType.Create)
          folders.addObject(calendarFolder);
      } else if ("IPF.Contact".equals(aChange.getFolder().getFolderClass())) {
        ExchangeContactsFolder contactsFolder = (ExchangeContactsFolder) ExchangeContactsFolder.createFromServer(aChange.getFolder());
        if (aChange.getChangeType() == ChangeType.Create)
          folders.addObject(contactsFolder);
      } else if ("IPF.Task".equals(aChange.getFolder().getFolderClass())) {
        ExchangeTasksFolder tasksFolder = (ExchangeTasksFolder) ExchangeTasksFolder.createFromServer(aChange.getFolder());
        if (aChange.getChangeType() == ChangeType.Create)
          folders.addObject(tasksFolder);
      } else if ("IPF.Note".equals(aChange.getFolder().getFolderClass())) {
        ExchangeEmailFolder emailFolder = (ExchangeEmailFolder) ExchangeEmailFolder.createFromServer(aChange.getFolder());
        if (aChange.getChangeType() == ChangeType.Create)
          folders.addObject(emailFolder);
      }
    }
    return folders.immutableClone();
  }

  public void createCalendarFolders() {

  }

  /* TODO: setting sharing permissions
     ArrayOfCalendarPermissionsType permissions = new ArrayOfCalendarPermissionsType();

    CalendarPermissionType permissionType = new CalendarPermissionType();
    permissionType.setCalendarPermissionLevel(CalendarPermissionLevelType.CUSTOM);
    permissionType.setCanCreateItems(false);
    permissionType.setCanCreateSubFolders(false);
    permissionType.setDeleteItems(PermissionActionType.NONE);
    permissionType.setEditItems(PermissionActionType.NONE);
    permissionType.setIsFolderContact(false);
    permissionType.setIsFolderOwner(false);
    permissionType.setIsFolderVisible(true);
    UserIdType sharedUser = new UserIdType();
    sharedUser.setPrimarySmtpAddress(primarySmtpAddress);
    sharedUser.setDisplayName(userDisplayName);
    permissionType.setUserId(sharedUser);
    permissionType.setReadItems(CalendarPermissionReadAccessType.NONE);
    permissions.getCalendarPermission().add(permissionType);

    permissionType = new CalendarPermissionType();
    permissionType.setCalendarPermissionLevel(CalendarPermissionLevelType.NONE);
    UserIdType defaultUser = new UserIdType();
    defaultUser.setDistinguishedUser(DistinguishedUserType.DEFAULT);
    permissionType.setUserId(defaultUser);
    permissionType.setReadItems(CalendarPermissionReadAccessType.NONE);
    permissions.getCalendarPermission().add(permissionType);

    CalendarPermissionSetType permissionsSet = new CalendarPermissionSetType();
    permissionsSet.setCalendarPermissions(permissions);
    // The CalendarPermissionSetType class contains all the permissions that are configured for a calendar folder. 
    newFolder.setPermissionSet(permissionsSet);

   */

  /**
   * 
   * @param displayName The name of the calendar folder
   * @throws Throwable 
   * @throws FolderAlreadyExistsException 
   */
  public void createCalendarFolder(String displayName) throws Throwable {
    createFolder(displayName, ERGWFolderType.CALENDAR);
  }

  /**
   * 
   * @param displayName The name of the contacts folder
   * @throws Throwable 
   * @throws FolderAlreadyExistsException 
   */
  public void createContactsFolder(String displayName) throws Throwable {
    createFolder(displayName, ERGWFolderType.CALENDAR);
  }

  /**
   * 
   * @param folderDisplayName The name of the calendar
   * @param typeOfFolder The type of calendar (Calendar, Tasks, Search, etc.) to create
   * @throws Throwable, FolderAlreadyExistsException 
   */
  public void createFolder(String folderDisplayName, ERGWFolderType typeOfFolder) throws Throwable {

    Folder newFolder = null;
    if (typeOfFolder.equals(ERGWFolderType.CALENDAR)) {
      newFolder = new CalendarFolder(service);
    } else if (typeOfFolder.equals(ERGWFolderType.CONTACTS)) {
      newFolder = new ContactsFolder(service);
    } else if (typeOfFolder.equals(ERGWFolderType.TASKS)) {
      newFolder = new TasksFolder(service);
    } else if (typeOfFolder.equals(ERGWFolderType.SEARCH)) {
      newFolder = new SearchFolder(service);
    } else {
      newFolder = new Folder(service);
    }

    newFolder.setDisplayName(folderDisplayName);

    newFolder.save(WellKnownFolderName.Inbox); 

  }

  protected Recurrence recurrenceForEvent(ERGWCalendarObject component, Calendar startDate) throws ArgumentOutOfRangeException, ArgumentException {
    ERGWRecurrenceRule recurrenceRule = component.recurrenceRule();

    if (recurrenceRule != null) {
      ERGWRecurrenceFrequency frequency = recurrenceRule.frequency();
      // RRULE:FREQ=WEEKLY;BYDAY=FR
      // RRULE:FREQ=WEEKLY;BYDAY=WE,FR
      if (frequency.equals(ERGWRecurrenceFrequency.WEEKLY)) {
        WeeklyPattern recurType = new WeeklyPattern();
        if (recurrenceRule.interval() != null)
          recurType.setInterval(recurrenceRule.interval());
        else 
          recurType.setInterval(1);

        if (recurrenceRule.periods().count() > 0) {
          for (ERGWRecurrencePeriod period: recurrenceRule.periods()) {
            if (period.periodType().equals(ERGWRecurrencePeriodType.BYDAY)) {
              for (Object day: period.values()) {
                if (day instanceof String) {
                  recurType.getDaysOfTheWeek().add(ERGWRecurrenceDay.getByRFC2445Value((String)day).ewsValue());
                }
              }
            }
          }
        } else {
          int starDayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
          switch (starDayOfWeek) {
          case Calendar.SUNDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Sunday);
            break;
          case Calendar.MONDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Monday);
            break;
          case Calendar.TUESDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Tuesday);
            break;
          case Calendar.WEDNESDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Wednesday);
            break;
          case Calendar.THURSDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Thursday);
            break;
          case Calendar.FRIDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Friday);
            break;
          case Calendar.SATURDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Saturday);
            break;
          }
        }

        java.util.Date until = recurrenceRule.until();

        if (until != null) {
          // RRULE:FREQ=WEEKLY;UNTIL=20120915T150000Z;BYDAY=FR
          recurType.setStartDate(startDate.getTime());
          recurType.setEndDate(until);
          recurType.setInterval(1);
        } else if ((recurrenceRule != null) && (recurrenceRule.repeatCount() != null) && (recurrenceRule.repeatCount() > 0)) {
          // RRULE:FREQ=WEEKLY;WKST=MO;COUNT=3;INTERVAL=2;BYDAY=FR (SUMMARY:Formation plan de retraite et éducation financière)
          recurType.setStartDate(startDate.getTime());
          recurType.setNumberOfOccurrences(recurrenceRule.repeatCount());
        } else {
          recurType.setStartDate(startDate.getTime());
        }

        return recurType;

      } else if (frequency.equals(ERGWRecurrenceFrequency.MONTHLY)) {
        // RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR,SA,SU;BYSETPOS=1 (SUMMARY:Effacer les audiences du mois précédent - rôle - synbad)
        if (recurrenceRule.positions().count() > 0) {
          for (Object setPost: recurrenceRule.positions()) {

            RelativeMonthlyPattern recurType = new RelativeMonthlyPattern();
            recurType.setDayOfTheWeek(DayOfTheWeek.Day);

            Integer position = (Integer)setPost;
            switch (position) {
            case 1: 
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.First);
              break;
            case 2:
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.Second);
              break;
            case 3:
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.Third);
              break;
            case 4:
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.Fourth);
              break;
            case -1:
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.Last);
              break;
            default:
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.First);
            }

            recurType.setInterval(1);
            recurType.setStartDate(startDate.getTime());

            return recurType;
          }
        } else {
          // RRULE:FREQ=MONTHLY;BYMONTHDAY=3 (SUMMARY:Préparer et transmettre par courriel à Me Leduc (Mme Charles) plumitif à jour et modifié selon ses exigences Et accompagné des nouvelles plaintes)
          /*
           * The BYMONTHDAY rule part specifies a COMMA character (ASCII decimal 44) separated list of days of the month. 
           * Valid values are 1 to 31 or -31 to -1. For example, -10 represents the tenth to the last day of the month.
           */
          MonthlyPattern recurType = new MonthlyPattern();
          for (ERGWRecurrencePeriod period: recurrenceRule.periods()) {
            for (Object dayOfMonth: period.values()) {
              recurType.setDayOfMonth((Integer)dayOfMonth);               
            }
          }
          if (recurrenceRule.interval() != null) { 
            recurType.setInterval(recurrenceRule.interval());
          } else {
            recurType.setInterval(1);
          }
        }
      }
    }
    return null;
  }

  protected Recurrence recurrenceForTask(ERGWCalendarObject component, java.util.Date startDate) throws ArgumentOutOfRangeException, ArgumentException {
    ERGWRecurrenceRule recurrenceRule = component.recurrenceRule();

    if (recurrenceRule != null) {
      ERGWRecurrenceFrequency frequency = recurrenceRule.frequency();
      // RRULE:FREQ=WEEKLY;BYDAY=FR
      // RRULE:FREQ=WEEKLY;BYDAY=WE,FR
      if (frequency.equals(ERGWRecurrenceFrequency.WEEKLY)) {
        WeeklyPattern recurType = new WeeklyPattern();
        if (recurrenceRule.interval() != null)
          recurType.setInterval(recurrenceRule.interval());
        else 
          recurType.setInterval(1);

        if (recurrenceRule.periods().count() > 0) {
          for (ERGWRecurrencePeriod period: recurrenceRule.periods()) {
            if (period.periodType().equals(ERGWRecurrencePeriodType.BYDAY)) {
              for (Object day: period.values()) {
                if (day instanceof String) {
                  recurType.getDaysOfTheWeek().add(ERGWRecurrenceDay.getByRFC2445Value((String)day).ewsValue());
                }
              }
            }
          }
        } else {
          Calendar cStDate = GregorianCalendar.getInstance();
          cStDate.setTime(startDate);
          int starDayOfWeek = cStDate.get(Calendar.DAY_OF_WEEK);
          switch (starDayOfWeek) {
          case Calendar.SUNDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Sunday);
            break;
          case Calendar.MONDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Monday);
            break;
          case Calendar.TUESDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Tuesday);
            break;
          case Calendar.WEDNESDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Wednesday);
            break;
          case Calendar.THURSDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Thursday);
            break;
          case Calendar.FRIDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Friday);
            break;
          case Calendar.SATURDAY:
            recurType.getDaysOfTheWeek().add(DayOfTheWeek.Saturday);
            break;
          }
        }

        java.util.Date until = recurrenceRule.until();

        if (until != null) {
          // RRULE:FREQ=WEEKLY;UNTIL=20120915T150000Z;BYDAY=FR
          recurType.setStartDate(startDate);
          recurType.setEndDate(until);
          recurType.setInterval(1);
        } else if (recurrenceRule.repeatCount() > 0) {
          recurType.setStartDate(startDate);
          recurType.setNumberOfOccurrences(recurrenceRule.repeatCount());
        } else {
          recurType.setStartDate(startDate);
        }

        return recurType;

      } else if (frequency.equals(ERGWRecurrenceFrequency.MONTHLY)) {
        // RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR,SA,SU;BYSETPOS=1 (SUMMARY:Effacer les audiences du mois précédent - rôle - synbad)
        if (recurrenceRule.positions().count() > 0) {
          for (Object setPost: recurrenceRule.positions()) {

            RelativeMonthlyPattern recurType = new RelativeMonthlyPattern();
            recurType.setDayOfTheWeek(DayOfTheWeek.Day);

            Integer position = (Integer)setPost;
            switch (position) {
            case 1: 
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.First);
              break;
            case 2:
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.Second);
              break;
            case 3:
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.Third);
              break;
            case 4:
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.Fourth);
              break;
            case -1:
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.Last);
              break;
            default:
              recurType.setDayOfTheWeekIndex(DayOfTheWeekIndex.First);
            }

            recurType.setInterval(1);
            recurType.setStartDate(startDate);

            return recurType;
          }
        } else {
          // RRULE:FREQ=MONTHLY;BYMONTHDAY=3 (SUMMARY:Préparer et transmettre par courriel à Me Leduc (Mme Charles) plumitif à jour et modifié selon ses exigences Et accompagné des nouvelles plaintes)
          /*
           * The BYMONTHDAY rule part specifies a COMMA character (ASCII decimal 44) separated list of days of the month. 
           * Valid values are 1 to 31 or -31 to -1. For example, -10 represents the tenth to the last day of the month.
           */
          MonthlyPattern recurType = new MonthlyPattern();
          for (ERGWRecurrencePeriod period: recurrenceRule.periods()) {
            for (Object dayOfMonth: period.values()) {
              recurType.setDayOfMonth((Integer)dayOfMonth);
            }
          }
          recurType.setInterval(recurrenceRule.interval());
        }
      }
    }
    return null;
  }

  /*
 BEGIN:VALARM
 ACTION:DISPLAY
 DESCRIPTION:Le sujet
 TRIGGER;RELATED=START:-PT15M
 END:VALARM
   */
  protected void setAlarms(ERGWCalendarObject event, Item component) throws Exception {
    for (ERGWAlarm alarm: event.alarms()) {
      if (alarm.isAbsolute()) {
        java.util.Calendar alarmDueDate = GregorianCalendar.getInstance();
        alarmDueDate.setTime(alarm.alarmDate());
        component.setReminderDueBy(alarmDueDate.getTime());
      } else {
        int duration = 0;
        switch (alarm.durationType()) {
        case MINUTES:
          duration = alarm.duration();
          break;
        case DAYS:
          duration = duration * (24 * 60);
          break;
        case HOURS:
          duration = duration * 60;
          break;
        case SECONDS:
          duration = duration / 60;
          break;
        case WEEKS:
          duration = duration * (24 * 60 * 7);
          break;
        default:
          duration = alarm.duration();
        }
        component.setReminderMinutesBeforeStart(duration);
      }
    }
  }

  public void createCalendarEvent(ERGWCalendar calendar) throws Exception {   
    WellKnownFolderName calendarFolder = WellKnownFolderName.Calendar;
    FolderId folderId = new FolderId(calendarFolder);
    ExchangeCalendarFolder exchangeFolder = new ExchangeCalendarFolder("Contacts");
    exchangeFolder.setId(folderId);
    createCalendarEvent(calendar, exchangeFolder);
  }
  
  public void createCalendarEvent(ERGWCalendar calendar, ExchangeCalendarFolder calendarFolder) throws Exception {   
    for (ERGWEvent event: calendar.getEvents()) {
      Appointment calendarItem = new Appointment(service);

      /*
       * A CUA with a three-level priority scheme of "HIGH", "MEDIUM" and "LOW" is mapped 
       * into this property such that a property value in the range of one (US-ASCII decimal 49) 
       * to four (US-ASCII decimal 52) specifies "HIGH" priority. 
       * A value of five (US-ASCII decimal 53) is the normal or "MEDIUM" priority. 
       * A value in the range of six (US- ASCII decimal 54) to nine (US-ASCII decimal 58) is "LOW" priority.
       */
      ERGWPriority priority = event.priority();
      if (priority != null) {
        calendarItem.setImportance(priority.ewsValue());
      } else {
        calendarItem.setImportance(Importance.Normal);
      }

      // UID:c35d64d0-2187-45ba-884d-ada56ada927a
      String uid = event.uid();
      if (uid != null)
        calendarItem.setICalUid(uid);

      // CLASS:PUBLIC
      ERGWClassification classification = event.classification();
      if (classification != null) {
        calendarItem.setSensitivity(classification.ewsValue());
      } else {
        calendarItem.setSensitivity(Sensitivity.Normal);
      }

      // X-MICROSOFT-CDO-ALLDAYEVENT:TRUE
      calendarItem.setIsAllDayEvent(event.isFullDay());

      // X-MICROSOFT-CDO-BUSYSTATUS:BUSY
      ERGWFreeBusyStatus freeBusyStatus = event.freeBusyStatus();
      if (freeBusyStatus != null) 
        calendarItem.setLegacyFreeBusyStatus(freeBusyStatus.ewsValue());

      // ORGANIZER;CN="Pascal Robert":mailto:probert@macti.ca
      ERGWOrganizer organizer = event.organizer();
      if (organizer != null) {
        EmailAddress email = new EmailAddress();

        if (organizer.name() != null)
          email.setName(organizer.name());

        if (organizer.emailAddress() != null)
          email.setAddress(organizer.emailAddress());      

        if (organizer.emailAddress().startsWith(username)) {
          //calendarItem.setMyResponseType(ResponseTypeType.ORGANIZER);
          //calendarItem.setMeetingRequestWasSent(true);
        }

        calendarItem.getOrganizer().setAddress(email.getAddress());
      }

      // ATTENDEE;RSVP=TRUE;X-SENT=TRUE;CN=probert@macti.ca;CUTYPE=INDIVIDUAL:mailto:probert@macti.ca
      if (event.attendees().count() > 0) {

        //calendarItem.setIsMeeting(true);
        //calendarItem.setIsResponseRequested(false);

        for (ERGWAttendee attendee: event.attendees()) {
          if ((organizer == null) || (!(attendee.emailAddress().equals(organizer.emailAddress())))) {
            ERGWAttendeeRole role = attendee.role();

            Attendee attendeeDetails = new Attendee();
            attendeeDetails.setAddress(attendee.emailAddress());

            if (role.equals(ERGWAttendeeRole.REQ_PARTICIPANT)) {
              calendarItem.getRequiredAttendees().add(attendeeDetails);
            } else if ((role.equals(ERGWAttendeeRole.OPT_PARTICIPANT)) || (role.equals(ERGWAttendeeRole.NON_PARTICIPANT)) || (role.equals(ERGWAttendeeRole.CHAIR))) {
              calendarItem.getOptionalAttendees().add(attendeeDetails);
            } else {
              calendarItem.getResources().add(attendeeDetails);
            }
          }
        }
      }

      //  CATEGORIES:Appels téléphoniques,Buts/Objectifs
      NSArray<String> categories = event.categories();
      if (categories != null) {
        StringList strings = new StringList();
        for (String category: categories) {
          strings.add(category);
        }
        calendarItem.setCategories(strings);
      }

      // SUMMARY: The title
      calendarItem.setSubject(event.summary());

      // DESCRIPTION:La description
      String description = event.description();
      if (description != null) {
        MessageBody body = new MessageBody(BodyType.Text, description);
        calendarItem.setBody(body);
      }

      // DTSTART;VALUE=DATE:20120913
      NSTimestamp eventStartDate = event.startTime();
      Calendar startDate = GregorianCalendar.getInstance();
      startDate.setTime(eventStartDate);

      if (event.isFullDay()) {
        startDate.add(Calendar.HOUR_OF_DAY, 5);
      } 

      calendarItem.setStart(startDate.getTime());

      // DTEND;VALUE=DATE:20120914
      NSTimestamp eventEndDate = event.endTime();
      Calendar endDate = GregorianCalendar.getInstance();
      endDate.setTime(eventEndDate);
      calendarItem.setEnd(endDate.getTime());

      String location = event.location();
      if (location != null)
        calendarItem.setLocation(location);

      calendarItem.setRecurrence(this.recurrenceForEvent(event, startDate));
      setAlarms(event, calendarItem);

      calendarItem.save(SendInvitationsMode.SendOnlyToAll);
    }
  }

  /**
   * Create a contact in the default Contacts folder
   * @param card
   * @throws Exception
   */
  public void createContact(ERGWContact card) throws Exception {
    WellKnownFolderName contactsFolder = WellKnownFolderName.Contacts;
    FolderId folderId = new FolderId(contactsFolder);
    ExchangeContactsFolder exchangeFolder = new ExchangeContactsFolder("Contacts");
    exchangeFolder.setId(folderId);
    createContact(card, exchangeFolder);
  }
  
  /**
   * Create a contact in a specific contacts folder
   * @param card
   * @param folder
   * @throws Exception
   */
  public void createContact(ERGWContact card, ExchangeContactsFolder folder) throws Exception {
    Contact contact = new Contact(service);

    contact.setSurname(card.familyName());
    contact.setGivenName(card.givenName());
    if (card.fileAs() != null) {
      if (card.fileAs().equals(ERGWContactFileAsStyle.LAST_COMMA_FIRST_SUFFIX)) {
        contact.setFileAs(contact.getSurname() + ", " + contact.getGivenName());      
      } else {
        contact.setFileAs(contact.getGivenName() + " " + contact.getSurname());      
      }
    }

    if (card.jobTitle() != null) 
      contact.setJobTitle(card.jobTitle());

    if (card.emails().count() > 0) {
      for (ERGWContactEmail email: card.emails()) {
        if (email.types().count() > 0) {
          for (ERGWContactEmailType type: email.types()) {
            switch (type) {
            case WORK:
              contact.getEmailAddresses().setEmailAddress(EmailAddressKey.EmailAddress1, new EmailAddress(email.email()));
              break;
            case HOME:
              contact.getEmailAddresses().setEmailAddress(EmailAddressKey.EmailAddress2, new EmailAddress(email.email()));
              break;
            case OTHER:
              contact.getEmailAddresses().setEmailAddress(EmailAddressKey.EmailAddress3, new EmailAddress(email.email()));
              break;
            }
          }
        } else {
          contact.getEmailAddresses().setEmailAddress(EmailAddressKey.EmailAddress1, new EmailAddress(email.email()));
        }          
      }
    }

    String assistant = card.assistantName();
    if (assistant != null)
      contact.setAssistantName(assistant);

    NSTimestamp birthday = card.birthday();
    if (birthday != null) {
      Calendar bdayAsCalendar = GregorianCalendar.getInstance();
      bdayAsCalendar.setTimeInMillis(birthday.getTime());
      bdayAsCalendar.add(Calendar.HOUR_OF_DAY, 5);
      contact.setBirthday(bdayAsCalendar.getTime());
    }

    String businessName = card.businessName();
    if (businessName != null) {
      contact.setCompanyName(businessName);
    }

    String departmentName = card.departmentName();
    if (departmentName != null) {
      contact.setDepartment(departmentName);
    }

    if (card.telephones().count() > 0) {
      for (ERGWContactTelephone telephone: card.telephones()) {
        for (ERGWContactTelephoneType type: telephone.types()) {
          switch (type) {
          case HOME:
            if (telephone.isFaxNumber()) {
              contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.HomeFax, telephone.value());
            } else {
              contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.HomePhone, telephone.value());
            }
            break;
          case WORK:
            if (telephone.isFaxNumber()) {
              contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.BusinessFax, telephone.value());
            } else {
              contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.BusinessPhone, telephone.value());
            }
            break;
          case ASSISTANT:
            contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.AssistantPhone, telephone.value());
            break;
          case CAR:
            contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.CarPhone, telephone.value());
            break;
          case MOBILE:
            contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.MobilePhone, telephone.value());
            break;
          case PAGER:
            contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.Pager, telephone.value());
            break;
          case VOICE_MSG:
            contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.Callback, telephone.value());
            break;
          case OTHER_TELEPHONE:
            contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.OtherTelephone, telephone.value());
            break;
          case OTHER_FAX:
            contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.OtherFax, telephone.value());
            break;
          case COMPANY:
            contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.CompanyMainPhone, telephone.value());
            break;
          case PRIMARY_PHONE:
            contact.getPhoneNumbers().setPhoneNumber(PhoneNumberKey.PrimaryPhone, telephone.value());
          }
        }
      }
    }    

    if (card.physicalAddresses().count() > 0) {
      for (ERGWContactPhysicalAddress address: card.physicalAddresses()) {
        PhysicalAddressEntry entry = new PhysicalAddressEntry();
        entry.setCity(address.city());
        entry.setCountryOrRegion(address.country());
        entry.setPostalCode(address.postalCode());
        entry.setState(address.region());
        entry.setStreet(address.street());

        if (address.types().count() > 0) {
          for (ERGWContactPhysicalAddressType type: address.types()) {
            switch (type) {
            case HOME:
              contact.getPhysicalAddresses().setPhysicalAddress(PhysicalAddressKey.Home, entry);
              break;
            case WORK:
              contact.getPhysicalAddresses().setPhysicalAddress(PhysicalAddressKey.Business, entry);
              break;
            case OTHER:
              contact.getPhysicalAddresses().setPhysicalAddress(PhysicalAddressKey.Other, entry);
              break;
            }
          }
        } else {
          contact.getPhysicalAddresses().setPhysicalAddress(PhysicalAddressKey.Business, entry);
        }
      }
    }       

    NSArray<String> categories = card.categories();
    if (categories.count() > 0) {
      StringList strings = new StringList();
      for (String category: categories) {
        strings.add(category);
      }
      contact.setCategories(strings);
    }

    if (card.urls().count() > 0) {
      for (ERGWContactUrl url: card.urls()) {
        if (url.type() != null) {
          switch (url.type()) {
          case WORK:
            contact.setBusinessHomePage(url.value());
            break;
          }
        } else {
          contact.setBusinessHomePage(url.value());
        }
      }      
    }

    String nickname = card.nickname();
    if (nickname != null)
      contact.setNickName(nickname);

    if (card.children().count() > 0) {
      StringList strings = new StringList();
      for (String child: card.children()) {
        strings.add(child);
      }      
      contact.setChildren(strings);      
    }

    String manager = card.manager();
    if (manager != null)
      contact.setManager(manager);

    String spouseName = card.spouseName();
    if (spouseName != null)
      contact.setSpouseName(spouseName);


    if (card.imAddresses().count() > 0) {
      if (card.imAddresses().count() > 2) {
        contact.getImAddresses().setImAddressKey(ImAddressKey.ImAddress1, card.imAddresses().objectAtIndex(0));
        contact.getImAddresses().setImAddressKey(ImAddressKey.ImAddress2, card.imAddresses().objectAtIndex(1));
        contact.getImAddresses().setImAddressKey(ImAddressKey.ImAddress3, card.imAddresses().objectAtIndex(2));
      } else if (card.imAddresses().count() == 2) {
        contact.getImAddresses().setImAddressKey(ImAddressKey.ImAddress1, card.imAddresses().objectAtIndex(0));
        contact.getImAddresses().setImAddressKey(ImAddressKey.ImAddress2, card.imAddresses().objectAtIndex(1));
      } else {
        contact.getImAddresses().setImAddressKey(ImAddressKey.ImAddress1, card.imAddresses().objectAtIndex(0));
      }
    }

    if (card.photo() != null) {
      contact.getAttachments().addFileAttachment("ContactPicture.jpg", card.photo().bytes());
    }
    
    contact.save(folder.id());
  }

  public void createTask(ERGWCalendar calendar, ExchangeTasksFolder folder) throws Exception {
    for (ERGWTask task: calendar.tasks()) {
      Task taskType = new Task(service);
      taskType.setSubject(task.summary());

      if (task.dueDate() != null) {
        Calendar dueDate = GregorianCalendar.getInstance();
        dueDate.setTime(task.dueDate());
        if (task.isFullDay()) {
          dueDate.add(Calendar.HOUR_OF_DAY, 5);
        }
        taskType.setDueDate(dueDate.getTime());
      }

      if (task.description() != null) {
        MessageBody bodyType = new MessageBody(task.description());
        taskType.setBody(bodyType);
      }

      NSArray<String> categories = task.categories();
      if (categories != null) {
        StringList strings = new StringList();
        for (String category: categories) {
          strings.add(category);
        }
        taskType.setCategories(strings);
      }

      if (task.priority() != null)
        taskType.setImportance(task.priority().ewsValue());

      if (task.completedOn() != null) {
        Calendar completedDate = GregorianCalendar.getInstance();
        completedDate.setTime(task.completedOn());
        taskType.setCompleteDate(completedDate.getTime());
      }

      taskType.setPercentComplete(new Double(task.percentComplete()));

      if (task.classification() != null) 
        taskType.setSensitivity(task.classification().ewsValue());

      if (task.startTime() != null) {
        Calendar startDate = GregorianCalendar.getInstance();
        startDate.setTime(task.startTime());
        taskType.setStartDate(startDate.getTime());
      }

      if (task.status() != null)
        taskType.setStatus(task.status().ewsValue());

      setAlarms(task, taskType);

      Recurrence recurrence = recurrenceForTask(task, taskType.getStartDate());
      if (recurrence != null)
        taskType.setRecurrence(recurrence);

      taskType.save();      
    }
  }

  public void fetchInvitations() throws NotImplementedException {
    //  <m:FindFolder Traversal="Deep" xmlns:m="http://schemas.microsoft.com/exchange/services/2006/messages" xmlns:t="http://schemas.microsoft.com/exchange/services/2006/types">

    /*

    SearchFolder findFolderRequest = new SearchFolder(service);
    findFolderRequest.getSearchParameters().setTraversal(SearchFolderTraversal.Deep);

    <m:Restriction>
      <t:Or>
        <t:Contains ContainmentMode="FullString" ContainmentComparison="Exact">
          <t:FieldURI FieldURI="folder:FolderClass" />
          <t:Constant Value="IPF.Appointment" />
        </t:Contains>
        <t:Contains ContainmentMode="Prefixed" ContainmentComparison="Exact">
          <t:FieldURI FieldURI="folder:FolderClass" />
          <t:Constant Value="IPF.Appointment." />
        </t:Contains>
        <t:Contains ContainmentMode="FullString" ContainmentComparison="Exact">
          <t:FieldURI FieldURI="folder:FolderClass" />
          <t:Constant Value="IPF.Task" />
        </t:Contains>
        <t:Contains ContainmentMode="Prefixed" ContainmentComparison="Exact">
          <t:FieldURI FieldURI="folder:FolderClass" />
          <t:Constant Value="IPF.Task." />
        </t:Contains>
      </t:Or>
    </m:Restriction>
     */

    /*
    ConstantValueType apptConstant = new ConstantValueType();
    apptConstant.setValue("IPF.Appointment");

    ConstantValueType apptConstantPrefix = new ConstantValueType();
    apptConstantPrefix.setValue("IPF.Appointment.");

    ConstantValueType taskConstant = new ConstantValueType();
    taskConstant.setValue("IPF.Task");

    ConstantValueType taskConstantPrefix = new ConstantValueType();
    taskConstantPrefix.setValue("IPF.Task.");

    PathToUnindexedFieldType folderClass = new PathToUnindexedFieldType();
    folderClass.setFieldURI(UnindexedFieldURIType.FOLDER_CHILD_FOLDER_COUNT);
    JAXBElement<PathToUnindexedFieldType> folderClassElement = factory.createFieldURI(parentFieldType);

    RestrictionType restriction = new RestrictionType();

    OrType searchExprType = new OrType();

    ContainsExpressionType apptFullString = new ContainsExpressionType();
    apptFullString.setContainmentComparison(ContainmentComparisonType.EXACT);
    apptFullString.setContainmentMode(ContainmentModeType.FULL_STRING);
    apptFullString.setConstant(apptConstant);
    apptFullString.setPath(folderClassElement);
    JAXBElement<ContainsExpressionType> apptFullStringExpression = factory.createContains(apptFullString);
    searchExprType.getSearchExpression().add(apptFullStringExpression);

    ContainsExpressionType apptPrefixed = new ContainsExpressionType();
    apptPrefixed.setContainmentComparison(ContainmentComparisonType.EXACT);
    apptPrefixed.setContainmentMode(ContainmentModeType.PREFIXED);
    apptPrefixed.setConstant(apptConstantPrefix);
    apptPrefixed.setPath(folderClassElement);
    JAXBElement<ContainsExpressionType> apptPrefixedExpression = factory.createContains(apptPrefixed);
    searchExprType.getSearchExpression().add(apptPrefixedExpression);

    ContainsExpressionType taskFullString = new ContainsExpressionType();
    taskFullString.setContainmentComparison(ContainmentComparisonType.EXACT);
    taskFullString.setContainmentMode(ContainmentModeType.FULL_STRING);
    taskFullString.setConstant(taskConstant);
    taskFullString.setPath(folderClassElement);
    JAXBElement<ContainsExpressionType> taskFullStringExpression = factory.createContains(taskFullString);
    searchExprType.getSearchExpression().add(taskFullStringExpression);

    ContainsExpressionType taskPrefixed = new ContainsExpressionType();
    taskPrefixed.setContainmentComparison(ContainmentComparisonType.EXACT);
    taskPrefixed.setContainmentMode(ContainmentModeType.PREFIXED);
    taskPrefixed.setConstant(apptConstantPrefix);
    taskPrefixed.setPath(folderClassElement);
    JAXBElement<ContainsExpressionType> taskPrefixedExpression = factory.createContains(taskPrefixed);
    searchExprType.getSearchExpression().add(taskPrefixedExpression);

    JAXBElement<SearchExpressionType> restrictionExpression = factory.createSearchExpression(searchExprType);
    restriction.setSearchExpression(restrictionExpression);
    findFolderRequest.setRestriction(restriction);

    NonEmptyArrayOfBaseFolderIdsType parentFolderIds = new NonEmptyArrayOfBaseFolderIdsType();
    DistinguishedFolderIdType distinguishedFolderId = new DistinguishedFolderIdType();
    distinguishedFolderId.setId(DistinguishedFolderIdNameType.MSGFOLDERROOT);
    parentFolderIds.getFolderIdOrDistinguishedFolderId().add(distinguishedFolderId);
    findFolderRequest.setParentFolderIds(parentFolderIds);

    Holder<FindFolderResponseType> responseHolder = new Holder<FindFolderResponseType>(new FindFolderResponseType());

    port.findFolder(findFolderRequest, mailboxCulture, serverVersionForRequest, tzContext, responseHolder, null);
     */
  }

}
