package main.java.myexperiments.plugins.maven.xml.entities;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This implementation supports only two profiles: "alt" and "default"
 */
public class PropertiesSource {
    private Map<String, String> modules = new LinkedHashMap<>();
    private ArrayList<String> dependencies = new ArrayList<>();
    private Map<String, Map<String, String>> properties = new HashMap<>();
    private ArrayList<String> propertiesList = new ArrayList<>();
    private String unrecognizedData, propName, propValue;
    private ArrayList<String> modulesAlt = new ArrayList<>();
    private ArrayList<String> modulesDefault = new ArrayList<>();
    private ArrayList<String> modulesRoot = new ArrayList<>();
    private ArrayList<JAXBElement<String>> propertiesAlt = new ArrayList<>();
    private ArrayList<JAXBElement<String>> propertiesDefault = new ArrayList<>();
    private ArrayList<String> dependenciesAlt = new ArrayList<>();
    private ArrayList<String> dependenciesDefault = new ArrayList<>();

    public PropertiesSource() {
    }

    public PropertiesSource(String path) throws FileNotFoundException {
        Scanner in = new Scanner(new File(path));
        String nextline;
        while (in.hasNextLine()) {
            nextline = in.nextLine();
            if (nextline.matches("module_[0-9]{2}\\.alt\\..*")) {
                modulesAlt.add(nextline.split("=")[1].trim());
                modules.put(nextline.split("=")[1].trim(), nextline.split("=")[0].replace(".alt", "").trim());
            } else if (nextline.matches("module_[0-9]{2}\\.default\\..*")) {
                modulesDefault.add(nextline.split("=")[1].trim());
                modules.put(nextline.split("=")[1].trim(), nextline.split("=")[0].replace(".default", "").trim());
            } else if (nextline.matches("module_[0-9]{2}\\..*")) {
                if (!nextline.split("=")[1].trim().equals(".")) {
                    modulesRoot.add(nextline.split("=")[1].trim());
                }
                modules.put(nextline.split("=")[1].trim(), nextline.split("=")[0].trim());
            } else if (nextline.startsWith("dependency")) {
                dependencies.add(nextline.split("=")[1].trim());
            } else if (nextline.startsWith("property.module_")) {
                propertiesList.add(nextline);
            } else if (nextline.startsWith("profile_alt.property.")) {
                propName = nextline.substring(0, nextline.indexOf("="));
                propValue = nextline.substring(nextline.indexOf("=") + 1);
                propertiesAlt.add(new JAXBElement<String>(new QName(propName.replace("profile_alt.property.", "").trim()), String.class, propValue));
                propName = null;
                propValue = null;
            } else if (nextline.startsWith("profile_default.property.")) {
                propName = nextline.substring(0, nextline.indexOf("="));
                propValue = nextline.substring(nextline.indexOf("=") + 1);
                propertiesDefault.add(new JAXBElement<String>(new QName(propName.replace("profile_default.property.", "").trim()), String.class, propValue));
                propName = null;
                propValue = null;
            } else if (nextline.startsWith("profile_alt.dependency")) {
                dependenciesAlt.add(nextline.split("=")[1].trim());
            } else if (nextline.startsWith("profile_default.dependency")) {
                dependenciesDefault.add(nextline.split("=")[1].trim());
            } else if (nextline.startsWith("//") || nextline.equals("")) {
                //processing comments
                continue;
            } else {
                //processing unrecognized data
                if (unrecognizedData != null) {
                    unrecognizedData += "\n" + nextline;
                } else {
                    unrecognizedData = "Some data in properties file are not processed:\n" + nextline;
                }
            }
        }
        if (!propertiesList.isEmpty()) {
            Map<String, String> props = new HashMap<>();
            String nextModule = null;
            String curmodule = null;
            for (int i = 0; i < propertiesList.size(); i++) {
                curmodule = propertiesList.get(i).substring(0, 18).trim();
                nextModule = (i < propertiesList.size() - 1) ? propertiesList.get(i + 1).substring(0, 18).trim() : null;
                propName = propertiesList.get(i).substring(19).substring(0, propertiesList.get(i).substring(19).indexOf("="));
                propValue = propertiesList.get(i).substring(19).substring(propertiesList.get(i).substring(19).indexOf("=") + 1);
                props.put(propName.trim(), propValue.trim());
                propName = null;
                propValue = null;
                if (curmodule.equals(nextModule)) {
                    continue;
                }

                properties.put(propertiesList.get(i).substring(9, 18), new HashMap<String, String>(props));
                props.clear();
            }
        }
    }

    public ArrayList<JAXBElement<String>> getPropertiesAlt() {
        return propertiesAlt;
    }

    public ArrayList<JAXBElement<String>> getPropertiesDefault() {
        return propertiesDefault;
    }

    public Map<String, String> getModules() {
        return modules;
    }

    public ArrayList<String> getDependencies() {
        return dependencies;
    }

    public Map<String, Map<String, String>> getProperties() {
        return properties;
    }

    public String getUnrecognizedData() {
        return unrecognizedData;
    }

    public ArrayList<String> getModulesAlt() {
        return modulesAlt;
    }

    public ArrayList<String> getModulesDefault() {
        return modulesDefault;
    }

    public ArrayList<String> getModulesRoot() {
        return modulesRoot;
    }

    public ArrayList<String> getDependenciesAlt() {
        return dependenciesAlt;
    }

    public ArrayList<String> getDependenciesDefault() {
        return dependenciesDefault;
    }

    public Map<String, String> getModuleProperties(String moduleNo) {
        for (Map.Entry<String, Map<String, String>> entry : properties.entrySet()) {
            if (entry.getKey().equals(moduleNo)) {
                return entry.getValue();
            }
        }
        return new HashMap<String, String>();
    }

    public ArrayList<JAXBElement<String>> getModulePropertiesJaxb(String moduleNo) {
        for (Map.Entry<String, Map<String, String>> entry : properties.entrySet()) {
            if (entry.getKey().equals(moduleNo)) {
                ArrayList<JAXBElement<String>> moduleProperties = new ArrayList<>();
                for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
                    moduleProperties.add(new JAXBElement<String>(new QName(innerEntry.getKey()), String.class, innerEntry.getValue()));
                }
                return moduleProperties;
            }
        }
        return new ArrayList<JAXBElement<String>>();
    }
}
