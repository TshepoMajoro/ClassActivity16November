package org.springtech.classactivity16november.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springtech.classactivity16november.Entities.Students;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students, Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    Optional<Students> findByEmail(String email);
}
