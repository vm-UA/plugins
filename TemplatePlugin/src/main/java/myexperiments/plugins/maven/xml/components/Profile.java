package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlType(propOrder = {"id", "activation", "properties", "modules", "dependencies"})
public class Profile {
    private String id;
    private ArrayList<String> activation;
    private Properties properties;
    private Modules modules;
    private ArrayList<Dependency> dependencies;

    public Profile() {
    }

    public Profile(String id, ArrayList<String> activation, Modules modules) {
        this.id = id;
        this.activation = activation;
        this.modules = modules;
    }

    public Profile(String id, Properties properties, Modules modules, ArrayList<Dependency> dependencies) {
        this.id = id;
        this.properties = properties;
        this.modules = modules;
        this.dependencies = dependencies;
    }

    public Profile(String id, ArrayList<String> activation, Properties properties, Modules modules, ArrayList<Dependency> dependencies) {
        this.id = id;
        this.activation = activation;
        this.properties = properties;
        this.modules = modules;
        this.dependencies = dependencies;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElementWrapper(name = "activation")
    @XmlElement(name = "activeByDefault")
    public ArrayList<String> getActivation() {
        return activation;
    }

    public void setActivation(ArrayList<String> activation) {
        this.activation = activation;
    }

    @XmlElement
    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @XmlElement
    public Modules getModules() {
        return modules;
    }

    public void setModules(Modules modules) {
        this.modules = modules;
    }

    @XmlElementWrapper(name = "dependencies")
    @XmlElement(name = "dependency")
    public ArrayList<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(ArrayList<Dependency> dependencies) {
        this.dependencies = dependencies;
    }
}
