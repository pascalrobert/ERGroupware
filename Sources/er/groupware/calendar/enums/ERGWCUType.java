package er.groupware.calendar.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.parameter.CuType;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.zclient.ZInvite.ZCalendarUserType;

import er.groupware.calendar.ERGWCalendarPrincipal;

public enum ERGWCUType implements ERGWICalendarProperty {

  INDIVIDUAL("Individu", CuType.INDIVIDUAL, ZCalendarUserType.IND),
  GROUP("Groupe", CuType.GROUP, ZCalendarUserType.GRO),
  RESOURCE("Ressource", CuType.RESOURCE, ZCalendarUserType.RES),
  ROOM("Salle", CuType.ROOM, ZCalendarUserType.ROO),
  UNKNOWN("Inconnu", CuType.UNKNOWN, ZCalendarUserType.UNK);

  private String localizedDescription;
  private CuType rfc2445Value;
  private ZCalendarUserType zimbraValue;

  private ERGWCUType(String localizedDescription, CuType rfc2445Value, ZCalendarUserType zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
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

}
