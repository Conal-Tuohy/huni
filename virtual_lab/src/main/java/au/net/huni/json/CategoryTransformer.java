package au.net.huni.json;

import java.lang.reflect.Type;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;

import au.net.huni.model.ToolCategory;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import flexjson.transformer.AbstractTransformer;

public class CategoryTransformer extends AbstractTransformer implements
		ObjectFactory {
	
    static final Logger logger = Logger.getLogger(CategoryTransformer.class);

	public CategoryTransformer() {
		super();
	}

	@Override
	public void transform(Object object) {
		ToolCategory toolCategory = (ToolCategory)object;
        this.getContext().writeQuoted(toolCategory.getName());
	}

	@Override
	public Object instantiate(ObjectBinder context, Object value,
			Type targetType, @SuppressWarnings("rawtypes") Class targetClass) {
		return findExistingToolCategory((String) value);
	}
	    
	protected ToolCategory findExistingToolCategory(String name) {
		ToolCategory existingToolCategory = null;
        try {
        	existingToolCategory = ToolCategory.findToolCategorysByNameEquals(name).getSingleResult();
        } catch (NoResultException ignore) {
        	String message = "A tool category with that name was not found: " + name;
        	logger.info(message);
        } catch (EmptyResultDataAccessException ignore) {
        	String message = "A tool category with that name was not found: " + name;
        	logger.info(message);
        } catch (Exception ignore) {
        	String message = "A tool category with that name was not found: " + name;
        	logger.info(message);
        }         
        return existingToolCategory;
	}

}
