package er.groupware.interfaces;

import com.webobjects.foundation.NSArray;

import er.groupware.enums.ERGWFolderType;
import er.groupware.enums.ERGWSupportedObjectType;

public interface IERGWBaseFolder {

  ERGWFolderType folderType();
  void setFolderType(ERGWFolderType folderType);
  
  <T extends IERGWBaseFolder> IERGWBaseFolder parent();
  <T extends IERGWBaseFolder> void setParent(IERGWBaseFolder parent);
  
  String displayName();
  void setDisplayName(String folderName);
  
  NSArray<ERGWSupportedObjectType> supportedObjectTypes();
  void setSupportedObjectTypes(NSArray<ERGWSupportedObjectType> supportedObjectTypes);
  void addSupportedObjectType(ERGWSupportedObjectType objectType);
  
  boolean canHaveChildren();
  
  NSArray<IERGWBaseFolder> children();
  void setChildren(NSArray<IERGWBaseFolder> children);
  void addChild(IERGWBaseFolder children);
  
  
}
