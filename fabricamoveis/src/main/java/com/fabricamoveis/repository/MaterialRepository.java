package com.fabricamoveis.repository;

import com.fabricamoveis.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}