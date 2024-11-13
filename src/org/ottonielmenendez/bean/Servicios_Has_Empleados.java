package org.ottonielmenendez.bean;

import java.util.Date;
import java.sql.Time;

public class Servicios_Has_Empleados {

    private int Servicios_codigoServicio;
    private int codigoEmpleado;
    private int codigoServicio;
    private Date fechaEvento;
    private String horaEvento;
    private String lugarEvento;

    public Servicios_Has_Empleados() {
    }

    public Servicios_Has_Empleados(int Servicios_codigoServicio, int codigoEmpleado, int codigoServicio, Date fechaEvento, String horaEvento, String lugarEvento) {
        this.Servicios_codigoServicio = Servicios_codigoServicio;
        this.codigoEmpleado = codigoEmpleado;
        this.codigoServicio = codigoServicio;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.lugarEvento = lugarEvento;
    }

    public int getServicios_codigoServicio() {
        return Servicios_codigoServicio;
    }

    public void setServicios_codigoServicio(int Servicios_codigoServicio) {
        this.Servicios_codigoServicio = Servicios_codigoServicio;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

}
