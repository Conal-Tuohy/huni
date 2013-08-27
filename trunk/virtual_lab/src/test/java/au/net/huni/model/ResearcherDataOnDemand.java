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
@RooDataOnDemand(entity = Researcher.class)
public class ResearcherDataOnDemand {

	@Autowired
	private HistoryItemDataOnDemand historyItemDataOnDemand;

	@Autowired
	private ToolLibraryItemDataOnDemand toolCatalogItemDataOnDemand;

	@Autowired
	private ProjectDataOnDemand projectDataOnDemand;

	private Random rnd = new SecureRandom();

	private List<Researcher> data;

	public Researcher getNewTransientResearcher(int index) {
        Researcher obj = new Researcher();
        setUserName(obj, index);
        setPassword(obj, index);
        setIsAccountEnabled(obj, index);
        setEmailAddress(obj, index);
        setInstitution(obj, index);
        setFamilyName(obj, index);
        setGivenName(obj, index);
        setCreationDate(obj, index);
        setDefaultTool(obj, index);
        setToolList(obj, index);
        // Last so all fields are valid before passing to history item as an owner.
        setHistoryList(obj, index);
        setProjectList(obj, index);
        return obj;
    }

	public void setFamilyName(Researcher obj, int index) {
        String familyName = "familyName_" + index;
        if (familyName.length() > 60) {
            familyName = familyName.substring(0, 60);
        }
        obj.setFamilyName(familyName);
    }

	public void setGivenName(Researcher obj, int index) {
        String givenName = "givenName_" + index;
        if (givenName.length() > 60) {
            givenName = givenName.substring(0, 60);
        }
        obj.setGivenName(givenName);
    }

	public void setUserName(Researcher obj, int index) {
		// The base name  is shortened as field has a length constraint.
        String userName = "uNme_" + index;
        if (userName.length() > 10) {
            userName = userName.substring(0, 10);
        }
        obj.setUserName(userName);
    }
	
	public void setHistoryList(Researcher researcher, int index) {
        HistoryItem historyItem = new HistoryItem();
        historyItem.setToolName("tool1");
        historyItem.setOwner(researcher);
        researcher.addHistoryItem(historyItem);
    }

	public void setDefaultTool(Researcher obj, int index) {
        ToolLibraryItem defaultTool = toolCatalogItemDataOnDemand.getRandomToolLibraryItem();
        obj.setDefaultTool(defaultTool);
    }
	
	public void setToolList(Researcher obj, int index) {
		ToolLibraryItem toolCatalogItem = toolCatalogItemDataOnDemand.getRandomToolLibraryItem();
        obj.getToolkit().add(toolCatalogItem);
    }
	
	public void setProjectList(Researcher obj, int index) {
		Project project = projectDataOnDemand.getRandomProject();
        obj.getProjects().add(project);
    }

	public Researcher getSpecificResearcher(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Researcher obj = data.get(index);
        Long id = obj.getId();
        return Researcher.findResearcher(id);
    }

	public Researcher getRandomResearcher() {
        init();
        Researcher obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Researcher.findResearcher(id);
    }

	public boolean modifyResearcher(Researcher obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = Researcher.findResearcherEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Researcher' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Researcher>();
        for (int i = 0; i < 10; i++) {
            Researcher obj = getNewTransientResearcher(i);
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
