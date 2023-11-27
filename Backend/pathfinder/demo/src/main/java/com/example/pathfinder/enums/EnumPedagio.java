package com.example.pathfinder.enums;

import lombok.*;

import java.util.Arrays;

import static com.example.pathfinder.utils.Utils.ZEROD;

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

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
