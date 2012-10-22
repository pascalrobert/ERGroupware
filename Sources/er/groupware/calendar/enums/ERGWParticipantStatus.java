package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.parameter.PartStat;

import com.microsoft.schemas.exchange.services._2006.types.ResponseTypeType;
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

public enum ERGWParticipantStatus implements ERGWICalendarProperty {

  NEEDS_ACTION("Requiert action", PartStat.NEEDS_ACTION, ZParticipantStatus.NE, ResponseTypeType.NO_RESPONSE_RECEIVED),
  ACCEPTED("Accepté", PartStat.ACCEPTED, ZParticipantStatus.AC, ResponseTypeType.ACCEPT),
  DECLINED("Refusé", PartStat.DECLINED, ZParticipantStatus.DE, ResponseTypeType.DECLINE),
  TENTATIVE("Tentatif", PartStat.TENTATIVE, ZParticipantStatus.TE, ResponseTypeType.TENTATIVE),
  DELEGATED("Délégué à autrui", PartStat.DELEGATED, ZParticipantStatus.DG, ResponseTypeType.UNKNOWN);
  
  private String description;
  private PartStat rfc2445Value;
  private ZParticipantStatus zimbraValue;
  private ResponseTypeType ewsValue;

  private ERGWParticipantStatus(String description, PartStat rfc2445Value, ZParticipantStatus zimbraValue, ResponseTypeType ewsValue) {
    this.description = description;
    this.zimbraValue = zimbraValue;
    this.rfc2445Value = rfc2445Value;
    this.ewsValue = ewsValue;
  }
  
  private static final Map<ZParticipantStatus,ERGWParticipantStatus> zimbraLookup = new HashMap<ZParticipantStatus,ERGWParticipantStatus>();
  private static final Map<PartStat,ERGWParticipantStatus> rfc2445Lookup = new HashMap<PartStat,ERGWParticipantStatus>();
  
  static {
    for(ERGWParticipantStatus s : EnumSet.allOf(ERGWParticipantStatus.class)) {
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
  
  public static NSArray<ERGWParticipantStatus> statuses() {
    return new NSArray<ERGWParticipantStatus>(ERGWParticipantStatus.values());
  }
  
  private ERGWParticipantStatus() {
  }
  
  public static ERGWParticipantStatus getByZimbraValue(ZParticipantStatus zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }

  public ResponseTypeType ewsValue() {
    return ewsValue;
  }
  
}
