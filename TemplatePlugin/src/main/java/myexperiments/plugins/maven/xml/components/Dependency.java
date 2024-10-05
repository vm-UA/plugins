package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"groupId", "artifactId", "version", "scope", "type", "classifier"})
public class Dependency {
    private String groupId, artifactId, version, scope, type, classifier;

    public Dependency() {

    }

    public Dependency(String[] params) {
        this.groupId = params[0];
        this.artifactId = params[1];
        this.version = params[2];
        this.scope = params[3];
        this.type = params[4];
        this.classifier = params[5];
    }

    public Dependency(String groupId, String artifactId, String version, String scope, String type, String classifier) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.scope = scope;
        this.type = type;
        this.classifier = classifier;
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
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @XmlElement
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement
    public String getClassifier() {
        return classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }
}
