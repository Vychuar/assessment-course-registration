package com.course.registration.repository;

import com.course.registration.model.Registration;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration,Long> {
    Optional<Registration> findById(Long id) ;

}
