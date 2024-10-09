package com.fabricamoveis.service;

import com.fabricamoveis.exception.InsufficientMaterialException;
import com.fabricamoveis.exception.ResourceNotFoundException;
import com.fabricamoveis.model.Material;
import com.fabricamoveis.model.Movel;
import com.fabricamoveis.model.Pedido;
import com.fabricamoveis.model.StatusPedido; // Adicione esta importação
import com.fabricamoveis.repository.MaterialRepository;
import com.fabricamoveis.repository.MovelRepository;
import com.fabricamoveis.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MovelRepository movelRepository;

    @Autowired
    private MaterialRepository materialRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com ID: " + id));
    }

    public Pedido criarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void aprovarPedido(Long id) {
        Pedido pedido = buscarPedidoPorId(id);

        if (!verificarMateriaisSuficientes(pedido.getMovel(), pedido.getQuantidade())) {
            throw new InsufficientMaterialException("Materiais insuficientes para produzir este móvel.");
        }

        atualizarEstoque(pedido.getMovel(), pedido.getQuantidade());
        pedido.setStatus(StatusPedido.APROVADO);
        pedidoRepository.save(pedido);
    }

    public void rejeitarPedido(Long id) {
        Pedido pedido = buscarPedidoPorId(id);
        pedido.setStatus(StatusPedido.REJEITADO);
        pedidoRepository.save(pedido);
    }

    private boolean verificarMateriaisSuficientes(Movel movel, int quantidade) {
        Map<Material, Integer> materiais = movel.getMateriais();

        for (Map.Entry<Material, Integer> entry : materiais.entrySet()) {
            Material material = materialRepository.findById(entry.getKey().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado."));

            if (material.getQuantidade() < entry.getValue() * quantidade) {
                return false;
            }
        }

        return true;
    }

    private void atualizarEstoque(Movel movel, int quantidade) {
        Map<Material, Integer> materiais = movel.getMateriais();

        for (Map.Entry<Material, Integer> entry : materiais.entrySet()) {
            Material material = materialRepository.findById(entry.getKey().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado."));

            material.setQuantidade(material.getQuantidade() - entry.getValue() * quantidade);
            materialRepository.save(material);
        }
    }
}