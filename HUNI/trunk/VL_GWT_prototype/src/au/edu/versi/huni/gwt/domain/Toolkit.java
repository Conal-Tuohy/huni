package au.edu.versi.huni.gwt.domain;

import java.util.ArrayList;
import java.util.List;

public class Toolkit {
	
	private Long id;
	private String name;
	private String description;
	private Category category;
	private Domain domain;
	private String author;
	private List<ToolReference> tools = new ArrayList<ToolReference>();

}
