/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.service;

import com.bizstudio.application.enums.NavigationRoute;
import com.bizstudio.application.managers.NavigationManger;
import com.bizstudio.security.entities.CredentialEntity;
import com.bizstudio.security.entities.SessionEntity;
import com.bizstudio.security.entities.UserAccountEntity;
import com.bizstudio.security.entities.UserRoleEntity;
import com.bizstudio.security.entities.controllers.SessionEntityJpaController;
import com.bizstudio.security.entities.controllers.UserAccountEntityJpaController;
import com.bizstudio.security.entities.controllers.UserRoleEntityJpaController;
import com.bizstudio.security.entities.controllers.exceptions.NonexistentEntityException;
import com.bizstudio.security.entities.enums.UserPermissions;
import com.bizstudio.security.entities.interfaces.Principal;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 *
 * @author ObinnaAsuzu
 */
public class SecurityService extends AuthorizingRealm implements
        RolePermissionResolver,
        PermissionResolver,
        SessionDAO,
        SessionListener {

    UserAccountEntityJpaController accountEntityJpaController;

    UserRoleEntityJpaController roleEntityJpaController;

    SessionEntityJpaController sessionEntityJpaController;

    Gson gson = new Gson();

    public SecurityService() {
        EntityManagerFactory dataEMF = PersistenceManger.getInstance().getDataEMF();
        EntityManagerFactory cacheEMF = PersistenceManger.getInstance().getCacheEMF();

        accountEntityJpaController = new UserAccountEntityJpaController(dataEMF);
        roleEntityJpaController = new UserRoleEntityJpaController(dataEMF);

        sessionEntityJpaController = new SessionEntityJpaController(cacheEMF);
        initialiseSuperuser();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        List<UserAccountEntity> users = accountEntityJpaController.findByUsername(username);

        Set<Permission> permisions = new TreeSet<>();
        Set<String> roles = new TreeSet<>();
        if (users != null
                && !users.isEmpty()
                && users.get(0) != null
                && users.get(0).getRoles() != null
                && !users.get(0).getRoles().isEmpty()) {
            for (UserRoleEntity role : users.get(0).getRoles()) {
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

        authorizationInfo.setRoles(roles);
        authorizationInfo.setObjectPermissions(permisions);
        return authorizationInfo;
    }

    public final void initialiseSuperuser() {
        List<UserAccountEntity> users = accountEntityJpaController.findByUsername("superuser");
        if (users == null || users.isEmpty()) {
            UserAccountEntity superuser = new UserAccountEntity();
            superuser.setUsername("superuser");
            superuser.setPin("123456");
            List<CredentialEntity> credentials = new ArrayList<>();
            credentials.add(new CredentialEntity("password", "password123"));
            superuser.setCredentials(credentials);
            accountEntityJpaController.create(superuser);
        } else {
            System.out.println("user -> " + users);
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        

        return null;

    }

    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        UserRoleEntity role = roleEntityJpaController.findFirstByName(roleString);
        List<Permission> permisions = new ArrayList<>();
        if (role != null && !role.getPermissions().isEmpty()) {
            role.getPermissions().forEach(permision -> {
                permisions.add(permision);
            });
        }
        return permisions;
    }

    @Override
    public Permission resolvePermission(String permissionString) {
        return Arrays.asList(UserPermissions.values())
                .stream()
                .filter(p -> p.getValue().equalsIgnoreCase(permissionString))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Serializable create(Session session) {
        if (session instanceof SimpleSession) {
            SessionEntity entity = SessionEntity.create((SimpleSession) session);
            sessionEntityJpaController.create(entity);
            ((SimpleSession) session).setId(entity.getId());
            return entity.getId();
        } else {
            throw new UnknownSessionException("Session is Invalid");
        }
    }

    @Override
    public SimpleSession readSession(Serializable sessionId) throws UnknownSessionException {
        SessionEntity findById = sessionEntityJpaController.findSessionEntity((Long) sessionId);
        if (findById == null || findById == null) {
            throw new UnknownSessionException("Session with the id " + sessionId + " was not found");
        }
        return findById.toSession();
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session instanceof SimpleSession) {
            SessionEntity findById = sessionEntityJpaController.findSessionEntity((Long) session.getId());
            if (findById == null || findById == null) {
                throw new UnknownSessionException("Session with the id " + (Long) session.getId() + " was not found");
            }
            findById.updateSession((SimpleSession) session);
            System.out.println("----> Updates session");
            System.out.println("----> save user " + session.getAttribute("user"));
            try {
                sessionEntityJpaController.edit(findById);
            } catch (Exception ex) {
                Logger.getLogger(SecurityService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            throw new UnknownSessionException("Session with the id " + session.getId() + " was not found");
        }
    }

    @Override
    public void delete(Session session) {
        try {
            sessionEntityJpaController.destroy((Long) session.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SecurityService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return sessionEntityJpaController.findSessionEntities().stream()
                .map(entity -> {
                    return (SimpleSession) entity.toSession();
                })
                .filter(session -> session != null && !session.isExpired())
                .collect(Collectors.toList());
    }

    @Override
    public void onStart(Session session) {
        NavigationManger.getInstance().showNavigation();
    }

    @Override
    public void onStop(Session session) {
        NavigationManger.getInstance().hideNavigation();
        NavigationManger.getInstance().navigate(NavigationRoute.LOGIN);
    }

    @Override
    public void onExpiration(Session session) {
        NavigationManger.getInstance().hideNavigation();
        NavigationManger.getInstance().navigate(NavigationRoute.LOGIN);
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {
        SimpleAuthenticationInfo authenticationInfo = (SimpleAuthenticationInfo) info;
        Map<String, String> credentials = (Map<String, String>) authenticationInfo.getCredentials();
        System.out.println("match " + credentials.toString());
        if (token instanceof PinToken) {
            PinToken pToken = (PinToken) token;
            if (pToken.getPin() == null
                    || pToken.getPin().length < 1) {
                throw new IncorrectCredentialsException("Incorrect credentials");
            }
            if (!credentials.get("pin").endsWith(String.copyValueOf(pToken.getPin()))) {
                throw new IncorrectCredentialsException("Incorrect credentials");
            }

        } else if (token instanceof UsernamePasswordToken) {
            UsernamePasswordToken uToken = (UsernamePasswordToken) token;
            if (uToken.getUsername() == null
                    || uToken.getUsername().isEmpty()) {
                throw new IncorrectCredentialsException("Incorrect credentials");
            }
            if (!credentials.get("password").endsWith(String.copyValueOf(uToken.getPassword()))) {
                throw new IncorrectCredentialsException("Incorrect credentials");
            }
        } else {
            throw new IncorrectCredentialsException("Incorrect credentials");
        }
    }

}
