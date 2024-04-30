package com.kamrul.userapp.repository;

import com.kamrul.userapp.entity.user.Child;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

  Optional<Child> findByIdAndActiveStatus(Long id, int status);

  List<Child> findAllByActiveStatus(int status);
}
