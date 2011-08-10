package er.groupware.calendar.enums;


import com.webobjects.foundation.NSArray;

public enum Recurrence {

  DAILY("Chaque jour"),
  WEEKLY("Chaque semaine"),
  MONTHLY("Chaque mois"),
  YEARLY("Chaque année");
    
  private String description;

  private Recurrence(String description) {
    this.description = description;
  }
    
  public String description() {
    return description;
  }
  
  public static NSArray<Recurrence> recurrences() {
    return new NSArray<Recurrence>(Recurrence.values());
  }
  
  private Recurrence() {
  }
  
}
