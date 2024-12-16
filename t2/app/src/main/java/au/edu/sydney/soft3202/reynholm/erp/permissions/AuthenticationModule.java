package au.edu.sydney.soft3202.reynholm.erp.permissions;

public interface AuthenticationModule {
    public boolean authenticate(AuthToken token);
    public AuthToken login(String username, String password);
    public void logout(AuthToken token);
}
