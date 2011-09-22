package er.groupware.calendar.enums;


import net.fortuna.ical4j.model.property.Status;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.mailbox.calendar.IcalXmlStrMap;

import er.groupware.calendar.ERCalendarPrincipal;

public enum TaskStatus implements IStatus, ICalendarProperty {

  NEEDS_ACTION("Non commencé", Status.VTODO_NEEDS_ACTION, IcalXmlStrMap.STATUS_NEEDS_ACTION),
  IN_PROCESS("En cours", Status.VTODO_IN_PROCESS, IcalXmlStrMap.STATUS_IN_PROCESS),
  COMPLETED("Terminé", Status.VTODO_COMPLETED, IcalXmlStrMap.STATUS_COMPLETED),
  CANCELLED("Annulé", Status.VTODO_CANCELLED, IcalXmlStrMap.STATUS_CANCELLED);
  
  private String localizedDescription;
  private Status rfc2445Value;
  private String zimbraValue;

  private TaskStatus(String localizedDescription, Status rfc2445Value, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }
    
  public String localizedDescription() {
    return ERCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }
  
  public Status rfc2445Value() {
    return rfc2445Value;
  }

  public String zimbraValue() {
    return zimbraValue;
  }

  public static NSArray<TaskStatus> statuses() {
    return new NSArray<TaskStatus>(TaskStatus.values());
  }
  
  private TaskStatus() {
  }
  
}
