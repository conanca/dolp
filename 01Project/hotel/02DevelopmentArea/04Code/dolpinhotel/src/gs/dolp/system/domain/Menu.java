package gs.dolp.system.domain;

import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_MENU")
public class Menu {
	@Column
	@Id
	private int id;
	@Column
	private String name;
	@Column
	private String url;
	@Column
	private String description;
	@Column
	private int parentId;
	@Many(target = Menu.class, field = "parentId")
	private List<Menu> childrenMenus;
	@Column
	private int level;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public List<Menu> getChildrenMenu() {
		return childrenMenus;
	}

	public void setChildrenMenu(List<Menu> childrenMenu) {
		this.childrenMenus = childrenMenu;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
