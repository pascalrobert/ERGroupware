package er.groupware.exchange.ews;

import er.groupware.enums.ERGWFolderType;

public class ExchangeCalendarFolder extends ExchangeBaseFolder {

  public ExchangeCalendarFolder(String displayName) {
    super(ERGWFolderType.CALENDAR, displayName);
  }
  
}
