/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories.data;

import com.bizstudio.security.entities.RoleEntity;
import com.bizstudio.security.entities.UserEntity;
import com.bizstudio.security.entities.UserPrincipalEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author obinna.asuzu
 */
public class UserSpecification implements Specification<UserEntity> {

    private final Map<String, String> criteria;
    private List<String> roles;

    public UserSpecification(Map<String, String> criteria, String... roles) {
        this.criteria = criteria;
        if(roles != null && roles.length > 0){
            this.roles = Arrays.asList(roles);
        }
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        final Path<Collection<UserPrincipalEntity>> principles = root.join("principals");
        criteria.keySet().forEach(key -> {
            if (criteria.get(key) != null && !criteria.get(key).trim().isEmpty()) {
                Predicate p = cb.and(
                        cb.equal(principles.get("key"), key),
                        cb.like(principles.get("value"), "%" + criteria.get(key) + "%"));
                predicates.add(p);
            }
        });

        query.distinct(true);

        if (roles != null && !roles.isEmpty()) {
            final Path<Collection<RoleEntity>> uRoles = root.join("roles");
            if (predicates.isEmpty()) {
                return uRoles.get("name").in(roles);
            } else {
                return cb.and(
                        uRoles.get("name").in(roles),
                        cb.or(predicates.toArray(new Predicate[predicates.size()])));
            }
        } else {
            if (predicates.isEmpty()) {
                return null;
            }
            return cb.or(predicates.toArray(new Predicate[predicates.size()]));
        }

    }

}
