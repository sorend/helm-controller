package helm_operator.crds;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = JsonDeserializer.None.class)
public class HelmRelease extends CustomResource implements Namespaced {

    private HelmReleaseSpec spec;

    private HelmReleaseStatus status;

    public HelmReleaseSpec getSpec() {
        return spec;
    }

    public void setSpec(HelmReleaseSpec spec) {
        this.spec = spec;
    }

    public HelmReleaseStatus getStatus() {
        return status;
    }

    public void setStatus(HelmReleaseStatus status) {
        this.status = status;
    }
}
