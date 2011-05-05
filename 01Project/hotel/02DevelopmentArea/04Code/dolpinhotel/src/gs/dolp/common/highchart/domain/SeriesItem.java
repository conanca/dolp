package gs.dolp.common.highchart.domain;

import java.util.List;

public class SeriesItem {
	private String name;
	private List<Float> data;

	public SeriesItem(String name, List<Float> data) {
		super();
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Float> getData() {
		return data;
	}

	public void setData(List<Float> data) {
		this.data = data;
	}
}