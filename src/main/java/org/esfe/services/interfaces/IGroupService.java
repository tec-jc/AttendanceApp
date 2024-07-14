package org.esfe.services.interfaces;

import org.esfe.entities.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IGroupService {
    Page<Group> findAll(Pageable pageable);
    Optional<Group> findOneById(Integer groupId);
    Group createOrEditOne(Group group);
    void deleteOneById(Integer groupId);
}
