package er.groupware.calendar;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;

import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VToDo;

import com.webobjects.appserver.xml.WOXMLCoder;
import com.webobjects.foundation.NSTimestamp;

import er.groupware.calendar.enums.IStatus;
import er.groupware.calendar.enums.TaskStatus;

public class ERTask extends ERCalendarObject {

  private NSTimestamp completedOn;
  private NSTimestamp dueDate;
  private int percentComplete;
  private TaskStatus status;

  public ERTask(ERCalendar calendar) {
    calendar.addTask(this);
  }

  @Override
  public TaskStatus status() {
    return status;
  }

  @Override
  public void setStatus(IStatus status) {
    this.status = (TaskStatus)status;
  }

  public NSTimestamp completedOn() {
    return completedOn;
  }

  public void setCompletedOn(NSTimestamp completedOn) {
    this.completedOn = completedOn;
  }

  public NSTimestamp dueDate() {
    return dueDate;
  }

  public void setDueDate(NSTimestamp dueDate) {
    this.dueDate = dueDate;
  }
  
  public int percentComplete() {
    return percentComplete;
  }
  
  public void setPercentComplete(int percentComplete) {
    this.percentComplete = percentComplete;
  }

  public Class<? extends ERTask> classForCoder() {
    return this.getClass();
  }

  public void encodeWithWOXMLCoder(WOXMLCoder arg0) {
    // TODO Auto-generated method stub
    
  }
  
  public static CalendarComponent transformToICalObject(ERTask task) throws SocketException, ParseException, URISyntaxException {
    VToDo vTodo = (VToDo)ERCalendarObject.transformToICalObject(task, new VToDo());
    return vTodo;
  }
  
}
