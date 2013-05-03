package au.net.huni.tool_library.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Tool {

    // Metadata fields

    @NotNull
    @Size(max = 128)
    private String name;

    @NotNull
    @Size(max = 256)
    private String description;

    @NotNull
    @Size(max = 256)
    private String url;

    @NotNull
    private Boolean isDefault;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Category> categories = new HashSet<Category>();

    // File upload fields
    
    @NotNull
    @Size(max = 128)
    private String filepath;

    @NotNull
    private long filesize;

    @NotNull
    private String contentType;
    
    @Transient
    private byte[] content;

}
