// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.ToolCategory;
import au.net.huni.model.ToolCategoryDataOnDemand;
import au.net.huni.model.ToolCategoryIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ToolCategoryIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ToolCategoryIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ToolCategoryIntegrationTest: @Transactional;
    
    @Autowired
    ToolCategoryDataOnDemand ToolCategoryIntegrationTest.dod;
    
    @Test
    public void ToolCategoryIntegrationTest.testCountToolCategorys() {
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to initialize correctly", dod.getRandomToolCategory());
        long count = ToolCategory.countToolCategorys();
        Assert.assertTrue("Counter for 'ToolCategory' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ToolCategoryIntegrationTest.testFindToolCategory() {
        ToolCategory obj = dod.getRandomToolCategory();
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to provide an identifier", id);
        obj = ToolCategory.findToolCategory(id);
        Assert.assertNotNull("Find method for 'ToolCategory' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'ToolCategory' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void ToolCategoryIntegrationTest.testFindAllToolCategorys() {
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to initialize correctly", dod.getRandomToolCategory());
        long count = ToolCategory.countToolCategorys();
        Assert.assertTrue("Too expensive to perform a find all test for 'ToolCategory', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<ToolCategory> result = ToolCategory.findAllToolCategorys();
        Assert.assertNotNull("Find all method for 'ToolCategory' illegally returned null", result);
        Assert.assertTrue("Find all method for 'ToolCategory' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ToolCategoryIntegrationTest.testFindToolCategoryEntries() {
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to initialize correctly", dod.getRandomToolCategory());
        long count = ToolCategory.countToolCategorys();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<ToolCategory> result = ToolCategory.findToolCategoryEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'ToolCategory' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'ToolCategory' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ToolCategoryIntegrationTest.testFlush() {
        ToolCategory obj = dod.getRandomToolCategory();
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to provide an identifier", id);
        obj = ToolCategory.findToolCategory(id);
        Assert.assertNotNull("Find method for 'ToolCategory' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyToolCategory(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'ToolCategory' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ToolCategoryIntegrationTest.testMergeUpdate() {
        ToolCategory obj = dod.getRandomToolCategory();
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to provide an identifier", id);
        obj = ToolCategory.findToolCategory(id);
        boolean modified =  dod.modifyToolCategory(obj);
        Integer currentVersion = obj.getVersion();
        ToolCategory merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'ToolCategory' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ToolCategoryIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to initialize correctly", dod.getRandomToolCategory());
        ToolCategory obj = dod.getNewTransientToolCategory(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'ToolCategory' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'ToolCategory' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void ToolCategoryIntegrationTest.testRemove() {
        ToolCategory obj = dod.getRandomToolCategory();
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'ToolCategory' failed to provide an identifier", id);
        obj = ToolCategory.findToolCategory(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'ToolCategory' with identifier '" + id + "'", ToolCategory.findToolCategory(id));
    }
    
}
