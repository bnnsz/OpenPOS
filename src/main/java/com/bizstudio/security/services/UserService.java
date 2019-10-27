/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.services;

import com.bizstudio.security.enums.Error;
import com.bizstudio.core.exceptions.ServiceException;
import com.bizstudio.security.entities.CredentialEntity;
import com.bizstudio.security.entities.PrincipalEntity;
import com.bizstudio.security.entities.UserEntity;
import com.bizstudio.security.repositories.data.PrivilegeEntityRepository;
import com.bizstudio.security.repositories.data.RoleEntityRepository;
import com.bizstudio.security.repositories.data.UserEntityRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.toMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author obinna.asuzu
 */
@Service
public class UserService {
    @Autowired
    UserEntityRepository userRepository;
    @Autowired
    PrivilegeEntityRepository permissionRepository;
    @Autowired
    RoleEntityRepository roleRepository;
    
    public UserEntity getUser(String username) throws ServiceException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(Error.user_not_exist));
    }

    public Map<String, String> getUserProfile(String username) throws Exception {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(Error.user_not_exist));
        return user.getPrincipals().stream().collect(toMap(PrincipalEntity::getKey, PrincipalEntity::getValue));
    }

    public UserEntity registerUser(
            String username,
            String firstname,
            String lastname,
            String email,
            String phone,
            List<String> roles) throws Exception {
        UserEntity user = createUser(username, firstname, lastname, email, phone, roles);
        return user;
    }

    public UserEntity createUser(
            String username,
            String firstname,
            String lastname,
            String email,
            String phone,
            List<String> roles) throws Exception {
        UserEntity user = new UserEntity();
        user.setUsername(username);

        Map<String, String> principles = new HashMap<>();
        principles.put("username", username);
        principles.put("firstname", firstname);
        principles.put("lastname", lastname);
        principles.put("email", email);
        principles.put("phone", phone);

        principles.entrySet().forEach(entry -> {
            user.setPrincipal(entry.getKey(), entry.getValue());
        });

        if (!userRepository.findByUsernameOrEmailOrPhone(username, email, phone).isEmpty()) {
            throw new ServiceException(Error.user_exist);
        };

        userRepository.save(user);
        return user;
    }

    public boolean deactivateUser(String username) throws ServiceException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(Error.user_not_exist));
        if (user.getSystem()) {
            throw new ServiceException(Error.cannot_deactivate_sys_user);
        }
        user.setEnabled(false);
        userRepository.save(user);
        return true;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<UserEntity> getAllUsers(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    public Page<UserEntity> getAllUsers(String search,PageRequest pageRequest) {
        if (search != null && !search.trim().isEmpty()) {
            return userRepository.searchByCriteria(search,  pageRequest);
        }
        return userRepository.findAll(pageRequest);
    }

    public UserEntity updateUserProfile(String username, Map<String, String> principles) throws ServiceException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(Error.user_not_exist));
        principles.entrySet().forEach(entry -> {
            user.setPrincipal(entry.getKey(), entry.getValue());
        });
        return userRepository.save(user);
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) throws ServiceException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException(Error.user_not_exist));

        Optional<CredentialEntity> credential = user.getCredentials()
                .stream().filter(c -> c.getName().equals("password"))
                .findFirst();
        if (!credential.isPresent() && oldPassword == null) {
            CredentialEntity password = new CredentialEntity("password", newPassword, user);
            password.setExpired(false);
            user.getCredentials().add(password);
        } else if (oldPassword.equals(credential.get().getValue())) {
            user.getCredentials().forEach(cr ->{
                if(cr.getName().equals("password")){
                    cr.setValue(newPassword);
                    cr.setExpired(false);
                }
            });
        }
        return newPassword.equals(getPassword(userRepository.save(user)));
    }
    
    
    private String getPassword(UserEntity user){
        CredentialEntity password = user.getCredentials()
                .stream().filter(c -> c.getName().equals("password"))
                .findFirst().orElse(null);
        return password == null ? null : password.getValue();
    }
}








