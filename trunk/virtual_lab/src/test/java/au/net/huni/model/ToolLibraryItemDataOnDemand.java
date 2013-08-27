package au.net.huni.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;


@RooDataOnDemand(entity = ToolLibraryItem.class)
public class ToolLibraryItemDataOnDemand {

	@Autowired
	private ToolCategoryDataOnDemand toolCategoryDataOnDemand;

	public ToolLibraryItem getNewTransientToolCatalogItem(int index) {
        ToolLibraryItem obj = new ToolLibraryItem();
        setName(obj, index);
        setDescription(obj, index);
        setUrl(obj, index);
        setCategoryList(obj, index);
        return obj;
    }
	
	public void setCategoryList(ToolLibraryItem obj, int index) {
		ToolCategory toolCategory = toolCategoryDataOnDemand.getRandomToolCategory();
	    obj.getCategories().add(toolCategory);
	}

	public void setUrl(ToolLibraryItem obj, int index) {
        String url = "http://localhost/path_" + + index;
        obj.setUrl(url);
    }
}



