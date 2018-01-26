package main.model;

import javafx.beans.property.SimpleStringProperty;

public class SubTableData {
	
	private SimpleStringProperty sub;
	private SimpleStringProperty total;
	private SimpleStringProperty days;
	
	public SubTableData(String sub, double total, int days) {
		if(total == Math.floor(total) && !Double.isInfinite(total)) {
			int tot = (int) total;
			this.total = new SimpleStringProperty(tot+"");
		} else {
			this.total = new SimpleStringProperty(total+"");
		}
		this.sub = new SimpleStringProperty(sub);
		this.days = new SimpleStringProperty(days+"");
	}
	
	public SubTableData(String sub, double total, int days, String unit) {
		if(total == Math.floor(total) && !Double.isInfinite(total)) {
			int tot = (int) total;
			this.total = new SimpleStringProperty(tot+unit);
		} else {
			this.total = new SimpleStringProperty(total+unit);
		}
		this.sub = new SimpleStringProperty(sub);
		this.days = new SimpleStringProperty(days+"");
	}

	public SimpleStringProperty getSub() {
		return sub;
	}

	public SimpleStringProperty getTotal() {
		return total;
	}

	public SimpleStringProperty getDays() {
		return days;
	}

	
	
}
