package com.ertanyilmaz.verikazima.repository;


import com.ertanyilmaz.verikazima.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}