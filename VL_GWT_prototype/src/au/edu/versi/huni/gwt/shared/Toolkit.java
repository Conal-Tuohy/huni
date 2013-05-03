package au.edu.versi.huni.gwt.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class Toolkit implements Serializable {
	
	private Long id;
	private String name;
	private String description;
	private Category category;
	private Domain domain;
	private String author;
	private List<ToolReference> tools = new ArrayList<ToolReference>();
	
	public Toolkit() {
		super();
	}
	
	public Toolkit(Long id, String name, String description, Category category,
			Domain domain, String author) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.domain = domain;
		this.author = author;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<ToolReference> getTools() {
		return tools;
	}

	public void setTools(List<ToolReference> tools) {
		this.tools = tools;
	}
	
}
