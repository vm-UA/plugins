package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"groupId", "artifactId", "version", "relativePath"})
@XmlRootElement(name = "parent")
public class Parent {
    private String groupId, artifactId, version, relativePath;

    public Parent() {
        groupId = "${root.groupId}";
        artifactId = "${root.artifactId}";
        version = "${revision}${changelist}";
    }

    public Parent(String relativePath) {
        this();
        this.relativePath = relativePath;
    }

    public Parent(String relativePath, String artifactId) {
        this(relativePath);
        this.artifactId = artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    @XmlElement
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    @XmlElement
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    @XmlElement
    public void setVersion(String version) {
        this.version = version;
    }

    public String getRelativePath() {
        return relativePath;
    }

    @XmlElement
    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }
}
