package com.kamrul.userapp.repository;

import com.kamrul.userapp.entity.user.Parent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

  Optional<Parent> findByIdAndActiveStatus(Long id, int status);

  List<Parent> findAllByActiveStatus(int status);

}
