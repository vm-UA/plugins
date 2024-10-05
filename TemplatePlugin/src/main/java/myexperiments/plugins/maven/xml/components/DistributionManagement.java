package main.java.myexperiments.plugins.maven.xml.components;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "distributionManagement")
public class DistributionManagement {
    private Repository repository, snapshotRepository;

    public DistributionManagement() {
    }

    public DistributionManagement(Repository repository, Repository snapshotRepository) {
        this.repository = repository;
        this.snapshotRepository = snapshotRepository;
    }

    @XmlElement
    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @XmlElement
    public Repository getSnapshotRepository() {
        return snapshotRepository;
    }

    public void setSnapshotRepository(Repository snapshotRepository) {
        this.snapshotRepository = snapshotRepository;
    }
}
