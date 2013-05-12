// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.ToolParameter;
import au.net.huni.model.ToolParameterDataOnDemand;
import au.net.huni.model.ToolParameterIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ToolParameterIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ToolParameterIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ToolParameterIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: ToolParameterIntegrationTest: @Transactional;
    
    @Autowired
    ToolParameterDataOnDemand ToolParameterIntegrationTest.dod;
    
    @Test
    public void ToolParameterIntegrationTest.testCountToolParameters() {
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to initialize correctly", dod.getRandomToolParameter());
        long count = ToolParameter.countToolParameters();
        Assert.assertTrue("Counter for 'ToolParameter' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ToolParameterIntegrationTest.testFindToolParameter() {
        ToolParameter obj = dod.getRandomToolParameter();
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to provide an identifier", id);
        obj = ToolParameter.findToolParameter(id);
        Assert.assertNotNull("Find method for 'ToolParameter' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ToolParameter' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void ToolParameterIntegrationTest.testFindAllToolParameters() {
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to initialize correctly", dod.getRandomToolParameter());
        long count = ToolParameter.countToolParameters();
        Assert.assertTrue("Too expensive to perform a find all test for 'ToolParameter', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ToolParameter> result = ToolParameter.findAllToolParameters();
        Assert.assertNotNull("Find all method for 'ToolParameter' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ToolParameter' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ToolParameterIntegrationTest.testFindToolParameterEntries() {
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to initialize correctly", dod.getRandomToolParameter());
        long count = ToolParameter.countToolParameters();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<ToolParameter> result = ToolParameter.findToolParameterEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'ToolParameter' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ToolParameter' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ToolParameterIntegrationTest.testFlush() {
        ToolParameter obj = dod.getRandomToolParameter();
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to provide an identifier", id);
        obj = ToolParameter.findToolParameter(id);
        Assert.assertNotNull("Find method for 'ToolParameter' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyToolParameter(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'ToolParameter' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ToolParameterIntegrationTest.testMergeUpdate() {
        ToolParameter obj = dod.getRandomToolParameter();
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to provide an identifier", id);
        obj = ToolParameter.findToolParameter(id);
        boolean modified =  dod.modifyToolParameter(obj);
        Integer currentVersion = obj.getVersion();
        ToolParameter merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ToolParameter' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ToolParameterIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to initialize correctly", dod.getRandomToolParameter());
        ToolParameter obj = dod.getNewTransientToolParameter(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ToolParameter' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'ToolParameter' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void ToolParameterIntegrationTest.testRemove() {
        ToolParameter obj = dod.getRandomToolParameter();
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ToolParameter' failed to provide an identifier", id);
        obj = ToolParameter.findToolParameter(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'ToolParameter' with identifier '" + id + "'", ToolParameter.findToolParameter(id));
    }
    
}
