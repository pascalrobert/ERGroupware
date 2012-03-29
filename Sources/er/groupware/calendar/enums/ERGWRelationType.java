package er.groupware.calendar.enums;


import net.fortuna.ical4j.model.parameter.RelType;

import com.webobjects.foundation.NSArray;

public enum ERGWRelationType {

  CHILD("child", RelType.CHILD),
  PARENT("parent", RelType.PARENT),
  SIBLING("sibling", RelType.SIBLING);
    
  private String localizedDescription;
  private RelType rfc2445Value;

  private ERGWRelationType(String localizedDescription, RelType zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = zimbraValue;
  }
  
  public String localizedDescription() {
    return localizedDescription;
  }

  public RelType zimbraValue() {
    return rfc2445Value;
  }
  
  public static NSArray<ERGWRelationType> types() {
    return new NSArray<ERGWRelationType>(ERGWRelationType.values());
  }
  
  private ERGWRelationType() {
  }
  
}
