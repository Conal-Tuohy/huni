// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package au.net.huni.model;

import au.net.huni.model.UserRole;
import flexjson.JSONDeserializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect UserRole_Roo_Json {
    
    public static UserRole UserRole.fromJsonToUserRole(String json) {
        return new JSONDeserializer<UserRole>().use(null, UserRole.class).deserialize(json);
    }
    
    public static Collection<UserRole> UserRole.fromJsonArrayToUserRoles(String json) {
        return new JSONDeserializer<List<UserRole>>().use(null, ArrayList.class).use("values", UserRole.class).deserialize(json);
    }
    
}
