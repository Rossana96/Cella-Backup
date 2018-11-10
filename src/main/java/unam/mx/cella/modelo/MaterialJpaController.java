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
public class MaterialJpaController implements Serializable {

    public MaterialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Material material) {
        if (material.getContenerKitMaterialCollection() == null) {
            material.setContenerKitMaterialCollection(new ArrayList<ContenerKitMaterial>());
        }
        if (material.getUnidadmaterialCollection() == null) {
            material.setUnidadmaterialCollection(new ArrayList<Unidadmaterial>());
        }
        if (material.getPertenecerMaterialCategoriaCollection() == null) {
            material.setPertenecerMaterialCategoriaCollection(new ArrayList<PertenecerMaterialCategoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ContenerKitMaterial> attachedContenerKitMaterialCollection = new ArrayList<ContenerKitMaterial>();
            for (ContenerKitMaterial contenerKitMaterialCollectionContenerKitMaterialToAttach : material.getContenerKitMaterialCollection()) {
                contenerKitMaterialCollectionContenerKitMaterialToAttach = em.getReference(contenerKitMaterialCollectionContenerKitMaterialToAttach.getClass(), contenerKitMaterialCollectionContenerKitMaterialToAttach.getId());
                attachedContenerKitMaterialCollection.add(contenerKitMaterialCollectionContenerKitMaterialToAttach);
            }
            material.setContenerKitMaterialCollection(attachedContenerKitMaterialCollection);
            Collection<Unidadmaterial> attachedUnidadmaterialCollection = new ArrayList<Unidadmaterial>();
            for (Unidadmaterial unidadmaterialCollectionUnidadmaterialToAttach : material.getUnidadmaterialCollection()) {
                unidadmaterialCollectionUnidadmaterialToAttach = em.getReference(unidadmaterialCollectionUnidadmaterialToAttach.getClass(), unidadmaterialCollectionUnidadmaterialToAttach.getId());
                attachedUnidadmaterialCollection.add(unidadmaterialCollectionUnidadmaterialToAttach);
            }
            material.setUnidadmaterialCollection(attachedUnidadmaterialCollection);
            Collection<PertenecerMaterialCategoria> attachedPertenecerMaterialCategoriaCollection = new ArrayList<PertenecerMaterialCategoria>();
            for (PertenecerMaterialCategoria pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoriaToAttach : material.getPertenecerMaterialCategoriaCollection()) {
                pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoriaToAttach = em.getReference(pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoriaToAttach.getClass(), pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoriaToAttach.getId());
                attachedPertenecerMaterialCategoriaCollection.add(pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoriaToAttach);
            }
            material.setPertenecerMaterialCategoriaCollection(attachedPertenecerMaterialCategoriaCollection);
            em.persist(material);
            for (ContenerKitMaterial contenerKitMaterialCollectionContenerKitMaterial : material.getContenerKitMaterialCollection()) {
                Material oldIdMaterialOfContenerKitMaterialCollectionContenerKitMaterial = contenerKitMaterialCollectionContenerKitMaterial.getIdMaterial();
                contenerKitMaterialCollectionContenerKitMaterial.setIdMaterial(material);
                contenerKitMaterialCollectionContenerKitMaterial = em.merge(contenerKitMaterialCollectionContenerKitMaterial);
                if (oldIdMaterialOfContenerKitMaterialCollectionContenerKitMaterial != null) {
                    oldIdMaterialOfContenerKitMaterialCollectionContenerKitMaterial.getContenerKitMaterialCollection().remove(contenerKitMaterialCollectionContenerKitMaterial);
                    oldIdMaterialOfContenerKitMaterialCollectionContenerKitMaterial = em.merge(oldIdMaterialOfContenerKitMaterialCollectionContenerKitMaterial);
                }
            }
            for (Unidadmaterial unidadmaterialCollectionUnidadmaterial : material.getUnidadmaterialCollection()) {
                Material oldIdMaterialOfUnidadmaterialCollectionUnidadmaterial = unidadmaterialCollectionUnidadmaterial.getIdMaterial();
                unidadmaterialCollectionUnidadmaterial.setIdMaterial(material);
                unidadmaterialCollectionUnidadmaterial = em.merge(unidadmaterialCollectionUnidadmaterial);
                if (oldIdMaterialOfUnidadmaterialCollectionUnidadmaterial != null) {
                    oldIdMaterialOfUnidadmaterialCollectionUnidadmaterial.getUnidadmaterialCollection().remove(unidadmaterialCollectionUnidadmaterial);
                    oldIdMaterialOfUnidadmaterialCollectionUnidadmaterial = em.merge(oldIdMaterialOfUnidadmaterialCollectionUnidadmaterial);
                }
            }
            for (PertenecerMaterialCategoria pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria : material.getPertenecerMaterialCategoriaCollection()) {
                Material oldIdMaterialOfPertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria = pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria.getIdMaterial();
                pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria.setIdMaterial(material);
                pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria = em.merge(pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria);
                if (oldIdMaterialOfPertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria != null) {
                    oldIdMaterialOfPertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria.getPertenecerMaterialCategoriaCollection().remove(pertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria);
                    oldIdMaterialOfPertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria = em.merge(oldIdMaterialOfPertenecerMaterialCategoriaCollectionPertenecerMaterialCategoria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Material material) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Material persistentMaterial = em.find(Material.class, material.getId());
            Collection<ContenerKitMaterial> contenerKitMaterialCollectionOld = persistentMaterial.getContenerKitMaterialCollection();
            Collection<ContenerKitMaterial> contenerKitMaterialCollectionNew = material.getContenerKitMaterialCollection();
            Collection<Unidadmaterial> unidadmaterialCollectionOld = persistentMaterial.getUnidadmaterialCollection();
            Collection<Unidadmaterial> unidadmaterialCollectionNew = material.getUnidadmaterialCollection();
            Collection<PertenecerMaterialCategoria> pertenecerMaterialCategoriaCollectionOld = persistentMaterial.getPertenecerMaterialCategoriaCollection();
            Collection<PertenecerMaterialCategoria> pertenecerMaterialCategoriaCollectionNew = material.getPertenecerMaterialCategoriaCollection();
            List<String> illegalOrphanMessages = null;
            for (ContenerKitMaterial contenerKitMaterialCollectionOldContenerKitMaterial : contenerKitMaterialCollectionOld) {
                if (!contenerKitMaterialCollectionNew.contains(contenerKitMaterialCollectionOldContenerKitMaterial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContenerKitMaterial " + contenerKitMaterialCollectionOldContenerKitMaterial + " since its idMaterial field is not nullable.");
                }
            }
            for (Unidadmaterial unidadmaterialCollectionOldUnidadmaterial : unidadmaterialCollectionOld) {
                if (!unidadmaterialCollectionNew.contains(unidadmaterialCollectionOldUnidadmaterial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Unidadmaterial " + unidadmaterialCollectionOldUnidadmaterial + " since its idMaterial field is not nullable.");
                }
            }
            for (PertenecerMaterialCategoria pertenecerMaterialCategoriaCollectionOldPertenecerMaterialCategoria : pertenecerMaterialCategoriaCollectionOld) {
                if (!pertenecerMaterialCategoriaCollectionNew.contains(pertenecerMaterialCategoriaCollectionOldPertenecerMaterialCategoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PertenecerMaterialCategoria " + pertenecerMaterialCategoriaCollectionOldPertenecerMaterialCategoria + " since its idMaterial field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ContenerKitMaterial> attachedContenerKitMaterialCollectionNew = new ArrayList<ContenerKitMaterial>();
            for (ContenerKitMaterial contenerKitMaterialCollectionNewContenerKitMaterialToAttach : contenerKitMaterialCollectionNew) {
                contenerKitMaterialCollectionNewContenerKitMaterialToAttach = em.getReference(contenerKitMaterialCollectionNewContenerKitMaterialToAttach.getClass(), contenerKitMaterialCollectionNewContenerKitMaterialToAttach.getId());
                attachedContenerKitMaterialCollectionNew.add(contenerKitMaterialCollectionNewContenerKitMaterialToAttach);
            }
            contenerKitMaterialCollectionNew = attachedContenerKitMaterialCollectionNew;
            material.setContenerKitMaterialCollection(contenerKitMaterialCollectionNew);
            Collection<Unidadmaterial> attachedUnidadmaterialCollectionNew = new ArrayList<Unidadmaterial>();
            for (Unidadmaterial unidadmaterialCollectionNewUnidadmaterialToAttach : unidadmaterialCollectionNew) {
                unidadmaterialCollectionNewUnidadmaterialToAttach = em.getReference(unidadmaterialCollectionNewUnidadmaterialToAttach.getClass(), unidadmaterialCollectionNewUnidadmaterialToAttach.getId());
                attachedUnidadmaterialCollectionNew.add(unidadmaterialCollectionNewUnidadmaterialToAttach);
            }
            unidadmaterialCollectionNew = attachedUnidadmaterialCollectionNew;
            material.setUnidadmaterialCollection(unidadmaterialCollectionNew);
            Collection<PertenecerMaterialCategoria> attachedPertenecerMaterialCategoriaCollectionNew = new ArrayList<PertenecerMaterialCategoria>();
            for (PertenecerMaterialCategoria pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoriaToAttach : pertenecerMaterialCategoriaCollectionNew) {
                pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoriaToAttach = em.getReference(pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoriaToAttach.getClass(), pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoriaToAttach.getId());
                attachedPertenecerMaterialCategoriaCollectionNew.add(pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoriaToAttach);
            }
            pertenecerMaterialCategoriaCollectionNew = attachedPertenecerMaterialCategoriaCollectionNew;
            material.setPertenecerMaterialCategoriaCollection(pertenecerMaterialCategoriaCollectionNew);
            material = em.merge(material);
            for (ContenerKitMaterial contenerKitMaterialCollectionNewContenerKitMaterial : contenerKitMaterialCollectionNew) {
                if (!contenerKitMaterialCollectionOld.contains(contenerKitMaterialCollectionNewContenerKitMaterial)) {
                    Material oldIdMaterialOfContenerKitMaterialCollectionNewContenerKitMaterial = contenerKitMaterialCollectionNewContenerKitMaterial.getIdMaterial();
                    contenerKitMaterialCollectionNewContenerKitMaterial.setIdMaterial(material);
                    contenerKitMaterialCollectionNewContenerKitMaterial = em.merge(contenerKitMaterialCollectionNewContenerKitMaterial);
                    if (oldIdMaterialOfContenerKitMaterialCollectionNewContenerKitMaterial != null && !oldIdMaterialOfContenerKitMaterialCollectionNewContenerKitMaterial.equals(material)) {
                        oldIdMaterialOfContenerKitMaterialCollectionNewContenerKitMaterial.getContenerKitMaterialCollection().remove(contenerKitMaterialCollectionNewContenerKitMaterial);
                        oldIdMaterialOfContenerKitMaterialCollectionNewContenerKitMaterial = em.merge(oldIdMaterialOfContenerKitMaterialCollectionNewContenerKitMaterial);
                    }
                }
            }
            for (Unidadmaterial unidadmaterialCollectionNewUnidadmaterial : unidadmaterialCollectionNew) {
                if (!unidadmaterialCollectionOld.contains(unidadmaterialCollectionNewUnidadmaterial)) {
                    Material oldIdMaterialOfUnidadmaterialCollectionNewUnidadmaterial = unidadmaterialCollectionNewUnidadmaterial.getIdMaterial();
                    unidadmaterialCollectionNewUnidadmaterial.setIdMaterial(material);
                    unidadmaterialCollectionNewUnidadmaterial = em.merge(unidadmaterialCollectionNewUnidadmaterial);
                    if (oldIdMaterialOfUnidadmaterialCollectionNewUnidadmaterial != null && !oldIdMaterialOfUnidadmaterialCollectionNewUnidadmaterial.equals(material)) {
                        oldIdMaterialOfUnidadmaterialCollectionNewUnidadmaterial.getUnidadmaterialCollection().remove(unidadmaterialCollectionNewUnidadmaterial);
                        oldIdMaterialOfUnidadmaterialCollectionNewUnidadmaterial = em.merge(oldIdMaterialOfUnidadmaterialCollectionNewUnidadmaterial);
                    }
                }
            }
            for (PertenecerMaterialCategoria pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria : pertenecerMaterialCategoriaCollectionNew) {
                if (!pertenecerMaterialCategoriaCollectionOld.contains(pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria)) {
                    Material oldIdMaterialOfPertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria = pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria.getIdMaterial();
                    pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria.setIdMaterial(material);
                    pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria = em.merge(pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria);
                    if (oldIdMaterialOfPertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria != null && !oldIdMaterialOfPertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria.equals(material)) {
                        oldIdMaterialOfPertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria.getPertenecerMaterialCategoriaCollection().remove(pertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria);
                        oldIdMaterialOfPertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria = em.merge(oldIdMaterialOfPertenecerMaterialCategoriaCollectionNewPertenecerMaterialCategoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = material.getId();
                if (findMaterial(id) == null) {
                    throw new NonexistentEntityException("The material with id " + id + " no longer exists.");
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
            Material material;
            try {
                material = em.getReference(Material.class, id);
                material.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The material with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ContenerKitMaterial> contenerKitMaterialCollectionOrphanCheck = material.getContenerKitMaterialCollection();
            for (ContenerKitMaterial contenerKitMaterialCollectionOrphanCheckContenerKitMaterial : contenerKitMaterialCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Material (" + material + ") cannot be destroyed since the ContenerKitMaterial " + contenerKitMaterialCollectionOrphanCheckContenerKitMaterial + " in its contenerKitMaterialCollection field has a non-nullable idMaterial field.");
            }
            Collection<Unidadmaterial> unidadmaterialCollectionOrphanCheck = material.getUnidadmaterialCollection();
            for (Unidadmaterial unidadmaterialCollectionOrphanCheckUnidadmaterial : unidadmaterialCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Material (" + material + ") cannot be destroyed since the Unidadmaterial " + unidadmaterialCollectionOrphanCheckUnidadmaterial + " in its unidadmaterialCollection field has a non-nullable idMaterial field.");
            }
            Collection<PertenecerMaterialCategoria> pertenecerMaterialCategoriaCollectionOrphanCheck = material.getPertenecerMaterialCategoriaCollection();
            for (PertenecerMaterialCategoria pertenecerMaterialCategoriaCollectionOrphanCheckPertenecerMaterialCategoria : pertenecerMaterialCategoriaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Material (" + material + ") cannot be destroyed since the PertenecerMaterialCategoria " + pertenecerMaterialCategoriaCollectionOrphanCheckPertenecerMaterialCategoria + " in its pertenecerMaterialCategoriaCollection field has a non-nullable idMaterial field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(material);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Material> findMaterialEntities() {
        return findMaterialEntities(true, -1, -1);
    }

    public List<Material> findMaterialEntities(int maxResults, int firstResult) {
        return findMaterialEntities(false, maxResults, firstResult);
    }

    private List<Material> findMaterialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Material.class));
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

    public Material findMaterial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Material.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaterialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Material> rt = cq.from(Material.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Material findMaterial(String material) {
        EntityManager em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Material.findByNombrematerial")
                .setParameter("nombrematerial",material);
                
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (Material) q.getSingleResult();
    }
    
    
}
