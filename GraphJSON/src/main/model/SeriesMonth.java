package main.model;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.scene.chart.XYChart;

public class SeriesMonth {
	
	private Sub jv = new Sub("#45ef6a");
	private Sub kip = new Sub("#e28214");
	private Sub kra = new Sub("#038216");
	private Sub emma = new Sub("#f25ce8");
	private Sub peter = new Sub("#c1adcc");
	private Sub alk = new Sub("#baf3f4");
	private Sub n2o = new Sub ("#ad47e8");
	private Sub pheni = new Sub("#efe267");
	
	
	public SeriesMonth(JSONArray data) {
		
		for(int i = 0; i < data.length(); i++) {
			JSONObject day = data.getJSONObject(i);
			
			//Datemode 0 = Take Day as normal data
			//Datamode 1 = Take Day as total
			//Datemode 2 = Take Day as days
			int dateMode = 0;
			if(day.getString("datum").equals("Gesamt")) dateMode = 1;
			if(day.getString("datum").equals("Tage")) dateMode = 2;
			Iterator<?> keys = day.keys();
			double jv = 0;
			double kip = 0;
			double kra = 0;
			double emma = 0;
			double peter = 0;
			double alk = 0;
			double n2o = 0;
			double pheni = 0;
			int date = -1;
			while(keys.hasNext()) {
				String key = (String)keys.next();
				switch(key) {
					case "datum":
						if(dateMode == 0) {
							date = getDateDay(day.getString(key));
						}
						break;
					case "jvg":
						jv += getJValue(day);
						break;
					case "krag" :
						kra += day.getDouble(key);
						break;
					case "kip" :
						kip += day.getDouble(key);
						break;
					case "peterl" :
						peter += day.getDouble(key);
						break;
					case "emmamg" :
						emma += day.getDouble(key);
						break;
					case "n2ocap" :
						n2o += day.getDouble(key);
						break;
					case "alkbier" :
						alk += day.getDouble(key);
						break;
					case "phenig" :
						pheni += day.getDouble(key);
						break;
					default :
						//throw new Exception(key);
				}
			}
			if(dateMode == 1) {
				this.jv.setTotal(jv);
				this.kra.setTotal(kra);
				this.peter.setTotal(peter);
				this.emma.setTotal(emma);
				this.n2o.setTotal(n2o);
				this.alk.setTotal(alk);
				this.pheni.setTotal(pheni);
				this.kip.setTotal(kip);
			} else if(dateMode == 2) {
				this.jv.setDays((int) jv);
				this.kra.setDays((int) kra);
				this.peter.setDays((int) peter);
				this.emma.setDays((int) emma);
				this.n2o.setDays((int) n2o);
				this.alk.setDays((int) alk);
				this.pheni.setDays((int) pheni);
				this.kip.setDays((int) kip);
			} else {
				this.jv.put(date,jv);
				this.kra.put(date,kra);
				this.peter.put(date,peter);
				this.emma.put(date,emma);
				this.n2o.put(date,n2o);
				this.alk.put(date,alk);
				this.kip.put(date,kip);
				this.pheni.put(date, pheni);
			}
		}
	}
	
	public SeriesMonth(ArrayList<SeriesMonth> months,int getAllMonths) {
	
		int dayOffset = 0;
		double jv = 0;
		double kip = 0;
		double kra = 0;
		double emma = 0;
		double peter = 0;
		double alk = 0;
		double n2o = 0;
		double pheni = 0;
		
		int jvDays = 0;
		int kipDays = 0;
		int kraDays = 0;
		int emmaDays = 0;
		int peterDays = 0;
		int alkDays = 0;
		int n2oDays = 0;
		int pheniDays = 0;
		
		for(SeriesMonth month : months) {
			jv += month.getJv().getTotal();
			jvDays += month.getJv().getDays();
			kip += month.getKip().getTotal();
			kipDays += month.getKip().getDays();
			kra += month.getKra().getTotal();
			kraDays += month.getKra().getDays();
			emma += month.getEmma().getTotal();
			emmaDays += month.getEmma().getDays();
			peter += month.getPeter().getTotal();
			peterDays += month.getPeter().getDays();
			alk += month.getAlk().getTotal();
			alkDays += month.getAlk().getDays();
			n2o += month.getN2o().getTotal();
			n2oDays += month.getN2o().getDays();
			pheni += month.getPheni().getTotal();
			pheniDays += month.getPheni().getDays();
			
			for(XYChart.Data<Number, Number> data :month.getJv().getSub().getData()) {
				this.jv.put(data.getXValue().intValue() + dayOffset, data.getYValue().doubleValue());
			}
			for(XYChart.Data<Number, Number> data :month.getKra().getSub().getData()) {
				this.kra.put(data.getXValue().intValue() + dayOffset, data.getYValue().doubleValue());
			}
			for(XYChart.Data<Number, Number> data :month.getKip().getSub().getData()) {
				this.kip.put(data.getXValue().intValue() + dayOffset, data.getYValue().doubleValue());
			}
			for(XYChart.Data<Number, Number> data :month.getPeter().getSub().getData()) {
				this.peter.put(data.getXValue().intValue() + dayOffset, data.getYValue().doubleValue());
			}
			for(XYChart.Data<Number, Number> data :month.getEmma().getSub().getData()) {
				this.emma.put(data.getXValue().intValue() + dayOffset, data.getYValue().doubleValue());
			}
			for(XYChart.Data<Number, Number> data :month.getAlk().getSub().getData()) {
				this.alk.put(data.getXValue().intValue() + dayOffset, data.getYValue().doubleValue());
			}
			for(XYChart.Data<Number, Number> data :month.getN2o().getSub().getData()) {
				this.n2o.put(data.getXValue().intValue() + dayOffset, data.getYValue().doubleValue());
			}
			for(XYChart.Data<Number, Number> data :month.getPheni().getSub().getData()) {
				this.pheni.put(data.getXValue().intValue() + dayOffset, data.getYValue().doubleValue());
			}
			for(XYChart.Data<Number, Number> data :month.getAlk().getSub().getData()) {
				this.alk.put(data.getXValue().intValue() + dayOffset, data.getYValue().doubleValue());
			}
			dayOffset += month.getJv().getSub().getData().size();
		}
		this.jv.setTotal(jv);
		this.kip.setTotal(kip);
		this.kra.setTotal(kra);
		this.peter.setTotal(peter);
		this.emma.setTotal(emma);
		this.pheni.setTotal(pheni);
		this.n2o.setTotal(n2o);
		this.alk.setTotal(alk);
		
		this.jv.setDays(jvDays);
		this.kip.setDays(kipDays);
		this.kra.setDays(kraDays);
		this.peter.setDays(peterDays);
		this.emma.setDays(emmaDays);
		this.pheni.setDays(pheniDays);
		this.n2o.setDays(n2oDays);
		this.alk.setDays(alkDays);

	}
	
	
	public SeriesMonth(ArrayList<SeriesMonth> months) {

		double jv = 0;
		double kip = 0;
		double kra = 0;
		double emma = 0;
		double peter = 0;
		double alk = 0;
		double n2o = 0;
		double pheni = 0;
		
		int jvDays = 0;
		int kipDays = 0;
		int kraDays = 0;
		int emmaDays = 0;
		int peterDays = 0;
		int alkDays = 0;
		int n2oDays = 0;
		int pheniDays = 0;
		
		int monthId = 1;
		for(SeriesMonth month : months) {
			
			jv += month.getJv().getTotal();
			jvDays += month.getJv().getDays();
			kip += month.getKip().getTotal();
			kipDays += month.getKip().getDays();
			kra += month.getKra().getTotal();
			kraDays += month.getKra().getDays();
			emma += month.getEmma().getTotal();
			emmaDays += month.getEmma().getDays();
			peter += month.getPeter().getTotal();
			peterDays += month.getPeter().getDays();
			alk += month.getAlk().getTotal();
			alkDays += month.getAlk().getDays();
			n2o += month.getN2o().getTotal();
			n2oDays += month.getN2o().getDays();
			pheni += month.getPheni().getTotal();
			pheniDays += month.getPheni().getDays();
			
			this.jv.put(monthId, month.getJv().getTotal());
			this.kip.put(monthId, month.getKip().getTotal());
			this.kra.put(monthId, month.getKra().getTotal());
			this.emma.put(monthId, month.getEmma().getTotal());
			this.peter.put(monthId, month.getPeter().getTotal());
			this.alk.put(monthId, month.getAlk().getTotal());
			this.n2o.put(monthId, month.getN2o().getTotal());
			this.pheni.put(monthId, month.getPheni().getTotal());
			
			monthId++;
			
		}
		this.jv.setTotal(jv);
		this.kip.setTotal(kip);
		this.kra.setTotal(kra);
		this.peter.setTotal(peter);
		this.emma.setTotal(emma);
		this.pheni.setTotal(pheni);
		this.n2o.setTotal(n2o);
		this.alk.setTotal(alk);
		
		this.jv.setDays(jvDays);
		this.kip.setDays(kipDays);
		this.kra.setDays(kraDays);
		this.peter.setDays(peterDays);
		this.emma.setDays(emmaDays);
		this.pheni.setDays(pheniDays);
		this.n2o.setDays(n2oDays);
		this.alk.setDays(alkDays);
	}
	
	
//	public Date getGregDate(String dateString) {
//		String[] temp = dateString.split("T")[0].split("-");
//		return new Date(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
//	}
	
	public int getDateDay(String str) {
		return Integer.parseInt(str.split("T")[0].split("-")[2]);
	}
	
	private double getJValue(JSONObject day) {
		double sum = 0;
		String value;
		try {
			value = day.getString("jvg");
		} catch(JSONException j) {
			try {
				sum = day.getDouble("jvg");
				return sum;
			} catch (Exception e) {
				return 0;
			}
			
		}
		if(value.contains("-")) {
			try {
				String[] parts = value.split("\\-");
				sum += Double.parseDouble(parts[0]);
				sum += Double.parseDouble(parts[1]);
			} catch(Exception e) { }
		} else if(value.contains("/")) {
			try {
				String[] parts = value.split("/");
				sum += Double.parseDouble(parts[0]);
				sum += Double.parseDouble(parts[1]);
			} catch(Exception e) { }
		} else {
			try {
				sum += Double.parseDouble(value);
			} catch(Exception e) { }
		}
		return sum;
	}


	public Sub getJv() {
		return jv;
	}


	public Sub getKip() {
		return kip;
	}


	public Sub getKra() {
		return kra;
	}


	public Sub getEmma() {
		return emma;
	}


	public Sub getPeter() {
		return peter;
	}


	public Sub getAlk() {
		return alk;
	}


	public Sub getN2o() {
		return n2o;
	}


	public Sub getPheni() {
		return pheni;
	}
}
