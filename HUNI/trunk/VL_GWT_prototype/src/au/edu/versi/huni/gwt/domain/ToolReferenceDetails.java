package au.edu.versi.huni.gwt.domain;


public class ToolReferenceDetails {
	
	private Long id;

	private String name;
	
	public ToolReferenceDetails(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
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
