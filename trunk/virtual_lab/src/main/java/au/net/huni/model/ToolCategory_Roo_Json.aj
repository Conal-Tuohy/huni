// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.ToolCategory;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect ToolCategory_Roo_Json {
    
    public String ToolCategory.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static ToolCategory ToolCategory.fromJsonToToolCategory(String json) {
        return new JSONDeserializer<ToolCategory>().use(null, ToolCategory.class).deserialize(json);
    }
    
    public static String ToolCategory.toJsonArray(Collection<ToolCategory> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<ToolCategory> ToolCategory.fromJsonArrayToToolCategorys(String json) {
        return new JSONDeserializer<List<ToolCategory>>().use(null, ArrayList.class).use("values", ToolCategory.class).deserialize(json);
    }
    
}