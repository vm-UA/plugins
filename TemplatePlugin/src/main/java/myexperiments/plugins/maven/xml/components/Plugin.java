package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;

@XmlType(propOrder = {"groupId", "artifactId", "version", "inherited", "extensions", "dependencies", "configuration", "executions"})
public class Plugin {
    private String groupId, artifactId, version, inherited, extensions;
    private ArrayList<Dependency> dependencies;
    private Properties configuration;
    private ArrayList<Execution> executions;

    public Plugin() {
    }

    public Plugin(String groupId, String artifactId, String version, String inherited, String extensions, ArrayList<Dependency> dependencies, Properties configuration, ArrayList<Execution> executions) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.inherited = inherited;
        this.extensions = extensions;
        this.dependencies = dependencies;
        this.configuration = configuration;
        this.executions = executions;
    }

    @XmlElement
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @XmlElement
    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    @XmlElement
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @XmlElement
    public String getInherited() {
        return inherited;
    }

    public void setInherited(String inherited) {
        this.inherited = inherited;
    }

    @XmlElementWrapper(name = "dependencies")
    @XmlElement(name = "dependency")
    public ArrayList<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(ArrayList<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    @XmlElement
    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }

    @XmlElementWrapper(name = "executions")
    @XmlElement(name = "execution")
    public ArrayList<Execution> getExecutions() {
        return executions;
    }

    public void setExecutions(ArrayList<Execution> executions) {
        this.executions = executions;
    }

    @XmlElement
    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

}
