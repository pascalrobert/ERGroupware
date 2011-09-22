package er.groupware.calendar.enums;


import com.webobjects.foundation.NSArray;

public enum ERGWRecurrence {

  DAILY("Chaque jour"),
  WEEKLY("Chaque semaine"),
  MONTHLY("Chaque mois"),
  YEARLY("Chaque année");
    
  private String description;

  private ERGWRecurrence(String description) {
    this.description = description;
  }
    
  public String description() {
    return description;
  }
  
  public static NSArray<ERGWRecurrence> recurrences() {
    return new NSArray<ERGWRecurrence>(ERGWRecurrence.values());
  }
  
  private ERGWRecurrence() {
  }
  
}
