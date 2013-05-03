package au.edu.versi.huni.gwt.shared;

import java.io.Serializable;


@SuppressWarnings("serial")
public class ToolReferenceDetails  implements Serializable{
	
	private Long id;

	private String name;
	
	public ToolReferenceDetails() {
		super();
	}
	
	public ToolReferenceDetails(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return getName();
	}
}
