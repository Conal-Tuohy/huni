// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.DataSource;
import au.net.huni.model.Project;
import java.util.Calendar;
import java.util.Set;

privileged aspect Project_Roo_JavaBean {
    
    public String Project.getName() {
        return this.name;
    }
    
    public void Project.setName(String name) {
        this.name = name;
    }
    
    public Calendar Project.getStartDate() {
        return this.startDate;
    }
    
    public void Project.setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
    
    public Set<DataSource> Project.getDataSources() {
        return this.dataSources;
    }
    
    public void Project.setDataSources(Set<DataSource> dataSources) {
        this.dataSources = dataSources;
    }
    
}
