package org.esfe.repositories;

import org.esfe.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupRepository extends JpaRepository<Group, Integer> {
}
