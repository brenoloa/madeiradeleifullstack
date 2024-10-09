package com.fabricamoveis.service;

import com.fabricamoveis.exception.ResourceNotFoundException;
import com.fabricamoveis.model.Material;
import com.fabricamoveis.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    public List<Material> listarMateriais() {
        return materialRepository.findAll();
    }

    public Material buscarMaterialPorId(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado com ID: " + id));
    }

    public Material criarMaterial(Material material) {
        return materialRepository.save(material);
    }

    public Material atualizarMaterial(Long id, Material novoMaterial) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado com ID: " + id));

        material.setNome(novoMaterial.getNome());
        material.setQuantidade(novoMaterial.getQuantidade());

        return materialRepository.save(material);
    }

    public void deletarMaterial(Long id) {
        materialRepository.deleteById(id);
    }
}