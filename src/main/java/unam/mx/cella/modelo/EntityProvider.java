package unam.mx.cella.modelo;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author miguel
 */
public class EntityProvider {

    private static EntityManagerFactory _emf;

    private EntityProvider() {
    }

    public static EntityManagerFactory provider() {
        if (_emf == null) {
            _emf = Persistence.createEntityManagerFactory("unam.mx.cella.proyecto_mi-primer-aplicacion-web_war_1.0-SNAPSHOTPU");
        }
        return _emf;
    }

}