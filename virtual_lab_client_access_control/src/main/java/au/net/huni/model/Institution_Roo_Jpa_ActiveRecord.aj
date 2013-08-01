// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.Institution;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Institution_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Institution.entityManager;
    
    public static final EntityManager Institution.entityManager() {
        EntityManager em = new Institution().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Institution.countInstitutions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Institution o", Long.class).getSingleResult();
    }
    
    public static List<Institution> Institution.findAllInstitutions() {
        return entityManager().createQuery("SELECT o FROM Institution o", Institution.class).getResultList();
    }
    
    public static Institution Institution.findInstitution(Long id) {
        if (id == null) return null;
        return entityManager().find(Institution.class, id);
    }
    
    public static List<Institution> Institution.findInstitutionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Institution o", Institution.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Institution.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Institution.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Institution attached = Institution.findInstitution(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Institution.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Institution.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Institution Institution.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Institution merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
