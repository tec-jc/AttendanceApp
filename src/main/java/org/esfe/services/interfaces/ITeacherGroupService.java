package org.esfe.services.interfaces;

import org.esfe.entities.TeacherGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITeacherGroupService {
    List<TeacherGroup> getAll();

    Page<TeacherGroup> getAllPaged(Pageable pageable);

    TeacherGroup getById(Integer idTeacherGroup);

    TeacherGroup save(TeacherGroup teacherGroup);

    void deleteById(Integer idTeacherGroup);
}
