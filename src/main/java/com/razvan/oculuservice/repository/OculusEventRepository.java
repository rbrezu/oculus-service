package com.razvan.oculuservice.repository;

import com.razvan.oculuservice.domain.OculusEvent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the OculusEvent entity.
 */
public interface OculusEventRepository extends JpaRepository<OculusEvent,Long> {

}
