package com.ertanyilmaz.verikazima.repository;

import com.ertanyilmaz.verikazima.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
