package er.groupware.calendar;

import er.extensions.ERXFrameworkPrincipal;
import er.extensions.foundation.ERXProperties;
import er.extensions.localization.ERXLocalizer;

public class ERGWCalendarPrincipal extends ERXFrameworkPrincipal {

  @Override
  public void finishInitialization() {

  }

  public final static ERXLocalizer localizer() {
    return ERXLocalizer.localizerForLanguages(ERXProperties.arrayForKey("er.extensions.ERXLocalizer.availableLanguages"));
  }
}
