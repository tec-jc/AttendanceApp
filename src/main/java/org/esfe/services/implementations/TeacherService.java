package org.esfe.services.implementations;

import org.esfe.entities.Teacher;
import org.esfe.repositories.ITeacherRepository;
import org.esfe.services.interfaces.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService implements ITeacherService {
    @Autowired
    private ITeacherRepository teacherRepository;
    @Override
    public Page<Teacher> findAll(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> findOneById(Integer teacherId) {
        return teacherRepository.findById(teacherId);
    }

    @Override
    public Teacher createOrEditOne(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteOneById(Integer teacherId) {
        teacherRepository.deleteById(teacherId);
    }
}
