package au.net.huni.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
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
@RooDataOnDemand(entity = Registration.class)
public class RegistrationDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Registration> data;

	@Autowired
    private InstitutionDataOnDemand institutionDataOnDemand;

	public Registration getNewTransientRegistration(int index) {
        Registration obj = new Registration();
        setApplicationDate(obj, index);
        setApprovalDate(obj, index);
        setEmailAddress(obj, index);
        setFamilyName(obj, index);
        setGivenName(obj, index);
        setInstitution(obj, index);
        setUserName(obj, index);
        return obj;
    }

	public void setApplicationDate(Registration obj, int index) {
        Calendar applicationDate = Calendar.getInstance();
        obj.setApplicationDate(applicationDate);
    }

	public void setApprovalDate(Registration obj, int index) {
        Calendar approvalDate = Calendar.getInstance();
        obj.setApprovalDate(approvalDate);
    }

	public void setEmailAddress(Registration obj, int index) {
        String emailAddress = "foo" + index + "@bar.com";
        obj.setEmailAddress(emailAddress);
    }

	public void setFamilyName(Registration obj, int index) {
        String familyName = "familyName_" + index;
        if (familyName.length() > 60) {
            familyName = familyName.substring(0, 60);
        }
        obj.setFamilyName(familyName);
    }

	public void setGivenName(Registration obj, int index) {
        String givenName = "givenName_" + index;
        if (givenName.length() > 60) {
            givenName = givenName.substring(0, 60);
        }
        obj.setGivenName(givenName);
    }

	public void setInstitution(Registration obj, int index) {
        Institution institution = institutionDataOnDemand.getRandomInstitution();
        obj.setInstitution(institution);
    }

	public void setUserName(Registration obj, int index) {
        String userName = "userName_" + index;
        if (userName.length() > 10) {
            userName = new Random().nextInt(10) + userName.substring(1, 10);
        }
        obj.setUserName(userName);
    }

	public Registration getSpecificRegistration(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Registration obj = data.get(index);
        Long id = obj.getId();
        return Registration.findRegistration(id);
    }

	public Registration getRandomRegistration() {
        init();
        Registration obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Registration.findRegistration(id);
    }

	public boolean modifyRegistration(Registration obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = Registration.findRegistrationEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Registration' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Registration>();
        for (int i = 0; i < 10; i++) {
            Registration obj = getNewTransientRegistration(i);
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
