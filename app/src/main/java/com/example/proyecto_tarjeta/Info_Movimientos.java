package com.example.proyecto_tarjeta;

public class Info_Movimientos {

    int Monto;
    String  Tipo, Fecha, Hora;

    public Info_Movimientos(int monto, String tipo, String fecha, String hora) {
        Monto = monto;
        Tipo = tipo;
        Fecha = fecha;
        Hora = hora;
    }

    public Info_Movimientos(String monto, int tipo, String fecha, String hora) {
    }

    public int getMonto() {
        return Monto;
    }

    public void setMonto(int monto) {
        Monto = monto;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }
}
