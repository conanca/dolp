package gs.dolp.system.domain;

import java.util.List;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.json.Json;

@Table("SYSTEM_ORGANIZATION")
public class Organization {
	@Id
	private int id;
	@Column
	private String code;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private int parentOrgId;
	@One(target = Organization.class, field = "parentOrgId")
	private Organization parentOrg;
	@Many(target = Organization.class, field = "parentOrgId")
	private List<Organization> nodes;
	private boolean open;
	private boolean isParent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public int getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(int parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	public Organization getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(Organization parentOrg) {
		this.parentOrg = parentOrg;
	}

	public List<Organization> getChildrenOrgs() {
		return nodes;
	}

	public void setChildrenOrgs(List<Organization> childrenOrgs) {
		this.nodes = childrenOrgs;
	}

	public List<Organization> getNodes() {
		return nodes;
	}

	public void setNodes(List<Organization> nodes) {
		this.nodes = nodes;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String toString() {
		return Json.toJson(this);
	}
}
