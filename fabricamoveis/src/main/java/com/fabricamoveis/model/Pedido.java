package com.fabricamoveis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movel_id", nullable = false)
    private Movel movel;

    @Column(nullable = false)
    private int quantidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    public Pedido(Movel movel, int quantidade) {
        this.movel = movel;
        this.quantidade = quantidade;
        this.status = StatusPedido.PENDENTE;
    }
}