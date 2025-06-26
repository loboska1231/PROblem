package org.copyria2.order_service.repository;

import org.copyria2.order_service.entity.ChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeRepository extends JpaRepository<ChangeEntity, Integer> {
    ChangeEntity findByCurrency(String currency);
}
