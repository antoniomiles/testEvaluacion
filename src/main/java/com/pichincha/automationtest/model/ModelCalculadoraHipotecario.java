package com.pichincha.automationtest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ModelCalculadoraHipotecario {
    private String productoInteres;
    private String costo;
    private String montoSolicitud;
    private String plazoAnios;
    private String amortizacion;


}