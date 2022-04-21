package com.pichincha.automationtest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {
        private String nombre;
        private String ciudad;
        private String pais;
        private String numeroTarjeta;
        private String mesVencimiento;
        private String anioVencimiento;
}