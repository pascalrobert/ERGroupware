package er.groupware.exchange.ews;

import com.microsoft.schemas.exchange.services._2006.types.BaseFolderType;
import com.microsoft.schemas.exchange.services._2006.types.CalendarFolderType;
import com.microsoft.schemas.exchange.services._2006.types.ContactsFolderType;
import com.microsoft.schemas.exchange.services._2006.types.ExtendedPropertyType;
import com.microsoft.schemas.exchange.services._2006.types.TasksFolderType;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.groupware.enums.ERGWFolderType;
import er.groupware.enums.ERGWSupportedObjectType;
import er.groupware.interfaces.IERGWBaseFolder;

public abstract class ExchangeBaseFolder implements IERGWBaseFolder {

  protected ERGWFolderType folderType;
  protected ExchangeBaseFolder parent;
  protected String displayName;
  protected NSArray<ERGWSupportedObjectType> supportedObjectTypes;
  protected NSArray<IERGWBaseFolder> children;
  protected Integer totalCount;
  protected Integer childFolderCount;
  protected String id;
  protected String changeKey;
  protected Integer unreadCount;
  protected boolean isHidden;
  
  public ExchangeBaseFolder(ERGWFolderType folderType, String displayName) {
    this.folderType = folderType;
    this.displayName = displayName;
  }
  
  public ERGWFolderType folderType() {
    return folderType;
  }

  public void setFolderType(ERGWFolderType folderType) {
    this.folderType = folderType;
  }

  public String displayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public NSArray<ERGWSupportedObjectType> supportedObjectTypes() {
    if (supportedObjectTypes == null) {
      supportedObjectTypes = new NSArray<ERGWSupportedObjectType>();
    }
    return supportedObjectTypes;
  }

  public void setSupportedObjectTypes(NSArray<ERGWSupportedObjectType> supportedObjectTypes) {
    this.supportedObjectTypes = supportedObjectTypes;
  }

  public void addSupportedObjectType(ERGWSupportedObjectType objectType) {
    NSMutableArray<ERGWSupportedObjectType> mutableArray = this.supportedObjectTypes().mutableClone();
    mutableArray.add(objectType);
    this.setSupportedObjectTypes(mutableArray.immutableClone());
  }

  public boolean canHaveChildren() {
    return true;
  }

  public NSArray<IERGWBaseFolder> children() {
    if (children == null) {
      children = new NSArray<IERGWBaseFolder>();
    }
    return children;
  }

  public void setChildren(NSArray<IERGWBaseFolder> children) {
    this.children = children;
  }

  public void addChild(IERGWBaseFolder children) {
    NSMutableArray<IERGWBaseFolder> mutableArray = this.children().mutableClone();
    mutableArray.add(children);
    this.setChildren(mutableArray.immutableClone());
  }

  public <T extends IERGWBaseFolder> ExchangeBaseFolder parent() {
    return parent;
  }

  public <T extends IERGWBaseFolder> void setParent(IERGWBaseFolder parent) {
    this.parent = (ExchangeBaseFolder) parent;    
  }

  public Integer totalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Integer childFolderCount() {
    return childFolderCount;
  }

  public void setChildFolderCount(Integer childFolderCount) {
    this.childFolderCount = childFolderCount;
  }

  public String id() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String changeKey() {
    return changeKey;
  }

  public void setChangeKey(String changeKey) {
    this.changeKey = changeKey;
  }

  public Integer unreadCount() {
    return unreadCount;
  }

  public void setUnreadCount(Integer unreadCount) {
    this.unreadCount = unreadCount;
  }
  
  public boolean isHidden() {
    return isHidden;
  }
  
  public void setIsHidden(boolean isHidden) {
    this.isHidden = isHidden;
  }
  
  public static ExchangeBaseFolder createFromServer(BaseFolderType serverFolder) {
    ExchangeBaseFolder localFolder = null;
    
    if (serverFolder instanceof CalendarFolderType) {
      localFolder = new ExchangeCalendarFolder(serverFolder.getDisplayName());
    } else if (serverFolder instanceof ContactsFolderType) {
      localFolder = new ExchangeContactsFolder(serverFolder.getDisplayName());
    } else if (serverFolder instanceof TasksFolderType) {
      localFolder = new ExchangeTasksFolder(serverFolder.getDisplayName());
    } else {
      localFolder = new ExchangeEmailFolder(serverFolder.getDisplayName());
    }
    
    localFolder.setId(serverFolder.getFolderId().getId());
    localFolder.setChangeKey(serverFolder.getFolderId().getChangeKey());
    localFolder.setChildFolderCount(serverFolder.getChildFolderCount());
    localFolder.setTotalCount(serverFolder.getTotalCount());
    
    for (ExtendedPropertyType extendedProp: serverFolder.getExtendedProperty()) {
      if ("0x10f4".equals(extendedProp)) {
        localFolder.setIsHidden(new Boolean(extendedProp.getValue()));
      }
    }
    
    return localFolder;
  }
  
}
