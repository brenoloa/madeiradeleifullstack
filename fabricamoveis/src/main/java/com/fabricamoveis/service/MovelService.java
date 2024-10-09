package com.fabricamoveis.service;

import com.fabricamoveis.exception.ResourceNotFoundException;
import com.fabricamoveis.model.Material;
import com.fabricamoveis.model.Movel;
import com.fabricamoveis.repository.MaterialRepository;
import com.fabricamoveis.repository.MovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovelService {

    @Autowired
    private MovelRepository movelRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<Movel> listarMoveis() {
        return movelRepository.findAll();
    }

    public Movel buscarMovelPorId(Long id) {
        return movelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Móvel não encontrado com ID: " + id));
    }

    public Movel criarMovel(Movel movel) {
        return movelRepository.save(movel);
    }

    public Movel atualizarMovel(Long id, Movel novoMovel) {
        Movel movel = movelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Móvel não encontrado com ID: " + id));

        movel.setNome(novoMovel.getNome());
        movel.setMateriais(novoMovel.getMateriais());

        return movelRepository.save(movel);
    }

    public void deletarMovel(Long id) {
        movelRepository.deleteById(id);
    }
}