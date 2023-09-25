package dao;

import javax.persistence.*;
import java.util.List;

@Entity
public class Torneo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTorneo;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private int cupo;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Equipo> equipos;

    public Torneo() {
    }

    public Torneo(String nombre, int cupo, List<Equipo> equipos) {
        this.nombre = nombre;
        this.cupo = cupo;
        this.equipos = equipos;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
}
