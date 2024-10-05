package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement(name = "modules")
public class Modules {
    private ArrayList<String> modules = new ArrayList<>();

    public Modules() {
    }

    public Modules(String[] modules) {
        for (int i = 0; i < modules.length; i++) {
            if (modules[i] != null && modules[i] != "") {
                this.modules.add(modules[i]);
            }
        }
    }

    public Modules(ArrayList<String> modules) {
        this.modules = modules;
    }

    @XmlElement(name = "module")
    public ArrayList<String> getModules() {
        return modules;
    }

    public void setModules(ArrayList<String> modules) {
        this.modules = modules;
    }

    public void addModule(String module) {
        this.modules.add(module);
    }
}
