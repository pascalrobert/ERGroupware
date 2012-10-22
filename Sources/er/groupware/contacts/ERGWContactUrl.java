package er.groupware.contacts;

import er.groupware.contacts.enums.ERGWContactUrlType;

public class ERGWContactUrl {

  protected ERGWContactUrlType type;
  protected String value;
  
  public ERGWContactUrl() {
    
  }

  public ERGWContactUrlType type() {
    return type;
  }

  public void setType(ERGWContactUrlType type) {
    this.type = type;
  }

  public String value() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
  
}
