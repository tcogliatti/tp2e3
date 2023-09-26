package dao;
import org.hibernate.boot.archive.spi.JarFileEntryUrlAdjuster;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = Equipo.OBTENER_TODOS, query = "SELECT e FROM Equipo e")
public class Equipo {
    @Id
    private int idEquipo;
    @ManyToOne
    private Persona dt;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "equipo")
    private List<Jugador> equipo;
    @Column(nullable = true)
    private String nombre;
    @Column(nullable = true)
    private String sponsor;
    public static final String OBTENER_TODOS = "Equipos.obtenerTodos";
    public Equipo() {
        this.equipo = new ArrayList<>();
    }

    public Equipo(Persona dt, String nombre, String sponsor) {
        this.dt = dt;
        this.nombre = nombre;
        this.sponsor = sponsor;
    }

    public Equipo(Persona dt, List<Jugador> equipo, String sponsor, String nombre) {
        this.dt = dt;
        this.equipo = equipo;
        this.sponsor = sponsor;
        this.nombre = nombre;
    }

    public Equipo(int idEquipo, Persona dt, String nombre, String sponsor) {
        this.idEquipo = idEquipo;
        this.dt = dt;
        this.equipo = new ArrayList<>();
        this.nombre = nombre;
        this.sponsor = sponsor;
    }

    public int getIdEquipo() {
        return idEquipo;
    }
    public void addJugador(Jugador j){
        j.setEquipo(this);
        equipo.add(j);
    }
    public Persona getDt() {
        return dt;
    }
    public void setDt(Persona dt) {
        this.dt = dt;
    }
    public List<Jugador> getEquipo() {
        return equipo;
    }
    public void setEquipo(List<Jugador> equipo) {
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

    @Override
    public String toString() {
        return "Equipo{" +
                "idEquipo=" + idEquipo +
                ", nombre='" + nombre + '\'' +
                ", sponsor='" + sponsor + '\'' +
                ", dt=" + dt + '\'' +
                ", equipo=" + equipo +
                '}';
    }
}
