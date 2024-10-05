package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;

public class TestResource {
    private String directory;
    private ArrayList<String> excludes, includes;

    public TestResource() {
    }

    public TestResource(String directory, ArrayList<String> includes, ArrayList<String> excludes) {
        this.directory = directory;
        this.excludes = excludes;
        this.includes = includes;
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

    @XmlElement
    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
