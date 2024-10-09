package com.fabricamoveis.controller;

import com.fabricamoveis.dto.MaterialDTO;
import com.fabricamoveis.model.Material;
import com.fabricamoveis.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materiais")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Material> listarMateriais() {
        return materialService.listarMateriais();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Material> buscarMaterialPorId(@PathVariable Long id) {
        Material material = materialService.buscarMaterialPorId(id);
        return ResponseEntity.ok(material);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Material> criarMaterial(@RequestBody MaterialDTO materialDTO) {
        Material novoMaterial = materialService.criarMaterial(new Material(materialDTO.getNome(), materialDTO.getQuantidade()));
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMaterial);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Material> atualizarMaterial(@PathVariable Long id, @RequestBody MaterialDTO materialDTO) {
        Material materialAtualizado = materialService.atualizarMaterial(id, new Material(materialDTO.getNome(), materialDTO.getQuantidade()));
        return ResponseEntity.ok(materialAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarMaterial(@PathVariable Long id) {
        materialService.deletarMaterial(id);
        return ResponseEntity.noContent().build();
    }
}