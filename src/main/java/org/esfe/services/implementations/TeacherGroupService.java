package org.esfe.services.implementations;

import org.esfe.entities.TeacherGroup;
import org.esfe.repositories.ITeacherGroupRepository;
import org.esfe.services.interfaces.ITeacherGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherGroupService implements ITeacherGroupService {
    @Autowired
    private ITeacherGroupRepository teacherGroupRepository;

    @Override
    public List<TeacherGroup> getAll() {
        return teacherGroupRepository.findAll();
    }

    @Override
    public Page<TeacherGroup> getAllPaged(Pageable pageable) {
        return teacherGroupRepository.findByOrderByTeacherDesc(pageable);
    }

    @Override
    public TeacherGroup getById(Integer idTeacherGroup) {
        return teacherGroupRepository.findById(idTeacherGroup).get();
    }

    @Override
    public TeacherGroup save(TeacherGroup teacherGroup) {
        return teacherGroupRepository.save(teacherGroup);
    }

    @Override
    public void deleteById(Integer idTeacherGroup) {
        teacherGroupRepository.deleteById(idTeacherGroup);
    }
}
