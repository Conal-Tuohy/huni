package au.edu.versi.huni.gwt.client;

import java.util.ArrayList;
import java.util.List;

import au.edu.versi.huni.gwt.domain.ToolReference;
import au.edu.versi.huni.gwt.domain.ToolReferenceDetails;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ToolReferencesServiceAsync {

	void findAll(AsyncCallback<List<ToolReferenceDetails>> callback);

	void getToolReference(Long id, AsyncCallback<ToolReference> asyncCallback);

	void getToolReferenceDetails(
			AsyncCallback<ArrayList<ToolReferenceDetails>> asyncCallback);

	void deleteToolReferences(ArrayList<Long> ids,
			AsyncCallback<ArrayList<ToolReferenceDetails>> asyncCallback);

	void updateToolReference(ToolReference toolReference,
			AsyncCallback<ToolReference> asyncCallback);

}
