package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlType(propOrder = {"testResource", "plugins", "pluginManagement"})
@XmlRootElement(name = "build")
public class Build {
    private ArrayList<TestResource> testResources;
    private Plugins pluginManagement;
    private ArrayList<Plugin> plugins;

    public Build() {
    }

    public Build(ArrayList<Plugin> plugins, Plugins pluginManagement) {
        this.plugins = plugins;
        this.pluginManagement = pluginManagement;
    }

    public Build(ArrayList<Plugin> plugins) {
        this.plugins = plugins;
    }

    public Build(ArrayList<TestResource> testResources, ArrayList<Plugin> plugins) {
        this.testResources = testResources;
        this.plugins = plugins;
    }

    public Build(ArrayList<TestResource> testResources, ArrayList<Plugin> plugins, Plugins pluginManagement) {
        this.testResources = testResources;
        this.plugins = plugins;
        this.pluginManagement = pluginManagement;
    }

    @XmlElementWrapper(name = "testResources")
    @XmlElement(name = "testResource")
    public ArrayList<TestResource> getTestResources() {
        return testResources;
    }

    public void setTestResources(ArrayList<TestResource> testResources) {
        this.testResources = testResources;
    }

    public Plugins getPluginManagement() {
        return pluginManagement;
    }

    public void setPluginManagement(Plugins pluginManagement) {
        this.pluginManagement = pluginManagement;
    }

    @XmlElementWrapper(name = "plugins")
    @XmlElement(name = "plugin")
    public ArrayList<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(ArrayList<Plugin> plugins) {
        this.plugins = plugins;
    }
}
