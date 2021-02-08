package helm_operator.crds;

public class RepoChartSource {
    String repository;
    String name;
    String version;
    // LocalObjectReference chartPullSecret;


    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
