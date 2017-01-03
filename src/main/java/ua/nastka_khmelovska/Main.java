package ua.nastka_khmelovska;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ua.nastka_khmelovska.Utils.DatabaseUtil;

public class Main extends Application {
	
	public static DatabaseUtil databaseUtil;
	
	GridPane root = new GridPane();
	Scene scene = new Scene(root, 500, 600); //главное окно
	//Stage primaryStage = new Stage();
	int number = 250; // номер комнаты
	String category = new String("standart"); // категория комнаты
	Image logo = new Image(getClass().getClassLoader().getResourceAsStream("logo.jpg")); // логотип отеля
	ImageView logoImage = new ImageView();
	HBox box = new HBox();
	String nameOfClient = new String("Anastasiia Khmelovska"); // имя клиента
	String lastDate = new String("25.11.2016"); // до какого числа забронирован номер
	Double debtsOfRoom = new Double(120); // задолженность
	VBox vbox = new VBox();
	TableView<Order> tableForTodayOrders = new TableView<Order>();
	// лист для отображения заказов в таблице
	ObservableList<Order> ordersForToday = FXCollections.observableArrayList();
	Order mySpa = new Order("Spa", 100.0, false);
	Order myHousekeeping = new Order("Housekeeping", 20.0, false);
	Order myDelivery = new Order("Delivery", 10.0, false);
	Order myFood = new Order("Food", 30.0, false);
	Order myTransport = new Order("Transport", 20.0, false); 
	Order myTour = new Order("Tour", 150.0, false); 
	double sumOfOrders = 0; 
			

	public static void main(String[] args) {
		
		//databaseUtil = new DatabaseUtil();
		
		//Session session = databaseUtil.getSessionFactory().openSession();
		//ua.nastka_khmelovska.models.Order ord = new ua.nastka_khmelovska.models.Order();
		//Transaction trn = session.beginTransaction();
		//session.createSQLQuery("INSERT INTO order (name, price) VALUES (\"jhjhj\", 12.)");
		//ord.setIdOrder(1l);
		//ord.setName("Lol");
		//ord.setPrice(150.0);
		//session.save(ord);
		//trn.commit();
		//session.close();
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		 File file = new File("Plaza");
		 
		    try {
		        //проверяем, что если файл не существует то создаем его
		        if(!file.exists()){
		            file.createNewFile();
		        }
		 
		        //PrintWriter обеспечит возможности записи в файл
		        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
		 
		        try {
		            //Записываем текст у файл
		            out.println(nameOfClient);
		            out.println(lastDate);
		            out.println("Debts: " + debtsOfRoom);
		        } finally {
		            //После чего мы должны закрыть файл
		            //Иначе файл не запишется
		            out.close();
		        }
		    } catch(IOException e) {
		        throw new RuntimeException(e);
		    }
		
		primaryStage.setTitle("Plaza");
		primaryStage.setScene(scene);
		
		root.setStyle("-fx-background-color: lightblue;");
		primaryStage.show();
		
		Text title = new Text("Your number is " + number + "(" + category + ")");
		title.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 20));
		//настройки корня
		root.add(title, 1, 1);
		root.setAlignment(Pos.TOP_LEFT);
		root.setHgap(1);
		root.setVgap(1);
		root.setPadding(new Insets (10,10,10,10));
		
		logoImage.setImage(logo);
		logoImage.setVisible(true);
		logoImage.setFitHeight(150);
		logoImage.setFitWidth(150);
		box.getChildren().add(logoImage);
		root.add(box, 2, 2);
		
		Text name = new Text("Name: " + nameOfClient);
		name.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
		root.add(name, 1, 2);
		
		Text time = new Text("Your room is ordered before " + lastDate);
		time.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
		root.add(time, 1, 3);
		
		Text debts = new Text("Debts: " + debtsOfRoom + "$");
		debts.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
		root.add(debts, 1, 4);
		
		Text orders = new Text("Your orders for today: ");
		orders.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
		root.add(orders, 1, 5);
		// настройка отображения таблица и листа
		tableForTodayOrders.setEditable(true);
		TableColumn nameOfOrder = new TableColumn("Order");
		TableColumn priceOfOrder = new TableColumn("Price");
		nameOfOrder.setCellValueFactory(
				new PropertyValueFactory<Order, String>("nameOfOrder")
				);
		priceOfOrder.setCellValueFactory(
				new PropertyValueFactory<Order, Double>("price")
				);
		tableForTodayOrders.getColumns().addAll(nameOfOrder, priceOfOrder);
		tableForTodayOrders.setItems(ordersForToday);
		tableForTodayOrders.setMaxHeight(200);
		vbox.getChildren().add(tableForTodayOrders);
		root.add(vbox, 1, 6);
		
		Button orderServices = new Button();
		orderServices.setText("Order services");
		orderServices.setOnAction(new EventHandler<ActionEvent>(){
			// слушатель на кнопку Order services
			public void handle(ActionEvent arg0) {
				final GridPane root = new GridPane();
				root.setPadding(new Insets (10,10,10,10));
				root.setStyle("-fx-background-color: lightblue;");
				Scene scene = new Scene(root, 600, 300);
				Stage primaryStage = new Stage();
				primaryStage.setTitle("Order services");
				primaryStage.setScene(scene);
				primaryStage.show();
				
				
				/*TableView<Order> tableOfOrders = new TableView<Order>();
				ObservableList<Order> orderServices = FXCollections.observableArrayList(
						new Order("Spa", 100.0, false),
						new Order("Housekeeping", 20.0, false),
						new Order("Delivery", 10.0, false),
						new Order("Food", 30.0, false),
						new Order("Transport", 20.0, false),
						new Order("Tour", 150.0, false)
					);
				tableOfOrders.setEditable(true);
				TableColumn nameOfOrder = new TableColumn("Order");
				TableColumn priceOfOrder = new TableColumn("Price");
				TableColumn needing = new TableColumn("Needing");
				nameOfOrder.setCellValueFactory(
						new PropertyValueFactory<Order,String>("nameOfOrder")
						);
				priceOfOrder.setCellValueFactory(
						new PropertyValueFactory<Order, Double>("price")
						);
				tableOfOrders.getColumns().addAll(nameOfOrder, priceOfOrder, needing);
				tableOfOrders.setItems(orderServices);
				tableOfOrders.setMaxHeight(300);
				VBox vbox = new VBox();
				vbox.getChildren().add(tableOfOrders);
				root.add(vbox, 1, 1);*/
				Text title = new Text("Please select orders:");
				title.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 20));
				root.add(title, 3, 1);
				
				Text spa = new Text("Spa - 100$");
				spa.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 18));
				root.add(spa, 1, 2);
				CheckBox cSpa = new CheckBox();
				cSpa.setSelected(false);
				root.add(cSpa, 2, 2);
				cSpa.selectedProperty().addListener(new ChangeListener<Boolean>(){
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
							sumOfOrders = sumOfOrders + 100;
							mySpa.isOrdered = true;
							
					} 
			        });
				
				
				Text housekeeping = new Text("Housekeeping - 20.0$");
				housekeeping.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 18));
				root.add(housekeeping, 1, 3);
				CheckBox cHousekeeping = new CheckBox();
				root.add(cHousekeeping, 2, 3);
				cHousekeeping.selectedProperty().addListener(new ChangeListener<Boolean>(){
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
							sumOfOrders = sumOfOrders + 20;
							myHousekeeping.isOrdered = true;
							
					} 
			        });
				
				Text delivery = new Text("Delivery - 10.0$");
				delivery.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 18));
				root.add(delivery, 1, 4);
				CheckBox cDelivery = new CheckBox();
				root.add(cDelivery, 2, 4);
				cDelivery.selectedProperty().addListener(new ChangeListener<Boolean>(){
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
							sumOfOrders = sumOfOrders + 10;
							myDelivery.isOrdered = true;
							
					} 
			        });
				
				Text food = new Text("Food - 30.0$");
				food.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 18));
				root.add(food, 1, 5);
				CheckBox cFood = new CheckBox();
				root.add(cFood, 2, 5);
				cFood.selectedProperty().addListener(new ChangeListener<Boolean>(){
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
							sumOfOrders = sumOfOrders + 30;
							myFood.isOrdered = true;
							
					} 
			        });
				
				Text transport = new Text("Transport - 20.0$");
				transport.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 18));
				root.add(transport, 1, 6);
				CheckBox cTransport = new CheckBox();
				root.add(cTransport, 2, 6);
				cTransport.selectedProperty().addListener(new ChangeListener<Boolean>(){
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
							sumOfOrders = sumOfOrders + 20;
							myTransport.isOrdered = true;
							
					} 
			        });
				
				Text tour = new Text("Tour - 150.0$");
				tour.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 18));
				root.add(tour, 1, 7);
				CheckBox cTour = new CheckBox();
				root.add(cTour, 2, 7);
				cTour.selectedProperty().addListener(new ChangeListener<Boolean>(){
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
							sumOfOrders = sumOfOrders + 150;
							myTour.isOrdered = true;
							
					} 
			        });
				Button calculate = new Button();
				calculate.setText("Calculate summary");
				calculate.setOnAction(new EventHandler<ActionEvent>(){

					public void handle(ActionEvent arg0) {
						Text toPay = new Text("To pay:" + sumOfOrders);
						toPay.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 18));
						root.add(toPay, 3, 8);
						Button submit = new Button();
						submit.setText("Submit");
						submit.setOnAction(new EventHandler<ActionEvent>(){

							public void handle(ActionEvent arg0) {
								try{
								File orders = new File("Orders For Today");	
								  PrintWriter out = new PrintWriter(orders.getAbsoluteFile());
							        try {
							        	 if(!orders.exists()){
									            orders.createNewFile();
									        }
									        
								if(mySpa.isOrdered){
									ordersForToday.add(mySpa);
									 out.println("Spa");
								}
								if(myHousekeeping.isOrdered){
									ordersForToday.add(myHousekeeping);
									out.println("Housekeeping");
								}
								if(myDelivery.isOrdered){
									ordersForToday.add(myDelivery);
									out.println("Delivery");
								}
								if(myFood.isOrdered){
									ordersForToday.add(myFood);
									out.println("Food");
								}
								if(myTransport.isOrdered){
									ordersForToday.add(myTransport);
									out.println("Transport");
								}
								if(myTour.isOrdered){
									ordersForToday.add(myTour);
									out.println("Tour");
								}
								debtsOfRoom = sumOfOrders;
								out.println("Debts:" + sumOfOrders);
								
							        } finally {
							            //После чего мы должны закрыть файл
							            //Иначе файл не запишется
							            out.close();
							        }
							}    
							        catch(IOException e) {
								        throw new RuntimeException(e);
								    }
							}
							
							
						});
						
						root.add(submit,3,9);
					}
					
				});
				root.add(calculate, 3, 7);
				
				
				
				
			}
		});
		root.add(orderServices, 1, 7);
		
		Button changeTime = new Button();
		changeTime.setText("Change time of stay");
		// слушатель на кнопку Change time of stay
		changeTime.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent arg0) {
				RadioButton extend = new RadioButton();
				RadioButton reduce = new RadioButton();
				extend.setText("Extend");
				reduce.setText("Reduce");
				ToggleGroup time = new ToggleGroup();
				extend.setToggleGroup(time);
				reduce.setToggleGroup(time);
				root.add(extend, 1, 9);
				root.add(reduce, 1, 10);
				final DatePicker timeOfStay = new DatePicker();
				timeOfStay.setValue(LocalDate.now());
				root.add(timeOfStay, 1, 11);
				Button submitTime = new Button();
				submitTime.setText("Submit");
				submitTime.setOnAction(new EventHandler<ActionEvent>(){

					public void handle(ActionEvent arg0) {
						LocalDate newTimeOfStay = timeOfStay.getValue();
						Text tNewTimeOfStay = new Text("Your room is ordered before:" + newTimeOfStay);
						tNewTimeOfStay.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 12));
						root.add(tNewTimeOfStay, 1, 13);
						
						File file = new File("New Time Of Stay");
					    try {
					        //проверяем, что если файл не существует то создаем его
					        if(!file.exists()){
					            file.createNewFile();
					        }
					        PrintWriter out = new PrintWriter(file.getAbsoluteFile());
					        try {
					        		
					        	    out.println("New time of stay:" + newTimeOfStay);
					        	    
					            
					        } finally {
					            //После чего мы должны закрыть файл
					            //Иначе файл не запишется
					            out.close();
					        }
					    } catch(IOException e) {
					        throw new RuntimeException(e);
					    }
						
						
					}
				});
				root.add(submitTime, 1, 12);
			}
		});
		root.add(changeTime, 1, 8);
		
		Button call = new Button();
		call.setText("Call");
		// слушатель на кнопку Call
		call.setOnAction(new EventHandler<ActionEvent>(){
			
			public void handle(ActionEvent arg0) {
				RadioButton reception = new RadioButton();
				RadioButton administrator = new RadioButton();
				reception.setText("To reception");
				administrator.setText("To administartor");
				final ToggleGroup phone = new ToggleGroup();
				reception.setToggleGroup(phone);
				reception.setUserData("+380674523698");
				administrator.setToggleGroup(phone);
				administrator.setUserData("+380685596325");
				phone.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
				      public void changed(ObservableValue<? extends Toggle> ov,
				          Toggle old_toggle, Toggle new_toggle) {
				        if (phone.getSelectedToggle() != null) {
				          String phoneNumber = new String(phone.getSelectedToggle().getUserData().toString());
				          Text phoneNumberText = new Text(phoneNumber);
				          phoneNumberText.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 14));
				  		  root.add(phoneNumberText, 2, 10);
				        }
				      }
				    });
				root.add(reception, 2, 8);
				root.add(administrator, 2, 9);
			}
		});
		root.add(call, 2, 7);
		
		
		
		
		
		
		
		
		
	}

}
