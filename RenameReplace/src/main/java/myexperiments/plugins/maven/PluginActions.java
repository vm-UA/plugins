package main.java.myexperiments.plugins.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PluginActions extends AbstractMojo {
    int dirCount = 0;
    int filesCount = 0;
    int replCount = 0;

    public void recursiveRename(String dir, String token, String filter, String excludes) throws IOException {
        boolean skipPath;
        String inToken = token.split("=")[0].trim();
        String outToken = token.split("=")[1].trim();
        List<Path> listPaths = Files.walk(Paths.get(dir)).filter(p -> p.getFileName().toString().contains(inToken)).collect(Collectors.toList());

        if (filter != null) {
            listPaths = Files.walk(Paths.get(dir)).filter(p -> p.getFileName().toString().contains(inToken)).filter(p -> p.getFileName().toString().matches(filter)).collect(Collectors.toList());
        }
        if (!listPaths.isEmpty()) {
            for (Path p : listPaths) {
                skipPath = false;
                if (excludes != null) {
                    for (String exc : excludes.split(",")) {
                        if (Paths.get(exc).toAbsolutePath().compareTo(p.toAbsolutePath()) == 0) {
                            getLog().info("Path skipped: " + p.toAbsolutePath().toString());
                            skipPath = true;
                            break;
                        }
                    }
                }
                if (skipPath) {
                    if (listPaths.indexOf(p) == listPaths.size() - 1) {
                        break;
                    } else {
                        continue;
                    }
                }

                File someDir = Paths.get(p.toFile().getParent() + File.separator + p.toFile().getName().replace(inToken, outToken)).toFile();
                p.toFile().renameTo(someDir);

                if (someDir.isDirectory()) {
                    dirCount++;
                    recursiveRename(someDir.toString(), token, filter, excludes);
                } else if (someDir.isFile()) {
                    filesCount++;
                }
            }
        }
    }

    public void replacementsAndFiltersApply(String dir, String excludes, String filter, String token) throws IOException, MojoExecutionException {
        String inReplToken = token.split("=")[0].trim();
        String outReplToken = token.split("=")[1].trim();
        boolean skipPath;
        List<Path> listPaths = Files.walk(Paths.get(dir)).filter(Files::isRegularFile).collect(Collectors.toList());
        if (filter != null) {
            listPaths = Files.walk(Paths.get(dir)).filter(Files::isRegularFile).filter(p -> p.getFileName().toString().matches(filter)).collect(Collectors.toList());
        }
        for (Path p : listPaths) {
            skipPath = false;
            if (excludes != null) {
                for (String exc : excludes.split(",")) {
                    if (Paths.get(exc.trim()).toAbsolutePath().compareTo(p.toAbsolutePath()) == 0) {
                        getLog().info("Path skipped: " + p.toAbsolutePath().toString());
                        skipPath = true;
                        break;
                    }
                }
            }
            if (!skipPath) {
                Charset charset = StandardCharsets.UTF_8;
                String content = "";
                try {
                    content = new String(Files.readAllBytes(p), charset);
                } catch (IOException e) {
                    throw new MojoExecutionException("Failed to read data from file: " + p, e);
                }
                if (content.contains(inReplToken)) {
                    content = content.replaceAll(inReplToken, outReplToken);
                    try {
                        Files.write(p, content.getBytes(charset));
                    } catch (IOException e) {
                        throw new MojoExecutionException("Failed to create updated file: " + p, e);
                    }
                    replCount++;
                }
            }
        }
    }

    public void validateRequiredParameters(String path, String tokens) throws MojoExecutionException {
        String missingParameters = null;
        if (path == null || path == "" || tokens == null) {
            if (path == null || path == "") {
                missingParameters = "basePath";
            }
            if(tokens == null) {
                if(missingParameters == null) {
                    missingParameters = "tokens";
                } else {
                    missingParameters += ", tokens";
                }
            }
            throw new MojoExecutionException("Required parameters are missing: " + missingParameters);
        }
    }

    public int getDirCount() {
        return dirCount;
    }

    public int getFilesCount() {
        return filesCount;
    }

    public int getReplCount() {
        return replCount;
    }
}
