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
public class SolicitarprestamoalumnoJpaController implements Serializable {

    public SolicitarprestamoalumnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Solicitarprestamoalumno solicitarprestamoalumno) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Unidadmaterial idUnidadMaterial = solicitarprestamoalumno.getIdUnidadMaterial();
            if (idUnidadMaterial != null) {
                idUnidadMaterial = em.getReference(idUnidadMaterial.getClass(), idUnidadMaterial.getId());
                solicitarprestamoalumno.setIdUnidadMaterial(idUnidadMaterial);
            }
            em.persist(solicitarprestamoalumno);
            if (idUnidadMaterial != null) {
                idUnidadMaterial.getSolicitarprestamoalumnoCollection().add(solicitarprestamoalumno);
                idUnidadMaterial = em.merge(idUnidadMaterial);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Solicitarprestamoalumno solicitarprestamoalumno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Solicitarprestamoalumno persistentSolicitarprestamoalumno = em.find(Solicitarprestamoalumno.class, solicitarprestamoalumno.getId());
            Unidadmaterial idUnidadMaterialOld = persistentSolicitarprestamoalumno.getIdUnidadMaterial();
            Unidadmaterial idUnidadMaterialNew = solicitarprestamoalumno.getIdUnidadMaterial();
            if (idUnidadMaterialNew != null) {
                idUnidadMaterialNew = em.getReference(idUnidadMaterialNew.getClass(), idUnidadMaterialNew.getId());
                solicitarprestamoalumno.setIdUnidadMaterial(idUnidadMaterialNew);
            }
            solicitarprestamoalumno = em.merge(solicitarprestamoalumno);
            if (idUnidadMaterialOld != null && !idUnidadMaterialOld.equals(idUnidadMaterialNew)) {
                idUnidadMaterialOld.getSolicitarprestamoalumnoCollection().remove(solicitarprestamoalumno);
                idUnidadMaterialOld = em.merge(idUnidadMaterialOld);
            }
            if (idUnidadMaterialNew != null && !idUnidadMaterialNew.equals(idUnidadMaterialOld)) {
                idUnidadMaterialNew.getSolicitarprestamoalumnoCollection().add(solicitarprestamoalumno);
                idUnidadMaterialNew = em.merge(idUnidadMaterialNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = solicitarprestamoalumno.getId();
                if (findSolicitarprestamoalumno(id) == null) {
                    throw new NonexistentEntityException("The solicitarprestamoalumno with id " + id + " no longer exists.");
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
            Solicitarprestamoalumno solicitarprestamoalumno;
            try {
                solicitarprestamoalumno = em.getReference(Solicitarprestamoalumno.class, id);
                solicitarprestamoalumno.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The solicitarprestamoalumno with id " + id + " no longer exists.", enfe);
            }
            Unidadmaterial idUnidadMaterial = solicitarprestamoalumno.getIdUnidadMaterial();
            if (idUnidadMaterial != null) {
                idUnidadMaterial.getSolicitarprestamoalumnoCollection().remove(solicitarprestamoalumno);
                idUnidadMaterial = em.merge(idUnidadMaterial);
            }
            em.remove(solicitarprestamoalumno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Solicitarprestamoalumno> findSolicitarprestamoalumnoEntities() {
        return findSolicitarprestamoalumnoEntities(true, -1, -1);
    }

    public List<Solicitarprestamoalumno> findSolicitarprestamoalumnoEntities(int maxResults, int firstResult) {
        return findSolicitarprestamoalumnoEntities(false, maxResults, firstResult);
    }

    private List<Solicitarprestamoalumno> findSolicitarprestamoalumnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Solicitarprestamoalumno.class));
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

    public Solicitarprestamoalumno findSolicitarprestamoalumno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solicitarprestamoalumno.class, id);
        } finally {
            em.close();
        }
    }

    public int getSolicitarprestamoalumnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Solicitarprestamoalumno> rt = cq.from(Solicitarprestamoalumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
