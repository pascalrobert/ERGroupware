package er.groupware.enums;

import microsoft.exchange.webservices.data.CalendarFolder;
import microsoft.exchange.webservices.data.ContactsFolder;
import microsoft.exchange.webservices.data.Folder;
import microsoft.exchange.webservices.data.SearchFolder;
import microsoft.exchange.webservices.data.TasksFolder;

public enum ERGWFolderType {

  CALENDAR(CalendarFolder.class),
  CONTACTS(ContactsFolder.class),
  TASKS(TasksFolder.class),
  JOURNAL(Folder.class),
  EMAIL(Folder.class),
  SEARCH(SearchFolder.class),
  ROOT(Folder.class),
  PLAIN(Folder.class),
  CONVERSATION(null),
  TAG(null),
  USER_ROOT(null),
  TRASH(null),
  DOCUMENT(null);
  
  private Class<? extends Folder> exchangeType;
  
  private ERGWFolderType(Class<? extends Folder> exchangeType) {
    this.exchangeType = exchangeType;
  }
  
  public Class<? extends Folder> exchangeType() {
    return exchangeType;
  }
  
}
