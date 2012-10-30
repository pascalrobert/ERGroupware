package er.groupware.enums;

import com.microsoft.schemas.exchange.services._2006.types.BaseFolderType;
import com.microsoft.schemas.exchange.services._2006.types.CalendarFolderType;
import com.microsoft.schemas.exchange.services._2006.types.ContactsFolderType;
import com.microsoft.schemas.exchange.services._2006.types.FolderType;
import com.microsoft.schemas.exchange.services._2006.types.SearchFolderType;
import com.microsoft.schemas.exchange.services._2006.types.TasksFolderType;

public enum ERGWFolderType {

  CALENDAR(CalendarFolderType.class),
  CONTACTS(ContactsFolderType.class),
  TASKS(TasksFolderType.class),
  JOURNAL(FolderType.class),
  EMAIL(FolderType.class),
  SEARCH(SearchFolderType.class),
  ROOT(FolderType.class),
  PLAIN(FolderType.class);
  
  private Class<? extends BaseFolderType> exchangeType;
  
  private ERGWFolderType(Class<? extends BaseFolderType> exchangeType) {
    this.exchangeType = exchangeType;
  }
  
  public Class<? extends BaseFolderType> exchangeType() {
    return exchangeType;
  }
  
}
