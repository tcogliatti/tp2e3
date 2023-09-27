import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test {
    protected final static String PERSISTENCE = "tp2e3";
    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE);
    protected static EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {

    }
}
