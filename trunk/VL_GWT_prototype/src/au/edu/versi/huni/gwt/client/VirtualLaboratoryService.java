package au.edu.versi.huni.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("service")
public interface VirtualLaboratoryService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
}
