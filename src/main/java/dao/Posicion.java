package dao;

import javax.persistence.*;

@Entity
public class Posicion {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String posicion;
    public Posicion() {
    }
    public Posicion(int id, String posicion) {
        this.id = id;
        this.posicion = posicion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return "Posicion{" +
                "id=" + id +
                ", posicion='" + posicion + '\'' +
                '}';
    }
}
