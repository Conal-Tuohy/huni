package au.edu.versi.huni.gwt.client;

import java.util.ArrayList;
import java.util.List;

import au.edu.versi.huni.gwt.shared.ToolReference;
import au.edu.versi.huni.gwt.shared.ToolReferenceDetails;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service")
public interface ToolReferencesService extends RemoteService {
	List<ToolReferenceDetails> findAll() throws IllegalArgumentException;

	ToolReference getToolReference(Long id);

	ArrayList<ToolReferenceDetails> getToolReferenceDetails();

	ArrayList<ToolReferenceDetails> deleteToolReferences(ArrayList<Long> ids);

	ToolReference updateToolReference(ToolReference toolReference);
}
