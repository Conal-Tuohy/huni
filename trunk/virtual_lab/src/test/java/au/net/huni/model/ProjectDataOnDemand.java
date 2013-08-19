package au.net.huni.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Project.class)
public class ProjectDataOnDemand {

	@Autowired
	private DataSourceDataOnDemand dataSourceDataOnDemand;

	public Project getNewTransientProject(int index) {
        Project obj = new Project();
        setName(obj, index);
        setStartDate(obj, index);
        setDataSources(obj, index);
        return obj;
    }
    
	public void setDataSources(Project obj, int index) {
		DataSource dataSource = dataSourceDataOnDemand.getRandomDataSource();
	    obj.getDataSources().add(dataSource);
	}
}
