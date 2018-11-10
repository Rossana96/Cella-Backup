/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unam.mx.cella.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import unam.mx.cella.modelo.exceptions.NonexistentEntityException;

/**
 *
 * @author eduar
 */
public class SolicitarprestamoprofesorJpaController implements Serializable {

    public SolicitarprestamoprofesorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solicitarprestamoprofesor solicitarprestamoprofesor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Unidadmaterial idUnidadMaterial = solicitarprestamoprofesor.getIdUnidadMaterial();
            if (idUnidadMaterial != null) {
                idUnidadMaterial = em.getReference(idUnidadMaterial.getClass(), idUnidadMaterial.getId());
                solicitarprestamoprofesor.setIdUnidadMaterial(idUnidadMaterial);
            }
            em.persist(solicitarprestamoprofesor);
            if (idUnidadMaterial != null) {
                idUnidadMaterial.getSolicitarprestamoprofesorCollection().add(solicitarprestamoprofesor);
                idUnidadMaterial = em.merge(idUnidadMaterial);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Solicitarprestamoprofesor solicitarprestamoprofesor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitarprestamoprofesor persistentSolicitarprestamoprofesor = em.find(Solicitarprestamoprofesor.class, solicitarprestamoprofesor.getId());
            Unidadmaterial idUnidadMaterialOld = persistentSolicitarprestamoprofesor.getIdUnidadMaterial();
            Unidadmaterial idUnidadMaterialNew = solicitarprestamoprofesor.getIdUnidadMaterial();
            if (idUnidadMaterialNew != null) {
                idUnidadMaterialNew = em.getReference(idUnidadMaterialNew.getClass(), idUnidadMaterialNew.getId());
                solicitarprestamoprofesor.setIdUnidadMaterial(idUnidadMaterialNew);
            }
            solicitarprestamoprofesor = em.merge(solicitarprestamoprofesor);
            if (idUnidadMaterialOld != null && !idUnidadMaterialOld.equals(idUnidadMaterialNew)) {
                idUnidadMaterialOld.getSolicitarprestamoprofesorCollection().remove(solicitarprestamoprofesor);
                idUnidadMaterialOld = em.merge(idUnidadMaterialOld);
            }
            if (idUnidadMaterialNew != null && !idUnidadMaterialNew.equals(idUnidadMaterialOld)) {
                idUnidadMaterialNew.getSolicitarprestamoprofesorCollection().add(solicitarprestamoprofesor);
                idUnidadMaterialNew = em.merge(idUnidadMaterialNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitarprestamoprofesor.getId();
                if (findSolicitarprestamoprofesor(id) == null) {
                    throw new NonexistentEntityException("The solicitarprestamoprofesor with id " + id + " no longer exists.");
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
            Solicitarprestamoprofesor solicitarprestamoprofesor;
            try {
                solicitarprestamoprofesor = em.getReference(Solicitarprestamoprofesor.class, id);
                solicitarprestamoprofesor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitarprestamoprofesor with id " + id + " no longer exists.", enfe);
            }
            Unidadmaterial idUnidadMaterial = solicitarprestamoprofesor.getIdUnidadMaterial();
            if (idUnidadMaterial != null) {
                idUnidadMaterial.getSolicitarprestamoprofesorCollection().remove(solicitarprestamoprofesor);
                idUnidadMaterial = em.merge(idUnidadMaterial);
            }
            em.remove(solicitarprestamoprofesor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Solicitarprestamoprofesor> findSolicitarprestamoprofesorEntities() {
        return findSolicitarprestamoprofesorEntities(true, -1, -1);
    }

    public List<Solicitarprestamoprofesor> findSolicitarprestamoprofesorEntities(int maxResults, int firstResult) {
        return findSolicitarprestamoprofesorEntities(false, maxResults, firstResult);
    }

    private List<Solicitarprestamoprofesor> findSolicitarprestamoprofesorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitarprestamoprofesor.class));
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

    public Solicitarprestamoprofesor findSolicitarprestamoprofesor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solicitarprestamoprofesor.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitarprestamoprofesorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitarprestamoprofesor> rt = cq.from(Solicitarprestamoprofesor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
