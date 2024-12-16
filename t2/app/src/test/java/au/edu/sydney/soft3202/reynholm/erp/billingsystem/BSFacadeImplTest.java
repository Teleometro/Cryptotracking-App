package au.edu.sydney.soft3202.reynholm.erp.billingsystem;
import au.edu.sydney.soft3202.reynholm.erp.client.ClientReporting;
import au.edu.sydney.soft3202.reynholm.erp.compliance.ComplianceReporting;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthToken;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthenticationModule;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthorisationModule;
import au.edu.sydney.soft3202.reynholm.erp.project.Project;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.internal.matchers.Not;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalMatchers.*;


class BSFacadeImplTest {
    static AuthenticationModule atnMock;
    static AuthorisationModule atrMock;
    static AuthorisationModule atrMock2;
    static AuthToken OneTokenToRuleThemAll;

    @BeforeAll
    public static void stuff() {
        atnMock = mock(AuthenticationModule.class);
        atrMock = mock(AuthorisationModule.class);
        atrMock2 = mock(AuthorisationModule.class);
        OneTokenToRuleThemAll = mock(AuthToken.class);
        when(atnMock.authenticate(any(AuthToken.class))).thenReturn(true);
        when(atnMock.login(anyString(), anyString())).thenReturn(OneTokenToRuleThemAll);
        when(atrMock.authorise(any(AuthToken.class), eq(false))).thenReturn(true);
        when(atrMock.authorise(any(AuthToken.class), eq(true))).thenReturn(false);
        when(atrMock2.authorise(any(AuthToken.class), eq(false))).thenReturn(true);
        when(atrMock2.authorise(any(AuthToken.class), eq(true))).thenReturn(true);
    }
    @Test
    public void addProjectBasicTest() {
        Project yourProjectMock = mock(Project.class);



        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            assertThat(p, equalTo(yourProjectMock));
        }
    }
    @Test
    public void addProjectNoAuthTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");  
            fixture.injectAuth(null, null);
            assertThrows(IllegalStateException.class, () -> {fixture.addProject("name", "client", 1, 50);});
        }
    }

    @Test
    public void InvalidAddProjectTest() {
        Project yourProjectMock = mock(Project.class);
        

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            assertThrows(IllegalStateException.class, () -> {
                fixture.addProject("name", "client", 1, 50);
            });
        }
    }
    @Test 
    public void strangerDanger() {
        Project yourProjectMock = mock(Project.class);
        AuthenticationModule authMock = mock(AuthenticationModule.class);
        AuthorisationModule aurMock = mock(AuthorisationModule.class);

        
        when(aurMock.authorise(any(AuthToken.class), eq(false))).thenReturn(false);
        when(aurMock.authorise(any(AuthToken.class), eq(true))).thenReturn(false);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, aurMock);
            fixture.login("username", "password");
            assertThrows(IllegalStateException.class, () -> {
                fixture.addProject("name", "client", 1, 50);
            });
        }
    }
    @Test
    public void overRateOverFlowAddProjectTest() {
        Project yourProjectMock = mock(Project.class);


        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            assertThrows(IllegalArgumentException.class, () -> {
                fixture.addProject("name", "client", 1, 100);
            });
        }
    }
    @Test
    public void overRateUnderFlowAddProjectTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            assertThrows(IllegalArgumentException.class, () -> {
                fixture.addProject("name", "client", 1, 0.01);
            });
        }
    }
    @Test
    public void standardRateOverFlowAddProjectTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            assertThrows(IllegalArgumentException.class, () -> {
                fixture.addProject("name", "client", 100, 99);
            });
        }
    }
    
    @Test
    public void standardRateUnderFlowAddProjectTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            assertThrows(IllegalArgumentException.class, () -> {
                fixture.addProject("name", "client", 0.01, 10);
            });
        }
    }
    @Test
    public void nullNameAddProjectTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            assertThrows(IllegalArgumentException.class, () -> {
                fixture.addProject(null, "client", 1, 50);
            });
        }
    }

    @Test
    public void nullClientAddProjectTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            assertThrows(IllegalArgumentException.class, () -> {
                fixture.addProject("null", null, 1, 50);
            });
        }
    }

    @Test
    public void nullRemoveProjectTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            assertThrows(IllegalStateException.class, () -> {
                fixture.removeProject(0);
            });
        }
    }
    @Test
    public void notAuthorizedRemoveProjectTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            fixture.addProject("name", "client", 1, 50);
            assertThrows(IllegalStateException.class, () -> {
                fixture.removeProject(0);
            });
        }
    }

    @Test
    public void RemoveProjectBasicTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            fixture.addProject("name", "client", 1, 50);
            fixture.removeProject(0);
            assertEquals(fixture.getAllProjects().size(), 0);
        }
    }
    @Test
    public void RemoveProjectNoAuthTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            fixture.addProject("name", "client", 1, 50);
            fixture.injectAuth(null, null);
            
            assertThrows(IllegalStateException.class, () -> {fixture.removeProject(0);});
        }
    }
    @Test
    public void RemoveProjectNotClearTest() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("asdf");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("asdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            fixture.addProject("name", "client", 1, 50);
            fixture.addProject("asdf", "asdf", 1, 50);
            fixture.removeProject(fixture.findProjectID("asdf", "asdf"));
            assertEquals(fixture.getAllProjects().get(0), yourProjectMock);
            assertEquals(fixture.getAllProjects().size(), 1);
        }
    }
    @Test
    public void RemoveProjectNotClearTest2() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("asdf");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("asdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            fixture.addProject("name", "client", 1, 50);
            fixture.addProject("asdf", "asdf", 1, 50);
            fixture.removeProject(0);
            assertEquals(fixture.getAllProjects().get(0), yourProjectMock2);
            assertEquals(fixture.getAllProjects().size(), 1);
        }
    }

    @Test
    public void addTaskBasicUnforcedTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            assertEquals(true, fixture.addTask(0, "a", 1, false));
        }
    }
    @Test
    public void addTaskNoAuthTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.injectAuth(null, null);
            assertThrows(IllegalStateException.class, ()->{fixture.addTask(0, "a", 1, false);});
        }
    }
    @Test
    public void addTaskBasicForcedTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            assertEquals(true, fixture.addTask(0, "a", 1, true));
        }
    }
    @Test
    public void UnauthorizedForcedTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            assertThrows(IllegalStateException.class, () -> {
                fixture.addTask(0, "a", 2, true);
            });
        }
    }

    @Test
    public void addTooMuchUnforcedTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.addTask(0, "a", 50, false);
            assertEquals(false, fixture.addTask(0, "a", 51, false));
        }
    }
    @Test
    public void addTooMuchUnforcedBoogalooTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.addTask(0, "a", 50, false);
            assertEquals(false, fixture.addTask(0, "a", 51, false));
        }
    }
    @Test
    public void addTaskJustEnoughTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            assertEquals(true, fixture.addTask(0, "a", 100, false));
        }
    }
    @Test
    public void addTaskNoProjectTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            assertThrows(IllegalStateException.class, () -> {
                fixture.addTask(100, "a", 1, false);
            });
        }
    }
    @Test
    public void addTaskHourOverflowTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            assertThrows(IllegalArgumentException.class, () -> {
                fixture.addTask(0, "asd", 101, false);
            });
        }
    } 
    @Test
    public void addTaskHourUnderflowTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            assertThrows(IllegalArgumentException.class, () -> {
                fixture.addTask(0, "asd", 0, false);
            });
        }
    } 
    @Test
    public void addTaskHourNullStringTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            assertThrows(IllegalArgumentException.class, () -> {
                fixture.addTask(0, null, 1, false);
            });
        }
    } 
    @Test
    public void addTaskHourEmptyStringTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            assertThrows(IllegalArgumentException.class, () -> {
                fixture.addTask(0, "", 1, false);
            });
        }
    } 
    @Test
    public void addTaskUseTheForceTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.addTask(0, "asd", 100, true);
            assertEquals(true, fixture.addTask(0, "a", 1, true));
        }
    }
    @Test
    public void addTaskDifferentTestDifferentZestTest() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("asdf");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("asdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.addProject("asdf", "client", 1, 50);
            fixture.addTask(0, "asd", 100, false);
            assertEquals(true, fixture.addTask(1, "a", 1, false));
        }
    }
    @Test
    public void addTaskDontUseTheForceTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.addTask(0, "asd", 100, true);
            fixture.addTask(0, "a", 1, true);
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("usename", "pasword");
            assertEquals(false, fixture.addTask(0, "a", 3, false));
        }
    }
    @Test
    public void setProjCeilingBasicTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.setProjectCeiling(0, 1);
            assertEquals(false, fixture.addTask(0, "toomuch", 2, false));
        }
    }
    @Test
    public void setProjCeilingHighTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.setProjectCeiling(0, 1000);
            fixture.addTask(0, "asdf", 100, false);
            fixture.addTask(0, "asdf", 100, false);
            fixture.addTask(0, "asdf", 100, false);
            fixture.addTask(0, "asdf", 100, false);
            fixture.addTask(0, "asdf", 100, false);
            fixture.addTask(0, "asdf", 100, false);
            fixture.addTask(0, "asdf", 100, false);
            fixture.addTask(0, "asdf", 100, false);
            fixture.addTask(0, "asdf", 100, false);
            assertEquals(true, fixture.addTask(0, "asdf", 100, false));
        }
    }
    @Test
    public void setProjCeilingRangeTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            
            assertThrows(IllegalArgumentException.class, () -> {fixture.setProjectCeiling(0, 1001);});
            assertThrows(IllegalArgumentException.class, () -> {fixture.setProjectCeiling(0, 0);});
        }
    }
    @Test
    public void setProjCeilingInvalidProjTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            
            assertThrows(IllegalStateException.class, () -> {fixture.setProjectCeiling(100, 10);});
        }
    }
    @Test
    public void setProjCeilingNoPermsModuleProjTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.injectAuth(null, null);
            assertThrows(IllegalStateException.class, () -> {fixture.setProjectCeiling(0, 10);});
        }
    }
    @Test
    public void setProjCeilingBadUserProjTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            
            assertThrows(IllegalStateException.class, () -> {fixture.setProjectCeiling(0, 10);});
        }
    }
    @Test
    public void setProjCeilingNoUserProjTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.logout();
            assertThrows(IllegalStateException.class, () -> {fixture.setProjectCeiling(0, 10);});
        }
    }

    @Test
    public void FindNull1Test() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("asdf");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("asdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            fixture.addProject("name", "client", 1, 50);
            fixture.addProject("asdf", "asdf", 1, 50);
            fixture.removeProject(fixture.findProjectID("asdf", "asdf"));
            assertThrows(IllegalArgumentException.class, () -> {fixture.findProjectID(null, "client");});
        }
    }
    @Test
    public void FindNull2Test() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("asdf");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("asdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            fixture.addProject("name", "client", 1, 50);
            fixture.addProject("asdf", "asdf", 1, 50);
            fixture.removeProject(fixture.findProjectID("asdf", "asdf"));
            assertThrows(IllegalArgumentException.class, () -> {fixture.findProjectID("null", null);});
        }
    }
    @Test
    public void FindNoProjectsTest() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("asdf");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("asdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            fixture.addProject("name", "client", 1, 50);
            fixture.addProject("asdf", "asdf", 1, 50);
            assertThrows(IllegalStateException.class, () -> {fixture.findProjectID("null", "casd");});
        }
    }
    @Test
    public void searchProjTest() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("basdf");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("basdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            fixture.addProject("name", "client", 1, 50);
            fixture.addProject("basdf", "asdf", 1, 50);
            assertEquals("basdf", fixture.searchProjects("asdf").get(0).getName());
            assertEquals(1, fixture.searchProjects("asdf").size());
        }
    }
    @Test
    public void searchProjNoNullTest() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("basdf");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("basdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            assertNotNull(fixture.searchProjects("bakugon"));
            assertEquals(0, fixture.searchProjects("bakugon").size());
            fixture.addProject("name", "client", 1, 50);
            fixture.addProject("basdf", "asdf", 1, 50);
            assertNotNull(fixture.searchProjects("bakugon"));
            assertEquals(0, fixture.searchProjects("bakugon").size());
        }
    }
    @Test
    public void searchProjMultiTest() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        Project yourProjectMock3 = mock(Project.class);

        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("basdf");
        when(yourProjectMock2.getName()).thenReturn("gas");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);
        when(yourProjectMock3.getId()).thenReturn(2);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("basdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);
            mock.when(() -> Project.makeProject(anyInt(), eq("gas"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock3);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");
            fixture.addProject("name", "coolguy", 1, 50);
            fixture.addProject("basdf", "coolguy", 1, 50);
            fixture.addProject("gas", "wierdo", 1, 50);
            assertTrue(fixture.searchProjects("coolguy").contains(yourProjectMock));
            assertTrue(fixture.searchProjects("coolguy").contains(yourProjectMock2));
            assertEquals(2, fixture.searchProjects("coolguy").size());
        }
    }
    @Test
    public void searchProjForNullTest() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("basdf");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("basdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            fixture.addProject("name", "client", 1, 50);
            fixture.addProject("basdf", "asdf", 1, 50);
            assertThrows(IllegalArgumentException.class, ()->{fixture.searchProjects(null);});
        }
    }
    @Test
    public void getAllProjNoNullTest() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("basdf");
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("basdf"), anyDouble(), anyDouble()))
                     .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock2);
            fixture.login("username", "password");
            assertNotNull(fixture.getAllProjects());
            assertEquals(0, fixture.getAllProjects().size());
            fixture.addProject("name", "client", 1, 50);
            fixture.addProject("basdf", "asdf", 1, 50);
            assertNotNull(fixture.getAllProjects());
            assertEquals(2, fixture.getAllProjects().size());
        }
    }
    @Test
    public void auditBadUserTest() {
        Project yourProjectMock = mock(Project.class);
        ComplianceReporting comp = mock(ComplianceReporting.class);


        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.injectCompliance(comp);
            assertThrows(IllegalStateException.class, ()->{fixture.audit();});

        }
    }
    @Test
    public void auditNoLoginTest() {
        Project yourProjectMock = mock(Project.class);
        ComplianceReporting comp = mock(ComplianceReporting.class);


        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.logout();
            fixture.injectCompliance(comp);
            assertThrows(IllegalStateException.class, ()->{fixture.audit();});

        }
    }

    @Test
    public void auditNoAuthTest() {
        Project yourProjectMock = mock(Project.class);
        ComplianceReporting comp = mock(ComplianceReporting.class);


        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.injectCompliance(comp);
            fixture.injectAuth(null, null);
            assertThrows(IllegalStateException.class, ()->{fixture.audit();});

        }
    }
    @Test
    public void auditNoComplianceTest() {
        Project yourProjectMock = mock(Project.class);
        ComplianceReporting comp = mock(ComplianceReporting.class);


        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.injectCompliance(comp);
            fixture.injectCompliance(null);
            assertThrows(IllegalStateException.class, ()->{fixture.audit();});

        }
    }
    @Test
    public void auditBasicTest() {
        Project yourProjectMock = mock(Project.class);
        Project yourProjectMock2 = mock(Project.class);
        Project yourProjectMock3 = mock(Project.class);
        when(yourProjectMock.getName()).thenReturn("name");
        when(yourProjectMock2.getName()).thenReturn("nothername");
        when(yourProjectMock3.getName()).thenReturn("finalname");

        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock2.getId()).thenReturn(1);
        when(yourProjectMock3.getId()).thenReturn(2);

        when(yourProjectMock.getOverDifference()).thenReturn((double) 50);
        when(yourProjectMock2.getOverDifference()).thenReturn(0.0);
        when(yourProjectMock3.getOverDifference()).thenReturn(25.0);
        ComplianceReporting comp = mock(ComplianceReporting.class);


        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("nothername"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock2);
            mock.when(() -> Project.makeProject(anyInt(), eq("finalname"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock3);
            BSFacadeImpl fixture = new BSFacadeImpl();
           
            AuthToken a1 = new AuthToken() {

                @Override
                public String getToken() {
                    // TODO Auto-generated method stub
                    return "a";
                }

                @Override
                public String getUsername() {
                    // TODO Auto-generated method stub
                    return "nme";
                }
                
            };
            AuthToken a2 = new AuthToken() {

                @Override
                public String getToken() {
                    return "b";
                }

                @Override
                public String getUsername() {
                    return "nothame";
                }
                
            };

            AuthenticationModule nauth = mock(AuthenticationModule.class);
            when(nauth.login(eq("vlad"), eq("notavampire"))).thenReturn(a1);
            when(nauth.login(eq("frank"), eq("veryrealperson"))).thenReturn(a2);
            when(nauth.authenticate(any(AuthToken.class))).thenReturn(true);
            
            fixture.injectAuth(nauth, atrMock2);
            fixture.login("vlad", "notavampire");  
            
            fixture.addProject("name", "client", 1, 50);
            fixture.addProject("nothername", "clienta", 3, 54);
            fixture.addProject("finalname", "cliente", 2, 60);

            fixture.injectCompliance(comp);

            

            fixture.addTask(fixture.findProjectID("name", "client"), "canton", 100, false);
            fixture.addTask(fixture.findProjectID("nothername", "clienta"), "namez", 100, false);
            fixture.addTask(fixture.findProjectID("finalname", "cliente"), "namel", 99, false);
            fixture.addTask(fixture.findProjectID("name", "client"), "namez", 50, true);
            fixture.addTask(fixture.findProjectID("finalname", "cliente"), "namel", 26, true);
            fixture.audit();

            verify(comp).sendReport(eq("name"), eq(50), eq(a1));
            verify(comp).sendReport(eq("finalname"), eq(25), eq(a1));
            fixture.logout();
            fixture.login("frank", "veryrealperson");
            fixture.audit();

            verify(comp).sendReport(eq("name"), eq(50), eq(a2));
            verify(comp).sendReport(eq("finalname"), eq(25), eq(a2));
        }
    }
    @Test
    public void finalizeNoPermsTest() {
        Project yourProjectMock = mock(Project.class);
        ClientReporting cr = mock(ClientReporting.class);
        when(yourProjectMock.getId()).thenReturn(0);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();            fixture.injectAuth(atnMock, atrMock2);
            fixture.injectClient(cr);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.injectAuth(null, null);
            assertThrows(IllegalStateException.class, ()->{fixture.finaliseProject(0);});
        }
    }
    @Test
    public void finalizeNoClientTest() {
        Project yourProjectMock = mock(Project.class);
        ClientReporting cr = mock(ClientReporting.class);
        when(yourProjectMock.getId()).thenReturn(0);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();            fixture.injectAuth(atnMock, atrMock2);
            fixture.injectClient(cr);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.injectClient(null);
            assertThrows(IllegalStateException.class, ()->{fixture.finaliseProject(0);});
        }
    }
    @Test
    public void finalizeNoProjectTest() {
        Project yourProjectMock = mock(Project.class);
        ClientReporting cr = mock(ClientReporting.class);
        when(yourProjectMock.getId()).thenReturn(0);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();            
            fixture.injectAuth(atnMock, atrMock2);
            fixture.injectClient(cr);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            assertThrows(IllegalStateException.class, ()->{fixture.finaliseProject(100);});
        }
    }
    @Test
    public void finalizeYouDontHaveTheRightTest() {
        Project yourProjectMock = mock(Project.class);
        ClientReporting cr = mock(ClientReporting.class);
        when(yourProjectMock.getId()).thenReturn(0);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();            
            fixture.injectAuth(atnMock, atrMock);
            fixture.injectClient(cr);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            assertThrows(IllegalStateException.class, ()->{fixture.finaliseProject(0);});
        }
    }
    @Test
    public void finalizeYouDontHaveTheRightOYouDontHaveTheRightTest() {
        Project yourProjectMock = mock(Project.class);
        ClientReporting cr = mock(ClientReporting.class);
        AuthorisationModule auteurFaux = mock(AuthorisationModule.class);
        when(auteurFaux.authorise(any(AuthToken.class), eq(false))).thenReturn(false);
        when(auteurFaux.authorise(any(AuthToken.class), eq(true))).thenReturn(false);
        when(yourProjectMock.getId()).thenReturn(0);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();            
            fixture.injectAuth(atnMock, atrMock);
            fixture.injectClient(cr);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.injectAuth(atnMock, auteurFaux);
            assertThrows(IllegalStateException.class, ()->{fixture.finaliseProject(0);});
        }
    }
    @Test
    public void finalizeYouHaveNoPowerHereTest() {
        Project yourProjectMock = mock(Project.class);
        ClientReporting cr = mock(ClientReporting.class);
        AuthorisationModule auteurFaux = mock(AuthorisationModule.class);
        when(auteurFaux.authorise(any(AuthToken.class), eq(false))).thenReturn(false);
        when(auteurFaux.authorise(any(AuthToken.class), eq(true))).thenReturn(true);
        when(yourProjectMock.getId()).thenReturn(0);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();            
            fixture.injectAuth(atnMock, atrMock);
            fixture.injectClient(cr);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.injectAuth(atnMock, auteurFaux);
            assertThrows(IllegalStateException.class, ()->{fixture.finaliseProject(0);});
        }
    }
    @Test
    public void finalizeBasicTest() {
        Project yourProjectMock = mock(Project.class);
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock.getName()).thenReturn("name");
        ClientReporting cr = mock(ClientReporting.class);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();            
            fixture.injectAuth(atnMock, atrMock2);
            fixture.injectClient(cr);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.finaliseProject(0);
            verify(cr, times(1)).sendReport(eq("client"), notNull(), eq(OneTokenToRuleThemAll));
        }
    }
    @Test
    public void finalizeNoImSpidermanTest() {
        Project yourProjectMock = mock(Project.class);
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock.getName()).thenReturn("name");
        Project yourProjectMock2 = mock(Project.class);
        when(yourProjectMock2.getId()).thenReturn(1);
        when(yourProjectMock2.getName()).thenReturn("namer");
        ClientReporting cr = mock(ClientReporting.class);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), eq("name"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);
            mock.when(() -> Project.makeProject(anyInt(), eq("namer"), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock2);

            BSFacadeImpl fixture = new BSFacadeImpl();            
            fixture.injectAuth(atnMock, atrMock2);
            fixture.injectClient(cr);
            fixture.login("username", "password");  
            fixture.addProject("name", "cliencio", 1, 26);
            fixture.addProject("namer", "client", 1, 50);
            fixture.finaliseProject(1);
            verify(cr, times(1)).sendReport(eq("client"), notNull(), eq(OneTokenToRuleThemAll));
        }
    }
    @Test
    public void finalizeYouMadeThisIMadeThisTest() {
        Project yourProjectMock = mock(Project.class);
        when(yourProjectMock.getId()).thenReturn(0);
        when(yourProjectMock.getName()).thenReturn("name");
        ClientReporting cr = mock(ClientReporting.class);

        
        AuthToken a1 = new AuthToken() {

            @Override
            public String getToken() {
                // TODO Auto-generated method stub
                return "a";
            }

            @Override
            public String getUsername() {
                // TODO Auto-generated method stub
                return "nme";
            }
            
        };

        AuthenticationModule nauth = mock(AuthenticationModule.class);
        when(nauth.login(eq("vlad"), eq("notavampire"))).thenReturn(a1);
        when(nauth.authenticate(any(AuthToken.class))).thenReturn(true);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();            
            fixture.injectAuth(atnMock, atrMock2);
            fixture.injectClient(cr);
            fixture.login("username", "password");  
            Project p = fixture.addProject("name", "client", 1, 50);
            fixture.logout();
            fixture.injectAuth(nauth, atrMock2);
            fixture.login("vlad", "notavampire");
            fixture.finaliseProject(0);
            verify(cr, times(1)).sendReport(eq("client"), notNull(), eq(a1));
        }
    }

    @Test
    public void thereIsNoAuthorityTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            assertThrows(IllegalArgumentException.class, ()->{fixture.injectAuth(null, atrMock);}); 
        }
    }
    @Test
    public void thereIsNoAuthenticityTest() {
        Project yourProjectMock = mock(Project.class);

        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl();
            assertThrows(IllegalArgumentException.class, ()->{fixture.injectAuth(atnMock, null);}); 
        }
    }

    @Test
    public void loginFailTest() {
        Project yourProjectMock = mock(Project.class);



        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl(); 
            assertThrows(IllegalStateException.class, ()->{fixture.login("username", "password");});
        }
    }
    @Test
    public void loginNoPassTest() {
        Project yourProjectMock = mock(Project.class);



        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl(); 
            fixture.injectAuth(atnMock, atrMock);
            assertThrows(IllegalArgumentException.class, ()->{fixture.login("username", null);});
        }
    }
    @Test
    public void loginIHaveNoNameTest() {
        Project yourProjectMock = mock(Project.class);



        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl(); 
            fixture.injectAuth(atnMock, atrMock);
            assertThrows(IllegalArgumentException.class, ()->{fixture.login(null, "null");});
        }
    }
    @Test
    public void loginLogoutTest() {
        Project yourProjectMock = mock(Project.class);

        AuthToken a1 = new AuthToken() {

            @Override
            public String getToken() {
                // TODO Auto-generated method stub
                return "a";
            }

            @Override
            public String getUsername() {
                // TODO Auto-generated method stub
                return "nme";
            }
            
        };

        AuthenticationModule nauth = mock(AuthenticationModule.class);
        when(nauth.login(eq("vlad"), eq("notavampire"))).thenReturn(null);
        when(nauth.authenticate(eq(a1))).thenReturn(false);

        AuthorisationModule atra = mock(AuthorisationModule.class);
        when(atra.authorise(eq(a1), eq(false))).thenReturn(false);
        when(atra.authorise(eq(a1), eq(true))).thenReturn(false);
        
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(yourProjectMock);

            BSFacadeImpl fixture = new BSFacadeImpl(); 
            fixture.injectAuth(atnMock, atrMock);
            assertEquals(true, fixture.login("username", "password"));
            fixture.injectAuth(nauth, atra);
            assertEquals(false, fixture.login("vlad", "notavampire"));
            assertThrows(IllegalStateException.class, ()->{fixture.logout();});
        }
    }
    @Test
    public void IllegalLogoutTest() {

            BSFacadeImpl fixture = new BSFacadeImpl();
            fixture.injectAuth(atnMock, atrMock);
            fixture.login("username", "password");    
            fixture.injectAuth(null, null);      
            assertThrows(IllegalStateException.class, ()->{fixture.logout();});
    }
}