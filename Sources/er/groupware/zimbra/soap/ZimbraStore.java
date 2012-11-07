package er.groupware.zimbra.soap;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.zimbra.common.service.ServiceException;
import com.zimbra.common.soap.AccountConstants;
import com.zimbra.common.soap.AdminConstants;
import com.zimbra.common.soap.Element;
import com.zimbra.common.soap.Element.XMLElement;
import com.zimbra.common.soap.MailConstants;
import com.zimbra.common.zclient.ZClientException;
import com.zimbra.cs.account.Provisioning.AccountBy;
import com.zimbra.cs.zclient.ZMailbox;

import er.groupware.calendar.ERGWCalendar;
import er.groupware.enums.ERGWFolderType;

public class ZimbraStore {

  protected ZMailbox utils;
  protected String syncToken;
  protected NSArray<ZimbraFolder> folders;

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
  
  public void initialSync(boolean fetchData) throws ServiceException {
    XMLElement req = new XMLElement(MailConstants.SYNC_REQUEST);
    if (syncToken != null) 
      req.addAttribute(MailConstants.A_TOKEN, syncToken);
    
    Element response = (XMLElement)utils.invoke(req);
    
    syncToken = response.getAttribute(MailConstants.A_TOKEN);
    
    Element rootFolder = response.getElement(MailConstants.E_FOLDER);
    
    ZimbraFolder zRootFolder = new ZimbraFolder();
    zRootFolder.setFolderType(ERGWFolderType.ROOT);
    zRootFolder.setId(rootFolder.getAttribute(MailConstants.A_ID));
    zRootFolder.setColor(new Integer(rootFolder.getAttribute(MailConstants.A_COLOR)));
    
    for (Element topFolder: rootFolder.listElements(MailConstants.E_FOLDER)) {
      String folderName = topFolder.getAttribute(MailConstants.A_NAME);
      
      ZimbraFolder topLevel = new ZimbraFolder();
      topLevel.setParent(zRootFolder);
      zRootFolder.addChild(topLevel);
      topLevel.setId(topFolder.getAttribute(MailConstants.A_ID));
      topLevel.setColor(new Integer(topFolder.getAttribute(MailConstants.A_COLOR)));
      topLevel.setDisplayName(topFolder.getAttribute(MailConstants.A_NAME));
      
      String topFolderView = "USER_ROOT";
      try {
        topFolderView = topFolder.getAttribute(MailConstants.A_DEFAULT_VIEW);
      } catch (ServiceException ex) {
        
      }
      
      if ("tag".equals(topFolderView)) {
        topLevel.setFolderType(ERGWFolderType.TAG);        
      
      } else if ("conversation".equals(topFolderView)) {
        topLevel.setFolderType(ERGWFolderType.CONVERSATION);
        
      } else if ("USER_ROOT".equals(folderName)) {
        
        topLevel.setFolderType(ERGWFolderType.USER_ROOT);
        
        for (Element folder: topFolder.listElements(MailConstants.E_FOLDER)) {
                    
          String folderView = "";
          try {
            folderView = folder.getAttribute(MailConstants.A_DEFAULT_VIEW);
          } catch (ServiceException ex) {
          }

          if ("document".equals(folderView)) {
                        
          } else if ("appointment".equals(folderView)) {

            ZimbraCalendarFolder apptFolder = new ZimbraCalendarFolder();
            setFolderProperties(folder, apptFolder, topLevel);
            
            if (fetchData) {
              for (Element cn : folder.listElements(MailConstants.E_APPOINTMENT)) {
                String ids = cn.getAttribute(MailConstants.A_IDS);
                for (String id: NSArray.componentsSeparatedByString(ids, ",")) {
                  XMLElement getApptRequest = new XMLElement(MailConstants.GET_APPOINTMENT_REQUEST);
                  getApptRequest.addAttribute(MailConstants.A_ID, id);
                  Element getApptResponse = (XMLElement)utils.invoke(getApptRequest);
                  ERGWCalendar calendar = ERGWCalendar.transformFromZimbraResponse((XMLElement)getApptResponse);
                }
              }
            }
          
          } else if ("message".equals(folderView)) {
                    
            if (fetchData) {
              for (Element cn : folder.listElements(MailConstants.E_MSG)) {
                NSLog.out.appendln(cn);
              }
            }

          } else if ("contact".equals(folderView)) {
                      
            if (fetchData) {
              for (Element cn : folder.listElements(MailConstants.E_CONTACT)) {
                NSLog.out.appendln(cn);
              }
            }
          
          } else if ("task".equals(folderView)) {
                      
            if (fetchData) {
              for (Element cn : folder.listElements(MailConstants.E_TASK)) {
                NSLog.out.appendln(cn);
              }
            }

          } else { 
            ZimbraFolder subFolder = new ZimbraFolder();
            setFolderProperties(folder, subFolder, topLevel);
            if ("Trash".equals(subFolder.displayName())) {
              subFolder.setFolderType(ERGWFolderType.TRASH);
            } else {
              subFolder.setFolderType(ERGWFolderType.PLAIN);
            }  
          }
        }
      }
    }
  }
  
  protected void setFolderProperties(Element folder, ZimbraFolder subFolder, ZimbraFolder parent) {
    if (parent != null) {
      subFolder.setParent(parent);
      parent.addChild(subFolder);
    }

    try {
      subFolder.setDisplayName(folder.getAttribute(MailConstants.A_NAME));
      subFolder.setId(folder.getAttribute(MailConstants.A_ID));
      subFolder.setColor(new Integer(folder.getAttribute(MailConstants.A_COLOR)));
    } catch (ServiceException ex) {
    }

  }
  
  public void addFolder(ZimbraFolder newFolder) {
    NSMutableArray<ZimbraFolder> array = new NSMutableArray<ZimbraFolder>();
    array.addObject(newFolder);
    this.folders = array.immutableClone();
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
