package com.example.pathfinder.enums;

import java.util.Arrays;

public enum EnumPedagio {

    CAR("Car", 7.3),
    TRUCK("Truck", 14.6),
    BUS("Bus", 14.6),
    MOTORCYCLE("Motorcycle", 3.65),
    MINIBUS("Minibus", 7.3);

    private String tipo;
    private Double preco;

    EnumPedagio(String tipo, Double preco) {
        this.tipo = tipo;
        this.preco = preco;
    }

    public Double getPrecoPorVeiculo(String tipoVeiculo) {
        return Arrays.stream(EnumPedagio.values())
                .filter(t -> t.getTipo().equals(tipoVeiculo))
                .findFirst()
                .map(t -> t.getPreco())
                .orElse(0.0);
    }

    public String getTipo() {
        return tipo;
    }

    public Double getPreco() {
        return preco;
    }

}
