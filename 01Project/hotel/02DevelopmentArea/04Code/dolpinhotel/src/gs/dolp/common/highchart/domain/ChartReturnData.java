package gs.dolp.common.highchart.domain;

import java.util.List;

public class ChartReturnData {
	private List<String> categories;
	private List<SeriesItem> series;

	public ChartReturnData(List<String> categories, List<SeriesItem> series) {
		super();
		this.categories = categories;
		this.series = series;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<SeriesItem> getSeries() {
		return series;
	}

	public void setSeries(List<SeriesItem> series) {
		this.series = series;
	}
}
