package main.java.myexperiments.plugins.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Renames files and folders inisde a 'basePath' directory (not included).
 */

@Mojo(name = Rename.TITLE)
public class Rename extends PluginActions {
    public static final String TITLE = "Rename";

    @Parameter(property = "basePath", required = true, defaultValue = ".")
    protected String basePath;

    /**
     * Comma-separated list of tokens to be replaced within 'basePath' directory.
     * Format: FROM=TO
     */
    @Parameter(property = "tokens", required = true)
    protected String tokens;

    /**
     * Comma-separated list of paths to include.
     * Must be outside a 'baseDir'
     */
    @Parameter(property = "includes")
    protected String includes;

    /**
     * Comma-separated list of paths to exclude.
     * Must be inside a 'baseDir' and/or 'includes'
     */
    @Parameter(property = "excludes")
    protected String excludes;

    /**
     * Regular expression to filter files/folders by name
     */
    @Parameter(property = "filter")
    protected String filter;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().info("Setting current directory as 'baseDir' if nothing else specified");
        if (basePath.equals(".")) {
            Path currentRelativePath = Paths.get(basePath);
            basePath = currentRelativePath.toAbsolutePath().toString();
        }
        getLog().info("Project path: " + basePath);
        getLog().info("Validating required parameters...");
        validateRequiredParameters(basePath, tokens);
        String path = basePath;
        if (includes != null) {
            getLog().info("includes are there. Iterate over.");
            path = path + "," + includes;
        }
        String[] paths = path.split(",");
        getLog().info("Starting rename of project files...");
        for (String p : paths) {
            for (String token : tokens.split(",")) {
                try {
                    recursiveRename(p, token, filter, excludes);
                } catch (IOException e) {
                    throw new MojoExecutionException("Failed to build a tree.", e);
                }
            }
        }
        getLog().info("Rename completed.");
        getLog().info("*****************");
        getLog().info("Files modified: " + getFilesCount() + ", folders modified: " + getDirCount());
    }
}
