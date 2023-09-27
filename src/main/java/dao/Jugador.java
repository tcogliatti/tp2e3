package dao;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
public class Jugador extends Persona {
    private String posicion;
    @ManyToOne
    private Equipo equipo;
    public Jugador() {

    }
    public Jugador(Persona p){
        super(p.getNombre(), p.getMail(), p.getNacimiento());
        posicion = null;
        equipo = null;
    }
    public Jugador(String posicion, Equipo equipo) {
        this.posicion = posicion;
        this.equipo = equipo;
    }

    public Jugador(String nombre, String mail, Date nacimiento, String posicion, Equipo equipo) {
        super(nombre, mail, nacimiento);
        this.posicion = posicion;
        this.equipo = equipo;
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
                "idPersona=" + idPersona +
                ", nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", nacimiento=" + nacimiento +
                "posicion='" + posicion + '\'' +
                ", equipo=" + equipo +
                '}';
    }
}
