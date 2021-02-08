package helm_operator.crds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.KubernetesResource;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = JsonDeserializer.None.class)
public class HelmReleaseStatus implements KubernetesResource {
    private int observedGeneration;
    private Phase phase;
    private String releaseName;
    private String releaseStatus;
    private String revision;
    private String lastAttemptedRevision;
    private int rollbackCount;
    private HelmReleaseCondition[] conditions;

    public int getObservedGeneration() {
        return observedGeneration;
    }

    public void setObservedGeneration(int observedGeneration) {
        this.observedGeneration = observedGeneration;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getLastAttemptedRevision() {
        return lastAttemptedRevision;
    }

    public void setLastAttemptedRevision(String lastAttemptedRevision) {
        this.lastAttemptedRevision = lastAttemptedRevision;
    }

    public int getRollbackCount() {
        return rollbackCount;
    }

    public void setRollbackCount(int rollbackCount) {
        this.rollbackCount = rollbackCount;
    }

    public HelmReleaseCondition[] getConditions() {
        return conditions;
    }

    public void setConditions(HelmReleaseCondition[] conditions) {
        this.conditions = conditions;
    }
}
