package au.edu.versi.huni.gwt.client;

import java.util.List;

import au.edu.versi.huni.gwt.shared.ToolkitDetails;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service")
public interface ToolkitsService extends RemoteService {
	List<ToolkitDetails> findAll() throws IllegalArgumentException;
}
