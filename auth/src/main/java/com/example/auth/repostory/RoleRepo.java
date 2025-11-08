package com.example.auth.repostory;

import com.example.auth.Entites.Rolle;
import com.example.auth.Entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Rolle,Integer> {


}
