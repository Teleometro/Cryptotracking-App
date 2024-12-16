package au.edu.sydney.soft3202.reynholm.erp.permissions;

public interface AuthorisationModule {

    public boolean authorise(AuthToken token, boolean secure) throws IllegalArgumentException;

}
