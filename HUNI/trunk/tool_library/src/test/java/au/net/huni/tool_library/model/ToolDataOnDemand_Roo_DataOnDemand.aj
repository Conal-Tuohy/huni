// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.tool_library.model;

import au.net.huni.tool_library.model.Tool;
import au.net.huni.tool_library.model.ToolDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect ToolDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ToolDataOnDemand: @Component;
    
    private Random ToolDataOnDemand.rnd = new SecureRandom();
    
    private List<Tool> ToolDataOnDemand.data;
    
    public Tool ToolDataOnDemand.getNewTransientTool(int index) {
        Tool obj = new Tool();
        setContentType(obj, index);
        setDescription(obj, index);
        setFilepath(obj, index);
        setFilesize(obj, index);
        setIsDefault(obj, index);
        setName(obj, index);
        setUrl(obj, index);
        return obj;
    }
    
    public void ToolDataOnDemand.setContentType(Tool obj, int index) {
        String contentType = "contentType_" + index;
        obj.setContentType(contentType);
    }
    
    public void ToolDataOnDemand.setDescription(Tool obj, int index) {
        String description = "description_" + index;
        if (description.length() > 256) {
            description = description.substring(0, 256);
        }
        obj.setDescription(description);
    }
    
    public void ToolDataOnDemand.setFilepath(Tool obj, int index) {
        String filepath = "filepath_" + index;
        if (filepath.length() > 128) {
            filepath = filepath.substring(0, 128);
        }
        obj.setFilepath(filepath);
    }
    
    public void ToolDataOnDemand.setFilesize(Tool obj, int index) {
        Long filesize = new Integer(index).longValue();
        obj.setFilesize(filesize);
    }
    
    public void ToolDataOnDemand.setIsDefault(Tool obj, int index) {
        Boolean isDefault = Boolean.TRUE;
        obj.setIsDefault(isDefault);
    }
    
    public void ToolDataOnDemand.setName(Tool obj, int index) {
        String name = "name_" + index;
        if (name.length() > 128) {
            name = name.substring(0, 128);
        }
        obj.setName(name);
    }
    
    public void ToolDataOnDemand.setUrl(Tool obj, int index) {
        String url = "url_" + index;
        if (url.length() > 256) {
            url = url.substring(0, 256);
        }
        obj.setUrl(url);
    }
    
    public Tool ToolDataOnDemand.getSpecificTool(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Tool obj = data.get(index);
        Long id = obj.getId();
        return Tool.findTool(id);
    }
    
    public Tool ToolDataOnDemand.getRandomTool() {
        init();
        Tool obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Tool.findTool(id);
    }
    
    public boolean ToolDataOnDemand.modifyTool(Tool obj) {
        return false;
    }
    
    public void ToolDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Tool.findToolEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Tool' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Tool>();
        for (int i = 0; i < 10; i++) {
            Tool obj = getNewTransientTool(i);
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
