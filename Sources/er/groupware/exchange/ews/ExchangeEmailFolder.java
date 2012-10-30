package er.groupware.exchange.ews;

import er.groupware.enums.ERGWFolderType;

public class ExchangeEmailFolder extends ExchangeBaseFolder {

  public ExchangeEmailFolder(String displayName) {
    super(ERGWFolderType.EMAIL, displayName);
  }

}
