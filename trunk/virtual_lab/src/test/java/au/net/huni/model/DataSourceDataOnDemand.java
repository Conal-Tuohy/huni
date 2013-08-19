package au.net.huni.model;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = DataSource.class)
public class DataSourceDataOnDemand {

	public DataSource getNewTransientDataSource(int index) {
        DataSource obj = new DataSource();
        setDescription(obj, index);
        setImportDate(obj, index);
        setName(obj, index);
        return obj;
    }
}
