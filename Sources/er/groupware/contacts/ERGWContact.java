package er.groupware.contacts;

import java.net.SocketException;
import java.net.URISyntaxException;

import net.fortuna.ical4j.vcard.Parameter;
import net.fortuna.ical4j.vcard.Property;
import net.fortuna.ical4j.vcard.VCard;
import net.fortuna.ical4j.vcard.parameter.Type;
import net.fortuna.ical4j.vcard.property.Address;
import net.fortuna.ical4j.vcard.property.Email;
import net.fortuna.ical4j.vcard.property.N;
import net.fortuna.ical4j.vcard.property.Telephone;

import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSData;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import er.groupware.calendar.enums.ERGWClassification;
import er.groupware.contacts.enums.ERGWContactEmailType;
import er.groupware.contacts.enums.ERGWContactPhysicalAddressType;
import er.groupware.contacts.enums.ERGWContactTelephoneType;

public class ERGWContact {

  protected String familyName;      // Stevenson
  protected String givenName;       // John
  protected NSArray<String> additionalNames; // Philip,Paul
  protected NSArray<String> honorificPrefixes; // Dr.
  protected NSArray<String> honorificSuffixes; // Jr.,M.D.,A.C.P.
  protected String maidenName;
  protected String nickname;
  protected String fileAs;  // X-FILE-AS:Robert, Pascal Jr
  protected String jobTitle;
  protected NSArray<ERGWContactEmail> emails;
  protected String assistantName;
  protected NSTimestamp birthday;
  protected String businessName;
  protected String departmentName;
  protected NSArray<String> businessUnits;
  protected String notes;
  protected NSArray<ERGWContactTelephone> telephones;
  protected NSArray<ERGWContactPhysicalAddress> physicalAddresses;
  protected NSArray<String> categories;
  protected NSArray<ERGWContactUrl> urls;
  protected NSData photo;
  protected NSArray<String> labels;
  protected NSTimestamp anniversary; // X-ANNIVERSARY
  protected NSArray<String> hobbies; // X-HOBBIES
  protected String spouseName; // X-SPOUSE-NAME
  protected NSArray<String> children; // X-CHILDREN, multiple lines
  protected ERGWClassification classification;
  protected NSArray<String> imAddresses; // ICQ if only one
  protected String manager; // X-MANAGER
  protected String assistant; // X-ASSISTANT
  
  public ERGWContact() {
    emails = new NSArray<ERGWContactEmail>();
    businessUnits = new NSArray<String>();
    telephones = new NSArray<ERGWContactTelephone>();
    physicalAddresses = new NSArray<ERGWContactPhysicalAddress>();
    categories = new NSArray<String>();
    urls = new NSArray<ERGWContactUrl>();
    labels = new NSArray<String>();
    hobbies = new NSArray<String>();
    children = new NSArray<String>();
    imAddresses = new NSArray<String>();
  }

  public String familyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String givenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public NSArray<String> additionalNames() {
    return additionalNames;
  }

  public void setAdditionalNames(NSArray<String> additionalNames) {
    this.additionalNames = additionalNames;
  }

  public NSArray<String> honorificPrefix() {
    return honorificPrefixes;
  }

  public void setHonorificPrefix(NSArray<String> honorificPrefix) {
    this.honorificPrefixes = honorificPrefix;
  }

  public NSArray<String> honorificSuffix() {
    return honorificSuffixes;
  }

  public void setHonorificSuffix(NSArray<String> honorificSuffix) {
    this.honorificSuffixes = honorificSuffix;
  }

  public String maidenName() {
    return maidenName;
  }

  public void setMaidenName(String maidenName) {
    this.maidenName = maidenName;
  }

  public String nickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String fileAs() {
    return fileAs;
  }

  public void setFileAs(String fileAs) {
    this.fileAs = fileAs;
  }

  public String jobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public NSArray<ERGWContactEmail> emails() {
    return emails;
  }

  public void setEmails(NSArray<ERGWContactEmail> emails) {
    this.emails = emails;
  }
  
  public void addEmail(ERGWContactEmail email) {
    NSMutableArray<ERGWContactEmail> array = this.emails().mutableClone();
    array.addObject(email);
    this.setEmails(array.immutableClone());
  }

  public String assistantName() {
    return assistantName;
  }

  public void setAssistantName(String assistantName) {
    this.assistantName = assistantName;
  }

  public NSTimestamp birthday() {
    return birthday;
  }

  public void setBirthday(NSTimestamp birthday) {
    this.birthday = birthday;
  }

  public String businessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String departmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  public NSArray<String> businessUnits() {
    return businessUnits;
  }

  public void setBusinessUnits(NSArray<String> businessUnits) {
    this.businessUnits = businessUnits;
  }

  public String notes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public NSArray<ERGWContactTelephone> telephones() {
    return telephones;
  }

  public void setTelephones(NSArray<ERGWContactTelephone> telephones) {
    this.telephones = telephones;
  }
  
  public void addTelephone(ERGWContactTelephone telephone) {
    NSMutableArray<ERGWContactTelephone> array = this.telephones().mutableClone();
    array.addObject(telephone);
    this.setTelephones(array.immutableClone());
  }

  public NSArray<ERGWContactPhysicalAddress> physicalAddresses() {
    return physicalAddresses;
  }

  public void setPhysicalAddresses(NSArray<ERGWContactPhysicalAddress> physicalAddresses) {
    this.physicalAddresses = physicalAddresses;
  }
  
  public void addPhysicalAddress(ERGWContactPhysicalAddress address) {
    NSMutableArray<ERGWContactPhysicalAddress> array = this.physicalAddresses().mutableClone();
    array.addObject(address);
    this.setPhysicalAddresses(array.immutableClone());
  }

  public NSArray<String> categories() {
    return categories;
  }

  public void setCategories(NSArray<String> categories) {
    this.categories = categories;
  }

  public void addCategory(String category) {
    NSMutableArray<String> array = this.categories().mutableClone();
    array.addObject(category);
    this.setCategories(array.immutableClone());
  }
  
  public NSArray<ERGWContactUrl> urls() {
    return urls;
  }

  public void setUrls(NSArray<ERGWContactUrl> urls) {
    this.urls = urls;
  }
  
  public void addUrl(ERGWContactUrl url) {
    NSMutableArray<ERGWContactUrl> array = this.urls().mutableClone();
    array.addObject(url);
    this.setUrls(array.immutableClone());
  }

  public NSData photo() {
    return photo;
  }

  public void setPhoto(NSData photo) {
    this.photo = photo;
  }

  public NSArray<String> labels() {
    return labels;
  }

  public void setLabels(NSArray<String> labels) {
    this.labels = labels;
  }
  
  public void addLabel(String label) {
    NSMutableArray<String> array = this.labels().mutableClone();
    array.addObject(label);
    this.setLabels(array.immutableClone());
  }

  public NSTimestamp anniversary() {
    return anniversary;
  }

  public void setAnniversary(NSTimestamp anniversary) {
    this.anniversary = anniversary;
  }

  public NSArray<String> hobbies() {
    return hobbies;
  }

  public void setHobbies(NSArray<String> hobbies) {
    this.hobbies = hobbies;
  }

  public String spouseName() {
    return spouseName;
  }

  public void setSpouseName(String spouseName) {
    this.spouseName = spouseName;
  }

  public NSArray<String> children() {
    return children;
  }

  public void setChildren(NSArray<String> children) {
    this.children = children;
  }

  public ERGWClassification classification() {
    return classification;
  }

  public void setClassification(ERGWClassification classification) {
    this.classification = classification;
  }

  public NSArray<String> imAddresses() {
    return imAddresses;
  }

  public void setImAddresses(NSArray<String> imAddresses) {
    this.imAddresses = imAddresses;
  }
  
  public void addImAddress(String imAddress) {
    NSMutableArray<String> array = this.imAddresses().mutableClone();
    array.addObject(imAddress);
    this.setImAddresses(array.immutableClone());
  }

  public String manager() {
    return manager;
  }

  public void setManager(String manager) {
    this.manager = manager;
  }

  public String assistant() {
    return assistant;
  }

  public void setAssistant(String assistant) {
    this.assistant = assistant;
  }
  
  // TODO Well, it should be implemented
  public static VCard transformToVCardObject(ERGWContact contactObject, VCard vCardComponent) throws SocketException, URISyntaxException {
    return null;
  }

  public static ERGWContact transformFromVCardObject(VCard vCardComponent) throws SocketException, URISyntaxException {
    ERGWContact newContact = new ERGWContact();
    
    N name = (N)vCardComponent.getProperty(net.fortuna.ical4j.vcard.Property.Id.N);
    if (name != null) {
      newContact.setFamilyName(name.getFamilyName());
      newContact.setGivenName(name.getGivenName());
      newContact.setAdditionalNames(new NSArray<String>(name.getAdditionalNames()));
      newContact.setHonorificPrefix(new NSArray<String>(name.getPrefixes()));
      newContact.setHonorificSuffix(new NSArray<String>(name.getSuffixes()));
    }
    
    Property nickName = vCardComponent.getProperty(net.fortuna.ical4j.vcard.Property.Id.NICKNAME);
    if (nickName != null)
        newContact.setNickname(nickName.getValue());
    
    java.util.List<Property> telephones = vCardComponent.getProperties(net.fortuna.ical4j.vcard.Property.Id.TEL);
    if (telephones != null) {
      for (Property telephoneProp: telephones) {
        Telephone telDetails = (Telephone)telephoneProp;
        ERGWContactTelephone telephone = new ERGWContactTelephone();
        telephone.setValue(telDetails.getValue());
        Parameter typeParam = telDetails.getParameter(net.fortuna.ical4j.vcard.Parameter.Id.TYPE);
        NSMutableArray<ERGWContactTelephoneType> phoneTypes = new NSMutableArray<ERGWContactTelephoneType>();
        if (typeParam != null) {
          String[] paramValues = typeParam.getValue().split(",");
          for (int paramIterator = 0; paramIterator < paramValues.length; paramIterator++) {
            String typeFromVCard = paramValues[paramIterator];
            if (Type.PREF.getValue().equals(typeFromVCard)) {
              telephone.setIsPrefered(true);              
            }
            if ("VOICE".equals(typeFromVCard)) {
              telephone.setIsVoiceNumber(true);              
            }
            if ("FAX".equals(typeFromVCard)) {
              telephone.setIsFaxNumber(true);        
            }
            ERGWContactTelephoneType type = ERGWContactTelephoneType.getByRFC2445Value(paramValues[paramIterator]);            
            if (type != null) {
              phoneTypes.addObject(type);
            }
          }
        } else {
          telephone.setIsVoiceNumber(true);
          telephone.setIsPrefered(true);
        }
        telephone.setTypes(phoneTypes.immutableClone());
      }
    }
    
    java.util.List<Property> emails = vCardComponent.getProperties(net.fortuna.ical4j.vcard.Property.Id.EMAIL);
    if (emails != null) {
      for (Property emailProp: emails) {
        Email emailDetails = (Email)emailProp;
        ERGWContactEmail email = new ERGWContactEmail();
        email.setEmail(emailDetails.getValue());
        Parameter commonName = emailDetails.getParameter(net.fortuna.ical4j.vcard.Parameter.Id.CN);
        if (commonName != null) {
          email.setCommonName(commonName.getValue());
        }
        Parameter typeParam = emailDetails.getParameter(net.fortuna.ical4j.vcard.Parameter.Id.TYPE);
        NSMutableArray<ERGWContactEmailType> emailTypes = new NSMutableArray<ERGWContactEmailType>();
        if (typeParam != null) {
          String[] paramValues = typeParam.getValue().split(",");
          for (int paramIterator = 0; paramIterator < paramValues.length; paramIterator++) {
            String typeFromVCard = paramValues[paramIterator];
            if (Type.PREF.getValue().equals(typeFromVCard)) {
              email.setIsPrefered(true);              
            }
            ERGWContactEmailType type = ERGWContactEmailType.getByRFC2445Value(paramValues[paramIterator]);            
            if (type != null) {
              emailTypes.addObject(type);
            }
          }
        } else {
          email.setIsPrefered(true);
        } 
      }
    }

    Property fileAs = vCardComponent.getExtendedProperty("X-FILE-AS");
    if (fileAs != null)
      newContact.setFileAs(fileAs.getValue());

    return newContact; 
  }
  
}
