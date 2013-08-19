package au.net.huni.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;


@RooDataOnDemand(entity = ToolCatalogItem.class)
public class ToolCatalogItemDataOnDemand {

	@Autowired
	private ToolCategoryDataOnDemand toolCategoryDataOnDemand;

	public ToolCatalogItem getNewTransientToolCatalogItem(int index) {
        ToolCatalogItem obj = new ToolCatalogItem();
        setName(obj, index);
        setDescription(obj, index);
        setUrl(obj, index);
        setCategoryList(obj, index);
        return obj;
    }
	
	public void setCategoryList(ToolCatalogItem obj, int index) {
		ToolCategory toolCategory = toolCategoryDataOnDemand.getRandomToolCategory();
	    obj.getCategories().add(toolCategory);
	}

	public void setUrl(ToolCatalogItem obj, int index) {
        String url = "http://localhost/path_" + + index;
        obj.setUrl(url);
    }
}



