package au.edu.versi.huni.gwt.server;

import java.util.ArrayList;
import java.util.List;

import au.edu.versi.huni.gwt.client.ToolkitsService;
import au.edu.versi.huni.gwt.domain.ToolkitDetails;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ToolkitsServiceImpl extends RemoteServiceServlet implements ToolkitsService {

	public List<ToolkitDetails> findAll() throws IllegalArgumentException {

		return new ArrayList<ToolkitDetails>();

	}
}
