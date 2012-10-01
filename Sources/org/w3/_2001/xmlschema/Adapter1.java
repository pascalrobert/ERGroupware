
package org.w3._2001.xmlschema;

import java.util.Calendar;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import er.groupware.exchange.ews.DataTypeBinder;

public class Adapter1 extends XmlAdapter<String, Calendar> {

    public Calendar unmarshal(String value) {
        return (DataTypeBinder.unmarshalDateTime(value));
    }

    public String marshal(Calendar value) {
        return (DataTypeBinder.marshalDateTime(value));
    }

}
