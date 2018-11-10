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
public class PertenecerMaterialCategoriaJpaController implements Serializable {

    public PertenecerMaterialCategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PertenecerMaterialCategoria pertenecerMaterialCategoria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Material idMaterial = pertenecerMaterialCategoria.getIdMaterial();
            if (idMaterial != null) {
                idMaterial = em.getReference(idMaterial.getClass(), idMaterial.getId());
                pertenecerMaterialCategoria.setIdMaterial(idMaterial);
            }
            em.persist(pertenecerMaterialCategoria);
            if (idMaterial != null) {
                idMaterial.getPertenecerMaterialCategoriaCollection().add(pertenecerMaterialCategoria);
                idMaterial = em.merge(idMaterial);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PertenecerMaterialCategoria pertenecerMaterialCategoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PertenecerMaterialCategoria persistentPertenecerMaterialCategoria = em.find(PertenecerMaterialCategoria.class, pertenecerMaterialCategoria.getId());
            Material idMaterialOld = persistentPertenecerMaterialCategoria.getIdMaterial();
            Material idMaterialNew = pertenecerMaterialCategoria.getIdMaterial();
            if (idMaterialNew != null) {
                idMaterialNew = em.getReference(idMaterialNew.getClass(), idMaterialNew.getId());
                pertenecerMaterialCategoria.setIdMaterial(idMaterialNew);
            }
            pertenecerMaterialCategoria = em.merge(pertenecerMaterialCategoria);
            if (idMaterialOld != null && !idMaterialOld.equals(idMaterialNew)) {
                idMaterialOld.getPertenecerMaterialCategoriaCollection().remove(pertenecerMaterialCategoria);
                idMaterialOld = em.merge(idMaterialOld);
            }
            if (idMaterialNew != null && !idMaterialNew.equals(idMaterialOld)) {
                idMaterialNew.getPertenecerMaterialCategoriaCollection().add(pertenecerMaterialCategoria);
                idMaterialNew = em.merge(idMaterialNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pertenecerMaterialCategoria.getId();
                if (findPertenecerMaterialCategoria(id) == null) {
                    throw new NonexistentEntityException("The pertenecerMaterialCategoria with id " + id + " no longer exists.");
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
            PertenecerMaterialCategoria pertenecerMaterialCategoria;
            try {
                pertenecerMaterialCategoria = em.getReference(PertenecerMaterialCategoria.class, id);
                pertenecerMaterialCategoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pertenecerMaterialCategoria with id " + id + " no longer exists.", enfe);
            }
            Material idMaterial = pertenecerMaterialCategoria.getIdMaterial();
            if (idMaterial != null) {
                idMaterial.getPertenecerMaterialCategoriaCollection().remove(pertenecerMaterialCategoria);
                idMaterial = em.merge(idMaterial);
            }
            em.remove(pertenecerMaterialCategoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PertenecerMaterialCategoria> findPertenecerMaterialCategoriaEntities() {
        return findPertenecerMaterialCategoriaEntities(true, -1, -1);
    }

    public List<PertenecerMaterialCategoria> findPertenecerMaterialCategoriaEntities(int maxResults, int firstResult) {
        return findPertenecerMaterialCategoriaEntities(false, maxResults, firstResult);
    }

    private List<PertenecerMaterialCategoria> findPertenecerMaterialCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PertenecerMaterialCategoria.class));
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

    public PertenecerMaterialCategoria findPertenecerMaterialCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PertenecerMaterialCategoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getPertenecerMaterialCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PertenecerMaterialCategoria> rt = cq.from(PertenecerMaterialCategoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
