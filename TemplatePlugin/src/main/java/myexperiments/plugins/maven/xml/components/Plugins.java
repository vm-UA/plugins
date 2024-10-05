package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

public class Plugins {
    private ArrayList<Plugin> plugins = new ArrayList<>();

    public Plugins() {
    }

    public Plugins(ArrayList<Plugin> plugins) {
        this.plugins.addAll(plugins);
    }

    public Plugins(Plugin plugin) {
        plugins.add(plugin);
    }

    public void addPlugin(Plugin plugin) {
        plugins.add(plugin);
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
