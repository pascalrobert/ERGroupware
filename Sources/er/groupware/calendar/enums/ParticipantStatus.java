package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.parameter.PartStat;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.zclient.ZInvite.ZParticipantStatus;

/*
 * http://tools.ietf.org/html/rfc2445#section-4.2.12
 * 
"PARTSTAT" "="
                         ("NEEDS-ACTION"        ; Event needs action
                        / "ACCEPTED"            ; Event accepted
                        / "DECLINED"            ; Event declined
                        / "TENTATIVE"           ; Event tentatively
                        / "DELEGATED"           ; Event delegated
                        / x-name                ; Experimental status
                        / iana-token)           ; Other IANA registered
                                                ; status
 */

public enum ParticipantStatus implements ICalendarProperty {

  NEEDS_ACTION("Requiert action", PartStat.NEEDS_ACTION, ZParticipantStatus.NE),
  ACCEPTED("Accepté", PartStat.ACCEPTED, ZParticipantStatus.AC),
  DECLINED("Refusé", PartStat.DECLINED, ZParticipantStatus.DE),
  TENTATIVE("Tentatif", PartStat.TENTATIVE, ZParticipantStatus.TE),
  DELEGATED("Délégué à autrui", PartStat.DELEGATED, ZParticipantStatus.DG);
  
  private String description;
  private PartStat rfc2445Value;
  private ZParticipantStatus zimbraValue;

  private ParticipantStatus(String description, PartStat rfc2445Value, ZParticipantStatus zimbraValue) {
    this.description = description;
    this.zimbraValue = zimbraValue;
    this.rfc2445Value = rfc2445Value;
  }
  
  private static final Map<ZParticipantStatus,ParticipantStatus> zimbraLookup = new HashMap<ZParticipantStatus,ParticipantStatus>();
  private static final Map<PartStat,ParticipantStatus> rfc2445Lookup = new HashMap<PartStat,ParticipantStatus>();
  
  static {
    for(ParticipantStatus s : EnumSet.allOf(ParticipantStatus.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
    
  public String localizedDescription() {
    return description;
  }
  
  public ZParticipantStatus zimbraValue() {
    return zimbraValue;
  }
  
  public PartStat rfc2445Value() {
    return rfc2445Value;
  }
  
  public static NSArray<ParticipantStatus> statuses() {
    return new NSArray<ParticipantStatus>(ParticipantStatus.values());
  }
  
  private ParticipantStatus() {
  }
  
  public static ParticipantStatus getByZimbraValue(ZParticipantStatus zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
}
