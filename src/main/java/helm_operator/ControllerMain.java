package helm_operator;

import helm_operator.crds.*;
import io.fabric8.kubernetes.api.builder.Function;
import io.fabric8.kubernetes.api.model.apiextensions.v1beta1.CustomResourceDefinition;
import io.fabric8.kubernetes.client.CustomResourceDoneable;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watch;
import io.fabric8.kubernetes.client.dsl.FilterWatchListMultiDeletable;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedInformer;
import io.fabric8.kubernetes.client.informers.SharedInformerFactory;
import io.fabric8.kubernetes.internal.KubernetesDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ControllerMain {

    private static final Logger logger = LoggerFactory.getLogger(ControllerMain.class);

    public static final String CRD_GROUP = "helm.fluxcd.io";
    public static final String CRD_KIND = "HelmRelease";
    public static final String CRD_NAME = "helmreleases." + CRD_GROUP;
    public static final String CRD_VERSION = "v1";

    public static void main(String[] args) throws Exception {
        KubernetesClient client = new DefaultKubernetesClient();
        KubernetesDeserializer.registerCustomKind(CRD_GROUP + "/" + CRD_VERSION, CRD_KIND, HelmRelease.class);
        Optional<CustomResourceDefinition> helmReleaseCRD = client.customResourceDefinitions().list().getItems().stream().filter(x -> CRD_NAME.equals(x.getMetadata().getName())).findFirst();
        // NonNamespaceOperation<HelmRelease, HelmReleaseList, HelmReleaseDoneable, Resource<HelmRelease, HelmReleaseDoneable>> helmReleaseClient = client.customResource(helmReleaseCRD.get(), HelmRelease.class, HelmReleaseList.class, HelmReleaseDoneable.class);

        FilterWatchListMultiDeletable<HelmRelease, HelmReleaseList, Boolean, Watch> helmReleaseClient = client.customResource(helmReleaseCRD.get(), HelmRelease.class, HelmReleaseList.class, HelmReleaseDoneable.class).inAnyNamespace();
        SharedInformerFactory informerFactory = client.informers();
        SharedInformer<HelmRelease> helmReleaseInformer = informerFactory.sharedIndexInformerForCustomResource(CustomResourceDefinitionContext.fromCrd(helmReleaseCRD.get()), HelmRelease.class, HelmReleaseList.class, 10 * 60 * 1000);

        helmReleaseInformer.addEventHandler(new ResourceEventHandler<HelmRelease>() {
            @Override
            public void onAdd(HelmRelease obj) {
                reconcile(helmReleaseClient, obj);
            }

            @Override
            public void onUpdate(HelmRelease oldObj, HelmRelease newObj) {
                if (oldObj.getMetadata().getGeneration() != newObj.getMetadata().getGeneration())
                    reconcile(helmReleaseClient, newObj);
                else
                    logger.info("Not reconciling same generation of an object");
            }

            @Override
            public void onDelete(HelmRelease obj, boolean deletedFinalStateUnknown) {
                reconcile_Delete(helmReleaseClient, obj);
            }
        });

        informerFactory.startAllRegisteredInformers();
        informerFactory.addSharedInformerEventListener(exception -> logger.error("Exception occurred, but caught", exception));

        while (true) {
            Thread.sleep(1000);
        }

        /*
        while (!helmReleaseInformer.hasSynced()) {
            logger.info("!synced, waiting");
        }
        */
    }

    private static void reconcile_Delete(FilterWatchListMultiDeletable<HelmRelease, HelmReleaseList, Boolean, Watch> helmReleaseClient, HelmRelease obj) {
        logger.info("Reconcile_Delete: {}", obj);
        logger.info("Spec: {}", obj.getSpec());
        logger.info("ReleaseName: {}", obj.getSpec().getReleaseName());
        logger.info("Chart: {}, Values: {}", obj.getSpec().getChart(), obj.getSpec().getValues());
        logger.info("HelmVersion: {}", obj.getSpec().getHelmVersion());
    }

    private static void reconcile(FilterWatchListMultiDeletable<HelmRelease, HelmReleaseList, Boolean, Watch> helmReleaseClient, HelmRelease obj) {
        logger.info("Reconcile: {}", obj);
        logger.info("Spec: {}", obj.getSpec());
        logger.info("ReleaseName: {}", obj.getSpec().getReleaseName());
        logger.info("Chart: {}, Values: {}", obj.getSpec().getChart(), obj.getSpec().getValues());
        logger.info("HelmVersion: {}", obj.getSpec().getHelmVersion());
        if (obj.getStatus() == null)
            obj.setStatus(new HelmReleaseStatus());
        obj.getStatus().setPhase(Phase.ChartFetched);
        obj.getStatus().setReleaseStatus("Starting");
        obj.getStatus().setRevision("1");
        obj.getStatus().setRollbackCount(0);
        obj.getStatus().setReleaseName("whatever-release");
        obj.getStatus().setConditions(new HelmReleaseCondition[0]);
        obj.getStatus().setLastAttemptedRevision("1");
        obj.getStatus().setObservedGeneration(1);
        helmReleaseClient.updateStatus(obj);
    }


    private static class HelmReleaseDoneable extends CustomResourceDoneable<HelmRelease> {
        public HelmReleaseDoneable(HelmRelease resource, Function<HelmRelease, HelmRelease> function) {
            super(resource, function);
        }
    }
}
