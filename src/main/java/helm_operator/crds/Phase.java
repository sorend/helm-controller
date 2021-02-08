package helm_operator.crds;

public enum Phase {
    ChartFetched,
    ChartFetchFailed,
    Installing,
    Upgrading,
    Deployed,
    DeployFailed,
    Testing,
    TestFailed,
    Tested,
    Succeeded,
    RollingBack,
    RolledBack,
    RollbackFailed
}
