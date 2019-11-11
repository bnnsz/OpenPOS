/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.components;

import com.bizstudio.inventory.repositories.LocationRepository;
import com.bizstudio.security.entities.CredentialEntity;
import com.bizstudio.security.entities.PrincipalEntity;
import com.bizstudio.security.entities.PrivilegeEntity;
import com.bizstudio.security.entities.RoleEntity;
import com.bizstudio.security.entities.UserEntity;
import com.bizstudio.security.repositories.data.PrivilegeEntityRepository;
import com.bizstudio.security.repositories.data.RoleEntityRepository;
import com.bizstudio.security.repositories.data.TokenEntityRepository;
import com.bizstudio.security.repositories.data.UserEntityRepository;
import com.bizstudio.security.services.SecurityService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 *
 * @author obinna.asuzu
 */
@Component
public class InitializationBean {

    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    PrivilegeEntityRepository privilegeRepo;
    @Autowired
    RoleEntityRepository roleEntityRepository;
    @Autowired
    TokenEntityRepository tokenEntityRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    Environment env;

    @Autowired
    SecurityService realm;

    @PostConstruct
    public void init() {
        try {
            org.apache.shiro.mgt.SecurityManager securityManager = new DefaultSecurityManager(realm);
            SecurityUtils.setSecurityManager(securityManager);
            initRolesAndPrivileges();
            initUsers();
        } catch (Exception ex) {
            Logger.getLogger(InitializationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initRolesAndPrivileges() {
        List<PrivilegeEntity> privilegeEntities = initPrivileges();
        initSubPrivileges(privilegeEntities);
        List<String> roles = env.getProperty("application.roles", List.class);
        if (roles != null) {
            roles.forEach(role -> {
                RoleEntity roleEntity = roleEntityRepository.findByName(role.toUpperCase()).orElseGet(() -> {
                    return roleEntityRepository.save(new RoleEntity(role.toUpperCase(), true));
                });
                mapRoleToPrivilege(roleEntity, privilegeEntities);
            });
        }
    }

    private void initSubPrivileges(List<PrivilegeEntity> privilegeEntities) {
        privilegeEntities.forEach(privilege -> {
            List<String> subPrivileges = env.getProperty("application.subPrivileges." + privilege.getValue().toLowerCase(), List.class);
            if (subPrivileges != null) {
                subPrivileges.forEach(subPriv -> {
                    privilegeRepo.findByValue(subPriv.toUpperCase()).ifPresent(subPrivilege -> {
                        subPrivilege.setParent(privilege);
                        privilegeRepo.save(subPrivilege);
                    });
                });
            }
        });
    }

    private List<PrivilegeEntity> initPrivileges() {
        List<PrivilegeEntity> privilegeEntities = new ArrayList<>();
        List<String> privileges = env.getProperty("application.privileges", List.class);
        if (privileges != null && !privileges.isEmpty()) {
            privileges.forEach(priv -> {
                PrivilegeEntity privilege = privilegeRepo
                        .findByValue(priv.toUpperCase())
                        .orElseGet(() -> privilegeRepo.save(new PrivilegeEntity(priv.toUpperCase(), true)));
                privilegeEntities.add(privilege);
            });
        }
        return privilegeEntities;
    }

    private void mapRoleToPrivilege(RoleEntity roleEntity, List<PrivilegeEntity> privilegeEntities) {
        List<String> privs = env.getProperty("application.privileges." + roleEntity.getName().toLowerCase(), List.class);
        if (privs != null) {

            privs.forEach(priv -> {
                privilegeEntities.stream()
                        .filter(p -> p.getValue().equals(priv.toUpperCase()))
                        .findFirst()
                        .ifPresent(privileg -> {
                            roleEntity.getPrivileges().add(privileg);
                        });
            });
            roleEntityRepository.save(roleEntity);
        }
    }

    public void initUsers() throws Exception {
        String username = env.getProperty("application.admin.username");
        String password = env.getProperty("application.admin.password");
        String pin = env.getProperty("application.admin.pin");
        String email = env.getProperty("application.admin.email");
        String firstname = env.getProperty("application.admin.firstname");
        String lastname = env.getProperty("application.admin.lastname");
        String pic = env.getProperty("application.admin.pic");

        username = username == null ? "superuser" : username;
        password = password == null ? "admin" : password;
        firstname = firstname == null ? "Admin" : firstname;

        if (!userEntityRepository.findByUsername("superuser").isPresent()) {
            RoleEntity role = roleEntityRepository.findByName("ADMIN").orElseThrow(() -> new Exception());
            UserEntity superuser = new UserEntity();
            superuser.setUsername(username);
            superuser.setPin(pin);
            superuser.setPrincipal("firstname", firstname);
            if (email != null) {
                superuser.setPrincipal("email", email);
            }
            if (lastname != null) {
                superuser.setPrincipal("lastname", lastname);
            }

            if (pic != null) {
                superuser.setPrincipal("pic", pic);
            }
            List<CredentialEntity> credentials = new ArrayList<>();
            credentials.add(new CredentialEntity("password", "admin", superuser));
            superuser.setCredentials(credentials);

            List<PrincipalEntity> principals = new ArrayList<>();
            principals.add(new PrincipalEntity("username", superuser.getUsername(), superuser));
            superuser.setPrincipals(principals);
            userEntityRepository.save(superuser);
            superuser.getRoles().add(role);
            role.getUsers().add(superuser);
            userEntityRepository.save(superuser);
        }

    }
}






