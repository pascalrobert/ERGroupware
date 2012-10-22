package er.groupware.contacts;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.groupware.contacts.enums.ERGWContactTelephoneType;

public class ERGWContactTelephone {

  protected NSArray<ERGWContactTelephoneType> types;
  protected String value;
  protected boolean isPrefered;
  protected boolean isVoiceNumber;
  protected boolean isFaxNumber;
  
  public ERGWContactTelephone() {
    types = new NSArray<ERGWContactTelephoneType>();
    isPrefered = false;
    isVoiceNumber = false;
    isFaxNumber = false;
  }

  public NSArray<ERGWContactTelephoneType> types() {
    return types;
  }

  public void setTypes(NSArray<ERGWContactTelephoneType> type) {
    this.types = type;
  }
  
  public void addType(ERGWContactTelephoneType type) {
    NSMutableArray<ERGWContactTelephoneType> mutableTypes = this.types().mutableClone();
    mutableTypes.addObject(type);
    setTypes(mutableTypes.immutableClone());
  }

  public String value() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isPrefered() {
    return isPrefered;
  }

  public void setIsPrefered(boolean isPrefered) {
    this.isPrefered = isPrefered;
  }
  
  public boolean isVoiceNumber() {
    return isVoiceNumber;
  }

  public void setIsVoiceNumber(boolean isVoiceNumber) {
    this.isVoiceNumber = isVoiceNumber;
  }
  
  public boolean isFaxNumber() {
    return isFaxNumber;
  }

  public void setIsFaxNumber(boolean isFaxNumber) {
    this.isFaxNumber = isFaxNumber;
  }
  
}
