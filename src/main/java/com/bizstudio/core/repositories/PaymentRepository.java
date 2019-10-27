/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.repositories;

import com.bizstudio.security.entities.UserEntity;
import com.bizstudio.core.entities.PaymentEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author obinna.asuzu
 */
public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {
    Optional<PaymentEntity> findLastByPayer(UserEntity payer);
    Optional<PaymentEntity> findLastByPayerAndRole(UserEntity payer,String role);
}


