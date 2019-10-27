/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.security.repositories.data;

import com.bizstudio.security.entities.RoleEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author obinna.asuzu
 */
public class RoleSpecification implements Specification<RoleEntity> {

    private final Map<String, String> criteria;

    private final List<String> ignoreList;

    public RoleSpecification(Map<String, String> criteria, List<String> ignoreList) {
        this.criteria = criteria;
        this.ignoreList = ignoreList.stream().map(s -> s.toUpperCase()).collect(toList());
    }

    @Override
    public Predicate toPredicate(Root<RoleEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> ignore = new ArrayList<>();
        criteria.keySet().forEach(key -> {
            if (criteria.get(key) != null && !criteria.get(key).trim().isEmpty()) {
                predicates.add(cb.like(root.get(key), "%" + criteria.get(key) + "%"));
            }
        });

        ignoreList.forEach(role -> {
            ignore.add(cb.notEqual(root.get("name"), role));
        });

        query.distinct(true);
        if (predicates.isEmpty()) {
            return cb.and(ignore.toArray(new Predicate[ignore.size()]));
        }
        Predicate filterPredicate = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        Predicate ignorePredicate = cb.and(ignore.toArray(new Predicate[ignore.size()]));
        return cb.and(ignorePredicate, filterPredicate);
    }

}
