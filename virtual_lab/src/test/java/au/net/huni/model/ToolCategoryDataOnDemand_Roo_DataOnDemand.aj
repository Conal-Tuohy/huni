// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.ToolCategory;
import au.net.huni.model.ToolCategoryDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect ToolCategoryDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ToolCategoryDataOnDemand: @Component;
    
    private Random ToolCategoryDataOnDemand.rnd = new SecureRandom();
    
    private List<ToolCategory> ToolCategoryDataOnDemand.data;
    
    public ToolCategory ToolCategoryDataOnDemand.getNewTransientToolCategory(int index) {
        ToolCategory obj = new ToolCategory();
        setName(obj, index);
        return obj;
    }
    
    public void ToolCategoryDataOnDemand.setName(ToolCategory obj, int index) {
        String name = "name_" + index;
        if (name.length() > 64) {
            name = new Random().nextInt(10) + name.substring(1, 64);
        }
        obj.setName(name);
    }
    
    public ToolCategory ToolCategoryDataOnDemand.getSpecificToolCategory(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ToolCategory obj = data.get(index);
        Long id = obj.getId();
        return ToolCategory.findToolCategory(id);
    }
    
    public ToolCategory ToolCategoryDataOnDemand.getRandomToolCategory() {
        init();
        ToolCategory obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return ToolCategory.findToolCategory(id);
    }
    
    public boolean ToolCategoryDataOnDemand.modifyToolCategory(ToolCategory obj) {
        return false;
    }
    
    public void ToolCategoryDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = ToolCategory.findToolCategoryEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ToolCategory' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ToolCategory>();
        for (int i = 0; i < 10; i++) {
            ToolCategory obj = getNewTransientToolCategory(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
