package er.groupware.calendar.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.parameter.CuType;

import com.microsoft.schemas.exchange.services._2006.types.MeetingAttendeeType;
import com.webobjects.foundation.NSArray;
import com.zimbra.cs.zclient.ZInvite.ZCalendarUserType;

import er.groupware.calendar.ERGWCalendarPrincipal;

public enum ERGWCUType implements ERGWICalendarProperty {

  INDIVIDUAL("Individu", CuType.INDIVIDUAL, ZCalendarUserType.IND, null),
  GROUP("Groupe", CuType.GROUP, ZCalendarUserType.GRO, null),
  RESOURCE("Ressource", CuType.RESOURCE, ZCalendarUserType.RES, MeetingAttendeeType.RESOURCE),
  ROOM("Salle", CuType.ROOM, ZCalendarUserType.ROO, MeetingAttendeeType.ROOM),
  UNKNOWN("Inconnu", CuType.UNKNOWN, ZCalendarUserType.UNK, null);

  private String localizedDescription;
  private CuType rfc2445Value;
  private ZCalendarUserType zimbraValue;
  private MeetingAttendeeType ewsValue;

  private ERGWCUType(String localizedDescription, CuType rfc2445Value, ZCalendarUserType zimbraValue, MeetingAttendeeType ewsValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
    this.ewsValue = ewsValue;
  }
  
  private static final Map<ZCalendarUserType,ERGWCUType> zimbraLookup = new HashMap<ZCalendarUserType,ERGWCUType>();
  private static final Map<CuType,ERGWCUType> rfc2445Lookup = new HashMap<CuType,ERGWCUType>();
  
  static {
    for(ERGWCUType s : EnumSet.allOf(ERGWCUType.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }

  public String localizedDescription() {
    return ERGWCalendarPrincipal.localizer().localizedStringForKey(localizedDescription);
  }

  public CuType rfc2445Value() {
    return rfc2445Value;
  }

  public ZCalendarUserType zimbraValue() {
    return zimbraValue;
  }

  public static NSArray<ERGWCUType> types() {
    return new NSArray<ERGWCUType>(ERGWCUType.values());
  }

  private ERGWCUType() {
  }
  
  public static ERGWCUType getByZimbraValue(ZCalendarUserType zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }

  public MeetingAttendeeType ewsValue() {
    return ewsValue;
  }

}
