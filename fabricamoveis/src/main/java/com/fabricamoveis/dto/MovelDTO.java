package com.fabricamoveis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MovelDTO {
    private String nome;
    private Map<Long, Integer> materiais = new HashMap<>(); // materialId, quantidade
}