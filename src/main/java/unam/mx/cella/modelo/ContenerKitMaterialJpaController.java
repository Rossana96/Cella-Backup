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
public class ContenerKitMaterialJpaController implements Serializable {

    public ContenerKitMaterialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContenerKitMaterial contenerKitMaterial) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Material idMaterial = contenerKitMaterial.getIdMaterial();
            if (idMaterial != null) {
                idMaterial = em.getReference(idMaterial.getClass(), idMaterial.getId());
                contenerKitMaterial.setIdMaterial(idMaterial);
            }
            em.persist(contenerKitMaterial);
            if (idMaterial != null) {
                idMaterial.getContenerKitMaterialCollection().add(contenerKitMaterial);
                idMaterial = em.merge(idMaterial);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContenerKitMaterial contenerKitMaterial) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContenerKitMaterial persistentContenerKitMaterial = em.find(ContenerKitMaterial.class, contenerKitMaterial.getId());
            Material idMaterialOld = persistentContenerKitMaterial.getIdMaterial();
            Material idMaterialNew = contenerKitMaterial.getIdMaterial();
            if (idMaterialNew != null) {
                idMaterialNew = em.getReference(idMaterialNew.getClass(), idMaterialNew.getId());
                contenerKitMaterial.setIdMaterial(idMaterialNew);
            }
            contenerKitMaterial = em.merge(contenerKitMaterial);
            if (idMaterialOld != null && !idMaterialOld.equals(idMaterialNew)) {
                idMaterialOld.getContenerKitMaterialCollection().remove(contenerKitMaterial);
                idMaterialOld = em.merge(idMaterialOld);
            }
            if (idMaterialNew != null && !idMaterialNew.equals(idMaterialOld)) {
                idMaterialNew.getContenerKitMaterialCollection().add(contenerKitMaterial);
                idMaterialNew = em.merge(idMaterialNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contenerKitMaterial.getId();
                if (findContenerKitMaterial(id) == null) {
                    throw new NonexistentEntityException("The contenerKitMaterial with id " + id + " no longer exists.");
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
            ContenerKitMaterial contenerKitMaterial;
            try {
                contenerKitMaterial = em.getReference(ContenerKitMaterial.class, id);
                contenerKitMaterial.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contenerKitMaterial with id " + id + " no longer exists.", enfe);
            }
            Material idMaterial = contenerKitMaterial.getIdMaterial();
            if (idMaterial != null) {
                idMaterial.getContenerKitMaterialCollection().remove(contenerKitMaterial);
                idMaterial = em.merge(idMaterial);
            }
            em.remove(contenerKitMaterial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContenerKitMaterial> findContenerKitMaterialEntities() {
        return findContenerKitMaterialEntities(true, -1, -1);
    }

    public List<ContenerKitMaterial> findContenerKitMaterialEntities(int maxResults, int firstResult) {
        return findContenerKitMaterialEntities(false, maxResults, firstResult);
    }

    private List<ContenerKitMaterial> findContenerKitMaterialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContenerKitMaterial.class));
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

    public ContenerKitMaterial findContenerKitMaterial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContenerKitMaterial.class, id);
        } finally {
            em.close();
        }
    }

    public int getContenerKitMaterialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContenerKitMaterial> rt = cq.from(ContenerKitMaterial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
