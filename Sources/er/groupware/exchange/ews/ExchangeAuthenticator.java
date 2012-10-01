package er.groupware.exchange.ews;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * @author probert
 *
 */
public class ExchangeAuthenticator extends Authenticator {

  private String _username;
  private char[] _password;

  public ExchangeAuthenticator(String username, char[] password) {
    super();
    this._username = username;
    this._password = password;
  }

  @Override
  public PasswordAuthentication getPasswordAuthentication() {
    return (new PasswordAuthentication (_username, _password));
  }
}
