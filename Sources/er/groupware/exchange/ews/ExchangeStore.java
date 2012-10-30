package er.groupware.exchange.ews;

import java.net.Authenticator;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import com.microsoft.schemas.exchange.services._2006.messages.ArrayOfResponseMessagesType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateAttachmentResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateAttachmentType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateFolderResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateFolderType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateItemResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.CreateItemType;
import com.microsoft.schemas.exchange.services._2006.messages.ExchangeServicePortType;
import com.microsoft.schemas.exchange.services._2006.messages.ExchangeWebService;
import com.microsoft.schemas.exchange.services._2006.messages.FolderInfoResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.ItemInfoResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.SyncFolderHierarchyResponseMessageType;
import com.microsoft.schemas.exchange.services._2006.messages.SyncFolderHierarchyResponseType;
import com.microsoft.schemas.exchange.services._2006.messages.SyncFolderHierarchyType;
import com.microsoft.schemas.exchange.services._2006.types.AbsoluteMonthlyRecurrencePatternType;
import com.microsoft.schemas.exchange.services._2006.types.ArrayOfFoldersType;
import com.microsoft.schemas.exchange.services._2006.types.ArrayOfRealItemsType;
import com.microsoft.schemas.exchange.services._2006.types.ArrayOfStringsType;
import com.microsoft.schemas.exchange.services._2006.types.AttendeeType;
import com.microsoft.schemas.exchange.services._2006.types.BaseFolderType;
import com.microsoft.schemas.exchange.services._2006.types.BodyType;
import com.microsoft.schemas.exchange.services._2006.types.BodyTypeType;
import com.microsoft.schemas.exchange.services._2006.types.CalendarFolderType;
import com.microsoft.schemas.exchange.services._2006.types.CalendarItemCreateOrDeleteOperationType;
import com.microsoft.schemas.exchange.services._2006.types.CalendarItemType;
import com.microsoft.schemas.exchange.services._2006.types.ContactItemType;
import com.microsoft.schemas.exchange.services._2006.types.ContactsFolderType;
import com.microsoft.schemas.exchange.services._2006.types.DayOfWeekIndexType;
import com.microsoft.schemas.exchange.services._2006.types.DayOfWeekType;
import com.microsoft.schemas.exchange.services._2006.types.DefaultShapeNamesType;
import com.microsoft.schemas.exchange.services._2006.types.DistinguishedFolderIdNameType;
import com.microsoft.schemas.exchange.services._2006.types.DistinguishedFolderIdType;
import com.microsoft.schemas.exchange.services._2006.types.EmailAddressDictionaryEntryType;
import com.microsoft.schemas.exchange.services._2006.types.EmailAddressDictionaryType;
import com.microsoft.schemas.exchange.services._2006.types.EmailAddressKeyType;
import com.microsoft.schemas.exchange.services._2006.types.EmailAddressType;
import com.microsoft.schemas.exchange.services._2006.types.EndDateRecurrenceRangeType;
import com.microsoft.schemas.exchange.services._2006.types.ExchangeVersionType;
import com.microsoft.schemas.exchange.services._2006.types.FileAttachmentType;
import com.microsoft.schemas.exchange.services._2006.types.FolderIdType;
import com.microsoft.schemas.exchange.services._2006.types.FolderResponseShapeType;
import com.microsoft.schemas.exchange.services._2006.types.ImAddressDictionaryEntryType;
import com.microsoft.schemas.exchange.services._2006.types.ImAddressDictionaryType;
import com.microsoft.schemas.exchange.services._2006.types.ImAddressKeyType;
import com.microsoft.schemas.exchange.services._2006.types.ImportanceChoicesType;
import com.microsoft.schemas.exchange.services._2006.types.ItemIdType;
import com.microsoft.schemas.exchange.services._2006.types.ItemType;
import com.microsoft.schemas.exchange.services._2006.types.MailboxCultureType;
import com.microsoft.schemas.exchange.services._2006.types.MapiPropertyTypeType;
import com.microsoft.schemas.exchange.services._2006.types.NoEndRecurrenceRangeType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfAllItemsType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfAttachmentsType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfAttendeesType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfFoldersType;
import com.microsoft.schemas.exchange.services._2006.types.NonEmptyArrayOfPathsToElementType;
import com.microsoft.schemas.exchange.services._2006.types.NumberedRecurrenceRangeType;
import com.microsoft.schemas.exchange.services._2006.types.ObjectFactory;
import com.microsoft.schemas.exchange.services._2006.types.PathToExtendedFieldType;
import com.microsoft.schemas.exchange.services._2006.types.PathToUnindexedFieldType;
import com.microsoft.schemas.exchange.services._2006.types.PhoneNumberDictionaryEntryType;
import com.microsoft.schemas.exchange.services._2006.types.PhoneNumberDictionaryType;
import com.microsoft.schemas.exchange.services._2006.types.PhoneNumberKeyType;
import com.microsoft.schemas.exchange.services._2006.types.PhysicalAddressDictionaryEntryType;
import com.microsoft.schemas.exchange.services._2006.types.PhysicalAddressDictionaryType;
import com.microsoft.schemas.exchange.services._2006.types.PhysicalAddressKeyType;
import com.microsoft.schemas.exchange.services._2006.types.RecurrenceType;
import com.microsoft.schemas.exchange.services._2006.types.RelativeMonthlyRecurrencePatternType;
import com.microsoft.schemas.exchange.services._2006.types.RequestServerVersion;
import com.microsoft.schemas.exchange.services._2006.types.ResponseClassType;
import com.microsoft.schemas.exchange.services._2006.types.SearchFolderType;
import com.microsoft.schemas.exchange.services._2006.types.SensitivityChoicesType;
import com.microsoft.schemas.exchange.services._2006.types.ServerVersionInfo;
import com.microsoft.schemas.exchange.services._2006.types.SingleRecipientType;
import com.microsoft.schemas.exchange.services._2006.types.SyncFolderHierarchyCreateOrUpdateType;
import com.microsoft.schemas.exchange.services._2006.types.TargetFolderIdType;
import com.microsoft.schemas.exchange.services._2006.types.TaskType;
import com.microsoft.schemas.exchange.services._2006.types.TasksFolderType;
import com.microsoft.schemas.exchange.services._2006.types.TimeZoneContextType;
import com.microsoft.schemas.exchange.services._2006.types.TimeZoneDefinitionType;
import com.microsoft.schemas.exchange.services._2006.types.UnindexedFieldURIType;
import com.microsoft.schemas.exchange.services._2006.types.WeeklyRecurrencePatternType;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;
import com.webobjects.foundation.NSValidation;

import er.groupware.calendar.ERGWAlarm;
import er.groupware.calendar.ERGWAttendee;
import er.groupware.calendar.ERGWCalendar;
import er.groupware.calendar.ERGWEvent;
import er.groupware.calendar.ERGWOrganizer;
import er.groupware.calendar.ERGWRecurrencePeriod;
import er.groupware.calendar.ERGWRecurrenceRule;
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

  protected ExchangeServicePortType port;
  protected RequestServerVersion serverVersionForRequest;
  protected ServerVersionInfo serverVersionInResponse;
  protected TimeZoneContextType tzContext;
  protected MailboxCultureType mailboxCulture;
  protected String syncState;

  /**
   * This constructor will connect to a Microsoft Exchange server and authenticate the user.
   * 
   * @param urlToWSDL URL to the WSDL of your Exchange WSDL files. You can use the one in this framework by using ERXApplication.application().resourceManager().pathURLForResourceNamed("Services.wsdl", "ERGroupware", null);
   * @param pathToWS Path to exchange.asmx on your Exchange host (should look like https://mydomain.com/EWS/exchange.asmx)
   * @param username User to connect to the Exchange service
   * @param password Password to connect to the Exchange service
   * @param ntmlDomain If your Exchange host is only accepting NTML auth, this parameter is to specify in which domain your user is
   * 
   */
  public ExchangeStore(URL urlToWSDL, String pathToWS, String username, String password, String ntmlDomain) {

    if (ntmlDomain != null) {
      ExchangeAuthenticator authenticator = new ExchangeAuthenticator(ntmlDomain + "\\" + username, password.toCharArray());
      Authenticator.setDefault(authenticator);
    }

    ExchangeWebService service = new ExchangeWebService(urlToWSDL, new QName("http://schemas.microsoft.com/exchange/services/2006/messages", "ExchangeWebService"));
    port = service.getExchangeWebPort();

    ((BindingProvider)port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
    ((BindingProvider)port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
    ((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, pathToWS);

    serverVersionForRequest = new RequestServerVersion();
    serverVersionForRequest.setVersion(ExchangeVersionType.EXCHANGE_2007_SP_1);

    TimeZoneDefinitionType tzType = new TimeZoneDefinitionType();
    tzType.setId("UTC");
    tzContext = new TimeZoneContextType();
    tzContext.setTimeZoneDefinition(tzType);

    mailboxCulture = new MailboxCultureType();
    mailboxCulture.setValue("en-US");
    
    syncState = null;
  }

  /**
   * Optional. Specify the version of the EWS schema that we want. If none if specified, defaults to Exchange 2007 SP 1
   * 
   * @param versionForRequest Constant from com.microsoft.schemas.exchange.services._2006.types.ExchangeVersionType 
   */
  public void setServerVersionForRequest(ExchangeVersionType versionForRequest) {
    this.serverVersionForRequest.setVersion(versionForRequest);
  }

  /**
   * Optional. Set the timezone context for the mailboxes. If none is specified, UTC will be used.
   * 
   * @param timezone A java.util.TimeZone object (TimeZone.getTimeZone("Etc/GMT"), etc.)
   */
  public void setTimeZone(TimeZone timezone) {
    TimeZoneDefinitionType tzType = new TimeZoneDefinitionType();
    tzType.setId(timezone.getDisplayName(Locale.US));
    tzContext.setTimeZoneDefinition(tzType);
  }

  /**
   * Optional. Set the mailbox culture for the creation of items. If none is specified, en-US will be used.
   * 
   * @param countryLanguage 5 chars identifier (en-US, fr-CA, etc.)
   */
  public void setMailboxCulture(String countryLanguage) {
    mailboxCulture.setValue("en-US");
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

  public NSArray<ExchangeBaseFolder> folders() {
    ObjectFactory factory = new ObjectFactory();
    NSMutableArray<ExchangeBaseFolder> folders = new NSMutableArray<ExchangeBaseFolder>();
    
    SyncFolderHierarchyType syncFolderRequest = new SyncFolderHierarchyType();
    
    FolderResponseShapeType shape = new FolderResponseShapeType();
    shape.setBaseShape(DefaultShapeNamesType.ID_ONLY);
    
    NonEmptyArrayOfPathsToElementType propsArray = new NonEmptyArrayOfPathsToElementType();
    
    PathToUnindexedFieldType parentFieldType = new PathToUnindexedFieldType();
    parentFieldType.setFieldURI(UnindexedFieldURIType.FOLDER_PARENT_FOLDER_ID);
    JAXBElement<PathToUnindexedFieldType> parentFolderId = factory.createFieldURI(parentFieldType);
    propsArray.getPath().add(parentFolderId);
    
    PathToUnindexedFieldType displayNameFieldType = new PathToUnindexedFieldType();
    displayNameFieldType.setFieldURI(UnindexedFieldURIType.FOLDER_DISPLAY_NAME);
    JAXBElement<PathToUnindexedFieldType> displayName = factory.createFieldURI(displayNameFieldType);
    propsArray.getPath().add(displayName);
    
    PathToUnindexedFieldType folderClassType = new PathToUnindexedFieldType();
    folderClassType.setFieldURI(UnindexedFieldURIType.FOLDER_FOLDER_CLASS);
    JAXBElement<PathToUnindexedFieldType> folderClass = factory.createFieldURI(folderClassType);
    propsArray.getPath().add(folderClass);
    
    PathToUnindexedFieldType managedFolderInfoType = new PathToUnindexedFieldType();
    managedFolderInfoType.setFieldURI(UnindexedFieldURIType.FOLDER_MANAGED_FOLDER_INFORMATION);
    JAXBElement<PathToUnindexedFieldType> managedFolderInfo = factory.createFieldURI(managedFolderInfoType);
    propsArray.getPath().add(managedFolderInfo);
    
    PathToExtendedFieldType hiddenType = new PathToExtendedFieldType();
    hiddenType.setPropertyTag("0x10F4");
    hiddenType.setPropertyType(MapiPropertyTypeType.BOOLEAN);
    JAXBElement<PathToExtendedFieldType> hidden = factory.createExtendedFieldURI(hiddenType);
    propsArray.getPath().add(hidden);
    
    shape.setAdditionalProperties(propsArray);
    
    syncFolderRequest.setFolderShape(shape);
    
    if (this.syncState() != null) {
      syncFolderRequest.setSyncState(this.syncState());
    }
    
    TargetFolderIdType idType = new TargetFolderIdType();
    idType.setDistinguishedFolderId(null);
    syncFolderRequest.setSyncFolderId(null);
    
    Holder<SyncFolderHierarchyResponseType> syncFolderHolder = new Holder<SyncFolderHierarchyResponseType>();

    port.syncFolderHierarchy(syncFolderRequest, mailboxCulture, serverVersionForRequest, syncFolderHolder, null);
    
    SyncFolderHierarchyResponseType response = syncFolderHolder.value;
    ArrayOfResponseMessagesType responses = response.getResponseMessages();
    for (javax.xml.bind.JAXBElement responseObject: responses.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()) {
      SyncFolderHierarchyResponseMessageType itemResponse = (SyncFolderHierarchyResponseMessageType)responseObject.getValue();
      if ("NoError".equals(itemResponse.getResponseCode())) {
        this.setSyncState(syncState);
        List<JAXBElement<?>> iterator = itemResponse.getChanges().getCreateOrUpdateOrDelete();
        for (JAXBElement<?> element: iterator) {
          SyncFolderHierarchyCreateOrUpdateType value = (SyncFolderHierarchyCreateOrUpdateType) element.getValue();
          if (value.getCalendarFolder() != null) {
            ExchangeCalendarFolder calendarFolder = (ExchangeCalendarFolder) ExchangeCalendarFolder.createFromServer(value.getCalendarFolder());
            folders.add(calendarFolder);
          }
          if (value.getContactsFolder() != null) {
            ExchangeContactsFolder contactsFolder = (ExchangeContactsFolder) ExchangeContactsFolder.createFromServer(value.getContactsFolder());
            folders.add(contactsFolder);
          }
          if (value.getTasksFolder() != null) {
            ExchangeTasksFolder tasksFolder = (ExchangeTasksFolder) ExchangeTasksFolder.createFromServer(value.getTasksFolder());
            folders.add(tasksFolder);
          }
        }
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
  public void createCalendarFolder(String displayName) throws FolderAlreadyExistsException, Throwable {
    createFolder(displayName, ERGWFolderType.CALENDAR);
  }
  
  /**
   * 
   * @param displayName The name of the contacts folder
   * @throws Throwable 
   * @throws FolderAlreadyExistsException 
   */
  public void createContactsFolder(String displayName) throws FolderAlreadyExistsException, Throwable {
    createFolder(displayName, ERGWFolderType.CALENDAR);
  }
  
  /**
   * 
   * @param folderDisplayName The name of the calendar
   * @param typeOfFolder The type of calendar (Calendar, Tasks, Search, etc.) to create
   * @throws Throwable, FolderAlreadyExistsException 
   */
  public void createFolder(String folderDisplayName, ERGWFolderType typeOfFolder) throws Throwable, FolderAlreadyExistsException {

    TargetFolderIdType rootFolderId = new TargetFolderIdType();
    DistinguishedFolderIdType rootFolderType = new DistinguishedFolderIdType();
    rootFolderType.setId(DistinguishedFolderIdNameType.MSGFOLDERROOT);
    rootFolderId.setDistinguishedFolderId(rootFolderType);

    Holder<CreateFolderResponseType> folderHolder = new Holder<CreateFolderResponseType>(new CreateFolderResponseType());
    
    BaseFolderType newFolder = null;
    if (typeOfFolder.equals(ERGWFolderType.CALENDAR)) {
      newFolder = new CalendarFolderType();
    } else if (typeOfFolder.equals(ERGWFolderType.CONTACTS)) {
      newFolder = new ContactsFolderType();
    } else if (typeOfFolder.equals(ERGWFolderType.TASKS)) {
      newFolder = new TasksFolderType();
    } else if (typeOfFolder.equals(ERGWFolderType.SEARCH)) {
      newFolder = new SearchFolderType();
    } else {
      newFolder = new CalendarFolderType();
    }
    
    newFolder.setDisplayName(folderDisplayName);

    CreateFolderType folderType = new CreateFolderType();
    NonEmptyArrayOfFoldersType folders = new NonEmptyArrayOfFoldersType();
    folders.getFolderOrCalendarFolderOrContactsFolder().add(newFolder);
    folderType.setParentFolderId(rootFolderId);
    folderType.setFolders(folders);

    Holder<ServerVersionInfo> holderForServerVersion = new Holder<ServerVersionInfo>(new ServerVersionInfo());

    port.createFolder(folderType, serverVersionForRequest, tzContext, folderHolder, holderForServerVersion);

    CreateFolderResponseType response = folderHolder.value;
    ArrayOfResponseMessagesType responses = response.getResponseMessages();
    for (javax.xml.bind.JAXBElement responseObject: responses.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()) {
      FolderInfoResponseMessageType itemResponse = (FolderInfoResponseMessageType)responseObject.getValue();
      NSLog.out.appendln(itemResponse.getResponseClass()); // ERROR
      if (itemResponse.getResponseClass().equals(ResponseClassType.ERROR)) {
        NSLog.out.appendln(itemResponse.getResponseCode()); // ErrorFolderExists
        if ("ErrorFolderExists".equals(itemResponse.getResponseCode())) {
          throw new FolderAlreadyExistsException(itemResponse.getMessageText());
        } else {
          throw new Throwable(itemResponse.getMessageText());
        }
      }
      ArrayOfFoldersType items = itemResponse.getFolders();
      for (BaseFolderType item: items.getFolderOrCalendarFolderOrContactsFolder()) {
        NSLog.out.appendln(item.getFolderId());
      }
    }
  }
  
  public class FolderAlreadyExistsException extends NSValidation.ValidationException {

    public FolderAlreadyExistsException(String arg0) {
      super(arg0);
    }
    
  }

  public void createCalendarEvent(ERGWCalendar calendar, ExchangeCalendarFolder calendarFolder) {
    CreateItemType itemDetails = new CreateItemType();
    CalendarItemType calendarItem = new CalendarItemType();

    for (ERGWEvent event: calendar.getEvents()) {

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
        calendarItem.setImportance(ImportanceChoicesType.NORMAL);
      }

      // UID:c35d64d0-2187-45ba-884d-ada56ada927a
      String uid = event.uid();
      if (uid != null)
        calendarItem.setUID(uid);

      // CLASS:PUBLIC
      ERGWClassification classification = event.classification();
      if (classification != null) {
        calendarItem.setSensitivity(classification.ewsValue());
      } else {
        calendarItem.setSensitivity(SensitivityChoicesType.NORMAL);
      }

      // X-MICROSOFT-CDO-ALLDAYEVENT:TRUE
      calendarItem.setIsAllDayEvent(event.isFullDay());

      // X-MICROSOFT-CDO-BUSYSTATUS:BUSY
      ERGWFreeBusyStatus freeBusyStatus = event.freeBusyStatus();
      if (freeBusyStatus != null) 
        calendarItem.setLegacyFreeBusyStatus(freeBusyStatus.ewsValue());
      
      /*
     BEGIN:VALARM
     ACTION:DISPLAY
     DESCRIPTION:Le sujet
     TRIGGER;RELATED=START:-PT15M
     END:VALARM
       */
     
      for (ERGWAlarm alarm: event.alarms()) {
        if (alarm.alarmDate() != null) {
          calendarItem.setReminderIsSet(true);
          java.util.Calendar alarmDueDate = GregorianCalendar.getInstance();
          alarmDueDate.setTime(alarm.alarmDate());
          calendarItem.setReminderDueBy(alarmDueDate);
        } 
        // TODO: managing relative time
        // calendarItem.setReminderMinutesBeforeStart(new Integer(trigger.getDuration().getMinutes()).toString());
      }

      // ORGANIZER;CN="Pascal Robert":mailto:probert@macti.ca
      ERGWOrganizer organizer = event.organizer();
      if (organizer != null) {
        EmailAddressType email = new EmailAddressType();
        
        if (organizer.name() != null)
          email.setName(organizer.name());
        
        if (organizer.emailAddress() != null)
          email.setEmailAddress(organizer.emailAddress());           

        SingleRecipientType recipient = new SingleRecipientType();
        recipient.setMailbox(email);
        //calendarItem.setOrganizer(recipient);         
      }

      // ATTENDEE;RSVP=TRUE;X-SENT=TRUE;CN=probert@macti.ca;CUTYPE=INDIVIDUAL:mailto:probert@macti.ca
      if (event.attendees().count() > 0) {
        NonEmptyArrayOfAttendeesType requiredAttendes = new NonEmptyArrayOfAttendeesType();
        NonEmptyArrayOfAttendeesType optionalAttendes = new NonEmptyArrayOfAttendeesType();
        NonEmptyArrayOfAttendeesType resources = new NonEmptyArrayOfAttendeesType();

        for (ERGWAttendee attendee: event.attendees()) {

          ERGWAttendeeRole role = attendee.role();

          AttendeeType attendeeDetails = new AttendeeType();
          EmailAddressType email = new EmailAddressType();
          //email.setEmailAddress(attendee.emailAddress());
          // FIXME: don't put that in prod!
          email.setEmailAddress("probert@oaciq.com");
          email.setName(attendee.name());
          attendeeDetails.setMailbox(email);

          if (role.equals(ERGWAttendeeRole.REQ_PARTICIPANT)) {
            requiredAttendes.getAttendee().add(attendeeDetails);
          } else if ((role.equals(ERGWAttendeeRole.OPT_PARTICIPANT)) || (role.equals(ERGWAttendeeRole.NON_PARTICIPANT)) || (role.equals(ERGWAttendeeRole.CHAIR))) {
            optionalAttendes.getAttendee().add(attendeeDetails);
          } else {
            resources.getAttendee().add(attendeeDetails);
          }
        }
        
        if (resources.getAttendee().size() > 0) 
          calendarItem.setResources(resources);
        if (requiredAttendes.getAttendee().size() > 0)
          calendarItem.setRequiredAttendees(requiredAttendes);
        if (optionalAttendes.getAttendee().size() > 0)
          calendarItem.setOptionalAttendees(optionalAttendes);
      }
      
      //  CATEGORIES:Appels téléphoniques,Buts/Objectifs
      NSArray<String> categories = event.categories();
      if (categories != null) {
        ArrayOfStringsType strings = new ArrayOfStringsType();
        for (String category: categories) {
          strings.getString().add(category);
        }
        calendarItem.setCategories(strings);
      }

      // SUMMARY: The title
      calendarItem.setSubject(event.summary());

      // DESCRIPTION:La description
      String description = event.description();
      if (description != null) {
        BodyType body = new BodyType();
        body.setBodyType(BodyTypeType.TEXT);
        body.setValue(description);
        calendarItem.setBody(body);
      }

      // DTSTART;VALUE=DATE:20120913
      NSTimestamp eventStartDate = event.startTime();
      Calendar startDate = GregorianCalendar.getInstance();
      startDate.setTime(eventStartDate);
      calendarItem.setStart(startDate);

      // DTEND;VALUE=DATE:20120914
      NSTimestamp eventEndDate = event.endTime();
      Calendar endDate = GregorianCalendar.getInstance();
      endDate.setTime(eventEndDate);
      calendarItem.setEnd(endDate);
      
      ERGWRecurrenceRule recurrenceRule = event.recurrenceRule();
      
      if (recurrenceRule != null) {
        ERGWRecurrenceFrequency frequency = recurrenceRule.frequency();
        // RRULE:FREQ=WEEKLY;BYDAY=FR
        // RRULE:FREQ=WEEKLY;BYDAY=WE,FR
        if (frequency.equals(ERGWRecurrenceFrequency.WEEKLY)) {
          WeeklyRecurrencePatternType pattern = new WeeklyRecurrencePatternType();
          if (recurrenceRule.interval() != null)
            pattern.setInterval(recurrenceRule.interval());
          else 
            pattern.setInterval(1);
          
          if (recurrenceRule.periods().count() > 0) {
            for (ERGWRecurrencePeriod period: recurrenceRule.periods()) {
              if (period.periodType().equals(ERGWRecurrencePeriodType.BYDAY)) {
                for (Object day: period.values()) {
                  if (day instanceof String) {
                    pattern.getDaysOfWeek().add(ERGWRecurrenceDay.getByRFC2445Value(description).ewsValue());
                  }
                }
              }
            }
          } else {
            int starDayOfWeek = startDate.get(Calendar.DAY_OF_WEEK);
            switch (starDayOfWeek) {
              case Calendar.SUNDAY:
                pattern.getDaysOfWeek().add(DayOfWeekType.SUNDAY);
                break;
              case Calendar.MONDAY:
                pattern.getDaysOfWeek().add(DayOfWeekType.MONDAY);
                break;
              case Calendar.TUESDAY:
                pattern.getDaysOfWeek().add(DayOfWeekType.TUESDAY);
                break;
              case Calendar.WEDNESDAY:
                pattern.getDaysOfWeek().add(DayOfWeekType.WEDNESDAY);
                break;
              case Calendar.THURSDAY:
                pattern.getDaysOfWeek().add(DayOfWeekType.THURSDAY);
                break;
              case Calendar.FRIDAY:
                pattern.getDaysOfWeek().add(DayOfWeekType.FRIDAY);
                break;
              case Calendar.SATURDAY:
                pattern.getDaysOfWeek().add(DayOfWeekType.SATURDAY);
                break;
            }
          }

          RecurrenceType recurType = new RecurrenceType();
          recurType.setWeeklyRecurrence(pattern);
          java.util.Date until = recurrenceRule.until();

          if (until != null) {
            // RRULE:FREQ=WEEKLY;UNTIL=20120915T150000Z;BYDAY=FR
            EndDateRecurrenceRangeType endType = new EndDateRecurrenceRangeType();
            XMLGregorianCalendar xmlStartDate = new XMLGregorianCalendarImpl((GregorianCalendar)calendarItem.getStart());
            endType.setStartDate(xmlStartDate);
            Calendar gEndDate = GregorianCalendar.getInstance();
            gEndDate.setTimeInMillis(until.getTime());
            XMLGregorianCalendar xmlEndDate = new XMLGregorianCalendarImpl((GregorianCalendar)gEndDate);
            endType.setEndDate(xmlEndDate);
            recurType.setEndDateRecurrence(endType);

            pattern.setInterval(1);
          } else if (recurrenceRule.repeatCount() > 0) {
            // RRULE:FREQ=WEEKLY;WKST=MO;COUNT=3;INTERVAL=2;BYDAY=FR (SUMMARY:Formation plan de retraite et éducation financière)
            NumberedRecurrenceRangeType countType = new NumberedRecurrenceRangeType();
            countType.setNumberOfOccurrences(recurrenceRule.repeatCount());
            XMLGregorianCalendar xmlStartDate = new XMLGregorianCalendarImpl((GregorianCalendar)calendarItem.getStart());
            countType.setStartDate(xmlStartDate);
            recurType.setNumberedRecurrence(countType);
          } else {
            NoEndRecurrenceRangeType noEndType = new NoEndRecurrenceRangeType();
            XMLGregorianCalendar xmlStartDate = new XMLGregorianCalendarImpl((GregorianCalendar)calendarItem.getStart());
            noEndType.setStartDate(xmlStartDate);
            recurType.setNoEndRecurrence(noEndType);
          }

          calendarItem.setRecurrence(recurType);      

        } else if (frequency.equals(ERGWRecurrenceFrequency.MONTHLY)) {
          // RRULE:FREQ=MONTHLY;BYDAY=MO,TU,WE,TH,FR,SA,SU;BYSETPOS=1 (SUMMARY:Effacer les audiences du mois précédent - rôle - synbad)
          if (recurrenceRule.positions().count() > 0) {
            for (Object setPost: recurrenceRule.positions()) {

              RelativeMonthlyRecurrencePatternType pattern = new RelativeMonthlyRecurrencePatternType();
              pattern.setDaysOfWeek(DayOfWeekType.DAY);

              Integer position = (Integer)setPost;
              switch (position) {
              case 1: 
                pattern.setDayOfWeekIndex(DayOfWeekIndexType.FIRST);
                break;
              case 2:
                pattern.setDayOfWeekIndex(DayOfWeekIndexType.SECOND);
                break;
              case 3:
                pattern.setDayOfWeekIndex(DayOfWeekIndexType.THIRD);
                break;
              case 4:
                pattern.setDayOfWeekIndex(DayOfWeekIndexType.FOURTH);
                break;
              case -1:
                pattern.setDayOfWeekIndex(DayOfWeekIndexType.LAST);
                break;
              default:
                pattern.setDayOfWeekIndex(DayOfWeekIndexType.FIRST);
              }

              pattern.setInterval(1);

              NoEndRecurrenceRangeType noEndType = new NoEndRecurrenceRangeType();
              XMLGregorianCalendar xmlStartDate = new XMLGregorianCalendarImpl((GregorianCalendar)calendarItem.getStart());
              noEndType.setStartDate(xmlStartDate);

              RecurrenceType recurType = new RecurrenceType();
              recurType.setRelativeMonthlyRecurrence(pattern);
              recurType.setNoEndRecurrence(noEndType);

              calendarItem.setRecurrence(recurType);      
            }
          } else {
            // RRULE:FREQ=MONTHLY;BYMONTHDAY=3 (SUMMARY:Préparer et transmettre par courriel à Me Leduc (Mme Charles) plumitif à jour et modifié selon ses exigences Et accompagné des nouvelles plaintes)
            /*
             * The BYMONTHDAY rule part specifies a COMMA character (ASCII decimal 44) separated list of days of the month. 
             * Valid values are 1 to 31 or -31 to -1. For example, -10 represents the tenth to the last day of the month.
             */
            AbsoluteMonthlyRecurrencePatternType pattern = new AbsoluteMonthlyRecurrencePatternType();
            for (ERGWRecurrencePeriod period: recurrenceRule.periods()) {
              for (Object dayOfMonth: period.values()) {
                pattern.setDayOfMonth((Integer)dayOfMonth);               
              }
            }
            pattern.setInterval(recurrenceRule.interval());
          }
        }
      }

      String location = event.location();
      if (location != null)
        calendarItem.setLocation(location);

      NonEmptyArrayOfAllItemsType itemsArray = new NonEmptyArrayOfAllItemsType();
      itemsArray.getItemOrMessageOrCalendarItem().add(calendarItem);
      itemDetails.setItems(itemsArray);

      TargetFolderIdType calendarFolderId = new TargetFolderIdType();
      FolderIdType folderType = new FolderIdType();
      folderType.setId(calendarFolder.id());
      calendarFolderId.setFolderId(folderType);
      itemDetails.setSavedItemFolderId(calendarFolderId);
      itemDetails.setSendMeetingInvitations(CalendarItemCreateOrDeleteOperationType.SEND_TO_NONE);

      Holder<CreateItemResponseType> responseHolder = new Holder<CreateItemResponseType>(new CreateItemResponseType());

      port.createItem(itemDetails, mailboxCulture, serverVersionForRequest, tzContext, responseHolder, null);

      CreateItemResponseType response = responseHolder.value;
      ArrayOfResponseMessagesType responses = response.getResponseMessages();
    }
  }
  
  public void createContact(ERGWContact card) {
    ContactItemType contact = new ContactItemType();

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
      EmailAddressDictionaryType types = new EmailAddressDictionaryType();
      for (ERGWContactEmail email: card.emails()) {
        EmailAddressDictionaryEntryType exEmail = new EmailAddressDictionaryEntryType();
        exEmail.setValue(email.email());

        if (email.types().count() > 0) {
          for (ERGWContactEmailType type: email.types()) {
            switch (type) {
            case WORK:
              exEmail.setKey(EmailAddressKeyType.EMAIL_ADDRESS_1);
              break;
            case HOME:
              exEmail.setKey(EmailAddressKeyType.EMAIL_ADDRESS_2);
              break;
            case OTHER:
              exEmail.setKey(EmailAddressKeyType.EMAIL_ADDRESS_3);
              break;
            }
          }
        } else {
          exEmail.setKey(EmailAddressKeyType.EMAIL_ADDRESS_1);
        }          
        types.getEntry().add(exEmail);
      }
      contact.setEmailAddresses(types); 
    }
    
    String assistant = card.assistantName();
    if (assistant != null)
      contact.setAssistantName(assistant);

    NSTimestamp birthday = card.birthday();
    if (birthday != null) {
      Calendar bdayAsCalendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("Etc/UTC"));
      bdayAsCalendar.setTimeInMillis(birthday.getTime());
      contact.setBirthday(bdayAsCalendar);
    }

    String businessName = card.businessName();
    if (businessName != null) {
      contact.setCompanyName(businessName);
    }
    
    String departmentName = card.departmentName();
    if (departmentName != null) {
      contact.setDepartment(departmentName);
    }

    String notes = card.notes();
    if (notes != null)
      contact.setNotes(notes);

    if (card.telephones().count() > 0) {
      PhoneNumberDictionaryType phoneEntries = new PhoneNumberDictionaryType();
      for (ERGWContactTelephone telephone: card.telephones()) {
        PhoneNumberDictionaryEntryType entry = new PhoneNumberDictionaryEntryType();

        entry.setValue(telephone.value());
        
        for (ERGWContactTelephoneType type: telephone.types()) {
          switch (type) {
          case HOME:
            if (telephone.isFaxNumber()) {
              entry.setKey(PhoneNumberKeyType.HOME_FAX); 
            } else {
              entry.setKey(PhoneNumberKeyType.HOME_PHONE);              
            }
            break;
          case WORK:
            if (telephone.isFaxNumber()) {
              entry.setKey(PhoneNumberKeyType.BUSINESS_FAX); 
            } else {
              entry.setKey(PhoneNumberKeyType.BUSINESS_PHONE);              
            }
            break;
          case ASSISTANT:
            entry.setKey(PhoneNumberKeyType.ASSISTANT_PHONE);  
            break;
          case CAR:
            entry.setKey(PhoneNumberKeyType.CAR_PHONE);  
            break;
          case MOBILE:
            entry.setKey(PhoneNumberKeyType.MOBILE_PHONE);  
            break;
          case PAGER:
            entry.setKey(PhoneNumberKeyType.PAGER);  
            break;
          case VOICE_MSG:
            entry.setKey(PhoneNumberKeyType.CALLBACK);  
            break;
          }
        }

        phoneEntries.getEntry().add(entry);
      }
      contact.setPhoneNumbers(phoneEntries);
    }    

    if (card.physicalAddresses().count() > 0) {
      PhysicalAddressDictionaryType addressEntries = new PhysicalAddressDictionaryType();
      for (ERGWContactPhysicalAddress address: card.physicalAddresses()) {
        PhysicalAddressDictionaryEntryType entry = new PhysicalAddressDictionaryEntryType();
        entry.setCity(address.city());
        entry.setCountryOrRegion(address.country());
        entry.setPostalCode(address.postalCode());
        entry.setState(address.region());
        entry.setStreet(address.street());

        if (address.types().count() > 0) {
          for (ERGWContactPhysicalAddressType type: address.types()) {
            switch (type) {
            case HOME:
              entry.setKey(PhysicalAddressKeyType.HOME);  
              break;
            case WORK:
              entry.setKey(PhysicalAddressKeyType.BUSINESS);  
              break;
            case OTHER:
              entry.setKey(PhysicalAddressKeyType.OTHER);  
              break;
            }
          }
        } else {
          entry.setKey(PhysicalAddressKeyType.BUSINESS);            
        }
        
        addressEntries.getEntry().add(entry);

      }
      contact.setPhysicalAddresses(addressEntries);
    }       

    NSArray<String> categories = card.categories();
    if (categories.count() > 0) {
      ArrayOfStringsType strings = new ArrayOfStringsType();
      for (String category: categories) {
        strings.getString().add(category);
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
      contact.setNickname(nickname);
    
    if (card.children().count() > 0) {
      ArrayOfStringsType strings = new ArrayOfStringsType();
      for (String child: card.children()) {
        strings.getString().add(child);
      }      
      contact.setChildren(strings);      
    }
    
    //if (card.photo() != null)
      //contact.setPhoto(card.photo().bytes());
    
    String manager = card.manager();
    if (manager != null)
      contact.setManager(manager);
    
    String spouseName = card.spouseName();
    if (spouseName != null)
      contact.setSpouseName(spouseName);


    if (card.imAddresses().count() > 0) {
      ImAddressDictionaryType array = new ImAddressDictionaryType();
      if (card.imAddresses().count() > 2) {
        ImAddressDictionaryEntryType entry1 = new ImAddressDictionaryEntryType();
        entry1.setKey(ImAddressKeyType.IM_ADDRESS_1);
        entry1.setValue(card.imAddresses().objectAtIndex(0));
        array.getEntry().add(entry1);

        ImAddressDictionaryEntryType entry2 = new ImAddressDictionaryEntryType();
        entry2.setKey(ImAddressKeyType.IM_ADDRESS_1);
        entry2.setValue(card.imAddresses().objectAtIndex(1));
        array.getEntry().add(entry2);
        
        ImAddressDictionaryEntryType entry3 = new ImAddressDictionaryEntryType();
        entry3.setKey(ImAddressKeyType.IM_ADDRESS_1);
        entry3.setValue(card.imAddresses().objectAtIndex(2));
        array.getEntry().add(entry3);
      } else if (card.imAddresses().count() == 2) {
        ImAddressDictionaryEntryType entry1 = new ImAddressDictionaryEntryType();
        entry1.setKey(ImAddressKeyType.IM_ADDRESS_1);
        entry1.setValue(card.imAddresses().objectAtIndex(0));
        array.getEntry().add(entry1);

        ImAddressDictionaryEntryType entry2 = new ImAddressDictionaryEntryType();
        entry2.setKey(ImAddressKeyType.IM_ADDRESS_1);
        entry2.setValue(card.imAddresses().objectAtIndex(1));
        array.getEntry().add(entry2);        
      } else {
        ImAddressDictionaryEntryType entry1 = new ImAddressDictionaryEntryType();
        entry1.setKey(ImAddressKeyType.IM_ADDRESS_1);
        entry1.setValue(card.imAddresses().objectAtIndex(0));
        array.getEntry().add(entry1);        
      }
      contact.setImAddresses(array);
    }
    
    CreateItemType contactItemDetails = new CreateItemType();
    NonEmptyArrayOfAllItemsType contactsArray = new NonEmptyArrayOfAllItemsType();
    contactsArray.getItemOrMessageOrCalendarItem().add(contact);
    contactItemDetails.setItems(contactsArray);

    TargetFolderIdType contactFolderId = new TargetFolderIdType();
    DistinguishedFolderIdType contactFolderType = new DistinguishedFolderIdType();
    contactFolderType.setId(DistinguishedFolderIdNameType.CONTACTS);
    contactFolderId.setDistinguishedFolderId(contactFolderType);
    contactItemDetails.setSavedItemFolderId(contactFolderId);

    Holder<CreateItemResponseType> responseHolder = new Holder<CreateItemResponseType>(new CreateItemResponseType());

    port.createItem(contactItemDetails, mailboxCulture, serverVersionForRequest, tzContext, responseHolder, null);

    String idOfNewContact = null;

    CreateItemResponseType response = responseHolder.value;
    ArrayOfResponseMessagesType responses = response.getResponseMessages();
    for (javax.xml.bind.JAXBElement responseObject: responses.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()) {
      ItemInfoResponseMessageType itemResponse = (ItemInfoResponseMessageType)responseObject.getValue();
      NSLog.out.appendln(itemResponse.getResponseCode());
      ArrayOfRealItemsType items = itemResponse.getItems();
      for (ItemType item: items.getItemOrMessageOrCalendarItem()) {
        idOfNewContact = ((ContactItemType)item).getItemId().getId();
        NSLog.out.appendln(((ContactItemType)item).getItemId().getChangeKey());
      }
    }

    if (card.photo() != null) {
      if (idOfNewContact != null) {
        ItemIdType id = new ItemIdType();
        id.setId(idOfNewContact);
        CreateAttachmentType attachment = new CreateAttachmentType();
        attachment.setParentItemId(id);
        NonEmptyArrayOfAttachmentsType array = new NonEmptyArrayOfAttachmentsType();
        FileAttachmentType type = new FileAttachmentType();
        type.setContent(card.photo().bytes());
        type.setName("ContactPicture.jpg");
        array.getItemAttachmentOrFileAttachment().add(type);
        attachment.setAttachments(array);
        
        Holder<CreateAttachmentResponseType> attachmentHolder = new Holder<CreateAttachmentResponseType>(new CreateAttachmentResponseType());

        port.createAttachment(attachment, mailboxCulture, serverVersionForRequest, tzContext, attachmentHolder, null);
      }
    }

  }

  public void createTask(ERGWCalendar task) {
    TaskType taskType = new TaskType();
    taskType.setDueDate(GregorianCalendar.getInstance());
    taskType.setSubject("Une tâche");

    CreateItemType taskDetails = new CreateItemType();
    NonEmptyArrayOfAllItemsType tasksArray = new NonEmptyArrayOfAllItemsType();
    tasksArray.getItemOrMessageOrCalendarItem().add(taskType);
    taskDetails.setItems(tasksArray);

    TargetFolderIdType tasksFolderId = new TargetFolderIdType();
    DistinguishedFolderIdType tasksFolderType = new DistinguishedFolderIdType();
    tasksFolderType.setId(DistinguishedFolderIdNameType.TASKS);
    tasksFolderId.setDistinguishedFolderId(tasksFolderType);
    taskDetails.setSavedItemFolderId(tasksFolderId);

    Holder<CreateItemResponseType> responseHolder = new Holder<CreateItemResponseType>(new CreateItemResponseType());

    port.createItem(taskDetails, mailboxCulture, serverVersionForRequest, tzContext, responseHolder, null);
    
    CreateItemResponseType response = responseHolder.value;
    ArrayOfResponseMessagesType responses = response.getResponseMessages();
    for (javax.xml.bind.JAXBElement responseObject: responses.getCreateItemResponseMessageOrDeleteItemResponseMessageOrGetItemResponseMessage()) {
      ItemInfoResponseMessageType itemResponse = (ItemInfoResponseMessageType)responseObject.getValue();
      NSLog.out.appendln(itemResponse.getResponseCode());
      ArrayOfRealItemsType items = itemResponse.getItems();
      for (ItemType item: items.getItemOrMessageOrCalendarItem()) {
        ((ContactItemType)item).getItemId().getId();
        NSLog.out.appendln(((ContactItemType)item).getItemId().getChangeKey());
      }
    }
  }
  
}
