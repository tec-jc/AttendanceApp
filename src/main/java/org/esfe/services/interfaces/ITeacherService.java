package org.esfe.services.interfaces;

import org.esfe.entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ITeacherService {
    Page<Teacher> findAll(Pageable pageable);

    List<Teacher> getAll();

    Optional<Teacher> findOneById(Integer teacherId);

    Teacher createOrEditOne(Teacher teacher);

    void deleteOneById(Integer teacherId);
}
