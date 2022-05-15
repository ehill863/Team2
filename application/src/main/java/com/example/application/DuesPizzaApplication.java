/*
	Created By Ian Durant for Team 2
 */

//Some things still need to do/ most likely still need to do
//Set up the scenes correctly, so that they have their own scenes
//Add it to the database?
//Add comments
//Making sure it works with the other parts of the program correctly
//Changing of test names
//Visual improvements


package com.example.application;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class DuesPizzaApplication extends Application{
	private static Scene IPS;
	private static Scene IOS;
	private static Scene ICS;
	private static Scene UPS;
	private static Scene UOS;
	private static Scene UCS;
	private static Scene Home;
	private static Scene Help;
	private static Stage primaryStage;
	
	public static void main(String[] args){
		launch(args);
	}
	
	public void start(Stage primaryStage){
		DuesPizzaApplication.primaryStage = primaryStage;
		
		createHome();
		createHelp();
		createInsertPizzaScene();
		createInsertOptionScene();
		createInsertCustomerScene();
		createUpdatePizzaScene();
		createUpdateOptionScene();
		createUpdateCustomerScene();
		
		primaryStage.setScene(Home);
		primaryStage.setTitle("Due's Pizza Application");
		primaryStage.show();
	}
	
	private MenuBar createMenuBar(){
		MenuItem homeItem = new MenuItem("Home Page");
		MenuItem helpItem = new MenuItem("Help Menu");
		MenuItem IP = new MenuItem("Insert Pizza");
		MenuItem IO = new MenuItem("Insert Option");
		MenuItem IC = new MenuItem("Insert Customer");
		MenuItem UP = new MenuItem("Update Pizza");
		MenuItem UO = new MenuItem("Update Option");
		MenuItem UC = new MenuItem("Update Customer");
		Menu startMenu = new Menu("Start");
		Menu insertMenu = new Menu("Insert");
		Menu updateMenu = new Menu("Update");
		startMenu.getItems().setAll(homeItem, helpItem);
		insertMenu.getItems().setAll(IP, IO, IC);
		updateMenu.getItems().setAll(UP, UO, UC);
		MenuBar menuBar = new MenuBar(startMenu, insertMenu, updateMenu);
		homeItem.setOnAction(event -> primaryStage.setScene(Home));
		helpItem.setOnAction(event -> primaryStage.setScene(Help));
		IP.setOnAction(event -> primaryStage.setScene(IPS));
		IO.setOnAction(event -> primaryStage.setScene(IOS));
		IC.setOnAction(event -> primaryStage.setScene(ICS));
		UP.setOnAction(event -> primaryStage.setScene(UPS));
		UO.setOnAction(event -> primaryStage.setScene(UOS));
		UC.setOnAction(event -> primaryStage.setScene(UCS));
		return menuBar;
	}
	
	private void createHome(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Home"));
		Home = new Scene(root);
	}
	
	private void createHelp(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Help"));
		Help = new Scene(root);
	}
	
	private void createInsertPizzaScene(){
		private TextField customerNameText;
		private TextField customerAddressText;
		private TextField customerPhoneNumberText;
		private Scene createIdScene;
		private Scene test;
		private int customID;
		
		RadioButton smallPizza;
		RadioButton mediumPizza;
		RadioButton largePizza;
		ToggleGroup pizzaSizeToggleGroup;
		ComboBox pizzaCrustComboBox;
		ComboBox pizzaSauceComboBox;
		ListView pizzaToppingsListView;
		Button buildPizza;
		
		PizzaDBManager.createDB();
		//Connects to Database
		Connection conn = DriverManager.getConnection("jdbc:derby:PizzaDB;create=true");
		
		//Create CustomerID Scene
		Label customerNameLabel = new Label("Enter Your Name: ");
		Label customerAddressLabel = new Label("Enter Your Address: ");
		Label customerPhoneNumberLabel = new Label("Enter Your Phone Number: ");
		
		customerNameText = new TextField();
		customerAddressText = new TextField();
		customerPhoneNumberText = new TextField();
		
		Button createId = new Button("Create Customer ID");
		
		createId.setOnAction(new createIdHandler());
		
		VBox createIDVBox = new VBox(20,customerNameLabel, customerNameText, customerAddressLabel,
				customerAddressText, customerPhoneNumberLabel, customerPhoneNumberText, createId);
		
		createIdScene = new Scene(createIDVBox, 500, 500);
		
		//Build Your Own Pizza Scene
		Label title = new Label("Build Your Own Pizza:");
		Label customerIDLabel = new Label("Customer ID: " + customID);
		Label pizzaSizeLabel = new Label("Size: ");
		Label pizzaCrustLabel = new Label("Crust: ");
		Label pizzaSauceLabel = new Label("Sauce: ");
		Label pizzaToppingsLabel = new Label("Toppings: ");
		
		smallPizza = new RadioButton("Small");
		mediumPizza = new RadioButton("Medium");
		largePizza = new RadioButton("Large");
		
		pizzaSizeToggleGroup = new ToggleGroup();
		smallPizza.setToggleGroup(pizzaSizeToggleGroup);
		mediumPizza.setToggleGroup(pizzaSizeToggleGroup);
		largePizza.setToggleGroup(pizzaSizeToggleGroup);
		
		pizzaCrustComboBox = new ComboBox();
		
		//Puts the crust options in to the pizzaCrustComboBox
		try {
			String options = "SELECT Name FROM AvailableOption WHERE Type='C'";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(options);
			
			while (resultSet.next())
			{
				String crust = resultSet.getString("Name");
				pizzaCrustComboBox.getItems().addAll(crust);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		pizzaSauceComboBox = new ComboBox<>();
		//Puts the sauce options in to the pizzaSauceComboBox
		try {
			String options = "SELECT Name FROM AvailableOption WHERE Type='S'";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(options);
			
			while (resultSet.next())
			{
				String sauce = resultSet.getString("Name");
				pizzaSauceComboBox.getItems().addAll(sauce);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//ListView Size Variables
		final double listViewWidth = 254, listViewHeight = 140;
		
		pizzaToppingsListView = new ListView();
		pizzaToppingsListView.setMaxSize(listViewWidth,listViewHeight);
		pizzaToppingsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//Puts the toppings options in to the pizzaToppingsListView
		try{
			String options = "SELECT Name FROM AvailableOption WHERE Type='T'";
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(options);
			
			while(resultSet.next())
			{
				String toppings = resultSet.getString("Name");
				pizzaToppingsListView.getItems().addAll(toppings);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		buildPizza = new Button("Build Your Pizza!!!!!");
		
		buildPizza.setOnAction(new buildPizzaHandler());
		
		//To be deleted/changed for testing use only
		VBox testBox = new VBox(20,createIDVBox,title,customerIDLabel,pizzaSizeLabel,smallPizza,mediumPizza,largePizza,
				pizzaCrustLabel,pizzaCrustComboBox,pizzaSauceLabel,pizzaSauceComboBox,
				pizzaToppingsLabel,pizzaToppingsListView, buildPizza);
		test = new Scene(testBox, 1000,750);
		
		//Adding the Scene the Stage and Setting the Stage's Title
		//primaryStage.setScene(createIdScene);
		primaryStage.setScene(test);
		
		
		primaryStage.setTitle("Due's Pizzeria: The best pizza in town");
		
		//Showing the Stage
		primaryStage.show();
	}
	
	class createIdHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent aEvent) {
			//
			String name = customerNameText.getText();
			String address = customerAddressText.getText();
			String phoneNumber = customerPhoneNumberText.getText();
			
			try {
				int CID = PizzaDBManager.getUniqueCID();
				PizzaDBManager.updateCustomer(CID,name,address,phoneNumber);
				customID = CID;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	class buildPizzaHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent aEvent) {
			//
			String pizzaSize;
			String pizzaCrust;
			String pizzaSauce;
			ObservableList<String> toppings;
			
			pizzaSize = "";
			//
			if (smallPizza.isSelected())
			{
				pizzaSize = "S";
			}
			if (mediumPizza.isSelected())
			{
				pizzaSize = "M";
			}
			if (largePizza.isSelected())
			{
				pizzaSize = "L";
			}
			
			pizzaCrust = String.valueOf(pizzaCrustComboBox.getValue());
			
			pizzaSauce = String.valueOf(pizzaSauceComboBox.getValue());
			
			toppings = pizzaToppingsListView.getSelectionModel().getSelectedItems();
			
			System.out.println(customID);
			System.out.println(pizzaSize);
			System.out.println(pizzaCrust);
			System.out.println(pizzaSauce);
			System.out.println(toppings);
			
		}
	}
	
}
		
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Insert Pizza"));
		IPS = new Scene(root);
	}
	
	private void createInsertOptionScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Insert Option"));
		IOS = new Scene(root);
	}
	
	private void createInsertCustomerScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Insert Customer"));
		ICS = new Scene(root);
	}
	
	private void createUpdatePizzaScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Update Pizza"));
		UPS = new Scene(root);
	}
	
	private void createUpdateOptionScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Update Option"));
		UOS = new Scene(root);
	}
	
	private void createUpdateCustomerScene(){
		MenuBar menuBar = createMenuBar();
		VBox root = new VBox(menuBar, new Label("Update Customer"));
		UCS = new Scene(root);
	}
}
