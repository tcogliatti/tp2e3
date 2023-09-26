package dao;

import javax.persistence.*;

@Entity
@NamedQuery(name = Jugador.OBTENER_TODOS, query = "select j from Jugador j")
public class Jugador {
    @Id
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private Persona persona;
    @Column
    private String posicion;
    @ManyToOne
    private Equipo equipo;
    public static final String OBTENER_TODOS = "Jugador.obtenerTodos";

    public Jugador() {
    }
    public Jugador(Persona per) {
        this.persona = per;
    }

    public Jugador(Persona per, String pos, Equipo equi) {
        this.persona = per;
        this.posicion = pos;
        this.equipo = equi;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
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
                this.persona +
                "posicion='" + posicion + '\'' +
                ", equipo=" + equipo +
                '}';
    }
}
