package au.net.huni.tool_library.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.classpath.operations.jsr303.RooUploadedFile;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ToolOnFileSystem {

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

	@Transient
	private CommonsMultipartFile file; // added

	@Size(max = 20)
	private String fileName; // added
	private long size; // added

	public CommonsMultipartFile getFile() {
		return file;
	}

	// save file to disk ,save filename , file size to database
	public void setFile(CommonsMultipartFile file) {
		this.file = file;
		this.fileName = file.getOriginalFilename();
		this.size = file.getSize();
		System.out.println(" hehe  this.fileName: " + this.fileName + " ,  "
				+ file.getClass().getName());

		try {
			InputStream in = file.getInputStream();
			FileOutputStream f = new FileOutputStream("f:/work/tempDir/"
					+ new Date().getTime());

			int ch = 0;
			while ((ch = in.read()) != -1) {
				f.write(ch);
			}
			f.flush();
			f.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
