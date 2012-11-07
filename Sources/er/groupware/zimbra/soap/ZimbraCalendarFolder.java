package er.groupware.zimbra.soap;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.groupware.calendar.ERGWCalendarObject;
import er.groupware.enums.ERGWFolderType;

public class ZimbraCalendarFolder extends ZimbraFolder {

  protected NSArray<ERGWCalendarObject> appointments;
  
  public ZimbraCalendarFolder() {
    this.setFolderType(ERGWFolderType.CALENDAR);
    this.appointments = new NSArray<ERGWCalendarObject>();
  }

  @Override
  public boolean canHaveChildren() {
    return false;
  }

  public NSArray<ERGWCalendarObject> appointments() {
    return appointments;
  }

  public void setAppointments(NSArray<ERGWCalendarObject> appointments) {
    this.appointments = appointments;
  }
  
  public void addAppointment(ERGWCalendarObject appointment) {
    NSMutableArray<ERGWCalendarObject> array = this.appointments().mutableClone();
    array.addObject(appointment);
    this.setAppointments(array.immutableClone());
  }
  
}
