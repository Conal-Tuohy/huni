package au.net.huni.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Configurable
@Component
@RooDataOnDemand(entity = ToolParameter.class)
public class ToolParameterDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<ToolParameter> data;

	@Autowired
	private HistoryItemDataOnDemand historyItemDataOnDemand;

	public ToolParameter getNewTransientToolParameter(int index) {
        ToolParameter obj = new ToolParameter();
        setAmount(obj, index);
        setDisplayOrder(obj, index);
        setName(obj, index);
        setOwner(obj, index);
        return obj;
    }

	public void setAmount(ToolParameter obj, int index) {
        String amount = "amount_" + index;
        obj.setAmount(amount);
    }

	public void setDisplayOrder(ToolParameter obj, int index) {
        int displayOrder = index;
        obj.setDisplayOrder(displayOrder);
    }

	public void setName(ToolParameter obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

	public void setOwner(ToolParameter obj, int index) {
        HistoryItem owner = historyItemDataOnDemand.getRandomHistoryItem();
        obj.setOwner(owner);
    }

	public ToolParameter getSpecificToolParameter(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ToolParameter obj = data.get(index);
        Long id = obj.getId();
        return ToolParameter.findToolParameter(id);
    }

	public ToolParameter getRandomToolParameter() {
        init();
        ToolParameter obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return ToolParameter.findToolParameter(id);
    }

	public boolean modifyToolParameter(ToolParameter obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = ToolParameter.findToolParameterEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ToolParameter' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ToolParameter>();
        for (int i = 0; i < 10; i++) {
            ToolParameter obj = getNewTransientToolParameter(i);
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
