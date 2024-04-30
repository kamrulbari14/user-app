package com.kamrul.userapp.repository;

import com.kamrul.userapp.entity.user.Parent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Parent entities.
 */
@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

  /**
   * Finds a parent by its ID and active status.
   *
   * @param id     The ID of the parent.
   * @param status The active status of the parent.
   * @return An Optional containing the parent if found, empty otherwise.
   */
  Optional<Parent> findByIdAndActiveStatus(Long id, int status);

  /**
   * Finds all parents by their active status.
   *
   * @param status The active status of the parents.
   * @return A list of parents with the specified active status.
   */
  List<Parent> findAllByActiveStatus(int status);
}
