/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.services;

import com.bizstudio.security.services.SecurityService;
import com.bizstudio.security.services.PinToken;
import com.bizstudio.security.entities.CredentialEntity;
import com.bizstudio.security.entities.SessionEntity;
import com.bizstudio.security.entities.UserAccountEntity;
import com.bizstudio.security.entities.UserRoleEntity;
import com.bizstudio.security.repositories.SessionRepository;
import com.bizstudio.security.repositories.UserAccountRepository;
import com.bizstudio.security.repositories.UserRoleRepository;
import com.bizstudio.security.enums.UserPermissions;
import com.bizstudio.security.entities.Principal;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ObinnaAsuzu
 */
public class SecurityServiceTest {
    private static EntityManagerFactory dataEMF;
    private static EntityManagerFactory cacheEMF;
    @BeforeClass
    public static void setUpClass() {
        dataEMF = Persistence.createEntityManagerFactory("dataPU");
        cacheEMF = Persistence.createEntityManagerFactory("cachePU");
    }
    @AfterClass
    public static void tearDownClass() {
    }

    UserAccountRepository accountEntityJpaController;

    UserRoleRepository roleEntityJpaController;

    SessionRepository sessionEntityJpaController;

    Gson gson = new Gson();

    public SecurityServiceTest() {
    }

    private void initialiseSuperuser() {
        Optional<UserAccountEntity> user = accountEntityJpaController.findByUsername("superuser");
        if (!user.isPresent()) {
            UserAccountEntity superuser = new UserAccountEntity();
            superuser.setUsername("superuser");
            superuser.setPin("123456");
            List<CredentialEntity> credentials = new ArrayList<>();
            credentials.add(new CredentialEntity("password", "admin"));
            superuser.setCredentials(credentials);
            accountEntityJpaController.create(superuser);
        }
    }


    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of doGetAuthorizationInfo method, of class SecurityService.
     */
    @Test
    public void testDoGetAuthorizationInfo() {
        System.out.println("doGetAuthorizationInfo");
        accountEntityJpaController = new UserAccountRepository(dataEMF);
        roleEntityJpaController = new UserRoleRepository(dataEMF);

        initialiseSuperuser();

        SecurityService instance = new SecurityService();
        SimpleAuthorizationInfo expResult = new SimpleAuthorizationInfo();

        Optional<UserAccountEntity> user = accountEntityJpaController.findByUsername("superuser");
        Set<Permission> permisions = new TreeSet<>();
        Set<String> roles = new TreeSet<>();
        if (user.isPresent()
                && user.get().getRoles() != null
                && !user.get().getRoles().isEmpty()) {
            for (UserRoleEntity role : user.get().getRoles()) {
                if (role != null) {
                    roles.add(role.getName());
                    if (!role.getPermissions().isEmpty()) {
                        role.getPermissions().forEach(permision -> {
                            permisions.add(permision);
                        });
                    }
                }
            }
        }

        expResult.setRoles(roles);
        expResult.setObjectPermissions(permisions);
        SimplePrincipalCollection principals = new SimplePrincipalCollection("superuser", instance.getName());

        SimpleAuthorizationInfo result = (SimpleAuthorizationInfo) instance.doGetAuthorizationInfo(principals);
        assertEquals(expResult.getStringPermissions(), result.getStringPermissions());
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of doGetAuthenticationInfo method, of class SecurityService.
     */
    @Test
    public void testDoGetAuthenticationInfo() {
        System.out.println("doGetAuthenticationInfo");
        AuthenticationToken token = new UsernamePasswordToken("supe1ruser", "password123");
        SecurityService instance = new SecurityService();
        instance.initialiseSuperuser();

        accountEntityJpaController = new UserAccountRepository(dataEMF);

        Optional<UserAccountEntity> user = Optional.empty();
        if (token instanceof PinToken) {
            PinToken pToken = (PinToken) token;
            if (pToken.getPin() == null || pToken.getPin().length < 1) {
                try {
                    instance.doGetAuthenticationInfo(token);
                    fail("Incorrect credentials exception should be thrown");
                    return;
                } catch (AuthenticationException e) {
                    if (!(e instanceof IncorrectCredentialsException)) {
                        fail("Incorrect credentials exception should be thrown");
                    }
                    return;
                }

            } else {
                user = accountEntityJpaController.findByPin(String.copyValueOf(pToken.getPin()));
            }
        } else if (token instanceof UsernamePasswordToken) {
            UsernamePasswordToken uToken = (UsernamePasswordToken) token;
            if (uToken.getUsername() == null || uToken.getUsername().isEmpty()) {
                try {
                    instance.doGetAuthenticationInfo(token);
                    fail("Incorrect credentials exception should be thrown");
                    return;
                } catch (AuthenticationException e) {
                    if (!(e instanceof IncorrectCredentialsException)) {
                        fail("Incorrect credentials exception should be thrown");
                    }
                    return;
                }
            } else {
                user = accountEntityJpaController.findByUsername(uToken.getUsername());
            }
        }

        if (!user.isPresent()) {
            try {
                instance.doGetAuthenticationInfo(token);
                fail("Incorrect credentials exception should be thrown");
                return;
            } catch (AuthenticationException e) {
                if (!(e instanceof IncorrectCredentialsException)) {
                    fail("Incorrect credentials exception should be thrown");
                }
                return;
            }
        }

        SimpleAuthenticationInfo expResult = new SimpleAuthenticationInfo();
        Map<String, String> credentials = new TreeMap<>();
        user.get().getCredentials().forEach(c -> credentials.put(c.getName(), c.getValue()));
        expResult.setCredentials(credentials);

        List<Principal> princpals = new ArrayList<>();
        user.get().getPrincipals().forEach((p) -> princpals.add(p));
        PrincipalCollection principalCollection = new SimplePrincipalCollection(princpals, instance.getName());
        expResult.setPrincipals(principalCollection);

        AuthenticationInfo result = instance.doGetAuthenticationInfo(token);
        assertEquals(expResult, result);
    }

    /**
     * Test of resolvePermissionsInRole method, of class SecurityService.
     */
    @Test
    public void testResolvePermissionsInRole() {
        System.out.println("resolvePermissionsInRole");
        roleEntityJpaController = new UserRoleRepository(dataEMF);
        String roleString = "test.role";
        SecurityService instance = new SecurityService();
        UserRoleEntity role = roleEntityJpaController.findFirstByName(roleString);
        List<Permission> expResult = new ArrayList<>();
        if (role != null && !role.getPermissions().isEmpty()) {
            role.getPermissions().forEach(permision -> {
                expResult.add(permision);
            });
        }
        Collection<Permission> result = instance.resolvePermissionsInRole(roleString);
        assertEquals(expResult, result);

        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of resolvePermission method, of class SecurityService.
     */
    @Test
    public void testResolvePermission() {
        System.out.println("resolvePermission");
        String permissionString = "test.permission";
        SecurityService instance = new SecurityService();
        Permission expResult = Arrays.asList(UserPermissions.values())
                .stream()
                .filter(p -> p.getValue().equalsIgnoreCase(permissionString))
                .findFirst()
                .orElse(null);
        Permission result = instance.resolvePermission(permissionString);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of create method, of class SecurityService.
     */
    @Test
    public void testCreate() {
        System.out.println("create");

        SimpleSession session = new SimpleSession();
        session.setAttribute("user", "superuser");
        sessionEntityJpaController = new SessionRepository(cacheEMF);
        SessionEntity entity = SessionEntity.create(session);
        sessionEntityJpaController.create(entity);

        boolean expResult = entity.getId() > 0;

        SecurityService instance = new SecurityService();

        boolean result = ((Long) instance.create(session)) > 0;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of readSession method, of class SecurityService.
     */
    @Test
    public void testReadSession() {
        System.out.println("readSession");
        Serializable sessionId = new Long(1);
        sessionEntityJpaController = new SessionRepository(cacheEMF);
        SessionEntity findById = sessionEntityJpaController.findById((Long) sessionId);
        SecurityService instance = new SecurityService();
        if (findById == null) {
            try {
                instance.readSession(sessionId);
                fail("Exception is Expected");
            } catch (UnknownSessionException e) {
            }
        } else {
            SimpleSession expResult = findById.toSession();
            
            SimpleSession result = instance.readSession(sessionId);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of update method, of class SecurityService.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        sessionEntityJpaController = new SessionRepository(cacheEMF);
        SessionEntity findById = sessionEntityJpaController.findAll().stream().findFirst().orElse(null);
        if (findById == null) {
            fail("No Test Data available");
            return;
        }

        SimpleSession session = findById.toSession();
        session.setHost("test.host");
        findById.updateSession((SimpleSession) session);

        Serializable expResult = findById.toSession().getId();
        SecurityService instance = new SecurityService();
        instance.update(session);
        SimpleSession readSession = instance.readSession(findById.getId());

        if (readSession == null) {
            fail("Updated Session Missing");
            return;
        }

        if (!readSession.getHost().equals("test.host")) {
            fail("Updated Session invalid");
            return;
        }

        Serializable result = readSession.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class SecurityService.
     */
    @Test
    public void testDelete() {
        System.out.println("update");
        sessionEntityJpaController = new SessionRepository(cacheEMF);
        SessionEntity findById = sessionEntityJpaController.findAll().stream().findFirst().orElse(null);
        if (findById == null) {
            fail("No Test Data available");
            return;
        }

        SimpleSession session = findById.toSession();

        SecurityService instance = new SecurityService();
        instance.delete(session);
        try {
            SimpleSession readSession = instance.readSession(findById.getId());
            if (readSession != null) {
                fail("found deleted session");
            }
        } catch (UnknownSessionException e) {

        }

    }

    /**
     * Test of getActiveSessions method, of class SecurityService.
     */
    @Test
    public void testGetActiveSessions() {
        System.out.println("getActiveSessions");
        sessionEntityJpaController = new SessionRepository(cacheEMF);
        SecurityService instance = new SecurityService();
        Collection<Session> expResult = sessionEntityJpaController.findAll().stream()
                .map(entity -> {
                    return (SimpleSession) entity.toSession();
                })
                .filter(session -> session != null && !session.isExpired())
                .collect(Collectors.toList());
        Collection<Session> result = instance.getActiveSessions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of assertCredentialsMatch method, of class SecurityService.
     */
    @Test
    public void testAssertCredentialsMatch() {
        System.out.println("assertCredentialsMatch");
        SecurityService instance = new SecurityService();
        UsernamePasswordToken token = new UsernamePasswordToken("superuser", "password123");

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        Map<String, String> credentials = new HashMap<>();
        credentials.put("password", "password1");
        credentials.put("pin", "123456");
        info.setCredentials(credentials);

        if (token.getUsername() == null || token.getUsername().isEmpty()) {
            try {
                instance.assertCredentialsMatch(token, info);
                fail("The test case is a prototype.");
            } catch (AuthenticationException e) {
                if(e instanceof IncorrectCredentialsException){
                    
                }else{
                    fail("The test case is a prototype.");
                }
            }
            return;
        }

        if (!credentials.get("password").equals(String.copyValueOf(token.getPassword()))) {
            try {
                instance.assertCredentialsMatch(token, info);
                fail("The test case is a prototype.");
            } catch (AuthenticationException e) {
                if(e instanceof IncorrectCredentialsException){
                    
                }else{
                    fail("The test case is a prototype.");
                }
            }
        }
    }

}
