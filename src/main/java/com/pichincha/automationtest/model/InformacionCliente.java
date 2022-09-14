package com.pichincha.automationtest.model;

public class InformacionCliente {
    private InformacionCliente(){}

    public static Cliente conDatos(String nombre, String pais, String ciudad, String numeroTarjeta, String mesVencimiento, String anioVencimiento) {
        Cliente usuario = new Cliente();
        usuario.setNombre(nombre);
        usuario.setPais(pais);
        usuario.setCiudad(ciudad);
        usuario.setNumeroTarjeta(numeroTarjeta);
        usuario.setMesVencimiento(mesVencimiento);
        usuario.setAnioVencimiento(anioVencimiento);
        return usuario;
    }
}