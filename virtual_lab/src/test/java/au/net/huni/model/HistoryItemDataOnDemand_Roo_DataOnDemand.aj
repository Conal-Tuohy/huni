// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.HistoryItem;
import au.net.huni.model.HistoryItemDataOnDemand;
import au.net.huni.model.ResearcherDataOnDemand;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;

privileged aspect HistoryItemDataOnDemand_Roo_DataOnDemand {
    
    @Autowired
    ResearcherDataOnDemand HistoryItemDataOnDemand.researcherDataOnDemand;
    
    public HistoryItem HistoryItemDataOnDemand.getNewTransientHistoryItem(int index) {
        HistoryItem obj = new HistoryItem();
        setBackgroundColour(obj, index);
        setExecutionDate(obj, index);
        setToolName(obj, index);
        return obj;
    }
    
}