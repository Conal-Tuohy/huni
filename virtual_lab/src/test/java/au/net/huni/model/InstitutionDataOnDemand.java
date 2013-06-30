package au.net.huni.model;

import java.security.SecureRandom;
import java.util.ArrayList;
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
@RooDataOnDemand(entity = Institution.class)
public class InstitutionDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Institution> data;

	public Institution getNewTransientInstitution(int index) {
        Institution obj = new Institution();
        setName(obj, index);
        return obj;
    }

	public void setName(Institution obj, int index) {
        String name = "name_" + index;
        if (name.length() > 10) {
            name = new Random().nextInt(10) + name.substring(1, 10);
        }
        obj.setName(name);
    }

	public Institution getSpecificInstitution(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Institution obj = data.get(index);
        Long id = obj.getId();
        return Institution.findInstitution(id);
    }

	public Institution getRandomInstitution() {
        init();
        Institution obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Institution.findInstitution(id);
    }

	public boolean modifyInstitution(Institution obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = Institution.findInstitutionEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Institution' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Institution>();
        for (int i = 0; i < 10; i++) {
            Institution obj = getNewTransientInstitution(i);
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
