package com.example.proyecto_tarjeta;

public class Info_Movimientos {

    int ID, Monto;
    String  Tipo, Fecha, Hora;

    public Info_Movimientos(int ID, int monto, String tipo, String fecha, String hora) {
        this.ID = ID;
        Monto = monto;
        Tipo = tipo;
        Fecha = fecha;
        Hora = hora;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
