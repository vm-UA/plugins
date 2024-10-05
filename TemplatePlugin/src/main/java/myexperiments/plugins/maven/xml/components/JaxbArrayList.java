package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;

@XmlRootElement(name = "jaxbArrayList")
public class JaxbArrayList {
    private ArrayList<JAXBElement<String>> jaxbArrayList;

    public JaxbArrayList() {
        jaxbArrayList = new ArrayList<>();
    }

    public JaxbArrayList(String token, String value) {
        this();
        jaxbArrayList.add(new JAXBElement<String>(new QName(token), String.class, value));
    }

    public JaxbArrayList(ArrayList<JAXBElement<String>> jaxbArrayList) {
        this.jaxbArrayList = jaxbArrayList;
    }

    @XmlAnyElement
    public ArrayList<JAXBElement<String>> getJaxbArrayList() {
        return jaxbArrayList;
    }

    public void setJaxbArrayList(ArrayList<JAXBElement<String>> jaxbArrayList) {
        this.jaxbArrayList = jaxbArrayList;
    }
}
