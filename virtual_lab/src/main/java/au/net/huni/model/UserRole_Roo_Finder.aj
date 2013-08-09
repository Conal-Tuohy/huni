// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.UserRole;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect UserRole_Roo_Finder {
    
    public static TypedQuery<UserRole> UserRole.findUserRolesByNameEquals(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        EntityManager em = UserRole.entityManager();
        TypedQuery<UserRole> q = em.createQuery("SELECT o FROM UserRole AS o WHERE o.name = :name", UserRole.class);
        q.setParameter("name", name);
        return q;
    }
    
}
