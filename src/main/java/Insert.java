import dao.Equipo;
import dao.Jugador;
import dao.Persona;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Insert {
    private static String path = "./src/main/java/csv/";
    private static final String PERSONAS_FILE = "personas.csv";
    private static final String TORNEOS_FILE = "torneos.csv";
    private static final String EQUIPOS_FILE = "equipos.csv";
    private static final String POSICIONES_FILE = "posiciones.csv";
    private static final int CANTIDAD_DE_EQUIPOS = 9;
    private static final int ANIO_INICIAL = 1982;
    private static final int ANIO_FINAL = 2005;
    protected final static String PERSISTENCE = "tp2e3";
    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE);
    protected static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        // coneccion
        em.getTransaction().begin();

        // carga de Personas
//        cargarPersonas();

        // carga de equipos
        cargaDeEquipos();
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
        int añoAleatorio = random.nextInt(ANIO_FINAL - ANIO_INICIAL + 1) + ANIO_INICIAL;
        int mesAleatorio = random.nextInt(12);
        int diaAleatorio = random.nextInt(28) + 1;
        Calendar fechaAleatoria = new GregorianCalendar(añoAleatorio, mesAleatorio, diaAleatorio);
        return fechaAleatoria.getTime();
    }

    ///////////////////////////////////////////////////////////     CARGA EQUIPOS
    public static void cargaDeEquipos() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
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
            int tamanioEquipo = getRandomEntreDosValores(Equipo.MINIMO_JUGADORES, Equipo.MAXIMO_JUGADORES);
            e = new Equipo();

            // set nombre
            e.setNombre(obtenerNombreRandom(nombres));

            // determinar si lleva nombre de equipo o de sponsor
            int nombreOsponsor = getRandomEntreDosValores(0, 1);
            if (nombreOsponsor == 1)
                e.setSponsor(obtenerNombreRandom(nombres));
            else
                e.setNombre(null);

            // set DT
            int indexPersona = getRandomEntreDosValores(1, personas.size()) - 1;
            p = em.find(Persona.class, indexPersona);
            e.setDt(p);

            for (int ii = 0; ii < tamanioEquipo; ii++) {
                // seleccionar Persona
                indexPersona = getRandomEntreDosValores(1, personas.size()) - 1;
                j = new Jugador(em.find(Persona.class, indexPersona));

                // set equipo
                if (jugadores.size() == 0) {
                    j.setPosicion("arquero");
                } else {
                    int cantDefensa = getRandomEntreDosValores(1, 4);
                    for (int k = 0; k < cantDefensa; k++) {
                        j = new Jugador(em.find(Persona.class, indexPersona));
                        j.setPosicion(posiciones.get(2));
                        e.addJugador(j);
                    }
                    int cantMedioca = getRandomEntreDosValores(1, (6 - cantDefensa - 1));
                    for (int k = 0; k < cantMedioca; k++) {
                        j = new Jugador(em.find(Persona.class, indexPersona));
                        j.setPosicion(posiciones.get(3));
                        e.addJugador(j);
                    }
                    int cantDelante = 6 - cantDefensa - cantMedioca;
                    for (int k = 0; k < cantDelante; k++) {
                        j = new Jugador(em.find(Persona.class, indexPersona));
                        j.setPosicion(posiciones.get(4));
                        e.addJugador(j);
                    }
                    int cantSuplent = getRandomEntreDosValores(0, 3);
                    for (int k = 0; k < cantSuplent; k++) {
                        j = new Jugador(em.find(Persona.class, indexPersona));
                        j.setPosicion(posiciones.get(getRandomEntreDosValores(1, 4)));
                        e.addJugador(j);
                    }
                }
            }
        }
    }

    public static String obtenerNombreRandom(ArrayList<String> nombresList) {
        int nombreIndexRandom = getRandomEntreDosValores(0, nombresList.size() - 1);
        String nombre = nombresList.get(nombreIndexRandom);
        nombresList.remove(nombreIndexRandom);
        return nombre;
    }

    public static int getRandomEntreDosValores(int a, int b) {
        Random random = new Random();
        return random.nextInt(b - a + 1) + a;
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

}
