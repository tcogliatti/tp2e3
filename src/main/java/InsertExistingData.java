import dao.Equipo;
import dao.Jugador;
import dao.Persona;
import dao.Torneo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InsertExistingData {
    private static String path = "./src/main/resources/csv/";
    private static final String PERSONAS_FILE = "personas.csv";
    private static final String TORNEOS_FILE = "torneos.csv";
    private static final String EQUIPOS_FILE = "equipos.csv";
    private static final String TORNEO_EQUIPO_FILE = "torneo_equipo.csv";
    private static final String JUGADOR_FILE = "jugador.csv";
    protected final static String PERSISTENCE = "tp2e3_test";
    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE);
    protected static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) { ///////////////////////     MAIN      /////////////////////

        try {
            // coneccion
            em.getTransaction().begin();

            // carga de Personas
            cargarPersonas();

            // carga de equipos
            cargaDeEquipos();

            // ejecutar transacción
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return;
        }

        try {
            // coneccion
            em.getTransaction().begin();

            // carga de jugadores
            cargaDeJugadores();

            // carga de Torneos
            cargaDeTorneos();

            // ejecutar transacción
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return;
        }

        try {
            // coneccion
            em.getTransaction().begin();

            // carga de equipos a torneos
            cargaDeEquiposAtorneos();

            // ejecutar transacción
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return;
        } finally {
            // cierre
            em.close();
            emf.close();
        }
    }

    ///////////////////////////////////////////////////////////     CARGA PERSONAS
    public static void cargarPersonas() {
        Persona per;
        CSVParser parser;

        // carga CSV
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path + PERSONAS_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Cargando Personas --> ");
        for (CSVRecord row : parser) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date nacimiento = null;
            try {
                nacimiento = sdf.parse(row.get("nacimiento"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
//            Date nacimiento = new Date(row.get("nacimiento"));
            per = new Persona(Integer.parseInt(row.get("idPersona")), row.get("nombre"), row.get("mail"), nacimiento);
            System.out.println("\t"+per);
            em.persist(per);
        }
        System.out.println("\n                  --> proceso terminado /_");
    }

    ///////////////////////////////////////////////////////////     CARGA EQUIPOS
    public static void cargaDeEquipos() {
        Equipo equipo;
        Persona persona;
        CSVParser parser;

        // carga CSV
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path + EQUIPOS_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Cargando Equipos --> ");
        for (CSVRecord row : parser) {
            persona = em.find(Persona.class, Integer.parseInt(row.get("dt_idPersona")));
            equipo = new Equipo(Integer.parseInt(row.get("idEquipo")), persona, row.get("nombre"), row.get("sponsor"));
            System.out.println("\t"+equipo);
            em.persist(equipo);
        }
        System.out.println("\n                  --> proceso terminado /_");
    }

    ///////////////////////////////////////////////////////////     CARGA JUGADORES
    public static void cargaDeJugadores() {
        Equipo equipo;
        Persona persona;
        Jugador jugador;
        CSVParser parser;

        // carga CSV
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path + JUGADOR_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Cargando Jugadores --> ");
        for (CSVRecord row : parser) {
            persona = em.find(Persona.class, Integer.parseInt(row.get("persona_idPersona")));
            equipo = em.find(Equipo.class, Integer.parseInt(row.get("equipo_idEquipo")));
            jugador = new Jugador(persona, row.get("posicion"), equipo);
            System.out.println("\t"+jugador);
            em.persist(jugador);
        }
        System.out.println("\n                  --> proceso terminado /_");
    }

    ///////////////////////////////////////////////////////////     CARGA TORNEOS

    public static void cargaDeTorneos(){
        Torneo torneo;
        CSVParser parser;

        // carga CSV
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path + TORNEOS_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Cargando Torneos --> ");
        for (CSVRecord row : parser) {
            torneo = new Torneo(Integer.parseInt(row.get("idTorneo")), row.get("nombre"), Integer.parseInt(row.get("cupo")));
            System.out.println("\t"+torneo);
            em.persist(torneo);
        }
        System.out.println("\n                  --> proceso terminado /_");
    }

    ///////////////////////////////////////////////////////////     CARGA EQUIPOS A TORNEOS
    public static void cargaDeEquiposAtorneos(){
        Torneo torneo = new Torneo();
        Equipo equipo;
        CSVParser parser;

        // carga CSV
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path + TORNEO_EQUIPO_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Cargando Equipos a Torneos --> ");
        Integer idTorneo = null;
        Integer prevIdTorneo = null;
        for (CSVRecord row : parser) {
            idTorneo = Integer.parseInt(row.get("Torneo_idTorneo"));
            if(!idTorneo.equals(prevIdTorneo) || idTorneo.equals(null)){
                torneo = em.find(Torneo.class, idTorneo);
                prevIdTorneo = idTorneo;
            }
            equipo = em.find(Equipo.class, Integer.parseInt(row.get("equipos_idEquipo")));
            torneo.addEquipo(equipo);
            System.out.println("\t.");
        }
        System.out.println("\n                  --> proceso terminado /_");
    }
}
