import dao.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CreateAndInsert {
    private static String path = "./src/main/java/csv/";
    private static final String PERSONAS_FILE = "personas.csv";
    private static final String TORNEOS_FILE = "torneos.csv";
    private static final String EQUIPOS_FILE = "equipos.csv";
    private static final String POSICIONES_FILE = "posiciones.csv";
    private static final int CANTIDAD_DE_EQUIPOS = 10;
    private static final int ANIO_INICIAL = 1982;
    private static final int ANIO_FINAL = 2005;
    public static final int CANTIDAD_DE_TORNEOS = 6;
    protected final static String PERSISTENCE = "tp2e3";
    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE);
    protected static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) { ///////////////////////     MAIN      /////////////////////

        // coneccion
        em.getTransaction().begin();

        // carga de Personas
        cargarPersonas();

//        // carga de posiciones
//        cargaDePosiciones();
//
//        // carga de equipos
//        cargaDeEquipos();
//
//        // carga de Torneos
//        cargaDeTorneos();

        // enviando la transacción
        em.getTransaction().commit();

        // cierre
        em.close();
        emf.close();
    }

    ///////////////////////////////////////////////////////////     CARGA PERSONAS
    public static void cargarPersonas() {
        Persona per = null;
        CSVParser parser = null;
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path + PERSONAS_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.print("Cargando Personas --> ");
        for (CSVRecord row : parser) {
            Date nacimiento = obtenerNacimientoAleatoreo();
            per = new Persona(row.get("nombre"), row.get("email"), nacimiento);
            System.out.println(per);
            em.persist(per);
            System.out.print(".");
        }
        System.out.println("\n                  --> proceso terminado /_");
    }

    public static Date obtenerNacimientoAleatoreo() {
        Random random = new Random();
        int anioAleatorio = random.nextInt(ANIO_FINAL - ANIO_INICIAL + 1) + ANIO_INICIAL;
        int mesAleatorio = random.nextInt(12);
        int diaAleatorio = random.nextInt(28) + 1;
        Calendar fechaAleatoria = new GregorianCalendar(anioAleatorio, mesAleatorio, diaAleatorio);
        return fechaAleatoria.getTime();
    }
    ///////////////////////////////////////////////////////////     CARGA EQUIPOS
    public static void cargaDePosiciones(){
        Posicion pos = null;
        CSVParser parser = null;
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path + POSICIONES_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Cargando Posiciones --> ");
        for (CSVRecord row : parser) {
            pos = new Posicion(Integer.parseInt(row.get("nombre")), row.get("posicion"));
            System.out.println("\t"+pos);
            em.persist(pos);
        }
        System.out.println("\t\t\t--> proceso terminado /_");
    }

    ///////////////////////////////////////////////////////////     CARGA EQUIPOS
    public static void cargaDeEquipos() {
        ArrayList<String> nombres = obtenerNombre();
        ArrayList<Persona> personas = Select.obtenerTodasLasPErsonas();
        Jugador j;
        Equipo e;
        Persona p;
        Map<Integer, String> posiciones = new HashMap<>();
        posiciones.put(1, "arquero");
        posiciones.put(2, "defensa");
        posiciones.put(3, "medicampo");
        posiciones.put(4, "delantera");

        for (int i = 0; i < CANTIDAD_DE_EQUIPOS; i++) {
            // nuevo equipo
            e = new Equipo();

            // set nombre
            e.setNombre(obtenerNombreRandom(nombres));

            // determinar si lleva nombre de equipo o de sponsor
            int nombreOsponsor = getRandomEntreDosValores(0, 1);
            if (nombreOsponsor == 1)
                e.setSponsor(obtenerNombreRandom(nombres));
            else
                e.setSponsor(null);

            // set DT
            e.setDt(getPersonaRandom(personas));

            // persistiendo el equipo antes de cargar los jugadores
            em.persist(e);

            // armado de equipo seteando jugadores y sus posiciones
            // arquero
            p = getPersonaRandom(personas);
            j = new Jugador(p);
            j.setPosicion("arquero");
            j.setEquipo(e);
            em.merge(j);
            // defensa
            int cantDefensa = getRandomEntreDosValores(1, 4);
            for (int k = 0; k < cantDefensa; k++) {
                p = getPersonaRandom(personas);
                j = new Jugador(p);
                j.setPosicion(posiciones.get(2));
                j.setEquipo(e);
                em.merge(j);
            }
            // medicompo
            int cantMedioca = getRandomEntreDosValores(1, (6 - cantDefensa - 1));
            for (int k = 0; k < cantMedioca; k++) {
                p = getPersonaRandom(personas);
                j = new Jugador(p);
                j.setPosicion(posiciones.get(3));
                j.setEquipo(e);
                em.merge(j);
            }
            //delantera
            int cantDelante = 6 - cantDefensa - cantMedioca;
            for (int k = 0; k < cantDelante; k++) {
                p = getPersonaRandom(personas);
                j = new Jugador(p);
                j.setPosicion(posiciones.get(4));
                j.setEquipo(e);
                em.merge(j);
            }
            //suplentes
            int cantSuplent = getRandomEntreDosValores(0, 3);
            for (int k = 0; k < cantSuplent; k++) {
                p = getPersonaRandom(personas);
                j = new Jugador(p);
                j.setPosicion(posiciones.get(getRandomEntreDosValores(1, 4)));
                j.setEquipo(e);
                em.merge(j);
            }
        }
    }

    public static Persona getPersonaRandom(ArrayList<Persona> personas) {
        int index = getRandomEntreDosValores(0, personas.size() - 1);
        Persona p = personas.get(index);
        personas.remove(index);
        return p;
    }

    public static String obtenerNombreRandom(ArrayList<String> nombresList) {
        int nombreIndexRandom = getRandomEntreDosValores(0, nombresList.size() - 1);
        String nombre = nombresList.get(nombreIndexRandom);
        nombresList.remove(nombreIndexRandom);
        return nombre;
    }

    public static ArrayList<String> obtenerNombre() {
        CSVParser parser = null;
        ArrayList<String> nombres = new ArrayList<>();
        try {
            parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path + EQUIPOS_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (CSVRecord row : parser) {
            nombres.add(row.get("nombre"));
        }
        return nombres;
    }

    ///////////////////////////////////////////////////////////     CARGA TORNEOS

    public static void cargaDeTorneos(){
        ArrayList<String> nombres = obtenerNombre();
        ArrayList<Equipo> equipos = Select.obtenerTodasLosEquipos();
        Torneo torneo;
        HashMap<Integer, Integer> cupo = new HashMap<>();
        cupo.put(1,6);
        cupo.put(2,8);
        cupo.put(3,10);
        /*
        * Por iteración (definir cantidad de torneos)
        * 1- obtener nombre
        * 2- obtener cantidad aleatorea de equipos, 4, 6, 8
        * 3- obtener aleatoreamente los equipos participantes
        * */
        for (int i = 0; i < CANTIDAD_DE_TORNEOS ; i++) {
            ArrayList<Equipo> candidatos = new ArrayList<>(equipos);
            torneo = new Torneo();
            torneo.setNombre(obtenerNombreRandom(nombres));
            torneo.setCupo(cupo.get(getRandomEntreDosValores(1,3)));

            for (int j = 0; j < torneo.getCupo(); j++) {
                int index = getRandomEntreDosValores(0,candidatos.size()-1);
                Equipo e = candidatos.get(index);
                candidatos.remove(index);
                torneo.addEquipo(e);
            }
            em.persist(torneo);
        }
    }

    ///////////////////////////////////////////////////////////     Generales

    public static int getRandomEntreDosValores(int a, int b) {
        Random random = new Random();
        return random.nextInt(b - a + 1) + a;
    }


}
