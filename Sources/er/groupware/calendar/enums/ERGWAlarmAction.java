package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.property.Action;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.zclient.ZAlarm;

// TODO: alarme Facebook? IM? Appel d'un service par URL?

public enum ERGWAlarmAction {

  DISPLAY("Message", Action.DISPLAY, ZAlarm.ZAction.DISPLAY),
  AUDIO("Son", Action.AUDIO, ZAlarm.ZAction.AUDIO),
  EMAIL("Courriel", Action.EMAIL, ZAlarm.ZAction.EMAIL),
  PROCEDURE("Script", Action.PROCEDURE, ZAlarm.ZAction.PROCEDURE);
    
  private String description;
  private Action rfc2445Value;
  private ZAlarm.ZAction zimbraValue;

  private ERGWAlarmAction(String description, Action rfc2445Value, ZAlarm.ZAction zimbraValue) {
    this.description = description;
    this.zimbraValue = zimbraValue;
    this.rfc2445Value = rfc2445Value;
  }
  
  private static final Map<ZAlarm.ZAction,ERGWAlarmAction> zimbraLookup = new HashMap<ZAlarm.ZAction,ERGWAlarmAction>();
  private static final Map<Action,ERGWAlarmAction> rfc2445Lookup = new HashMap<Action,ERGWAlarmAction>();
  
  static {
    for(ERGWAlarmAction s : EnumSet.allOf(ERGWAlarmAction.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  public String localizedDescription() {
    return description;
  }

  public Action rfc2445Value() {
    return rfc2445Value;
  }
  
  public ZAlarm.ZAction zimbraValue() {
    return zimbraValue;
  }
  
  public static NSArray<ERGWAlarmAction> actions() {
    return new NSArray<ERGWAlarmAction>(ERGWAlarmAction.values());
  }
  
  private ERGWAlarmAction() {
  }

  public static ERGWAlarmAction getByZimbraValue(ZAlarm.ZAction zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
}
