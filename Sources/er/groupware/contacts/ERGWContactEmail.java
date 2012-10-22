package er.groupware.contacts;

import com.webobjects.foundation.NSArray;

import er.groupware.contacts.enums.ERGWContactEmailType;

public class ERGWContactEmail {

  protected NSArray<ERGWContactEmailType> types;
  protected String value;
  protected String commonName;
  protected boolean isPrefered;
  
  public ERGWContactEmail() {
    types = new NSArray<ERGWContactEmailType>();
  }

  public NSArray<ERGWContactEmailType> types() {
    return types;
  }

  public void setTypes(NSArray<ERGWContactEmailType> types) {
    this.types = types;
  }

  public String email() {
    return value;
  }

  public void setEmail(String email) {
    this.value = email;
  }

  public boolean isPrefered() {
    return isPrefered;
  }

  public void setIsPrefered(boolean isPrefered) {
    this.isPrefered = isPrefered;
  }

  public String commonName() {
    return commonName;
  }

  public void setCommonName(String commonName) {
    this.commonName = commonName;
  }
  
}
