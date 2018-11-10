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
public class SubcategoriasJpaController implements Serializable {

    public SubcategoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subcategorias subcategorias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(subcategorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subcategorias subcategorias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            subcategorias = em.merge(subcategorias);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subcategorias.getId();
                if (findSubcategorias(id) == null) {
                    throw new NonexistentEntityException("The subcategorias with id " + id + " no longer exists.");
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
            Subcategorias subcategorias;
            try {
                subcategorias = em.getReference(Subcategorias.class, id);
                subcategorias.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subcategorias with id " + id + " no longer exists.", enfe);
            }
            em.remove(subcategorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subcategorias> findSubcategoriasEntities() {
        return findSubcategoriasEntities(true, -1, -1);
    }

    public List<Subcategorias> findSubcategoriasEntities(int maxResults, int firstResult) {
        return findSubcategoriasEntities(false, maxResults, firstResult);
    }

    private List<Subcategorias> findSubcategoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subcategorias.class));
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

    public Subcategorias findSubcategorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subcategorias.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubcategoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subcategorias> rt = cq.from(Subcategorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
