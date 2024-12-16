package au.edu.sydney.soft3202.reynholm.erp.compliance;

import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthToken;

public interface ComplianceReporting {
    public void
    sendReport(String projectName, int variance, AuthToken authToken);
}
