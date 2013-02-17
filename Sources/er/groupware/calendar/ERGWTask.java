package er.groupware.calendar;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.model.property.Due;

import com.webobjects.appserver.xml.WOXMLCoder;
import com.webobjects.foundation.NSTimestamp;

import er.groupware.calendar.enums.ERGWIStatus;
import er.groupware.calendar.enums.ERGWTaskStatus;

public class ERGWTask extends ERGWCalendarObject {

  private NSTimestamp completedOn;
  private NSTimestamp dueDate;
  private int percentComplete;
  private ERGWTaskStatus status;

  public ERGWTask(ERGWCalendar calendar) {
    calendar.addTask(this);
  }

  @Override
  public ERGWTaskStatus status() {
    return status;
  }

  @Override
  public void setStatus(ERGWIStatus status) {
    this.status = (ERGWTaskStatus)status;
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

  public Class<? extends ERGWTask> classForCoder() {
    return this.getClass();
  }

  public void encodeWithWOXMLCoder(WOXMLCoder arg0) {
    // TODO Auto-generated method stub
    
  }
  
  public static CalendarComponent transformToICalObject(ERGWTask task) throws SocketException, ParseException, URISyntaxException {
    // TODO The useUtc parameter should be stored somewhere
    VToDo vTodo = (VToDo)ERGWCalendarObject.transformToICalObject(task, new VToDo(), false);
    vTodo.getProperties().add(new Due(new Date(task.dueDate())));
    if (task.status != null) {
    	vTodo.getProperties().add(task.status.rfc2445Value());
    }
    vTodo.getProperties().add(new Due(new Date(task.dueDate().getTime())));
    return vTodo;
  }
  
}
