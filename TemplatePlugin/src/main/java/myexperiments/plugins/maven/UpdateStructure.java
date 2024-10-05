package main.java.myexperiments.plugins.maven;

import main.java.myexperiments.plugins.maven.xml.components.JaxbArrayList;
import main.java.myexperiments.plugins.maven.xml.entities.ProjectRoot;
import main.java.myexperiments.plugins.maven.xml.entities.ProjectXml;
import main.java.myexperiments.plugins.maven.xml.entities.PropertiesSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class UpdateStructure {

    private Map<String, Map<String, String>> moduleProperties;
    private Map<String, String> modules;
    private PropertiesSource propertiesSource;
    private ProjectXml fileObject;
    private File file;
    private String currentProjectPath;
    private String currentPropertiesPath;
    private String dirPath;

    public static void main(String[] args) throws Exception {
        UpdateStructure updateStructure = new UpdateStructure();
        updateStructure.prepareSources(args[0], args[1]);
        //create java objects based on the list of modules from properties file and write pom.xml
        System.out.println("Start processing pom.xml files");
        try {
            updateStructure.writeXmlFileFromObject(args[0]);
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception("Object -> XML or XML -> Object transformation went wrong. Please check logs first" + "\n" + e);
        }
        System.out.println("Processing of pom.xml files is finished");
    }

    protected void generateProjectXmlObject(Map.Entry<String, String> entry, String projectPath) throws Exception {
        boolean useProperties = false;
        file = new File(projectPath + File.pathSeparator + entry.getKey() + File.pathSeparator + "pom.xml");

        String moduleNo = entry.getValue().substring(0, 9).toLowerCase();
        String moduleType = entry.getValue().substring(10).toLowerCase();
        if (moduleProperties.containsKey(moduleNo)) {
            useProperties = true;
        }
        switch (moduleType) {
            case "root":
                fileObject = new ProjectRoot(propertiesSource);
                file = new File(projectPath + File.pathSeparator + "pom.xml");
                dirPath = projectPath + File.pathSeparator;
                useProperties = false;
                break;
            /*case "some module":
                //some actions*/
            default:
                throw new Exception("Module: " + moduleType + " is not recognized as expected one");
        }
    }

    protected void prepareSources(String projectPath, String propertiesFilePath) throws FileNotFoundException {
        currentProjectPath = projectPath;
        if (projectPath == null) {
            Path currentRelativePath = Paths.get(".");
            currentProjectPath = currentRelativePath.toAbsolutePath().toString();
        }
        System.out.println("Project path: " + currentProjectPath);
        currentPropertiesPath = propertiesFilePath;
        if (propertiesFilePath == null) {
            currentPropertiesPath = currentProjectPath + File.pathSeparator + "repo.properties";
        }
        System.out.println("Path to properties file: " + currentPropertiesPath);
        propertiesSource = new PropertiesSource(currentPropertiesPath);
        moduleProperties = propertiesSource.getProperties();
        modules = propertiesSource.getModules();
        if (propertiesSource.getUnrecognizedData() != null) {
            System.out.println(propertiesSource.getUnrecognizedData());
        }
    }

    protected void writeXmlFileFromObject(String projectPath) throws Exception {
        File fileForReplacement = new File(projectPath + File.pathSeparator + "target" + File.pathSeparator + "pom.xml");
        if (Files.notExists(fileForReplacement.toPath().getParent())) {
            Files.createDirectories(fileForReplacement.toPath().getParent());
            Files.createFile(fileForReplacement.toPath());
        } else if (Files.notExists(fileForReplacement.toPath())) {
            Files.createFile(fileForReplacement.toPath());
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(ProjectXml.class, JaxbArrayList.class);
        Marshaller mar = jaxbContext.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        for (Map.Entry<String, String> entry : modules.entrySet()) {
            dirPath = projectPath + File.pathSeparator + entry.getKey() + File.pathSeparator;
            generateProjectXmlObject(entry, projectPath);
            mar.marshal(fileObject, fileForReplacement);
            if (Files.notExists(Paths.get(dirPath))) {
                Files.createDirectories(Paths.get(dirPath));
            }
            Files.copy(fileForReplacement.toPath(), file.toPath(), REPLACE_EXISTING);
            System.out.println(file.getPath() + " processed");
            Files.delete(fileForReplacement.toPath());
        }
        Files.deleteIfExists(fileForReplacement.toPath().getParent());
    }

    public String getCurrentProjectPath() {
        return currentProjectPath;
    }
}
