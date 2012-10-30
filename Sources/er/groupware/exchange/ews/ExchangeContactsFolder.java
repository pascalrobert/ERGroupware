package er.groupware.exchange.ews;

import er.groupware.enums.ERGWFolderType;

public class ExchangeContactsFolder extends ExchangeBaseFolder {

  public ExchangeContactsFolder(String displayName) {
    super(ERGWFolderType.CONTACTS, displayName);
  }

}
