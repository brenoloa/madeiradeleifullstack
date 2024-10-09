package com.fabricamoveis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "moveis")
@Getter
@Setter
@NoArgsConstructor
public class Movel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ElementCollection
    @Column(name = "material_quantidade")
    @MapKeyJoinColumn(name = "material_id")
    private Map<Material, Integer> materiais = new HashMap<>();

    public Movel(String nome) {
        this.nome = nome;
    }
}