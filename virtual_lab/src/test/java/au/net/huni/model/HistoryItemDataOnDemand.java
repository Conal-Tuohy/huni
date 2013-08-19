package au.net.huni.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Configurable
@Component
@RooDataOnDemand(entity = HistoryItem.class)
public class HistoryItemDataOnDemand {	

	private Random rnd = new SecureRandom();

	private List<HistoryItem> data;

	public HistoryItem getNewTransientHistoryItem(int index, Researcher researcher) {
        HistoryItem obj = new HistoryItem();
        setBackgroundColour(obj, index);
        setExecutionDate(obj, index);
        setToolName(obj, index);
        return obj;
    }

	public void setBackgroundColour(HistoryItem obj, int index) {
        String backgroundColour = "#EEFF22";
        obj.setBackgroundColour(backgroundColour);
    }

	public void setExecutionDate(HistoryItem obj, int index) {
        Calendar executionDate = Calendar.getInstance();
        obj.setExecutionDate(executionDate);
    }

	public void setToolName(HistoryItem obj, int index) {
        String toolName = "toolName_" + index;
        obj.setToolName(toolName);
    }

	public HistoryItem getSpecificHistoryItem(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        HistoryItem obj = data.get(index);
        Long id = obj.getId();
        return HistoryItem.findHistoryItem(id);
    }

	public HistoryItem getRandomHistoryItem() {
        init();
        HistoryItem obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return HistoryItem.findHistoryItem(id);
    }

	public boolean modifyHistoryItem(HistoryItem obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = HistoryItem.findHistoryItemEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'HistoryItem' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<HistoryItem>();
        for (int i = 0; i < 10; i++) {
            HistoryItem obj = getNewTransientHistoryItem(i);
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
