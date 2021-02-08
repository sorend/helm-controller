package helm_operator.crds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.KubernetesResource;

import java.util.Map;

/**
 *
 *
 * Created from {@link "https://github.com/fluxcd/helm-operator/blob/master/pkg/apis/helm.fluxcd.io/v1/types_helmrelease.go"}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = JsonDeserializer.None.class)
public class HelmReleaseSpec implements KubernetesResource {
    private HelmVersion helmVersion;
    private RepoChartSource chart;
    private String releaseName;
    private int maxHistory;

    // valueFileSecrets;
    // ValuesFromSource[] valuesFrom;

    private String targetNamespace;
    private int timeout;
    private boolean resetValues;

    // boolean skipCRDs;

    private boolean wait;
    private boolean forceUpgrade;

    // private Rollback rollback;
    // Test test;

    private Map<String, Object> values;
    private boolean disableOpenAPIValidation;

    public HelmVersion getHelmVersion() {
        return helmVersion;
    }

    public void setHelmVersion(HelmVersion helmVersion) {
        this.helmVersion = helmVersion;
    }

    public RepoChartSource getChart() {
        return chart;
    }

    public void setChart(RepoChartSource chart) {
        this.chart = chart;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public int getMaxHistory() {
        return maxHistory;
    }

    public void setMaxHistory(int maxHistory) {
        this.maxHistory = maxHistory;
    }

    public String getTargetNamespace() {
        return targetNamespace;
    }

    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isResetValues() {
        return resetValues;
    }

    public void setResetValues(boolean resetValues) {
        this.resetValues = resetValues;
    }

    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public boolean isForceUpgrade() {
        return forceUpgrade;
    }

    public void setForceUpgrade(boolean forceUpgrade) {
        this.forceUpgrade = forceUpgrade;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public boolean isDisableOpenAPIValidation() {
        return disableOpenAPIValidation;
    }

    public void setDisableOpenAPIValidation(boolean disableOpenAPIValidation) {
        this.disableOpenAPIValidation = disableOpenAPIValidation;
    }
}
