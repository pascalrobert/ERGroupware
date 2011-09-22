package er.groupware.calendar.enums;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import net.fortuna.ical4j.model.property.Action;

import com.webobjects.foundation.NSArray;
import com.zimbra.cs.zclient.ZAlarm;

// TODO: alarme Facebook? IM? Appel d'un service par URL?

public enum AlarmAction {

  DISPLAY("Message", Action.DISPLAY, ZAlarm.ZAction.DISPLAY),
  AUDIO("Son", Action.AUDIO, ZAlarm.ZAction.AUDIO),
  EMAIL("Courriel", Action.EMAIL, ZAlarm.ZAction.EMAIL),
  PROCEDURE("Script", Action.PROCEDURE, ZAlarm.ZAction.PROCEDURE);
    
  private String description;
  private Action rfc2445Value;
  private ZAlarm.ZAction zimbraValue;

  private AlarmAction(String description, Action rfc2445Value, ZAlarm.ZAction zimbraValue) {
    this.description = description;
    this.zimbraValue = zimbraValue;
    this.rfc2445Value = rfc2445Value;
  }
  
  private static final Map<ZAlarm.ZAction,AlarmAction> zimbraLookup = new HashMap<ZAlarm.ZAction,AlarmAction>();
  private static final Map<Action,AlarmAction> rfc2445Lookup = new HashMap<Action,AlarmAction>();
  
  static {
    for(AlarmAction s : EnumSet.allOf(AlarmAction.class)) {
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
  
  public static NSArray<AlarmAction> actions() {
    return new NSArray<AlarmAction>(AlarmAction.values());
  }
  
  private AlarmAction() {
  }

  public static AlarmAction getByZimbraValue(ZAlarm.ZAction zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
}
