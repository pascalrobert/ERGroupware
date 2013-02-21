package er.groupware.calendar;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.CuType;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.Attendee;
import er.extensions.eof.ERXKey;
import er.groupware.calendar.enums.ERGWAttendeeRole;
import er.groupware.calendar.enums.ERGWCUType;
import er.groupware.calendar.enums.ERGWParticipantStatus;

public class ERGWAttendee extends ERGWCalendarContact {

  private ERGWParticipantStatus partStat;
  private boolean rsvp;
  private ERGWCUType cutype;
  private String memberOf; // http://tools.ietf.org/html/rfc2445#section-4.2.11
  private ERGWCalendarContact delegatedFrom;
  private ERGWCalendarContact delegatedTo;
  private boolean rsvpSent;
  
  public static final ERXKey<ERGWParticipantStatus> PART_STAT = new ERXKey<ERGWParticipantStatus>("partStat");
  public static final ERXKey<Boolean> RSVP = new ERXKey<Boolean>("rsvp");
  public static final ERXKey<ERGWCUType> CUTYPE = new ERXKey<ERGWCUType>("cutype");
  public static final ERXKey<String> MEMBER_OF = new ERXKey<String>("memberOf");
  public static final ERXKey<ERGWCalendarContact> DELEGATED_FROM = new ERXKey<ERGWCalendarContact>("delegatedFrom");
  public static final ERXKey<ERGWCalendarContact> DELEGATED_TO = new ERXKey<ERGWCalendarContact>("delegatedTo");
  
  public ERGWAttendee() {
    
  }

  public ERGWParticipantStatus partStat() {
    return partStat;
  }

  public void setPartStat(ERGWParticipantStatus _partStat) {
    this.partStat = _partStat;
  }

  public boolean isRsvp() {
    return rsvp;
  }

  public void setRsvp(boolean _rsvp) {
    this.rsvp = _rsvp;
  }

  public ERGWCUType cutype() {
    return cutype;
  }

  public void setCutype(ERGWCUType _cutype) {
    this.cutype = _cutype;
  }

  public String memberOf() {
    return memberOf;
  }

  public void setMemberOf(String _memberOf) {
    this.memberOf = _memberOf;
  }

  public ERGWCalendarContact delegatedFrom() {
    return delegatedFrom;
  }

  public void setDelegatedFrom(ERGWCalendarContact _delegatedFrom) {
    this.delegatedFrom = _delegatedFrom;
  }

  public ERGWCalendarContact delegatedTo() {
    return delegatedTo;
  }

  public void setDelegatedTo(ERGWCalendarContact _delegatedTo) {
    this.delegatedTo = _delegatedTo;
  }
  
  public boolean isRsvpSent() {
	  return rsvpSent;
  }

  public void setRsvpSent(boolean _rsvpSent) {
	  this.rsvpSent = _rsvpSent;
  }
  
  public static ERGWAttendee transformFromICalObject(Attendee oldAttendee) {
    ERGWAttendee attendee = new ERGWAttendee();
    
    CuType type = (CuType)oldAttendee.getParameter(Parameter.CUTYPE);
    if (type == CuType.GROUP) {
      attendee.setCutype(ERGWCUType.GROUP);
    } else if (type == CuType.INDIVIDUAL) {
      attendee.setCutype(ERGWCUType.INDIVIDUAL);
    } else if (type == CuType.RESOURCE) {
      attendee.setCutype(ERGWCUType.RESOURCE);
    } else if (type == CuType.ROOM) {
      attendee.setCutype(ERGWCUType.ROOM);
    } else if (type == CuType.UNKNOWN) {
      attendee.setCutype(ERGWCUType.UNKNOWN);
    }
    
    Parameter role = oldAttendee.getParameter(Parameter.ROLE);
    if (role != null) {
      attendee.setRole(ERGWAttendeeRole.getByRFC2445ValueValue((Role)role));
    } else {
      attendee.setRole(ERGWAttendeeRole.REQ_PARTICIPANT);        
    }
    
    Cn name = (Cn)oldAttendee.getParameter(Parameter.CN);
    if (name != null)
      attendee.setName(name.getValue());
    
    attendee.setEmailAddress(null);
    String emailAddress = oldAttendee.getCalAddress().getSchemeSpecificPart();
    if (emailAddress != null)
      attendee.setEmailAddress(emailAddress);
          
    return attendee;
  }
    
}
