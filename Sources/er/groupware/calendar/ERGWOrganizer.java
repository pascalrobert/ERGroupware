package er.groupware.calendar;

import er.groupware.calendar.enums.ERGWCUType;
import er.groupware.calendar.enums.ERGWParticipantStatus;

public class ERGWOrganizer extends ERGWCalendarContact {

  private ERGWParticipantStatus partStat;
  private ERGWCUType cutype;

  public ERGWParticipantStatus partStat() {
    return partStat;
  }

  public void setPartStat(ERGWParticipantStatus _partStat) {
    this.partStat = _partStat;
  }
  
  public ERGWCUType cutype() {
    return cutype;
  }

  public void setCutype(ERGWCUType _cutype) {
    this.cutype = _cutype;
  }
  
}
