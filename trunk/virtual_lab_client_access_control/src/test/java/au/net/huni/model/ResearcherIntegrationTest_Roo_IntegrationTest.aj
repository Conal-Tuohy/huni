// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.Researcher;
import au.net.huni.model.ResearcherDataOnDemand;
import au.net.huni.model.ResearcherIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ResearcherIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ResearcherIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ResearcherIntegrationTest: @Transactional;
    
    @Autowired
    ResearcherDataOnDemand ResearcherIntegrationTest.dod;
    
    @Test
    public void ResearcherIntegrationTest.testCountResearchers() {
        Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());
        long count = Researcher.countResearchers();
        Assert.assertTrue("Counter for 'Researcher' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ResearcherIntegrationTest.testFindResearcher() {
        Researcher obj = dod.getRandomResearcher();
        Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Researcher' failed to provide an identifier", id);
        obj = Researcher.findResearcher(id);
        Assert.assertNotNull("Find method for 'Researcher' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Researcher' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void ResearcherIntegrationTest.testFindAllResearchers() {
        Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());
        long count = Researcher.countResearchers();
        Assert.assertTrue("Too expensive to perform a find all test for 'Researcher', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Researcher> result = Researcher.findAllResearchers();
        Assert.assertNotNull("Find all method for 'Researcher' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Researcher' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ResearcherIntegrationTest.testFindResearcherEntries() {
        Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());
        long count = Researcher.countResearchers();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Researcher> result = Researcher.findResearcherEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Researcher' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Researcher' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ResearcherIntegrationTest.testFlush() {
        Researcher obj = dod.getRandomResearcher();
        Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Researcher' failed to provide an identifier", id);
        obj = Researcher.findResearcher(id);
        Assert.assertNotNull("Find method for 'Researcher' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyResearcher(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Researcher' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ResearcherIntegrationTest.testMergeUpdate() {
        Researcher obj = dod.getRandomResearcher();
        Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Researcher' failed to provide an identifier", id);
        obj = Researcher.findResearcher(id);
        boolean modified =  dod.modifyResearcher(obj);
        Integer currentVersion = obj.getVersion();
        Researcher merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Researcher' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ResearcherIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", dod.getRandomResearcher());
        Researcher obj = dod.getNewTransientResearcher(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Researcher' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Researcher' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Researcher' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void ResearcherIntegrationTest.testRemove() {
        Researcher obj = dod.getRandomResearcher();
        Assert.assertNotNull("Data on demand for 'Researcher' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Researcher' failed to provide an identifier", id);
        obj = Researcher.findResearcher(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Researcher' with identifier '" + id + "'", Researcher.findResearcher(id));
    }
    
}
