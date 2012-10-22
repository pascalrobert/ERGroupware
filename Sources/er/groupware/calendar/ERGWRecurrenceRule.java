package er.groupware.calendar;

import java.util.ArrayList;

import net.fortuna.ical4j.model.NumberList;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.WeekDay;
import net.fortuna.ical4j.model.WeekDayList;
import net.fortuna.ical4j.model.property.RRule;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.eof.ERXKey;
import er.groupware.calendar.enums.ERGWRecurrenceDay;
import er.groupware.calendar.enums.ERGWRecurrenceFrequency;
import er.groupware.calendar.enums.ERGWRecurrencePeriodType;

public class ERGWRecurrenceRule {

  private Integer repeatCount;
  private ERGWRecurrenceFrequency frequency;
  private NSTimestamp until;
  private Integer interval;
  private String workweekStart;
  private NSArray<ERGWRecurrencePeriod> periods;
  private NSArray<Integer> positions;

  public static final ERXKey<ERGWRecurrenceFrequency> FREQUENCY = new ERXKey<ERGWRecurrenceFrequency>("frequency");
  public static final ERXKey<NSTimestamp> UNTIL = new ERXKey<NSTimestamp>("until");
  public static final ERXKey<Integer> REPEAT_COUNT = new ERXKey<Integer>("repeatCount");

  public ERGWRecurrenceRule() {
    periods = new NSArray<ERGWRecurrencePeriod>();
    positions = new NSArray<Integer>();
  }

  public Integer repeatCount() {
    return repeatCount;
  }

  public void setRepeatCount(Integer _repeatCount) {
    this.repeatCount = _repeatCount;
  }
  
  public ERGWRecurrenceFrequency frequency() {
    return frequency;
  }

  public void setFrequency(ERGWRecurrenceFrequency frequency) {
    this.frequency = frequency;
  }

  public NSTimestamp until() {
    return until;
  }

  public void setUntil(NSTimestamp until) {
    this.until = until;
  }

  public Integer interval() {
    return interval;
  }

  public void setInterval(Integer interval) {
    this.interval = interval;
  }

  public String workweekStart() {
    return workweekStart;
  }

  public void setWorkweekStart(String workweekStart) {
    this.workweekStart = workweekStart;
  }
  
  public NSArray<ERGWRecurrencePeriod> periods() {
    return periods;
  }

  public void setPeriods(NSArray<ERGWRecurrencePeriod> periods) {
    this.periods = periods;
  }
  
  public void addPeriod(ERGWRecurrencePeriod period) {
    NSMutableArray<ERGWRecurrencePeriod> mutablePeriods = this.periods().mutableClone();
    mutablePeriods.addObject(period);
    this.setPeriods(mutablePeriods.immutableClone());
  }
  
  public void addPeriod(ERGWRecurrencePeriodType periodType, RRule recurrenceRule, ArrayList list) {
    Parameter parameter = recurrenceRule.getParameter(periodType.rfc2445Value());

    if (parameter != null) {
      ERGWRecurrencePeriod period = new ERGWRecurrencePeriod();
      period.setPeriodType(periodType);
      for (Object elementFromList: list) { 
        if (list instanceof NumberList) {
          period.addValue(((Integer)elementFromList).toString());
        } else if (list instanceof WeekDayList) {
          WeekDay day = (WeekDay)elementFromList;
          period.addValue(ERGWRecurrenceDay.getByRFC2445Value(day.getDay()));
          if (day.getOffset() > 0) {
            period.setPosition(day.getOffset());
          }
        }
      }
      this.addPeriod(period);
    }
  }

  public NSArray<Integer> positions() {
    return positions;
  }

  public void setPositions(NSArray<Integer> positions) {
    this.positions = positions;
  }
  
  public void addPosition(Integer position) {
    NSMutableArray<Integer> mutablePositions = this.positions().mutableClone();
    mutablePositions.addObject(position);
    setPositions(mutablePositions.immutableClone());
  }

}
