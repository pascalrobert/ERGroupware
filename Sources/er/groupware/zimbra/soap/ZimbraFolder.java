package er.groupware.zimbra.soap;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.groupware.enums.ERGWFolderType;
import er.groupware.enums.ERGWSupportedObjectType;
import er.groupware.interfaces.IERGWBaseFolder;

public class ZimbraFolder implements IERGWBaseFolder {

  protected ERGWFolderType folderType;
  protected IERGWBaseFolder parent;
  protected String folderName;
  protected NSArray<ERGWSupportedObjectType> supportedObjectTypes;
  protected NSArray<IERGWBaseFolder> children;
  protected int color;
  protected String id;
  
  public ZimbraFolder() {
    supportedObjectTypes = new NSArray<ERGWSupportedObjectType>();
    children = new NSArray<IERGWBaseFolder>();
  }

  public ERGWFolderType folderType() {
    return folderType;
  }

  public void setFolderType(ERGWFolderType folderType) {
    this.folderType = folderType;
  }

  public <T extends IERGWBaseFolder> IERGWBaseFolder parent() {
    return parent;
  }

  public <T extends IERGWBaseFolder> void setParent(IERGWBaseFolder parent) {
    this.parent = parent;
  }

  public String displayName() {
    return folderName;
  }

  public void setDisplayName(String folderName) {
    this.folderName = folderName;
  }

  public NSArray<ERGWSupportedObjectType> supportedObjectTypes() {
    return supportedObjectTypes;
  }

  public void setSupportedObjectTypes(NSArray<ERGWSupportedObjectType> supportedObjectTypes) {
    this.supportedObjectTypes = supportedObjectTypes;
  }

  public void addSupportedObjectType(ERGWSupportedObjectType objectType) {
    NSMutableArray<ERGWSupportedObjectType> array = this.supportedObjectTypes().mutableClone();
    array.addObject(objectType);
    this.setSupportedObjectTypes(array.immutableClone());
  }

  public boolean canHaveChildren() {
    return true;
  }

  public NSArray<IERGWBaseFolder> children() {
    return this.children;
  }

  public void setChildren(NSArray<IERGWBaseFolder> children) {
    this.children = children;
  }

  public void addChild(IERGWBaseFolder children) {
    NSMutableArray<IERGWBaseFolder> array = this.children().mutableClone();
    array.addObject(children);
    this.setChildren(array.immutableClone());
  }

  public int color() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public String id() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
