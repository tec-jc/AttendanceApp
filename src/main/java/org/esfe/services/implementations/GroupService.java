package org.esfe.services.implementations;

import org.esfe.entities.Group;
import org.esfe.repositories.IGroupRepository;
import org.esfe.services.interfaces.IGroupService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IGroupService {
    @Autowired
    private IGroupRepository groupRepository;

    @Override
    public Page<Group> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }

    @Override
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<Group> findOneById(Integer groupId) {
        return groupRepository.findById(groupId);
    }

    @Override
    public Group createOrEditOne(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void deleteOneById(Integer groupId) {
        groupRepository.deleteById(groupId);
    }
}
