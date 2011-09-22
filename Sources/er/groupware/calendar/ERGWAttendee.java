package er.groupware.calendar;

import er.extensions.eof.ERXKey;
import er.groupware.calendar.enums.ERGWAttendeeRole;
import er.groupware.calendar.enums.ERGWCUType;
import er.groupware.calendar.enums.ERGWParticipantStatus;

public class ERGWAttendee extends ERGWContact {

  private ERGWAttendeeRole role;
  private ERGWParticipantStatus partStat;
  private boolean rsvp;
  private ERGWCUType cutype;
  private String memberOf; // http://tools.ietf.org/html/rfc2445#section-4.2.11
  private ERGWContact delegatedFrom;
  private ERGWContact delegatedTo;
  
  public static final ERXKey<ERGWAttendeeRole> ROLE = new ERXKey<ERGWAttendeeRole>("role");
  public static final ERXKey<ERGWParticipantStatus> PART_STAT = new ERXKey<ERGWParticipantStatus>("partStat");
  public static final ERXKey<Boolean> RSVP = new ERXKey<Boolean>("rsvp");
  public static final ERXKey<ERGWCUType> CUTYPE = new ERXKey<ERGWCUType>("cutype");
  public static final ERXKey<String> MEMBER_OF = new ERXKey<String>("memberOf");
  public static final ERXKey<ERGWContact> DELEGATED_FROM = new ERXKey<ERGWContact>("delegatedFrom");
  public static final ERXKey<ERGWContact> DELEGATED_TO = new ERXKey<ERGWContact>("delegatedTo");
  
  public ERGWAttendee() {
    
  }

  public ERGWAttendeeRole role() {
    return role;
  }

  public void setRole(ERGWAttendeeRole role) {
    this.role = role;
  }

  public ERGWParticipantStatus partStat() {
    return partStat;
  }

  public void setPartStat(ERGWParticipantStatus partStat) {
    this.partStat = partStat;
  }

  public boolean isRsvp() {
    return rsvp;
  }

  public void setRsvp(boolean rsvp) {
    this.rsvp = rsvp;
  }

  public ERGWCUType cutype() {
    return cutype;
  }

  public void setCutype(ERGWCUType cutype) {
    this.cutype = cutype;
  }

  public String memberOf() {
    return memberOf;
  }

  public void setMemberOf(String memberOf) {
    this.memberOf = memberOf;
  }

  public ERGWContact delegatedFrom() {
    return delegatedFrom;
  }

  public void setDelegatedFrom(ERGWContact delegatedFrom) {
    this.delegatedFrom = delegatedFrom;
  }

  public ERGWContact delegatedTo() {
    return delegatedTo;
  }

  public void setDelegatedTo(ERGWContact delegatedTo) {
    this.delegatedTo = delegatedTo;
  }
    
}
