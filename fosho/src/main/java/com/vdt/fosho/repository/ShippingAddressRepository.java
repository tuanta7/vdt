package com.vdt.fosho.repository;

import com.vdt.fosho.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {

    List<ShippingAddress> findByUserIdAndDeletedAtIsNull(Long userId);
}
