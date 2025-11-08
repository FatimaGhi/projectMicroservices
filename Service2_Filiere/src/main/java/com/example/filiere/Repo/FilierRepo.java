package com.example.filiere.Repo;

import com.example.filiere.Entites.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FilierRepo extends JpaRepository<Filiere,Long> {
}
