// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.ToolLibraryItem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect ToolLibraryItem_Roo_Jpa_Entity {
    
    declare @type: ToolLibraryItem: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ToolLibraryItem.id;
    
    @Version
    @Column(name = "version")
    private Integer ToolLibraryItem.version;
    
    public Long ToolLibraryItem.getId() {
        return this.id;
    }
    
    public void ToolLibraryItem.setId(Long id) {
        this.id = id;
    }
    
    public Integer ToolLibraryItem.getVersion() {
        return this.version;
    }
    
    public void ToolLibraryItem.setVersion(Integer version) {
        this.version = version;
    }
    
}