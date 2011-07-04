package gs.dolp.system.domain;

import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_SYSENUM")
public class SysEnum {
	@Id
	private Integer id;
	@Column
	private String name;
	@Column
	private String description;
	@Many(target = SysEnumItem.class, field = "sysEnumId")
	private List<SysEnumItem> items;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SysEnumItem> getItems() {
		return items;
	}

	public void setItems(List<SysEnumItem> items) {
		this.items = items;
	}
}