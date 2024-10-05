package main.java.myexperiments.plugins.maven.xml.entities;

import main.java.myexperiments.plugins.maven.xml.components.*;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Map;

@XmlRootElement(name = "project")
public class ProjectRoot extends ProjectXml {
    public ProjectRoot() {
        modelVersion = "4.0.0";
        packaging = "pom";
        properties = new Properties(new ArrayList<JAXBElement<String>>() {{
            add(new JAXBElement<>(new QName("changelist"), String.class, "-SNAPSHOT"));
            add(new JAXBElement<>(new QName("root.artifactId"), String.class, "Root"));
            add(new JAXBElement<>(new QName("skipTests"), String.class, "false"));
            add(new JAXBElement<>(new QName("nexus-tag"), String.class, "Nexus-NewArtifact-${project.version}"));
            add(new JAXBElement<>(new QName("repo-releases"), String.class, "releases"));
            add(new JAXBElement<>(new QName("nexus-id"), String.class, "nexus"));
            add(new JAXBElement<>(new QName("nexus-url"), String.class, "http://nexus.yourpath.com"));
        }});
        groupId = "${root.groupId}";
        artifactId = "${root.artifactId}";
        version = "${revision}${changelist}";
        distributionManagement = new DistributionManagement(
                new Repository("nexus", "http://nexus.yourpath.com/repository/releases/"),
                new Repository("nexus", "http://nexus.yourpath.com/repository/snapshots/")
        );
        build = new Build(
                new ArrayList<Plugin>() {{
                    add(new Plugin("org.codehaus.mojo", "flatten-maven-plugin", "1.1.0", null, null, null,
                            new Properties("updatePomFile", "true"),
                            new ArrayList<Execution>() {{
                                add(new Execution("flatten-flatten", "process-resources", new Goals("flatten"), null));
                                add(new Execution("flatten-clean", "clean", new Goals("clean"), null));
                            }}));
                    add(new Plugin("org.sonatype.plugins", "nxrm3-maven-plugin", "1.0.3", "true", null, null,
                            new Properties(new ArrayList<JAXBElement<String>>() {{
                                add(new JAXBElement<String>(new QName("nexusUrl"), String.class, "${nexus-url}"));
                                add(new JAXBElement<String>(new QName("serverId"), String.class, "${nexus-id}"));
                                add(new JAXBElement<String>(new QName("repository"), String.class, "${repo-releases}"));
                                add(new JAXBElement<String>(new QName("tag"), String.class, "${nexus-tag}"));
                                add(new JAXBElement<String>(new QName("skipNexusStagingDeployMojo"), String.class, "${staging}"));
                            }}),
                            new ArrayList<Execution>() {{
                                add(new Execution("default-deploy", "deploy", new Goals("deploy"), null));
                            }}));
                }},
                new Plugins(new ArrayList<Plugin>() {{
                    add(new Plugin("org.apache.maven.plugins", "maven-jar-plugin", "2.4", null, null, null, null, null));
                    add(new Plugin("org.apache.maven.plugins", "maven-resources-plugin", "2.6", null, null, null, null, null));
                    add(new Plugin("org.apache.maven.plugins", "maven-dependency-plugin", "3.3.0", null, null, null, null, null));
                    add(new Plugin("org.codehaus.mojo", "build-helper-maven-plugin", "3.1.0", null, null, null, null, null));
                    add(new Plugin("org.apache.maven.plugins", "maven-install-plugin", null, "false", null, null, new Properties("skip", "true"), null));
                    add(new Plugin("org.apache.maven.plugins", "maven-deploy-plugin", null, "false", null, null, new Properties("skip", "true"), null));
                }})
        );
    }

    public ProjectRoot(PropertiesSource propertiesSource) {
        this();
        if (!propertiesSource.getProperties().isEmpty()) {
            updateProperties(propertiesSource.getModuleProperties("module_01"));
        }
        if (!propertiesSource.getModulesRoot().isEmpty()) {
            this.modules = new Modules(propertiesSource.getModulesRoot());
        }
        if (!propertiesSource.getDependencies().isEmpty()) {
            dependencies = new ArrayList<>();
            updateDependencies(propertiesSource.getDependencies());
        }
        if (!propertiesSource.getModulesAlt().isEmpty() || !propertiesSource.getModulesDefault().isEmpty()) {
            profiles = new ArrayList<>();
            //default profile
            if (!propertiesSource.getModulesDefault().isEmpty()) {
                profiles.add(new Profile("default", new ArrayList<String>() {{
                    add("true");
                }}, new Modules(propertiesSource.getModulesDefault())));
            }
            if (!propertiesSource.getPropertiesDefault().isEmpty()) {
                updateProileProperties("default", new Properties(propertiesSource.getPropertiesDefault()));
            }
            if (!propertiesSource.getDependenciesDefault().isEmpty()) {
                updateProfileDependencies("default", stringsToDependencies(propertiesSource.getDependenciesDefault()));
            }
            //alt profile
            if (!propertiesSource.getModulesAlt().isEmpty()) {
                profiles.add(new Profile("alt", null, new Modules(propertiesSource.getModulesAlt())));
            }
            if (!propertiesSource.getPropertiesAlt().isEmpty()) {
                updateProileProperties("alt", new Properties(propertiesSource.getPropertiesAlt()));
            }
            if (!propertiesSource.getDependenciesAlt().isEmpty()) {
                updateProfileDependencies("alt", stringsToDependencies(propertiesSource.getDependenciesAlt()));
            }
        }
    }

    public ProjectRoot(Map<String, String> properties) {
        this();
        updateProperties(properties);
    }

    public ProjectRoot(Map<String, String> properties, ArrayList<String> dependencies) {
        this();
        updateProperties(properties);
        updateDependencies(dependencies);
    }

    public ProjectRoot(String[] modules, Map<String, String> properties) {
        this();
        updateModules(modules);
        updateProperties(properties);
    }

    public ProjectRoot(Map<String, String> properties, String[] modules, ArrayList<String> dependencies) {
        this();
        updateProperties(properties);
        updateModules(modules);
        updateDependencies(dependencies);
    }
}
