/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unam.mx.cella.modelo;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import unam.mx.cella.modelo.exceptions.NonexistentEntityException;

/**
 *
 * @author eduar
 */
public class ProfesorJpaController implements Serializable {

    public ProfesorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profesor profesor) {
        if (profesor.getKitCollection() == null) {
            profesor.setKitCollection(new ArrayList<Kit>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Kit> attachedKitCollection = new ArrayList<Kit>();
            for (Kit kitCollectionKitToAttach : profesor.getKitCollection()) {
                kitCollectionKitToAttach = em.getReference(kitCollectionKitToAttach.getClass(), kitCollectionKitToAttach.getId());
                attachedKitCollection.add(kitCollectionKitToAttach);
            }
            profesor.setKitCollection(attachedKitCollection);
            em.persist(profesor);
            for (Kit kitCollectionKit : profesor.getKitCollection()) {
                Profesor oldIdProfesorOfKitCollectionKit = kitCollectionKit.getIdProfesor();
                kitCollectionKit.setIdProfesor(profesor);
                kitCollectionKit = em.merge(kitCollectionKit);
                if (oldIdProfesorOfKitCollectionKit != null) {
                    oldIdProfesorOfKitCollectionKit.getKitCollection().remove(kitCollectionKit);
                    oldIdProfesorOfKitCollectionKit = em.merge(oldIdProfesorOfKitCollectionKit);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Profesor profesor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profesor persistentProfesor = em.find(Profesor.class, profesor.getId());
            Collection<Kit> kitCollectionOld = persistentProfesor.getKitCollection();
            Collection<Kit> kitCollectionNew = profesor.getKitCollection();
            Collection<Kit> attachedKitCollectionNew = new ArrayList<Kit>();
            for (Kit kitCollectionNewKitToAttach : kitCollectionNew) {
                kitCollectionNewKitToAttach = em.getReference(kitCollectionNewKitToAttach.getClass(), kitCollectionNewKitToAttach.getId());
                attachedKitCollectionNew.add(kitCollectionNewKitToAttach);
            }
            kitCollectionNew = attachedKitCollectionNew;
            profesor.setKitCollection(kitCollectionNew);
            profesor = em.merge(profesor);
            for (Kit kitCollectionOldKit : kitCollectionOld) {
                if (!kitCollectionNew.contains(kitCollectionOldKit)) {
                    kitCollectionOldKit.setIdProfesor(null);
                    kitCollectionOldKit = em.merge(kitCollectionOldKit);
                }
            }
            for (Kit kitCollectionNewKit : kitCollectionNew) {
                if (!kitCollectionOld.contains(kitCollectionNewKit)) {
                    Profesor oldIdProfesorOfKitCollectionNewKit = kitCollectionNewKit.getIdProfesor();
                    kitCollectionNewKit.setIdProfesor(profesor);
                    kitCollectionNewKit = em.merge(kitCollectionNewKit);
                    if (oldIdProfesorOfKitCollectionNewKit != null && !oldIdProfesorOfKitCollectionNewKit.equals(profesor)) {
                        oldIdProfesorOfKitCollectionNewKit.getKitCollection().remove(kitCollectionNewKit);
                        oldIdProfesorOfKitCollectionNewKit = em.merge(oldIdProfesorOfKitCollectionNewKit);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = profesor.getId();
                if (findProfesor(id) == null) {
                    throw new NonexistentEntityException("The profesor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profesor profesor;
            try {
                profesor = em.getReference(Profesor.class, id);
                profesor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profesor with id " + id + " no longer exists.", enfe);
            }
            Collection<Kit> kitCollection = profesor.getKitCollection();
            for (Kit kitCollectionKit : kitCollection) {
                kitCollectionKit.setIdProfesor(null);
                kitCollectionKit = em.merge(kitCollectionKit);
            }
            em.remove(profesor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Profesor> findProfesorEntities() {
        return findProfesorEntities(true, -1, -1);
    }

    public List<Profesor> findProfesorEntities(int maxResults, int firstResult) {
        return findProfesorEntities(false, maxResults, firstResult);
    }

    private List<Profesor> findProfesorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profesor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Profesor findProfesor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profesor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfesorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profesor> rt = cq.from(Profesor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
      public Profesor findProfesor(String usuario) {
        EntityManager em;
        em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Profesor.findByNombreusuario")
                .setParameter("nombreusuario",usuario);
                
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (Profesor) q.getSingleResult();
    }
    
    public Profesor findCorreo(String correo) {
        EntityManager em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Profesor.findByCorreo")
                .setParameter("correo",correo);
                
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (Profesor) q.getSingleResult();
    }
    
   

    
}
