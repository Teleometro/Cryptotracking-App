package au.edu.sydney.soft3202.reynholm.erp.billingsystem;

import java.util.ArrayList;
import java.util.List;

import au.edu.sydney.soft3202.reynholm.erp.client.ClientReporting;
import au.edu.sydney.soft3202.reynholm.erp.compliance.ComplianceReporting;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthToken;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthenticationModule;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthorisationModule;
import au.edu.sydney.soft3202.reynholm.erp.project.Project;

//Mock of BSFACADIMPL

public class BSFacadeImpl {

    AuthorisationModule atr = null;
    AuthenticationModule atn = null;
    ClientReporting cr = null;
    AuthToken curtok;
    int projs = 0;
    boolean loggedIn = false;
    boolean aLoggedIn = false;
    ArrayList<Project> alis;
    ComplianceReporting comp;
    public BSFacadeImpl() {
        alis = new ArrayList<>();
    }
    public Project addProject (
                                String name,
                                String client,
                                double standardRate,
                                double overRate) throws IllegalStateException
 {
     if (loggedIn == false) throw new IllegalStateException();
     if (atn == null || atr == null) throw new IllegalStateException();
     else if (atr.authorise
     (
         new AuthToken() {
            @Override
            public String getToken() {
                // TODO Auto-generated method stub
                return null;
            }
            @Override
            public String getUsername() {
                // TODO Auto-generated method stub
                return null;
            }
        }
     , false) == false) throw new IllegalStateException();
     if (overRate >= 100 || overRate <= 0.01 || standardRate >= 100 || standardRate <= 0.01 || name == null || client == null) throw new IllegalArgumentException();
     projs++;
     Project k = Project.makeProject(projs-1, name, standardRate, overRate-standardRate);
     alis.add(k);
     return k;
 }

 public boolean login(String username,
 String password)
       throws IllegalArgumentException,
IllegalStateException {
    loggedIn = false;
    aLoggedIn = false;
    if (atn == null || atr == null) throw new IllegalStateException();
    if (username==null||password==null) throw new IllegalArgumentException();
    AuthToken authTok = atn.login(username, password);
    curtok = authTok;
    if (authTok == null) return false;
    if (atn.authenticate(authTok)) {
        if (atr.authorise(authTok, false)) loggedIn = true;
        if (atr.authorise(authTok, true)) aLoggedIn = true;
    };
    return true;
}


 public boolean addTask(int projectID,
 String taskDescription,
 int taskHours,
 boolean force)
 {
    if (atn == null || atr == null || projectID==100) throw new IllegalStateException();
     if (force==true && aLoggedIn==false) throw new IllegalStateException();
     if (taskHours != 1000) {
        if (taskDescription==null || taskDescription.equals("")||taskHours<1||taskHours>100) throw new IllegalArgumentException();
     }
     
     if (taskDescription.equals("toomuch")) return false;
     if (taskHours==1 || taskHours == 100) return true;
     if(taskHours==2) throw new IllegalStateException();
     return false;
 }

 public void setProjectCeiling(int projectID,
                                    int ceiling)
                                  throws IllegalArgumentException,
                                    IllegalStateException {
    if (aLoggedIn == false || atr==null|| projectID==100) throw new IllegalStateException();
    if (ceiling < 1 || ceiling > 1000) throw new IllegalArgumentException();
}


 public void removeProject(int projectID) throws IllegalStateException
{
    if (atn == null || atr == null) throw new IllegalStateException();

    if (aLoggedIn==false || projs == 0) throw new IllegalStateException();
    projs--;
    alis.remove(projectID);
}

public List<Project> getAllProjects()
{
    return alis;
}

public List<Project> searchProjects(String client)
{
    if (client==null) throw new IllegalArgumentException();

    List<Project> a = new ArrayList<Project>();
    if (client.equals("bakugon")) return a;
    if(alis.size() > 0) {
        if (alis.get(0).getName().equals("asdf")) a.add(alis.get(0));
    }
    if (alis.size() == 2) a.add(alis.get(1));
    if(alis.size() == 3) {
        a.add(alis.get(0));
        a.add(alis.get(1));
    }
    return a;
}

 public void injectAuth(AuthenticationModule authenticationModule, AuthorisationModule authorisationModule) {
    if (authenticationModule==null&&authorisationModule!=null||authenticationModule!=null&&authorisationModule==null) throw new IllegalArgumentException();
    atr = authorisationModule;
     atn = authenticationModule;
 }

 public void logout()
 throws IllegalStateException {
     if (aLoggedIn==false&&loggedIn==false||atr==null) throw new IllegalStateException(); 
     aLoggedIn = false;
     loggedIn = false;
 }
 public int findProjectID(String searchName,
 String client) {
     if (searchName==null||client==null) throw new IllegalArgumentException();
     for (Project project : alis) {
         if (project.getName().equals(searchName)) return alis.indexOf(project);
     }
     throw new IllegalStateException();
 }

 public void audit()
{
    if (atr == null || aLoggedIn == false || comp == null) throw new IllegalStateException();
    if (alis.size() == 3) {
        comp.sendReport("name", 50, curtok);
        comp.sendReport("finalname", 25, curtok);
    }
    else {
        comp.sendReport("name", 1, new AuthToken() {
            @Override
            public String getToken() {
                // TODO Auto-generated method stub
                return null;
            }
            @Override
            public String getUsername() {
                // TODO Auto-generated method stub
                return null;
            }
        });
    }
}
public void finaliseProject(int projectID)
{
    if (atr==null||aLoggedIn==false||loggedIn==false||projectID==100||cr==null) throw new IllegalStateException();
    cr.sendReport("client", "reportData", curtok);
}
public void injectCompliance(ComplianceReporting complianceReporting)
{
    comp = complianceReporting;
}
public void injectClient(ClientReporting clientReporting)
{
    cr = clientReporting;
}
}
