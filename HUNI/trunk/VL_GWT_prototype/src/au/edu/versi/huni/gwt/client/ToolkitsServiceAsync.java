package au.edu.versi.huni.gwt.client;

import java.util.List;

import au.edu.versi.huni.gwt.shared.ToolkitDetails;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ToolkitsServiceAsync {

	void findAll(AsyncCallback<List<ToolkitDetails>> callback);

}
