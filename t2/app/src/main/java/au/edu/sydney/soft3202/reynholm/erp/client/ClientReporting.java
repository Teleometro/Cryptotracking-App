package au.edu.sydney.soft3202.reynholm.erp.client;

import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthToken;

public interface ClientReporting {
    public void
    sendReport(String clientName, String reportData, AuthToken authToken);
}
