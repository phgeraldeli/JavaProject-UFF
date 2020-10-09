package javaProject.macro.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;

public class EntityFactory {

    private static EntityFactory factory = null;
    private EntityManagerFactory emf = null;

    private EntityFactory() {
        try {
            emf = Persistence.createEntityManagerFactory("macro");
        } catch ( Throwable e ) {
            e.printStackTrace();
            System.out.println(">>>>> Mensagem de erro: " + e.getMessage());
        }
    }

    public static EntityManager criarSessao() {
        if (factory == null) {
            factory = new EntityFactory();
        }
        return factory.emf.createEntityManager();
    }

    public static void closeFactory() {
        if (Objects.nonNull(factory) && Objects.nonNull(factory.emf)) {
            factory.emf.close();
        }
    }
}
