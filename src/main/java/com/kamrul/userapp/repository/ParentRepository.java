package com.kamrul.userapp.repository;

import com.kamrul.userapp.entity.user.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

}
