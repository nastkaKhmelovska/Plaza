package ua.nastka_khmelovska;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Order {
	
	public StringProperty nameOfOrder;
	public DoubleProperty price;
	public Boolean isOrdered;
	
	public Order(){
	}
	
	public Order(String nameOfOrder, Double price, Boolean isOrdered){
		this.nameOfOrder = new SimpleStringProperty(nameOfOrder);
		this.price = new SimpleDoubleProperty(price);
		this.isOrdered = new Boolean(isOrdered);
	}

	public String  getNameOfOrder() {
		return nameOfOrder.get();
	}

	public void setNameOfOrder(String  nameOfOrder) {
		this.nameOfOrder.set(nameOfOrder);
	}

	public Double  getPrice() {
		return price.get();
	}

	public void setPrice(Double  price) {
		this.price.set(price);
	}

	public Boolean  getIsOrdered() {
		return isOrdered;
	}

	public void setIsOrdered(Boolean  isOrdered) {
		this.isOrdered = isOrdered;
	}
	
	
	
	Double sum;
	
	
	

}
