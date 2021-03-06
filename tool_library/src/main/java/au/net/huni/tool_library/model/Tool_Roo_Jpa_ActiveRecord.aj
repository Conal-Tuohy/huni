// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.tool_library.model;

import au.net.huni.tool_library.model.Tool;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Tool_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Tool.entityManager;
    
    public static final EntityManager Tool.entityManager() {
        EntityManager em = new Tool().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Tool.countTools() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Tool o", Long.class).getSingleResult();
    }
    
    public static List<Tool> Tool.findAllTools() {
        return entityManager().createQuery("SELECT o FROM Tool o", Tool.class).getResultList();
    }
    
    public static Tool Tool.findTool(Long id) {
        if (id == null) return null;
        return entityManager().find(Tool.class, id);
    }
    
    public static List<Tool> Tool.findToolEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Tool o", Tool.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Tool.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Tool.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Tool attached = Tool.findTool(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Tool.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Tool.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Tool Tool.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Tool merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
