package au.net.huni.model;

import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import au.net.huni.security.UserDetailsServiceImpl;
import au.net.huni.test.DummyTypedQuery;

@RunWith(JUnit4.class)
@MockStaticEntityMethods
public class ResearcherTest {

    @Test
    public void testMethod() {
        int expectedCount = 13;
        Researcher.countResearchers();
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedCount);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        org.junit.Assert.assertEquals(expectedCount, Researcher.countResearchers());
    }

	@Test
	public void testLoadUserByUsernameForKnownUser() {
		final String userName = "jbloggs";
		Researcher researcher = new Researcher();
		final DummyTypedQuery<Researcher> expectedTypedQuery = new DummyTypedQuery<Researcher>(researcher);
	    		
		Researcher.findResearchersByUserNameEquals(userName);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.expectReturn(expectedTypedQuery);
        org.springframework.mock.staticmock.AnnotationDrivenStaticEntityMockingControl.playback();
        
        TypedQuery<Researcher> typedQuery = Researcher.findResearchersByUserNameEquals(userName);
        org.junit.Assert.assertEquals(expectedTypedQuery, typedQuery);
	}
}
