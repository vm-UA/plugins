package main.java.myexperiments.plugins.maven.xml.entities;

import main.java.myexperiments.plugins.maven.xml.components.*;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@XmlType(propOrder = {"modelVersion", "packaging", "parent", "properties", "groupId", "artifactId", "version", "name", "description", "modules", "profiles", "distributionManagement", "dependencies", "build"})
@XmlRootElement(name = "project")
public class ProjectXml {
    @XmlAttribute
    String xmlns = "http://maven.apache.org/POM/4.0.0";
    @XmlAttribute(name = "xmlns:xsi")
    String xmlnsXsi = "http://www.w3.org/2001/XMLSchema-instance";
    @XmlAttribute(name = "xsi:schemaLocation")
    String xsiSchemaLocation = "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd";
    @XmlElement
    String modelVersion;
    @XmlElement
    String packaging;
    @XmlElement
    String parent;
    @XmlAnyElement
    Properties properties;
    @XmlElement
    String groupId;
    @XmlElement
    String artifactId;
    @XmlElement
    String version;
    @XmlElement
    String name;
    @XmlElement
    String description;
    @XmlElement
    Modules modules;
    @XmlElementWrapper(name = "profiles")
    @XmlElement(name = "profile")
    ArrayList<Profile> profiles;
    @XmlElement
    DistributionManagement distributionManagement;
    @XmlElementWrapper(name = "dependencies")
    @XmlElement(name = "dependency")
    ArrayList<Dependency> dependencies;
    @XmlElement
    Build build;

    public void updateProperties(Map<String, String> newProperties) {
        if (!newProperties.isEmpty()) {
            boolean addProperty;
            for (Map.Entry<String, String> entry : newProperties.entrySet()) {
                addProperty = true;
                for (JAXBElement<String> e : properties.getProperties()) {
                    if (e.getName().toString().equals(entry.getKey())) {
                        e.setValue(entry.getValue());
                        addProperty = false;
                        continue;
                    }
                }
                if (addProperty) {
                    properties.addProperties(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public void updateProperties(ArrayList<JAXBElement<String>> newProperties) {
        if (!newProperties.isEmpty()) {
            boolean addProperty;
            for (JAXBElement<String> prop : newProperties) {
                addProperty = true;
                for (JAXBElement<String> e : properties.getProperties()) {
                    if (e.getName().toString().equals(prop.getName().toString())) {
                        e.setValue(prop.getValue());
                        addProperty = false;
                        continue;
                    }
                }
                if (addProperty) {
                    properties.addProperties(prop.getName().toString(), prop.getValue());
                }
            }
        }
    }

    public ArrayList<JAXBElement<String>> setProperties(Map<String, String> newProperties) {
        ArrayList<JAXBElement<String>> properties = new ArrayList<>();
        for (Map.Entry<String, String> entry : newProperties.entrySet()) {
            properties.add(new JAXBElement<String>(new QName(entry.getKey()), String.class, entry.getValue()));
        }
        return properties;
    }

    void updateModules(String[] newModules) {
        if (newModules.length > 0) {
            this.modules = new Modules(newModules);
        }
    }

    void updateModules(List<String> newModules) {
        if (!newModules.isEmpty()) {
            this.modules = new Modules((ArrayList<String>) newModules);
        }
    }

    void updateDependencies(ArrayList<String> newDependencies) {
        if (!newDependencies.isEmpty()) {
            this.dependencies.clear();
            this.dependencies = stringsToDependencies(newDependencies);
        }
    }

    ArrayList<Dependency> stringsToDependencies(ArrayList<String> stringDependencies) {
        if (!stringDependencies.isEmpty() && stringDependencies != null) {
            String[] depParams;
            ArrayList<Dependency> newDependencies = new ArrayList<>();
            for (String dependency : stringDependencies) {
                String[] params = new String[6];
                depParams = dependency.split(":");
                for (int i = 0; i < depParams.length; i++) {
                    if (!depParams[i].equals("-")) {
                        params[i] = depParams[i].trim();
                    }
                }
                newDependencies.add(new Dependency(params));
                params = null;
                depParams = null;
            }
            return newDependencies;
        }
        return null;
    }

    void updateProfileModules(String id, ArrayList<String> profileModules) {
        for (Profile p : this.profiles) {
            if (p.getId().equals(id)) {
                p.setModules(new Modules(profileModules));
            }
        }
    }

    void updateProileProperties(String id, Properties properties) {
        for (Profile p : this.profiles) {
            if (p.getId().equals(id)) {
                p.setProperties(properties);
            }
        }
    }

    void updateProfileDependencies(String id, ArrayList<Dependency> dependencies) {
        for (Profile p : this.profiles) {
            if (p.getId().equals(id)) {
                p.setDependencies(dependencies);
            }
        }
    }

    boolean isPropertyPresent(String paramName) {
        if (!properties.getProperties().isEmpty()) {
            for (JAXBElement<String> p : properties.getProperties()) {
                if (p.getName().getLocalPart().equals(paramName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getPropertyValueByParamName(String paramName) {
        if (!properties.getProperties().isEmpty()) {
            for (JAXBElement<String> p : properties.getProperties()) {
                if (p.getName().getLocalPart().equals(paramName)) {
                    return p.getValue();
                }
            }
        }
        return null;
    }

    boolean isModulePresent(Map<String, String> modules, String moduleName) {
        if (!modules.isEmpty()) {
            for (String module : modules.values()) {
                if (module.contains(moduleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isDefaultModulePresent(ArrayList<String> modules, String moduleName) {
        if (!modules.isEmpty()) {
            for (String module : modules) {
                if (module.contains(moduleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected ArrayList<String> buildListOfStrings(String parameter) {
        return (parameter == null) ? null : new ArrayList<>(Arrays.asList(parameter.split(",")));
    }
}
