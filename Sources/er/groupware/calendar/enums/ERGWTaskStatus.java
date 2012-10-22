package er.groupware.calendar.enums;


import net.fortuna.ical4j.model.property.Status;

import com.microsoft.schemas.exchange.services._2006.types.TaskStatusType;
import com.webobjects.foundation.NSArray;
import com.zimbra.cs.mailbox.calendar.IcalXmlStrMap;

import er.groupware.calendar.ERGWCalendarPrincipal;

public enum ERGWTaskStatus implements ERGWIStatus, ERGWICalendarProperty {

  NEEDS_ACTION("Non commencé", Status.VTODO_NEEDS_ACTION, IcalXmlStrMap.STATUS_NEEDS_ACTION, TaskStatusType.NOT_STARTED),
  IN_PROCESS("En cours", Status.VTODO_IN_PROCESS, IcalXmlStrMap.STATUS_IN_PROCESS, TaskStatusType.IN_PROGRESS),
  COMPLETED("Terminé", Status.VTODO_COMPLETED, IcalXmlStrMap.STATUS_COMPLETED, TaskStatusType.COMPLETED),
  CANCELLED("Annulé", Status.VTODO_CANCELLED, IcalXmlStrMap.STATUS_CANCELLED, TaskStatusType.DEFERRED);
  
  private String localizedDescription;
  private Status rfc2445Value;
  private String zimbraValue;
  private TaskStatusType ewsValue;

  private ERGWTaskStatus(String localizedDescription, Status rfc2445Value, String zimbraValue, TaskStatusType ewsValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }
    
  public String localizedDescription() {
    return ERGWCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }
  
  public Status rfc2445Value() {
    return rfc2445Value;
  }

  public String zimbraValue() {
    return zimbraValue;
  }

  public static NSArray<ERGWTaskStatus> statuses() {
    return new NSArray<ERGWTaskStatus>(ERGWTaskStatus.values());
  }
  
  private ERGWTaskStatus() {
  }

  public TaskStatusType ewsValue() {
    return ewsValue;
  }
  
}
