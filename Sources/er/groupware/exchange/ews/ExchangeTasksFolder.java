package er.groupware.exchange.ews;

import er.groupware.enums.ERGWFolderType;

public class ExchangeTasksFolder extends ExchangeBaseFolder {

  public ExchangeTasksFolder(String displayName) {
    super(ERGWFolderType.TASKS, displayName);
  }

}
