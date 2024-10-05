package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "phase", "goals", "configuration"})
public class Execution {
    private String id, phase;
    private Goals goals;
    private Properties configuration;

    public Execution() {
    }

    public Execution(String id, String phase, Goals goals, Properties configuration) {
        this.id = id;
        this.phase = phase;
        this.goals = goals;
        this.configuration = configuration;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    @XmlElement
    public Goals getGoals() {
        return goals;
    }

    public void setGoals(Goals goals) {
        this.goals = goals;
    }

    @XmlElement
    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }
}
