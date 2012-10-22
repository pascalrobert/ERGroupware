package er.groupware.calendar;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.groupware.calendar.enums.ERGWRecurrencePeriodType;

public class ERGWRecurrencePeriod {

  protected ERGWRecurrencePeriodType periodType;
  protected NSArray<Object> values;
  protected Integer position;
  
  public ERGWRecurrencePeriod() {
    
  }

  public ERGWRecurrencePeriodType periodType() {
    return periodType;
  }

  public void setPeriodType(ERGWRecurrencePeriodType periodType) {
    this.periodType = periodType;
  }

  public NSArray<Object> values() {
    return values;
  }

  public void setValues(NSArray<Object> values) {
    this.values = values;
  }
  
  public void addValue(Object newValue) {
    NSMutableArray<Object> mutableValues = this.values().mutableClone();
    mutableValues.addObject(newValue);
    this.setValues(mutableValues.immutableClone());
  }

  public Integer position() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }
  
}
