package er.groupware.zimbra.soap;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import net.fortuna.ical4j.connector.dav.PathResolver;

import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.common.zclient.ZClientException;
import com.zimbra.cs.account.Provisioning.AccountBy;
import com.zimbra.cs.zclient.ZMailbox;

public class ZimbraStore {

  private ZMailbox utils;
  
  public ZimbraStore(String username, String password, URL url) throws ServiceException {
    ZMailbox.Options options = new ZMailbox.Options();
    options.setAccount(username);
    options.setAccountBy(AccountBy.name);
    options.setPassword(password);

    options.setUri(resolveUrl(url.getProtocol() + "://" +  url.getHost(), false));
    utils = ZMailbox.getMailbox(options);
  }
  
  public void disconnect() {
    
  }
  
  public void getCollections() {
    
  }
  
  public static String resolveUrl(String url, boolean isAdmin) throws ZClientException {
    try {
      URI uri = new URI(url);

      if (isAdmin && uri.getPort() == -1) {
        uri = new URI("https", uri.getUserInfo(), uri.getHost(), 7071, uri.getPath(), uri.getQuery(), uri.getFragment());
        url = uri.toString();
      }

      String service = (uri.getPort() == 7071) ? AdminConstants.ADMIN_SERVICE_URI : AccountConstants.USER_SERVICE_URI;
      if (uri.getPath() == null || uri.getPath().length() <= 1) {
        if (url.charAt(url.length()-1) == '/')
          url = url.substring(0, url.length()-1) + service;
        else
          url = url + service;
      }
      return url;
    } catch (URISyntaxException e) {
      throw ZClientException.CLIENT_ERROR("invalid URL: "+url, e);
    }
  }
  
}
