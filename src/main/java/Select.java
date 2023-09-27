import dao.Equipo;
import dao.Persona;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class Select {
    protected final static String PERSISTENCE = "tp2e3";
    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE);
    protected static EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {

    }
    public static ArrayList<Persona> obtenerTodasLasPErsonas(){
        Query personaQuery = em.createNamedQuery(Persona.OBTENER_TODOS);
        List<Persona> personaList = personaQuery.getResultList();
        return new ArrayList<>(personaList);
    }
    public  static  ArrayList<Equipo> obtenerTodasLosEquipos(){
        Query equipoQuery = em.createNamedQuery(Equipo.OBTENER_TODOS);
        List<Equipo> equipoList = equipoQuery.getResultList();
        return new ArrayList<>(equipoList);
    }
}
