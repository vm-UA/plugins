package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "goals")
public class Goals {
    private ArrayList<String> goals = new ArrayList<>();

    public Goals() {
    }

    public Goals(ArrayList<String> goals) {
        this.goals = goals;
    }

    public Goals(String goal) {
        goals.add(goal);
    }

    @XmlElement(name = "goal")
    public ArrayList<String> getGoals() {
        return goals;
    }

    public void setGoals(ArrayList<String> goals) {
        this.goals = goals;
    }
}
