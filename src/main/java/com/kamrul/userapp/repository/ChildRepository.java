package com.kamrul.userapp.repository;

import com.kamrul.userapp.entity.user.Child;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Child entities.
 */
@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

  /**
   * Finds a child by its ID and active status.
   *
   * @param id     The ID of the child.
   * @param status The active status of the child.
   * @return An Optional containing the child if found, empty otherwise.
   */
  Optional<Child> findByIdAndActiveStatus(Long id, int status);

  /**
   * Finds all children by their active status.
   *
   * @param status The active status of the children.
   * @return A list of children with the specified active status.
   */
  List<Child> findAllByActiveStatus(int status);
}
