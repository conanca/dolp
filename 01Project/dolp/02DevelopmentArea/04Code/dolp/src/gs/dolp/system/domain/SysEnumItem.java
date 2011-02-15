package gs.dolp.system.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

@Table("SYSTEM_SYSENUMITEM")
public class SysEnumItem {
	@Id
	private int id;
	@Column
	private String text;
	@Column
	private String value;
	@Column
	private int sysEnumId;
	@One(target = SysEnum.class, field = "sysEnumId")
	private SysEnum sysEnum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSysEnumId() {
		return sysEnumId;
	}

	public void setSysEnumId(int sysEnumId) {
		this.sysEnumId = sysEnumId;
	}

	public SysEnum getSysEnum() {
		return sysEnum;
	}

	public void setSysEnum(SysEnum sysEnum) {
		this.sysEnum = sysEnum;
	}
}
