
package com.microsoft.schemas.exchange.services._2006.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         Array of user mailbox.
 *       
 * 
 * <p>Java class for ArrayOfUserMailboxesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfUserMailboxesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserMailbox" type="{http://schemas.microsoft.com/exchange/services/2006/types}UserMailboxType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfUserMailboxesType", propOrder = {
    "userMailbox"
})
public class ArrayOfUserMailboxesType {

    @XmlElement(name = "UserMailbox", required = true)
    protected List<UserMailboxType> userMailbox;

    /**
     * Gets the value of the userMailbox property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userMailbox property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserMailbox().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserMailboxType }
     * 
     * 
     */
    public List<UserMailboxType> getUserMailbox() {
        if (userMailbox == null) {
            userMailbox = new ArrayList<UserMailboxType>();
        }
        return this.userMailbox;
    }

}
