// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.Registration;
import au.net.huni.model.RegistrationDataOnDemand;
import au.net.huni.model.RegistrationIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect RegistrationIntegrationTest_Roo_IntegrationTest {
    
    declare @type: RegistrationIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: RegistrationIntegrationTest: @Transactional;
    
    @Autowired
    RegistrationDataOnDemand RegistrationIntegrationTest.dod;
    
    @Test
    public void RegistrationIntegrationTest.testCountRegistrations() {
        Assert.assertNotNull("Data on demand for 'Registration' failed to initialize correctly", dod.getRandomRegistration());
        long count = Registration.countRegistrations();
        Assert.assertTrue("Counter for 'Registration' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void RegistrationIntegrationTest.testFindRegistration() {
        Registration obj = dod.getRandomRegistration();
        Assert.assertNotNull("Data on demand for 'Registration' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Registration' failed to provide an identifier", id);
        obj = Registration.findRegistration(id);
        Assert.assertNotNull("Find method for 'Registration' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Registration' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void RegistrationIntegrationTest.testFindAllRegistrations() {
        Assert.assertNotNull("Data on demand for 'Registration' failed to initialize correctly", dod.getRandomRegistration());
        long count = Registration.countRegistrations();
        Assert.assertTrue("Too expensive to perform a find all test for 'Registration', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Registration> result = Registration.findAllRegistrations();
        Assert.assertNotNull("Find all method for 'Registration' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Registration' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void RegistrationIntegrationTest.testFindRegistrationEntries() {
        Assert.assertNotNull("Data on demand for 'Registration' failed to initialize correctly", dod.getRandomRegistration());
        long count = Registration.countRegistrations();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Registration> result = Registration.findRegistrationEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Registration' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Registration' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void RegistrationIntegrationTest.testFlush() {
        Registration obj = dod.getRandomRegistration();
        Assert.assertNotNull("Data on demand for 'Registration' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Registration' failed to provide an identifier", id);
        obj = Registration.findRegistration(id);
        Assert.assertNotNull("Find method for 'Registration' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyRegistration(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Registration' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void RegistrationIntegrationTest.testMergeUpdate() {
        Registration obj = dod.getRandomRegistration();
        Assert.assertNotNull("Data on demand for 'Registration' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Registration' failed to provide an identifier", id);
        obj = Registration.findRegistration(id);
        boolean modified =  dod.modifyRegistration(obj);
        Integer currentVersion = obj.getVersion();
        Registration merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Registration' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void RegistrationIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Registration' failed to initialize correctly", dod.getRandomRegistration());
        Registration obj = dod.getNewTransientRegistration(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Registration' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Registration' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Registration' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void RegistrationIntegrationTest.testRemove() {
        Registration obj = dod.getRandomRegistration();
        Assert.assertNotNull("Data on demand for 'Registration' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Registration' failed to provide an identifier", id);
        obj = Registration.findRegistration(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Registration' with identifier '" + id + "'", Registration.findRegistration(id));
    }
    
}
