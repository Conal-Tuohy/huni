package au.net.huni.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ProjectTest {

    @Test
    public void testToString() {
    	Project project = new Project();
    	project.setName("project0");
    	assertEquals("Project toString is name.", "project0", project.toString());
    }

    @Test
    public void testEquals() {
    	Calendar startDate = Calendar.getInstance();
    	startDate.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	startDate.setTimeZone(timeZone);

    	Project project0 = new Project();
    	project0.setName("project name0");
    	project0.setStartDate(startDate);
    	
    	Project project1 = new Project();
    	project1.setName("project name0");
    	project1.setStartDate(startDate);
    	
    	assertTrue("Project equals is based on name and start date.", project0.equals(project1));
    }

    @Test
    public void testHashCode() {
    	Calendar startDate = Calendar.getInstance();
    	startDate.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
    	startDate.setTimeZone(timeZone);

    	Project project0 = new Project();
    	project0.setName("project name0");
    	project0.setStartDate(startDate);
    	
    	Project project1 = new Project();
    	project1.setName("project name0");
    	project1.setStartDate(startDate);
    	
    	assertEquals("Project hashcode is based on name and start date.", project0.hashCode(), project1.hashCode());
    }
    
    @Test 
    public void testToJsonProducesCorrectJson() {
    	Project project = new Project();
    	project.setId(25L);
    	project.setVersion(30);
    	project.setName("project0");
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
		calendar.setTimeZone(timeZone );
		project.setStartDate(calendar);
    	
    	String actualJson = project.toJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"project0\""));
     	assertTrue("JSON start date is correct", actualJson.contains("\"startDate\":\"25/12/2013 18:30:45 EST"));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));
    	assertTrue("JSON id is present", actualJson.contains("\"id\":25"));
    }
    
    @Test 
    public void testToJsonArrayProducesCorrectJson() {

    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");

    	Project project = new Project();
    	project.setId(25L);
    	project.setVersion(30);
    	project.setName("project0");
		calendar.setTimeZone(timeZone );
		project.setStartDate(calendar);
		
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);
    	
    	String actualJson = Project.toJsonArray(projects);
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"project0\""));
     	assertTrue("JSON start date is correct", actualJson.contains("\"startDate\":\"25/12/2013 18:30:45 EST"));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));
    	assertTrue("JSON id is present", actualJson.contains("\"id\":25"));
    }
    
    @Test 
    public void testToDeepJsonProducesCorrectJson() {
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
		calendar.setTimeZone(timeZone );
		
		DataSource dataSource = new DataSource();
		dataSource.setId(106789L);
		dataSource.setVersion(101234);
		dataSource.setName("datasource0");
		Set<DataSource> dataSources = new HashSet<DataSource>();
		dataSources.add(dataSource);

		Project project = new Project();
    	project.setId(25L);
    	project.setVersion(30);
    	project.setName("toolname0");
		project.setStartDate(calendar);
		project.setDataSources(dataSources);
    	
    	String actualJson = project.toDeepJson();
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"toolname0\""));
     	assertTrue("JSON start date is correct", actualJson.contains("\"startDate\":\"25/12/2013 18:30:45 EST"));
    	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));
    	assertTrue("JSON id is present", actualJson.contains("\"id\":25"));

    	assertTrue("JSON data source id is present", actualJson.contains("\"id\":106789"));
    	assertTrue("JSON data source version preseent", actualJson.contains("\"version\":101234"));
    	assertTrue("JSON data source name property is present", actualJson.contains("\"name\":\"datasource0\""));
    }
    
    @Test 
    public void testToDeepJsonArrayProducesCorrectJson() {
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(2013, 11, 25, 2, 30, 45);
    	TimeZone timeZone = TimeZone.getTimeZone("EST");
		calendar.setTimeZone(timeZone );
		
		DataSource dataSource = new DataSource();
		dataSource.setId(106789L);
		dataSource.setVersion(101234);
		dataSource.setName("datasource0");
		Set<DataSource> dataSources = new HashSet<DataSource>();
		dataSources.add(dataSource);

		Project project = new Project();
    	project.setId(25L);
    	project.setVersion(30);
    	project.setName("toolname0");
		project.setStartDate(calendar);
		project.setDataSources(dataSources);
		
		Set<Project> projects = new HashSet<Project>();
		projects.add(project);

    	String actualJson = Project.toDeepJsonArray(projects);
    	
    	assertTrue("JSON name is correct", actualJson.contains("\"name\":\"toolname0\""));
     	assertTrue("JSON start date is correct", actualJson.contains("\"startDate\":\"25/12/2013 18:30:45 EST"));
     	assertTrue("JSON version is present", actualJson.contains("\"version\":30"));
     	assertTrue("JSON id is present", actualJson.contains("\"id\":25"));

    	assertTrue("JSON data source id is present", actualJson.contains("\"id\":106789"));
    	assertTrue("JSON data source version present", actualJson.contains("\"version\":101234"));
    	assertTrue("JSON data source name property is present", actualJson.contains("\"name\":\"datasource0\""));
    }
    
    @Test 
    public void testFromJsonToSummaryProducesSummary() {
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.clear();
    	calendar.set(2013, 10, 25, 02, 30, 45);	// Values for GMT by default. Month is zero-base so dec = 11, nov = 10.
    	// Switch to EST and then force calculation of time. Don't do this.
    	// It seems to think the time zone has changed so it recalculates as if its a GMT to EST conversion.
    	//TimeZone timeZone = TimeZone.getTimeZone("EST");
    	//calendar.setTimeZone(timeZone);
    	calendar.get(0);	// Force calculation of time
		
		String json = "{"
				+ "userName: username0,"
				+ "projectName: projectname0,"
				+ "startDate: \"25/11/2013 02:30:45 EST\"}"
				;

    	Project.Summary summary = Project.Summary.fromJsonToSummary(json);

    	assertEquals("Summary project name is correct", "projectname0", summary.getProjectName());
    	assertEquals("Summary user name is correct", "username0", summary.getUserName());
    	assertEquals("Summary start date is correct", calendar.getTime(), summary.getStartDate().getTime());

    }
}
