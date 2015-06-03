package com.razvan.oculuservice.repository;

import com.razvan.oculuservice.domain.OculusUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the OculusUser entity.
 */
public interface OculusUserRepository extends JpaRepository<OculusUser,Long> {
    OculusUser findOneByFirstNameAndFamilyName(String firstName, String familyName);
}
