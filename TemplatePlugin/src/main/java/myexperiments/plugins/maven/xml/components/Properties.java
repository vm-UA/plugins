package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Map;

@XmlType(propOrder = {"properties", "includes", "excludes", "descriptors", "resources", "fileMappers"})
@XmlRootElement(name = "properties")
public class Properties {
    private ArrayList<JAXBElement<String>> properties;
    private ArrayList<String> includes;
    private ArrayList<String> excludes;
    private ArrayList<String> descriptors;
    private ArrayList<TestResource> resources;
    private ArrayList<JaxbArrayList> fileMappers;

    public Properties() {
    }

    public Properties(ArrayList<JAXBElement<String>> properties, ArrayList<String> includes, ArrayList<String> excludes, ArrayList<String> descriptors, ArrayList<TestResource> resources, ArrayList<JaxbArrayList> fileMappers) {
        this.properties = properties;
        this.includes = includes;
        this.excludes = excludes;
        this.descriptors = descriptors;
        this.resources = resources;
        this.fileMappers = fileMappers;
    }

    public Properties(Map<String, String> properties) {
        if (!properties.isEmpty()) {
            this.properties = new ArrayList<>();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                this.properties.add(new JAXBElement<String>(new QName(entry.getKey()), String.class, entry.getValue()));
            }
        }
    }

    public Properties(String parameter, String value) {
        properties = new ArrayList<>();
        properties.add(new JAXBElement<>(new QName(parameter), String.class, value));
    }

    public Properties(ArrayList<JAXBElement<String>> properties) {
        this.properties = new ArrayList<>(properties);
    }

    public void addProperties(String parameter, String value) {
        this.properties.add(new JAXBElement<String>(new QName(parameter), String.class, value));
    }

    public void addProperties(ArrayList<JAXBElement<String>> properties) {
        this.properties.addAll(properties);
    }

    public void addPropertiesIgnoreIfPresent(ArrayList<JAXBElement<String>> properties) {
        if (!properties.isEmpty()) {
            boolean addProperty;
            for (JAXBElement<String> prop : properties) {
                addProperty = true;
                for (JAXBElement<String> e : this.properties) {
                    if (e.getName().toString().equals(prop.getName().toString())) {
                        addProperty = false;
                        continue;
                    }
                }
                if (addProperty) {
                    addProperties(prop.getName().toString(), prop.getValue());
                }
            }
        }
    }

    public void addPropertiesOverWriteIfPresent(ArrayList<JAXBElement<String>> properties) {
        if (!properties.isEmpty()) {
            boolean addProperty;
            for (JAXBElement<String> prop : properties) {
                addProperty = true;
                for (JAXBElement<String> e : this.properties) {
                    if (e.getName().toString().equals((prop.getName().toString()))) {
                        addProperty = false;
                        e.setValue(prop.getValue());
                        break;
                    }
                }
                if (addProperty) {
                    addProperties(prop.getName().toString(), prop.getValue());
                }
            }
        }
    }

    @XmlElement
    public ArrayList<JAXBElement<String>> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<JAXBElement<String>> properties) {
        this.properties = properties;
    }

    @XmlElementWrapper(name = "includes")
    @XmlElement(name = "include")
    public ArrayList<String> getIncludes() {
        return includes;
    }

    public void setIncludes(ArrayList<String> includes) {
        this.includes = includes;
    }

    @XmlElementWrapper(name = "excludes")
    @XmlElement(name = "exclude")
    public ArrayList<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(ArrayList<String> excludes) {
        this.excludes = excludes;
    }

    @XmlElementWrapper(name = "descriptors")
    @XmlElement(name = "descriptor")
    public ArrayList<String> getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(ArrayList<String> descriptors) {
        this.descriptors = descriptors;
    }

    @XmlElementWrapper(name = "resources")
    @XmlElement(name = "resource")
    public ArrayList<TestResource> getResources() {
        return resources;
    }

    public void setResources(ArrayList<TestResource> resources) {
        this.resources = resources;
    }

    @XmlElementWrapper(name = "fileMappers")
    @XmlElement(name = "org.codehaus.plexus.components.io.filemappers.RegExpFileMapper")
    public ArrayList<JaxbArrayList> getFileMappers() {
        return fileMappers;
    }

    public void setFileMappers(ArrayList<JaxbArrayList> fileMappers) {
        this.fileMappers = fileMappers;
    }
}
