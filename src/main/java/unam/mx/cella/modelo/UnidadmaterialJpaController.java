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
import unam.mx.cella.modelo.exceptions.IllegalOrphanException;
import unam.mx.cella.modelo.exceptions.NonexistentEntityException;

/**
 *
 * @author eduar
 */
public class UnidadmaterialJpaController implements Serializable {

    public UnidadmaterialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Unidadmaterial unidadmaterial) {
        if (unidadmaterial.getSolicitarprestamoprofesorCollection() == null) {
            unidadmaterial.setSolicitarprestamoprofesorCollection(new ArrayList<Solicitarprestamoprofesor>());
        }
        if (unidadmaterial.getSolicitarprestamoalumnoCollection() == null) {
            unidadmaterial.setSolicitarprestamoalumnoCollection(new ArrayList<Solicitarprestamoalumno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Material idMaterial = unidadmaterial.getIdMaterial();
            if (idMaterial != null) {
                idMaterial = em.getReference(idMaterial.getClass(), idMaterial.getId());
                unidadmaterial.setIdMaterial(idMaterial);
            }
            Collection<Solicitarprestamoprofesor> attachedSolicitarprestamoprofesorCollection = new ArrayList<Solicitarprestamoprofesor>();
            for (Solicitarprestamoprofesor solicitarprestamoprofesorCollectionSolicitarprestamoprofesorToAttach : unidadmaterial.getSolicitarprestamoprofesorCollection()) {
                solicitarprestamoprofesorCollectionSolicitarprestamoprofesorToAttach = em.getReference(solicitarprestamoprofesorCollectionSolicitarprestamoprofesorToAttach.getClass(), solicitarprestamoprofesorCollectionSolicitarprestamoprofesorToAttach.getId());
                attachedSolicitarprestamoprofesorCollection.add(solicitarprestamoprofesorCollectionSolicitarprestamoprofesorToAttach);
            }
            unidadmaterial.setSolicitarprestamoprofesorCollection(attachedSolicitarprestamoprofesorCollection);
            Collection<Solicitarprestamoalumno> attachedSolicitarprestamoalumnoCollection = new ArrayList<Solicitarprestamoalumno>();
            for (Solicitarprestamoalumno solicitarprestamoalumnoCollectionSolicitarprestamoalumnoToAttach : unidadmaterial.getSolicitarprestamoalumnoCollection()) {
                solicitarprestamoalumnoCollectionSolicitarprestamoalumnoToAttach = em.getReference(solicitarprestamoalumnoCollectionSolicitarprestamoalumnoToAttach.getClass(), solicitarprestamoalumnoCollectionSolicitarprestamoalumnoToAttach.getId());
                attachedSolicitarprestamoalumnoCollection.add(solicitarprestamoalumnoCollectionSolicitarprestamoalumnoToAttach);
            }
            unidadmaterial.setSolicitarprestamoalumnoCollection(attachedSolicitarprestamoalumnoCollection);
            em.persist(unidadmaterial);
            if (idMaterial != null) {
                idMaterial.getUnidadmaterialCollection().add(unidadmaterial);
                idMaterial = em.merge(idMaterial);
            }
            for (Solicitarprestamoprofesor solicitarprestamoprofesorCollectionSolicitarprestamoprofesor : unidadmaterial.getSolicitarprestamoprofesorCollection()) {
                Unidadmaterial oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionSolicitarprestamoprofesor = solicitarprestamoprofesorCollectionSolicitarprestamoprofesor.getIdUnidadMaterial();
                solicitarprestamoprofesorCollectionSolicitarprestamoprofesor.setIdUnidadMaterial(unidadmaterial);
                solicitarprestamoprofesorCollectionSolicitarprestamoprofesor = em.merge(solicitarprestamoprofesorCollectionSolicitarprestamoprofesor);
                if (oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionSolicitarprestamoprofesor != null) {
                    oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionSolicitarprestamoprofesor.getSolicitarprestamoprofesorCollection().remove(solicitarprestamoprofesorCollectionSolicitarprestamoprofesor);
                    oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionSolicitarprestamoprofesor = em.merge(oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionSolicitarprestamoprofesor);
                }
            }
            for (Solicitarprestamoalumno solicitarprestamoalumnoCollectionSolicitarprestamoalumno : unidadmaterial.getSolicitarprestamoalumnoCollection()) {
                Unidadmaterial oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionSolicitarprestamoalumno = solicitarprestamoalumnoCollectionSolicitarprestamoalumno.getIdUnidadMaterial();
                solicitarprestamoalumnoCollectionSolicitarprestamoalumno.setIdUnidadMaterial(unidadmaterial);
                solicitarprestamoalumnoCollectionSolicitarprestamoalumno = em.merge(solicitarprestamoalumnoCollectionSolicitarprestamoalumno);
                if (oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionSolicitarprestamoalumno != null) {
                    oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionSolicitarprestamoalumno.getSolicitarprestamoalumnoCollection().remove(solicitarprestamoalumnoCollectionSolicitarprestamoalumno);
                    oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionSolicitarprestamoalumno = em.merge(oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionSolicitarprestamoalumno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Unidadmaterial unidadmaterial) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Unidadmaterial persistentUnidadmaterial = em.find(Unidadmaterial.class, unidadmaterial.getId());
            Material idMaterialOld = persistentUnidadmaterial.getIdMaterial();
            Material idMaterialNew = unidadmaterial.getIdMaterial();
            Collection<Solicitarprestamoprofesor> solicitarprestamoprofesorCollectionOld = persistentUnidadmaterial.getSolicitarprestamoprofesorCollection();
            Collection<Solicitarprestamoprofesor> solicitarprestamoprofesorCollectionNew = unidadmaterial.getSolicitarprestamoprofesorCollection();
            Collection<Solicitarprestamoalumno> solicitarprestamoalumnoCollectionOld = persistentUnidadmaterial.getSolicitarprestamoalumnoCollection();
            Collection<Solicitarprestamoalumno> solicitarprestamoalumnoCollectionNew = unidadmaterial.getSolicitarprestamoalumnoCollection();
            List<String> illegalOrphanMessages = null;
            for (Solicitarprestamoalumno solicitarprestamoalumnoCollectionOldSolicitarprestamoalumno : solicitarprestamoalumnoCollectionOld) {
                if (!solicitarprestamoalumnoCollectionNew.contains(solicitarprestamoalumnoCollectionOldSolicitarprestamoalumno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Solicitarprestamoalumno " + solicitarprestamoalumnoCollectionOldSolicitarprestamoalumno + " since its idUnidadMaterial field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idMaterialNew != null) {
                idMaterialNew = em.getReference(idMaterialNew.getClass(), idMaterialNew.getId());
                unidadmaterial.setIdMaterial(idMaterialNew);
            }
            Collection<Solicitarprestamoprofesor> attachedSolicitarprestamoprofesorCollectionNew = new ArrayList<Solicitarprestamoprofesor>();
            for (Solicitarprestamoprofesor solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesorToAttach : solicitarprestamoprofesorCollectionNew) {
                solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesorToAttach = em.getReference(solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesorToAttach.getClass(), solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesorToAttach.getId());
                attachedSolicitarprestamoprofesorCollectionNew.add(solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesorToAttach);
            }
            solicitarprestamoprofesorCollectionNew = attachedSolicitarprestamoprofesorCollectionNew;
            unidadmaterial.setSolicitarprestamoprofesorCollection(solicitarprestamoprofesorCollectionNew);
            Collection<Solicitarprestamoalumno> attachedSolicitarprestamoalumnoCollectionNew = new ArrayList<Solicitarprestamoalumno>();
            for (Solicitarprestamoalumno solicitarprestamoalumnoCollectionNewSolicitarprestamoalumnoToAttach : solicitarprestamoalumnoCollectionNew) {
                solicitarprestamoalumnoCollectionNewSolicitarprestamoalumnoToAttach = em.getReference(solicitarprestamoalumnoCollectionNewSolicitarprestamoalumnoToAttach.getClass(), solicitarprestamoalumnoCollectionNewSolicitarprestamoalumnoToAttach.getId());
                attachedSolicitarprestamoalumnoCollectionNew.add(solicitarprestamoalumnoCollectionNewSolicitarprestamoalumnoToAttach);
            }
            solicitarprestamoalumnoCollectionNew = attachedSolicitarprestamoalumnoCollectionNew;
            unidadmaterial.setSolicitarprestamoalumnoCollection(solicitarprestamoalumnoCollectionNew);
            unidadmaterial = em.merge(unidadmaterial);
            if (idMaterialOld != null && !idMaterialOld.equals(idMaterialNew)) {
                idMaterialOld.getUnidadmaterialCollection().remove(unidadmaterial);
                idMaterialOld = em.merge(idMaterialOld);
            }
            if (idMaterialNew != null && !idMaterialNew.equals(idMaterialOld)) {
                idMaterialNew.getUnidadmaterialCollection().add(unidadmaterial);
                idMaterialNew = em.merge(idMaterialNew);
            }
            for (Solicitarprestamoprofesor solicitarprestamoprofesorCollectionOldSolicitarprestamoprofesor : solicitarprestamoprofesorCollectionOld) {
                if (!solicitarprestamoprofesorCollectionNew.contains(solicitarprestamoprofesorCollectionOldSolicitarprestamoprofesor)) {
                    solicitarprestamoprofesorCollectionOldSolicitarprestamoprofesor.setIdUnidadMaterial(null);
                    solicitarprestamoprofesorCollectionOldSolicitarprestamoprofesor = em.merge(solicitarprestamoprofesorCollectionOldSolicitarprestamoprofesor);
                }
            }
            for (Solicitarprestamoprofesor solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor : solicitarprestamoprofesorCollectionNew) {
                if (!solicitarprestamoprofesorCollectionOld.contains(solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor)) {
                    Unidadmaterial oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor = solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor.getIdUnidadMaterial();
                    solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor.setIdUnidadMaterial(unidadmaterial);
                    solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor = em.merge(solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor);
                    if (oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor != null && !oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor.equals(unidadmaterial)) {
                        oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor.getSolicitarprestamoprofesorCollection().remove(solicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor);
                        oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor = em.merge(oldIdUnidadMaterialOfSolicitarprestamoprofesorCollectionNewSolicitarprestamoprofesor);
                    }
                }
            }
            for (Solicitarprestamoalumno solicitarprestamoalumnoCollectionNewSolicitarprestamoalumno : solicitarprestamoalumnoCollectionNew) {
                if (!solicitarprestamoalumnoCollectionOld.contains(solicitarprestamoalumnoCollectionNewSolicitarprestamoalumno)) {
                    Unidadmaterial oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionNewSolicitarprestamoalumno = solicitarprestamoalumnoCollectionNewSolicitarprestamoalumno.getIdUnidadMaterial();
                    solicitarprestamoalumnoCollectionNewSolicitarprestamoalumno.setIdUnidadMaterial(unidadmaterial);
                    solicitarprestamoalumnoCollectionNewSolicitarprestamoalumno = em.merge(solicitarprestamoalumnoCollectionNewSolicitarprestamoalumno);
                    if (oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionNewSolicitarprestamoalumno != null && !oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionNewSolicitarprestamoalumno.equals(unidadmaterial)) {
                        oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionNewSolicitarprestamoalumno.getSolicitarprestamoalumnoCollection().remove(solicitarprestamoalumnoCollectionNewSolicitarprestamoalumno);
                        oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionNewSolicitarprestamoalumno = em.merge(oldIdUnidadMaterialOfSolicitarprestamoalumnoCollectionNewSolicitarprestamoalumno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = unidadmaterial.getId();
                if (findUnidadmaterial(id) == null) {
                    throw new NonexistentEntityException("The unidadmaterial with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Unidadmaterial unidadmaterial;
            try {
                unidadmaterial = em.getReference(Unidadmaterial.class, id);
                unidadmaterial.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The unidadmaterial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Solicitarprestamoalumno> solicitarprestamoalumnoCollectionOrphanCheck = unidadmaterial.getSolicitarprestamoalumnoCollection();
            for (Solicitarprestamoalumno solicitarprestamoalumnoCollectionOrphanCheckSolicitarprestamoalumno : solicitarprestamoalumnoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Unidadmaterial (" + unidadmaterial + ") cannot be destroyed since the Solicitarprestamoalumno " + solicitarprestamoalumnoCollectionOrphanCheckSolicitarprestamoalumno + " in its solicitarprestamoalumnoCollection field has a non-nullable idUnidadMaterial field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Material idMaterial = unidadmaterial.getIdMaterial();
            if (idMaterial != null) {
                idMaterial.getUnidadmaterialCollection().remove(unidadmaterial);
                idMaterial = em.merge(idMaterial);
            }
            Collection<Solicitarprestamoprofesor> solicitarprestamoprofesorCollection = unidadmaterial.getSolicitarprestamoprofesorCollection();
            for (Solicitarprestamoprofesor solicitarprestamoprofesorCollectionSolicitarprestamoprofesor : solicitarprestamoprofesorCollection) {
                solicitarprestamoprofesorCollectionSolicitarprestamoprofesor.setIdUnidadMaterial(null);
                solicitarprestamoprofesorCollectionSolicitarprestamoprofesor = em.merge(solicitarprestamoprofesorCollectionSolicitarprestamoprofesor);
            }
            em.remove(unidadmaterial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Unidadmaterial> findUnidadmaterialEntities() {
        return findUnidadmaterialEntities(true, -1, -1);
    }

    public List<Unidadmaterial> findUnidadmaterialEntities(int maxResults, int firstResult) {
        return findUnidadmaterialEntities(false, maxResults, firstResult);
    }

    private List<Unidadmaterial> findUnidadmaterialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Unidadmaterial.class));
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

    public Unidadmaterial findUnidadmaterial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Unidadmaterial.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnidadmaterialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Unidadmaterial> rt = cq.from(Unidadmaterial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
