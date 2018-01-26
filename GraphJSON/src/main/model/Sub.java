package main.model;

import java.awt.Color;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import main.util.ColorHandler;

public class Sub {
	
	private XYChart.Series<Number, Number> sub = new XYChart.Series<Number, Number>();
	private double total = 0;
	private int days = 0;
	private Color color;
	
	public Sub() {
		color = ColorHandler.hex2Rgb("#9d5dd8");
	}
	
	public Sub(String rgb) {
		color = ColorHandler.hex2Rgb(rgb);
	}
	
	public void put(int day, double number) {
		sub.getData().add(new Data<Number, Number>(day,number));
	}
	
	public XYChart.Series<Number, Number> getSub() {
		return sub;
	}
	public void setSub(XYChart.Series<Number, Number> jv) {
		this.sub = jv;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	
	public Color getRGBColor() {
		return color;
	}
	
	
}
