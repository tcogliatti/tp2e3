package dao;
import org.hibernate.boot.archive.spi.JarFileEntryUrlAdjuster;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEquipo;
    @ManyToOne
    private Persona dt;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "equipo")

    private ArrayList<Jugador> equipo;
    @Column(nullable = true)
    private String nombre;
    @Column(nullable = true)
    private String sponsor;

    public static final int MAXIMO_JUGADORES = 10;
    public static final int MINIMO_JUGADORES = 7;

    public Equipo() {
        this.equipo = new ArrayList<Jugador>();
    }
    public Equipo(Persona dt, ArrayList<Jugador> equipo, String sponsor, String nombre) {
        this.dt = dt;
        this.equipo = equipo;
        this.sponsor = sponsor;
        this.nombre = nombre;
    }

    public int getIdEquipo() {
        return idEquipo;
    }
    public void addJugador(Jugador j){
        equipo.add(j);
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Persona getDt() {
        return dt;
    }

    public void setDt(Persona dt) {
        this.dt = dt;
    }

    public ArrayList<Jugador> getEquipo() {
        return equipo;
    }

    public void setEquipo(ArrayList<Jugador> equipo) {
        this.equipo = equipo;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}