package au.edu.versi.huni.gwt.shared;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ToolkitDetails implements Serializable {
	
	private Long id;
	private String name;
	
	public ToolkitDetails() {
	}
	
	public ToolkitDetails(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return getName();
	}
}
