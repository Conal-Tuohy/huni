// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.HistoryItem;
import au.net.huni.model.Researcher;
import java.awt.Color;
import java.util.Calendar;

privileged aspect HistoryItem_Roo_JavaBean {
    
    public String HistoryItem.getToolName() {
        return this.toolName;
    }
    
    public void HistoryItem.setToolName(String toolName) {
        this.toolName = toolName;
    }
    
    public Calendar HistoryItem.getExecutionDate() {
        return this.executionDate;
    }
    
    public void HistoryItem.setExecutionDate(Calendar executionDate) {
        this.executionDate = executionDate;
    }
    
    public Color HistoryItem.getBackgroundColour() {
        return this.backgroundColour;
    }
    
    public void HistoryItem.setBackgroundColour(Color backgroundColour) {
        this.backgroundColour = backgroundColour;
    }
    
    public String HistoryItem.getJson() {
        return this.json;
    }
    
    public void HistoryItem.setJson(String json) {
        this.json = json;
    }
    
    public Researcher HistoryItem.getOwner() {
        return this.owner;
    }
    
    public void HistoryItem.setOwner(Researcher owner) {
        this.owner = owner;
    }
    
}
