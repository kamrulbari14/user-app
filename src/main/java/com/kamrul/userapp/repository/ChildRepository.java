package com.kamrul.userapp.repository;

import com.kamrul.userapp.entity.user.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

}
