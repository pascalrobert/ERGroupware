package er.groupware.contacts.enums;

public enum ERGWContactFileAsStyle {

  LAST_COMMA_FIRST_SUFFIX, // X-FILE-AS:Robert, Pascal Jr.
  FIRST_SUFFIX_LAST, // X-FILE-AS:Pascal Jr. Robert
  LAST_COMMA_FIRST_SUFFIX_BUSINESS, // X-FILE-AS:Robert, Pascal Jr.\nMacTI
  BUSINESS_LAST_COMMA_FIRST_SUFFIX, // X-FILE-AS:MacTI\nRobert, Pascal Jr.
  PREFIX_FIRST_SUFFIX_LAST; // X-FILE-AS:M. Pascal Jr. Robert Junior
  
}
