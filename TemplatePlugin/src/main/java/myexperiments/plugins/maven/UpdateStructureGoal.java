package main.java.myexperiments.plugins.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.FileNotFoundException;

@Mojo(name = UpdateStructureGoal.TITLE)
public class UpdateStructureGoal extends AbstractMojo {
    public static final String TITLE = "UpdateStructure";

    /**
     * Path to properties file
     */
    @Parameter(property = "propertiesFile")
    protected String propertiesFile;

    /**
     * Path to a project files to be replaced. Usually default value is correct one
     */
    @Parameter(property = "projectPath")
    protected String projectPath;

    public void execute() throws MojoExecutionException {
        UpdateStructure updateStructure = new UpdateStructure();
        try {
            updateStructure.prepareSources(projectPath,propertiesFile);
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("Properties file is not found");
        }

        //create java objects based on the list of modules from properties file and write pom.xml files
        getLog().info("Start processing pom.xml files");

        try {
            updateStructure.writeXmlFileFromObject(updateStructure.getCurrentProjectPath());
        } catch (Exception e) {
            System.out.println(e);
            throw new MojoExecutionException("Object -> XML or XML -> Object transformation went wrong. Please check logs first" + "\n" + e);
        }
        getLog().info("Processing of pom.xml files is finished");
    }
}
