package er.groupware.calendar;

import er.extensions.eof.ERXKey;
import er.groupware.calendar.enums.ERGWAttendeeRole;

public abstract class ERGWCalendarContact {

  private String emailAddress;
  private String url;
  private String name;
  private String sentBy;
  private String ldapUrl;
  private String language;
  private ERGWAttendeeRole role;

  public static final ERXKey<String> EMAIL_ADDRESS = new ERXKey<String>("emailAddress");
  public static final ERXKey<String> URL = new ERXKey<String>("url");
  public static final ERXKey<String> NAME = new ERXKey<String>("name");
  public static final ERXKey<String> SENT_BY = new ERXKey<String>("sentBy");
  public static final ERXKey<String> LDAP_URL = new ERXKey<String>("ldapUrl");
  public static final ERXKey<String> LANGUAGE = new ERXKey<String>("language");
  public static final ERXKey<ERGWAttendeeRole> ROLE = new ERXKey<ERGWAttendeeRole>("role");

  public String emailAddress() {
    return emailAddress;
  }
  
  public void setEmailAddress(String _emailAddress) {
    this.emailAddress = _emailAddress;
  }
  
  public String url() {
    return url;
  }
  
  public void setUrl(String _url) {
    this.url = _url;
  }
  
  public String name() {
    return name;
  }
  
  public void setName(String _name) {
    this.name = _name;
  }
  
  public String sentBy() {
    return sentBy;
  }
  
  public void setSentBy(String _sentBy) {
    this.sentBy = _sentBy;
  }
  
  public String ldapUrl() {
    return ldapUrl;
  }
  
  public void setLdapUrl(String _ldapUrl) {
    this.ldapUrl = _ldapUrl;
  }
  
  public String language() {
    return language;
  }
  
  public void setLanguage(String _language) {
    this.language = _language;
  }
   
  public ERGWAttendeeRole role() {
    return role;
  }

  public void setRole(ERGWAttendeeRole _role) {
    this.role = _role;
  }

}
