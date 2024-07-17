package org.esfe.repositories;

import org.esfe.entities.TeacherGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeacherGroupRepository extends JpaRepository<TeacherGroup, Integer> {
    Page<TeacherGroup> findByOrderByTeacherDesc(Pageable pageable);
}
