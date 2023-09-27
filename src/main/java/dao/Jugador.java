package dao;

import javax.persistence.*;
import javax.sql.ConnectionPoolDataSource;

@Entity
@NamedQuery(name = Jugador.OBTENER_TODOS, query = "select j from Jugador j")
@DiscriminatorValue("jugador")
public class Jugador extends Persona{
    @OneToOne
    private Posicion posicion;
    @ManyToOne
    private Equipo equipo;
    public static final String OBTENER_TODOS = "Jugador.obtenerTodos";

    public Jugador() {
    }
    public Jugador(Persona per) {
        this.setIdPersona(per.getIdPersona());
        this.setNombre(per.getNombre());
        this.setMail(per.getMail());
        this.setNacimiento(per.getNacimiento());
    }

    public Jugador(Persona per, Posicion pos, Equipo equi) {
        this.setIdPersona(per.getIdPersona());
        this.setNombre(per.getNombre());
        this.setMail(per.getMail());
        this.setNacimiento(per.getNacimiento());
        this.posicion = pos;
        this.equipo = equi;
    }
    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                super.toString() +
                "posicion='" + posicion + '\'' +
                ", equipo=" + equipo +
                '}';
    }
}
