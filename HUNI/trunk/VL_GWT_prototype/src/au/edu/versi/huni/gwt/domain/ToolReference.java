package au.edu.versi.huni.gwt.domain;

import java.net.URL;
import java.sql.Date;

public class ToolReference {
	
	private Long id;
	private String name;
	private String description;
	private URL location;
	private String owner;
	private String version;
	private Date uploadDate;	

	public ToolReference() {
		// TODO Auto-generated constructor stub
	}

	public ToolReference(Long id, String name, String description,
			URL location, String owner, String version, Date uploadDate) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.owner = owner;
		this.version = version;
		this.uploadDate = uploadDate;
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

	public URL getLocation() {
		return location;
	}

	public void setLocation(URL location) {
		this.location = location;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

}
