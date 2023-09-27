package dao;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@NamedQuery(name = Persona.OBTENER_TODOS, query = "SELECT p FROM Persona p")
public class Persona {
    @Id
    private int idPersona;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date nacimiento;

    public static final String OBTENER_TODOS = "Persona.obtenerTodos";

    public Persona() {
    }

    public Persona(String nombre, String mail, Date nacimiento) {
        this.nombre = nombre;
        this.mail = mail;
        this.nacimiento = nacimiento;
    }

    public Persona(int idPersona, String nombre, String mail, Date nacimiento) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.mail = mail;
        this.nacimiento = nacimiento;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", nombre='" + nombre + '\'' +
                ", mail='" + mail + '\'' +
                ", nacimiento=" + nacimiento +
                '}';
    }
}
