/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.services;

import com.bizstudio.core.managers.NavigationManger;
import com.bizstudio.core.utils.AutowireHelper;
import com.bizstudio.security.entities.data.CredentialEntity;
import com.bizstudio.security.entities.cache.SessionEntity;
import com.bizstudio.security.entities.data.UserAccountEntity;
import com.bizstudio.security.entities.data.UserRoleEntity;
import com.bizstudio.security.repositories.cache.SessionRepository;
import com.bizstudio.security.repositories.data.UserAccountRepository;
import com.bizstudio.security.repositories.data.UserRoleRepository;
import com.bizstudio.security.entities.data.PrincipalEntity;
import com.bizstudio.security.enums.UserPermissions;
import com.bizstudio.security.entities.data.Principal;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ObinnaAsuzu
 */
@Service
public class SecurityService extends AuthorizingRealm implements
        RolePermissionResolver,
        PermissionResolver,
        SessionDAO,
        SessionListener {

    @Autowired
    UserAccountRepository userRepository;

    @Autowired
    UserRoleRepository roleRepository;

    @Autowired
    SessionRepository sessionRepository;
    
    

    Gson gson = new Gson();

    public SecurityService() {
//        AutowireHelper.autowire(this, userRepository,roleRepository,sessionRepository);
//        initialiseSuperuser();
    }
    
    
    
    

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Set<Permission> permisions = new TreeSet<>();
        Set<String> roles = new TreeSet<>();
        userRepository.findByUsername(username).ifPresent(user -> {
            if (user.getRoles() != null && user.getRoles().isEmpty()) {
                for (UserRoleEntity role : user.getRoles()) {
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
        });

        authorizationInfo.setRoles(roles);
        authorizationInfo.setObjectPermissions(permisions);
        return authorizationInfo;
    }

    @PostConstruct
    public final void initialiseSuperuser() {
        if (!userRepository.findByUsername("superuser").isPresent()) {
            UserAccountEntity superuser = new UserAccountEntity();
            superuser.setUsername("superuser");
            superuser.setPin("123456");
            List<CredentialEntity> credentials = new ArrayList<>();
            credentials.add(new CredentialEntity("password", "admin", superuser));
            superuser.setCredentials(credentials);

            List<PrincipalEntity> principals = new ArrayList<>();
            principals.add(new PrincipalEntity("username", superuser.getUsername(), superuser));
            superuser.setPrincipals(principals);
            userRepository.save(superuser);
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Optional<UserAccountEntity> user = Optional.empty();
        if (token instanceof PinToken) {
            PinToken pToken = (PinToken) token;
            if (pToken.getPin() == null || pToken.getPin().length < 1) {
                throw new IncorrectCredentialsException("Incorrect credentials");
            }
            user = userRepository.findByPin(String.copyValueOf(pToken.getPin()));

        } else if (token instanceof UsernamePasswordToken) {
            UsernamePasswordToken uToken = (UsernamePasswordToken) token;
            if (uToken.getUsername() == null || uToken.getUsername().isEmpty()) {
                throw new IncorrectCredentialsException("Incorrect credentials");
            }

            if (uToken.getPassword() == null || uToken.getPassword().length < 1) {
                throw new IncorrectCredentialsException("Incorrect credentials");
            }
            user = userRepository.findByUsername(uToken.getUsername());
            System.out.println("----> "+user);
        }

        if (!user.isPresent()) {
            throw new IncorrectCredentialsException("Incorrect credentialsx");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
        Map<String, String> credentials = new TreeMap<>();
        user.get().getCredentials().forEach(c -> credentials.put(c.getName(), c.getValue()));
        authenticationInfo.setCredentials(credentials);

        List<Principal> princpals = new ArrayList<>();
        user.get().getPrincipals().forEach((p) -> princpals.add(p));
        PrincipalCollection principalCollection = new SimplePrincipalCollection(princpals, getName());
        authenticationInfo.setPrincipals(principalCollection);

        return authenticationInfo;

    }

    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        UserRoleEntity role = roleRepository.findFirstByName(roleString);
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
            sessionRepository.save(entity);
            ((SimpleSession) session).setId(entity.getId());
            return entity.getId();
        } else {
            throw new UnknownSessionException("Session is Invalid");
        }
    }

    @Override
    public SimpleSession readSession(Serializable sessionId) throws UnknownSessionException {
        SessionEntity findById = sessionRepository.findById((Long) sessionId)
                .orElseThrow(() -> new UnknownSessionException("Session with id "+sessionId+" was not found"));
        
        return findById.toSession();
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session instanceof SimpleSession) {
            SessionEntity findById = sessionRepository.findById((Long) session.getId())
                .orElseThrow(() -> new UnknownSessionException("Session with id "+session.getId()+" was not found"));
            findById.updateSession((SimpleSession) session);
            System.out.println("----> Updates session");
            System.out.println("----> save user " + session.getAttribute("user"));
            try {
                sessionRepository.save(findById);
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
            sessionRepository.deleteById((Long) session.getId());
        } catch (Exception ex) {
            Logger.getLogger(SecurityService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return sessionRepository.findAll().stream()
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
        NavigationManger.getInstance().logout();
    }

    @Override
    public void onExpiration(Session session) {
        NavigationManger.getInstance().hideNavigation();
        NavigationManger.getInstance().logout();
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















