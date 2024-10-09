package com.fabricamoveis.controller;
import com.fabricamoveis.dto.MovelDTO;
import com.fabricamoveis.dto.PedidoDTO;
import com.fabricamoveis.model.Movel;
import com.fabricamoveis.model.Pedido;
import com.fabricamoveis.service.MovelService;
import com.fabricamoveis.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private MovelService movelService;

    @GetMapping
    public List<Pedido> listarPedidos() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        Movel movel = movelService.buscarMovelPorId(pedidoDTO.getMovelId());
        Pedido novoPedido = pedidoService.criarPedido(new Pedido(movel, pedidoDTO.getQuantidade()));
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }

    @PutMapping("/{id}/aprovar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> aprovarPedido(@PathVariable Long id) {
        pedidoService.aprovarPedido(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/rejeitar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> rejeitarPedido(@PathVariable Long id) {
        pedidoService.rejeitarPedido(id);
        return ResponseEntity.ok().build();
    }
}