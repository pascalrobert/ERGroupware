package er.groupware.contacts.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.zimbra.common.mailbox.ContactConstants;

import er.groupware.calendar.enums.ERGWICalendarProperty;

/*
 * http://tools.ietf.org/html/rfc2426#section-3.3.1
 * 
 * The default type is "voice"
 */
public enum ERGWContactTelephoneType implements ERGWICalendarProperty {

  HOME("Home phone number","HOME",ContactConstants.A_homePhone),
  ASSISTANT("Assistant phone number","X-ASSISTANT",ContactConstants.A_assistantPhone), // Extension
  VOICE_MSG("Voice mailbox","MSG",ContactConstants.A_callbackPhone), // "msg" to indicate the telephone number has voice messaging support
  WORK("Work phone","WORK",ContactConstants.A_workPhone),
  MOBILE("Mobile phone","CELL",ContactConstants.A_mobilePhone),
  FAX("Fax","FAX",ContactConstants.A_workFax),
  PAGER("Pager","PAGER",ContactConstants.A_pager),
  VIDEO_CONF("Video conference","VIDEO",ContactConstants.A_otherPhone), // "video" to indicate a video conferencing telephone number
  BBS("Bulletin-board system","BBS",ContactConstants.A_otherPhone),
  MODEM("Modem","MODEM",ContactConstants.A_otherPhone), 
  ISDN("ISDN","ISDN",ContactConstants.A_otherPhone), // "isdn" to indicate an ISDN service telephone number
  CAR("Car","CAR",ContactConstants.A_carPhone), // "car" to indicate a car-phone telephone number
  PCS("Personal Communication Services (PCS)","PCS",ContactConstants.A_otherPhone), // "pcs" to indicate a personal communication services telephone number
  OTHER_TELEPHONE("Other telephone number","VOICE",ContactConstants.A_otherPhone),
  OTHER_FAX("Other fax number","FAX",ContactConstants.A_otherFax),
  COMPANY("Company phone","X-COMPANY",ContactConstants.A_companyPhone),
  PRIMARY_PHONE("Primary phone","PREF",ContactConstants.A_otherPhone);

  private String localizedDescription;
  private String rfc2445Value;
  private String zimbraValue;

  private ERGWContactTelephoneType(String localizedDescription, String rfc2445Value, String zimbraValue) {
    this.localizedDescription = localizedDescription;
    this.rfc2445Value = rfc2445Value;
    this.zimbraValue = zimbraValue;
  }
  
  private static final Map<String,ERGWContactTelephoneType> zimbraLookup = new HashMap<String,ERGWContactTelephoneType>();
  private static final Map<String,ERGWContactTelephoneType> rfc2445Lookup = new HashMap<String,ERGWContactTelephoneType>();
  
  static {
    for(ERGWContactTelephoneType s : EnumSet.allOf(ERGWContactTelephoneType.class)) {
      zimbraLookup.put(s.zimbraValue(), s);
      rfc2445Lookup.put(s.rfc2445Value(), s);
    }
  }
  
  public String localizedDescription() {
    return localizedDescription;
  }

  public String zimbraValue() {
    return zimbraValue;
  }

  public String rfc2445Value() {
    return rfc2445Value;
  }

  public Object ewsValue() {
    return null;
  }
  
  public static ERGWContactTelephoneType getByZimbraValue(String zimbraValue) { 
    return zimbraLookup.get(zimbraValue); 
  }
  
  // TODO: should be rfc2426, or just rfc
  public static ERGWContactTelephoneType getByRFC2445Value(String zimbraValue) { 
    return rfc2445Lookup.get(zimbraValue); 
  }
  
}
