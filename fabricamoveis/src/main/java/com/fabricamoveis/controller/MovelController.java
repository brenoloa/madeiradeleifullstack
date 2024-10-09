package com.fabricamoveis.controller;

import com.fabricamoveis.dto.MovelDTO;
import com.fabricamoveis.exception.ResourceNotFoundException;
import com.fabricamoveis.model.Material;
import com.fabricamoveis.model.Movel;
import com.fabricamoveis.repository.MaterialRepository;
import com.fabricamoveis.service.MovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/moveis")
public class MovelController {

    @Autowired
    private MovelService movelService;

    @Autowired
    private MaterialRepository materialRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public List<Movel> listarMoveis() {
        return movelService.listarMoveis();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENTE')")
    public ResponseEntity<Movel> buscarMovelPorId(@PathVariable Long id) {
        Movel movel = movelService.buscarMovelPorId(id);
        return ResponseEntity.ok(movel);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movel> criarMovel(@RequestBody MovelDTO movelDTO) {
        Map<Material, Integer> materiais = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : movelDTO.getMateriais().entrySet()) {
            Material material = materialRepository.findById(entry.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado com ID: " + entry.getKey()));
            materiais.put(material, entry.getValue());
        }

        Movel novoMovel = new Movel(movelDTO.getNome());
        novoMovel.setMateriais(materiais);

        return ResponseEntity.status(HttpStatus.CREATED).body(movelService.criarMovel(novoMovel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movel> atualizarMovel(@PathVariable Long id, @RequestBody MovelDTO movelDTO) {
        Map<Material, Integer> materiais = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : movelDTO.getMateriais().entrySet()) {
            Material material = materialRepository.findById(entry.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado com ID: " + entry.getKey()));
            materiais.put(material, entry.getValue());
        }

        Movel novoMovel = new Movel(movelDTO.getNome());
        novoMovel.setMateriais(materiais);

        Movel movelAtualizado = movelService.atualizarMovel(id, novoMovel);
        return ResponseEntity.ok(movelAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarMovel(@PathVariable Long id) {
        movelService.deletarMovel(id);
        return ResponseEntity.noContent().build();
    }
}