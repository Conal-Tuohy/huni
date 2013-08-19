package au.net.huni.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import au.net.huni.model.Institution;
import au.net.huni.model.Registration;
import au.net.huni.model.RegistrationStatus;
import au.net.huni.model.Researcher;
import au.net.huni.model.ToolCatalogItem;
import au.net.huni.model.UserRole;
import au.net.huni.security.PasswordGenerator;

public class RegistrationControllerTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();

	@Test
	public void testUpdateWithNoChangeOfStatusToApprovedDoesNotCreateRegistration() {

		final Registration registration = new Registration();
		final Long id = 25L;
		registration.setId(id);
		registration.setUserName("user1");
		registration.setGivenName("given1");
		registration.setFamilyName("family1");
		registration.setEmailAddress("user1@test.net");
		registration.setInstitution(new Institution("occ", "omaha community college"));
		registration.setApplicationDate(Calendar.getInstance());
		registration.setStatus(RegistrationStatus.PENDING);

		final BindingResult bindingResult = context.mock(BindingResult.class);
		context.checking(new Expectations() {{
		    oneOf (bindingResult).hasErrors(); will(returnValue(false));
		}});

		final Map<String, Object> modelMap = new HashMap<String, Object>();		
		final Model uiModel = context.mock(Model.class);
		context.checking(new Expectations() {{
		    oneOf (uiModel).asMap(); will(returnValue(modelMap));
		}});

		final HttpServletRequest httpServletRequest = context.mock(HttpServletRequest.class);
		context.checking(new Expectations() {{
		    oneOf (httpServletRequest).getCharacterEncoding(); will(returnValue("UTF-8"));
		}});

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return null;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}

        	protected boolean isApproval(Registration updatedRegistration, Registration existingRegistration) {
        		return false;
        	}

        	protected Researcher approve(Registration updatedRegistration) {
        		wasApproved[0] = true;
        		return null;
        	}
        };

        controller.update(registration, bindingResult, uiModel, httpServletRequest);
		assertFalse(wasApproved[0]);
	}

	@Test
	public void testUpdateWithChangeOfStatusToApprovedCreatesRegistration() {

		final Registration registration = new Registration();
		final Long id = 25L;
		registration.setId(id);
		registration.setUserName("user1");
		registration.setGivenName("given1");
		registration.setFamilyName("family1");
		registration.setEmailAddress("user1@test.net");
		registration.setInstitution(new Institution("occ", "omaha community college"));
		registration.setApplicationDate(Calendar.getInstance());
		registration.setStatus(RegistrationStatus.PENDING);

		final BindingResult bindingResult = context.mock(BindingResult.class);
		context.checking(new Expectations() {{
		    oneOf (bindingResult).hasErrors(); will(returnValue(false));
		}});

		final Map<String, Object> modelMap = new HashMap<String, Object>();		
		final Model uiModel = context.mock(Model.class);
		context.checking(new Expectations() {{
		    oneOf (uiModel).asMap(); will(returnValue(modelMap));
		}});

		final HttpServletRequest httpServletRequest = context.mock(HttpServletRequest.class);
		context.checking(new Expectations() {{
		    oneOf (httpServletRequest).getCharacterEncoding(); will(returnValue("UTF-8"));
		}});

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return null;
        	}

        	protected boolean isApproval(Registration updatedRegistration, Registration existingRegistration) {
        		return true;
        	}

        	protected Researcher approve(Registration updatedRegistration) {
        		wasApproved[0] = true;
        		return null;
        	}
        	
        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}

        };

        controller.update(registration, bindingResult, uiModel, httpServletRequest);
		assertTrue("Is approval condition causes approval", wasApproved[0]);
	}

	@Test
	public void testChangeOfStatusFromPendingToApprovedDoesApprove() {

		final Registration updatedRegistration = new Registration();
		updatedRegistration.setId(25L);
		updatedRegistration.setUserName("user1");
		updatedRegistration.setGivenName("given1");
		updatedRegistration.setFamilyName("family1");
		updatedRegistration.setEmailAddress("user1@test.net");
		updatedRegistration.setInstitution(new Institution("occ", "omaha community college"));
		updatedRegistration.setApplicationDate(Calendar.getInstance());
		updatedRegistration.setStatus(RegistrationStatus.APPROVED);

		final Registration existingRegistration = new Registration();
		existingRegistration.setId(25L);
		existingRegistration.setUserName("user1");
		existingRegistration.setGivenName("given1");
		existingRegistration.setFamilyName("family1");
		existingRegistration.setEmailAddress("user1@test.net");
		existingRegistration.setInstitution(new Institution("occ", "omaha community college"));
		existingRegistration.setApplicationDate(Calendar.getInstance());
		existingRegistration.setStatus(RegistrationStatus.PENDING);

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return existingRegistration;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}
        	
        	protected Researcher approve(Registration updatedRegistration) {
        		wasApproved[0] = true;
        		return null;
        	}
        };

		boolean result = controller.isApproval(updatedRegistration, existingRegistration);
		assertTrue("Approval for status change from pending to approved", result);
	}

	@Test
	public void testChangeOfStatusFromPendingToPendingDoesNotApprove() {

		final Registration updatedRegistration = new Registration();
		updatedRegistration.setId(25L);
		updatedRegistration.setUserName("user1");
		updatedRegistration.setGivenName("given1");
		updatedRegistration.setFamilyName("family1");
		updatedRegistration.setEmailAddress("user1@test.net");
		updatedRegistration.setInstitution(new Institution("occ", "omaha community college"));
		updatedRegistration.setApplicationDate(Calendar.getInstance());
		updatedRegistration.setStatus(RegistrationStatus.PENDING);

		final Registration existingRegistration = new Registration();
		existingRegistration.setId(25L);
		existingRegistration.setUserName("user1");
		existingRegistration.setGivenName("given1");
		existingRegistration.setFamilyName("family1");
		existingRegistration.setEmailAddress("user1@test.net");
		existingRegistration.setInstitution(new Institution("occ", "omaha community college"));
		existingRegistration.setApplicationDate(Calendar.getInstance());
		existingRegistration.setStatus(RegistrationStatus.PENDING);

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return existingRegistration;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}
        	
        	protected Researcher approve(Registration updatedRegistration) {
        		wasApproved[0] = true;
        		return null;
        	}
        };

		boolean result = controller.isApproval(updatedRegistration, existingRegistration);
		assertFalse("Disapproval for status change from pending to pending", result);
	}

	@Test
	public void testChangeOfStatusFromApprovedToApprovedDoesNotApprove() {

		final Registration updatedRegistration = new Registration();
		updatedRegistration.setId(25L);
		updatedRegistration.setUserName("user1");
		updatedRegistration.setGivenName("given1");
		updatedRegistration.setFamilyName("family1");
		updatedRegistration.setEmailAddress("user1@test.net");
		updatedRegistration.setInstitution(new Institution("occ", "omaha community college"));
		updatedRegistration.setApplicationDate(Calendar.getInstance());
		updatedRegistration.setStatus(RegistrationStatus.APPROVED);

		final Registration existingRegistration = new Registration();
		existingRegistration.setId(25L);
		existingRegistration.setUserName("user1");
		existingRegistration.setGivenName("given1");
		existingRegistration.setFamilyName("family1");
		existingRegistration.setEmailAddress("user1@test.net");
		existingRegistration.setInstitution(new Institution("occ", "omaha community college"));
		existingRegistration.setApplicationDate(Calendar.getInstance());
		existingRegistration.setStatus(RegistrationStatus.APPROVED);

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return existingRegistration;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}
        	
        	protected Researcher approve(Registration updatedRegistration) {
        		wasApproved[0] = true;
        		return null;
        	}
        };

		boolean result = controller.isApproval(updatedRegistration, existingRegistration);
		assertFalse("Disapproval for status change from approved to approved", result);
	}

	@Test
	public void testChangeOfStatusFromPendingToRejectedDoesNotApprove() {

		final Registration updatedRegistration = new Registration();
		updatedRegistration.setId(25L);
		updatedRegistration.setUserName("user1");
		updatedRegistration.setGivenName("given1");
		updatedRegistration.setFamilyName("family1");
		updatedRegistration.setEmailAddress("user1@test.net");
		updatedRegistration.setInstitution(new Institution("occ", "omaha community college"));
		updatedRegistration.setApplicationDate(Calendar.getInstance());
		updatedRegistration.setStatus(RegistrationStatus.REJECTED);

		final Registration existingRegistration = new Registration();
		existingRegistration.setId(25L);
		existingRegistration.setUserName("user1");
		existingRegistration.setGivenName("given1");
		existingRegistration.setFamilyName("family1");
		existingRegistration.setEmailAddress("user1@test.net");
		existingRegistration.setInstitution(new Institution("occ", "omaha community college"));
		existingRegistration.setApplicationDate(Calendar.getInstance());
		existingRegistration.setStatus(RegistrationStatus.PENDING);

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return existingRegistration;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}
        	
        	protected Researcher approve(Registration updatedRegistration) {
        		wasApproved[0] = true;
        		return null;
        	}
        };

		boolean result = controller.isApproval(updatedRegistration, existingRegistration);
		assertFalse("Disapproval for status change from pending to reject", result);
	}

	@Test
	public void testChangeOfStatusFromPendingToRejectedDoesReject() {

		final Registration updatedRegistration = new Registration();
		updatedRegistration.setId(25L);
		updatedRegistration.setUserName("user1");
		updatedRegistration.setGivenName("given1");
		updatedRegistration.setFamilyName("family1");
		updatedRegistration.setEmailAddress("user1@test.net");
		updatedRegistration.setInstitution(new Institution("occ", "omaha community college"));
		updatedRegistration.setApplicationDate(Calendar.getInstance());
		updatedRegistration.setStatus(RegistrationStatus.REJECTED);

		final Registration existingRegistration = new Registration();
		existingRegistration.setId(25L);
		existingRegistration.setUserName("user1");
		existingRegistration.setGivenName("given1");
		existingRegistration.setFamilyName("family1");
		existingRegistration.setEmailAddress("user1@test.net");
		existingRegistration.setInstitution(new Institution("occ", "omaha community college"));
		existingRegistration.setApplicationDate(Calendar.getInstance());
		existingRegistration.setStatus(RegistrationStatus.PENDING);

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return existingRegistration;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}
        	
        	protected Researcher approve(Registration updatedRegistration) {
        		wasApproved[0] = true;
        		return null;
        	}
        };

		boolean result = controller.isRejection(updatedRegistration, existingRegistration);
		assertTrue("Rejection for status change from pending to reject", result);
	}

	@Test
	public void testChangeOfStatusFromPendingToApprovedCreatesResearcher() {

		final Registration updatedRegistration = new Registration();
		updatedRegistration.setId(25L);
		updatedRegistration.setUserName("user1");
		updatedRegistration.setGivenName("given1");
		updatedRegistration.setFamilyName("family1");
		updatedRegistration.setEmailAddress("user1@test.net");
		updatedRegistration.setInstitution(new Institution("occ", "omaha community college"));
		updatedRegistration.setApplicationDate(Calendar.getInstance());
		updatedRegistration.setStatus(RegistrationStatus.APPROVED);

		final Registration existingRegistration = new Registration();
		existingRegistration.setId(25L);
		existingRegistration.setUserName("user1");
		existingRegistration.setGivenName("given1");
		existingRegistration.setFamilyName("family1");
		existingRegistration.setEmailAddress("user1@test.net");
		existingRegistration.setInstitution(new Institution("occ", "omaha community college"));
		existingRegistration.setApplicationDate(Calendar.getInstance());
		existingRegistration.setStatus(RegistrationStatus.PENDING);
        
		final MailSender mailTemplate = context.mock(MailSender.class);
		context.checking(new Expectations() {{
		    oneOf (mailTemplate).send(with(any(SimpleMailMessage.class)));
		}});
        
		final PasswordGenerator passwordGenerator = context.mock(PasswordGenerator.class);
		context.checking(new Expectations() {{
		    oneOf (passwordGenerator).generate(); will(returnValue("password1"));
		}});
		
		final ToolCatalogItem defaultToolCatalogItem = new ToolCatalogItem();

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return existingRegistration;
        	}
        	
        	protected boolean isApproval(Registration updatedRegistration, Registration existingRegistration) {
        		return true;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}

    		protected UserRole assignDefaultRole() {
    			return new UserRole("USER_ROLE");
    		}
    		
    		protected void persistResearcher(Researcher newResearcher) {
    			// Stub out.
    		}
    		
    		protected boolean isExistingResearcher(String userName) {
    	        return false;
    		}

    		protected ToolCatalogItem findDefaultToolCatalogItem() {
    			return defaultToolCatalogItem;
    		}

        };

        controller.setMailTemplate(mailTemplate);
        controller.setPasswordGenerator(passwordGenerator);

        Researcher researcher = controller.approve(updatedRegistration);
		assertEquals("Created researcher on approval", "user1", researcher.getUserName());
		assertNotNull("Created researcher has creation date", researcher.getCreationDate());
		assertTrue("Created researcher has enabled account", researcher.getIsAccountEnabled());
		assertEquals("Created researcher has just one role", 1, researcher.getRoles().size());
		assertEquals("Created researcher has default role", "USER_ROLE", researcher.getRoles().iterator().next().toString());
		assertEquals("Registration status is now approved", RegistrationStatus.APPROVED, updatedRegistration.getStatus());
		assertNotNull("Registration status now has approval date", updatedRegistration.getApprovalDate());
	}

	@Test
	public void testChangeOfStatusFromPendingToRejectedDoesNotCreateResearcher() {

		final Registration updatedRegistration = new Registration();
		updatedRegistration.setId(25L);
		updatedRegistration.setUserName("user1");
		updatedRegistration.setGivenName("given1");
		updatedRegistration.setFamilyName("family1");
		updatedRegistration.setEmailAddress("user1@test.net");
		updatedRegistration.setInstitution(new Institution("occ", "omaha community college"));
		updatedRegistration.setApplicationDate(Calendar.getInstance());
		updatedRegistration.setStatus(RegistrationStatus.REJECTED);

		final Registration existingRegistration = new Registration();
		existingRegistration.setId(25L);
		existingRegistration.setUserName("user1");
		existingRegistration.setGivenName("given1");
		existingRegistration.setFamilyName("family1");
		existingRegistration.setEmailAddress("user1@test.net");
		existingRegistration.setInstitution(new Institution("occ", "omaha community college"));
		existingRegistration.setApplicationDate(Calendar.getInstance());
		existingRegistration.setStatus(RegistrationStatus.PENDING);
        
		final MailSender mailTemplate = context.mock(MailSender.class);
		context.checking(new Expectations() {{
		    oneOf (mailTemplate).send(with(any(SimpleMailMessage.class)));
		}});

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return existingRegistration;
        	}
        	
        	protected boolean isApproval(Registration updatedRegistration, Registration existingRegistration) {
        		return true;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}

    		protected UserRole assignDefaultRole() {
    			return new UserRole("USER_ROLE");
    		}
    		
    		protected void persistResearcher(Researcher newResearcher) {
    			// Stub out.
    		}
        };

        controller.setMailTemplate(mailTemplate);

        Researcher researcher = controller.reject(updatedRegistration);
		assertNull("Created null researcher on rejection", researcher);
		assertEquals("Registration status is now rejected", RegistrationStatus.REJECTED, updatedRegistration.getStatus());
		assertNotNull("Registration status now has approval/rejection date", updatedRegistration.getApprovalDate());
	}

	@Test
	public void testChangeOfStatusFromPendingToApprovedSendsEmail() {

		final Registration updatedRegistration = new Registration();
		updatedRegistration.setId(25L);
		updatedRegistration.setUserName("user1");
		updatedRegistration.setGivenName("given1");
		updatedRegistration.setFamilyName("family1");
		updatedRegistration.setEmailAddress("user1@test.net");
		updatedRegistration.setInstitution(new Institution("occ", "omaha community college"));
		updatedRegistration.setApplicationDate(Calendar.getInstance());
		updatedRegistration.setStatus(RegistrationStatus.APPROVED);

		final Registration existingRegistration = new Registration();
		existingRegistration.setId(25L);
		existingRegistration.setUserName("user1");
		existingRegistration.setGivenName("given1");
		existingRegistration.setFamilyName("family1");
		existingRegistration.setEmailAddress("user1@test.net");
		existingRegistration.setInstitution(new Institution("occ", "omaha community college"));
		existingRegistration.setApplicationDate(Calendar.getInstance());
		existingRegistration.setStatus(RegistrationStatus.PENDING);

		String expectedMessage = "Dear given1 family1,"
				+ "\n\nThank you for your interest in the HuNI Project."
				+ " Your application has been accepted. "
				+ " Your user name is: user1"
				+ " and your temporary password is: password1";

		final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("huniproject@gmail.com");
        mailMessage.setSubject("HuNI Virtual Lab: Registration Approval");
        mailMessage.setTo("user1@test.net");
        mailMessage.setText(expectedMessage);
             
		final PasswordGenerator passwordGenerator = context.mock(PasswordGenerator.class);
		context.checking(new Expectations() {{
		    oneOf (passwordGenerator).generate(); will(returnValue("password1"));
		}});

		final MailSender mailTemplate = context.mock(MailSender.class);
		context.checking(new Expectations() {{
		    oneOf (mailTemplate).send(mailMessage);
		}});

		final ToolCatalogItem defaultToolCatalogItem = new ToolCatalogItem();

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return existingRegistration;
        	}
        	
        	protected boolean isApproval(Registration updatedRegistration, Registration existingRegistration) {
        		return true;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}

    		protected UserRole assignDefaultRole() {
    			return new UserRole("USER_ROLE");
    		}
    		
    		protected void persistResearcher(Researcher newResearcher) {
    			// Stub out.
    		}
    		
    		protected boolean isExistingResearcher(String userName) {
    	        return false;
    		}

    		protected ToolCatalogItem findDefaultToolCatalogItem() {
    			return defaultToolCatalogItem;
    		}
        };
        controller.setMailTemplate(mailTemplate);
        controller.setPasswordGenerator(passwordGenerator);

        controller.approve(updatedRegistration);
	}

	@Test
	public void testChangeOfStatusFromPendingToRejectedSendsEmail() {

		final Registration updatedRegistration = new Registration();
		updatedRegistration.setId(25L);
		updatedRegistration.setUserName("user1");
		updatedRegistration.setGivenName("given1");
		updatedRegistration.setFamilyName("family1");
		updatedRegistration.setEmailAddress("user1@test.net");
		updatedRegistration.setInstitution(new Institution("occ", "omaha community college"));
		updatedRegistration.setApplicationDate(Calendar.getInstance());
		updatedRegistration.setStatus(RegistrationStatus.APPROVED);

		final Registration existingRegistration = new Registration();
		existingRegistration.setId(25L);
		existingRegistration.setUserName("user1");
		existingRegistration.setGivenName("given1");
		existingRegistration.setFamilyName("family1");
		existingRegistration.setEmailAddress("user1@test.net");
		existingRegistration.setInstitution(new Institution("occ", "omaha community college"));
		existingRegistration.setApplicationDate(Calendar.getInstance());
		existingRegistration.setStatus(RegistrationStatus.PENDING);

		String expectedMessage = "Dear given1 family1,"
				+ "\n\nThank you for your interest in the HuNI Project."
				+ " Your application has been rejected. ";

		final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("huniproject@gmail.com");
        mailMessage.setSubject("HuNI Virtual Lab: Registration Rejection");
        mailMessage.setTo("user1@test.net");
        mailMessage.setText(expectedMessage);
        
		final MailSender mailTemplate = context.mock(MailSender.class);
		context.checking(new Expectations() {{
		    oneOf (mailTemplate).send(mailMessage);
		}});

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return existingRegistration;
        	}
        	
        	protected boolean isApproval(Registration updatedRegistration, Registration existingRegistration) {
        		return false;
        	}

        	protected boolean isRejection(Registration updatedRegistration, Registration existingRegistration) {
        		return true;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}

    		protected UserRole assignDefaultRole() {
    			return new UserRole("USER_ROLE");
    		}
    		
    		protected void persistResearcher(Researcher newResearcher) {
    			// Stub out.
    		}
        };
        controller.setMailTemplate(mailTemplate);
		controller.reject(updatedRegistration);
	}

	@Test
	public void testChangeOfStatusFromPendingToApprovedGeneratesPassword() {

		final Registration updatedRegistration = new Registration();
		updatedRegistration.setId(25L);
		updatedRegistration.setUserName("user1");
		updatedRegistration.setGivenName("given1");
		updatedRegistration.setFamilyName("family1");
		updatedRegistration.setEmailAddress("user1@test.net");
		updatedRegistration.setInstitution(new Institution("occ", "omaha community college"));
		updatedRegistration.setApplicationDate(Calendar.getInstance());
		updatedRegistration.setStatus(RegistrationStatus.APPROVED);

		final Registration existingRegistration = new Registration();
		existingRegistration.setId(25L);
		existingRegistration.setUserName("user1");
		existingRegistration.setGivenName("given1");
		existingRegistration.setFamilyName("family1");
		existingRegistration.setEmailAddress("user1@test.net");
		existingRegistration.setInstitution(new Institution("occ", "omaha community college"));
		existingRegistration.setApplicationDate(Calendar.getInstance());
		existingRegistration.setStatus(RegistrationStatus.PENDING);
		
		final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("huniproject@gmail.com");
        mailMessage.setSubject("HuNI Virtual Lab: Registration Approval");
        mailMessage.setTo("user1@test.net");
        mailMessage.setText("Thank you for your interest in the HuNI Project. Your application has been accepted. ");
        
		final MailSender mailTemplate = context.mock(MailSender.class);
		context.checking(new Expectations() {{
		    oneOf (mailTemplate).send(with(any(SimpleMailMessage.class)));
		}});
        
		final PasswordGenerator passwordGenerator = context.mock(PasswordGenerator.class);
		context.checking(new Expectations() {{
		    oneOf (passwordGenerator).generate(); will(returnValue("12345678"));
		}});
		
		final ToolCatalogItem defaultToolCatalogItem = new ToolCatalogItem();

		final boolean wasApproved[] = new boolean[1];
		wasApproved[0] = false;
        RegistrationController controller = new RegistrationController() {
        	protected Registration findExistingRegistration(Registration registration) {
        		return existingRegistration;
        	}
        	
        	protected boolean isApproval(Registration updatedRegistration, Registration existingRegistration) {
        		return true;
        	}

        	protected void updateRegistration(Registration registration) {
        		// Stub out;
        	}

    		protected UserRole assignDefaultRole() {
    			return new UserRole("USER_ROLE");
    		}
    		
    		protected void persistResearcher(Researcher newResearcher) {
    			// Stub out.
    		}
    		
    		protected boolean isExistingResearcher(String userName) {
    	        return false;
    		}

    		protected ToolCatalogItem findDefaultToolCatalogItem() {
    			return defaultToolCatalogItem;
    		}
        };
        controller.setMailTemplate(mailTemplate);
        controller.setPasswordGenerator(passwordGenerator);
		Researcher researcher = controller.approve(updatedRegistration);
		assertNotNull("Password is set for researcher", researcher.getPassword());
		assertEquals("Hashed password has correct for researcher", 64, researcher.getPassword().length());
	}

	@Test
	public void testConstructApprovalMessage() {

		String expectedMessage = "Dear given1 family1,"
				+ "\n\nThank you for your interest in the HuNI Project."
				+ " Your application has been accepted. "
				+ " Your user name is: user1"
				+ " and your temporary password is: password1";

		RegistrationController controller = new RegistrationController();
 		String givenName = "given1";
		String familyName = "family1";
		String userName = "user1";
		String cleartextPassword = "password1";
		String message = controller.constructApprovalMessage(givenName, familyName, userName, cleartextPassword);
		assertEquals("Approval message is correct", expectedMessage, message);
	}

	@Test
	public void testConstructRejectionMessage() {

		String expectedMessage = "Dear given1 family1,"
				+ "\n\nThank you for your interest in the HuNI Project."
				+ " Your application has been rejected. ";

		RegistrationController controller = new RegistrationController();
 		String givenName = "given1";
		String familyName = "family1";
		String message = controller.constructRejectionMessage(givenName, familyName);
		assertEquals("Rejection message is correct", expectedMessage, message);
	}

}
