package er.groupware.contacts;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableArray;

import er.groupware.contacts.enums.ERGWContactPhysicalAddressType;

/**
 * http://tools.ietf.org/html/rfc2426#section-3.2.1
 * 
 * The structured type value consists of a sequence
   of address components. The component values MUST be specified in
   their corresponding position. The structured type value corresponds,
   in sequence, to the post office box; the extended address; the street
   address; the locality (e.g., city); the region (e.g., state or
   province); the postal code; the country name. When a component value
   is missing, the associated component separator MUST still be
   specified.
 * 
 * @author probert
 *
 */
public class ERGWContactPhysicalAddress {

  protected NSArray<ERGWContactPhysicalAddressType> types;
  protected String postOfficeBox;
  protected String extendedAddress;
  protected String street;
  protected String city;
  protected String state;
  protected String postalCode;
  protected String country;
  protected boolean isPrefered;
  
  public ERGWContactPhysicalAddress() {
    this.types = new NSArray<ERGWContactPhysicalAddressType>();
  }

  public NSArray<ERGWContactPhysicalAddressType> types() {
    return types;
  }

  public void setTypes(NSArray<ERGWContactPhysicalAddressType> types) {
    this.types = types;
  }
  
  public void addType(ERGWContactPhysicalAddressType type) {
    NSMutableArray<ERGWContactPhysicalAddressType> array = this.types().mutableClone();
    array.addObject(type);
    setTypes(array.immutableClone());
  }

  public String postOfficeBox() {
    return postOfficeBox;
  }

  public void setPostOfficeBox(String postOfficeBox) {
    this.postOfficeBox = postOfficeBox;
  }

  public String extendedAddress() {
    return extendedAddress;
  }

  public void setExtendedAddress(String extendedAddress) {
    this.extendedAddress = extendedAddress;
  }

  public String street() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String city() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  /** 
   * Region = province, state, etc.
   */
  public String region() {
    return state;
  }

  public void setRegion(String state) {
    this.state = state;
  }

  public String postalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String country() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public boolean isPrefered() {
    return isPrefered;
  }

  public void setIsPrefered(boolean isPrefered) {
    this.isPrefered = isPrefered;
  }
  
}
