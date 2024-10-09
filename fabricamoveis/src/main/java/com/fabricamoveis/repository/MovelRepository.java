package com.fabricamoveis.repository;

import com.fabricamoveis.model.Movel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovelRepository extends JpaRepository<Movel, Long> {
}