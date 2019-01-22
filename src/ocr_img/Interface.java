package ocr_img;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import javafx.stage.FileChooser;


public class Interface extends Application {
	
	private String path;
	private String res;
	private String username;
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage window) {
		
		welcome(window);
		
	}
	private void welcome(Stage window) {
		GridPane welcome_grid = new GridPane();
		welcome_grid.setPadding(new Insets(50,50,50,50));
		welcome_grid.setHgap(8);
		welcome_grid.setVgap(10);
		
		
		Label welcome_text = new Label("Welcome to the Image Recognition Software");
		GridPane.setRowIndex(welcome_text, 0);
		
		Label new_user = new Label("Old User ?");
		Label old_user = new Label("New User ?");
		
		GridPane.setConstraints(new_user, 0, 1);
		GridPane.setConstraints(old_user, 1, 1);
		
		Button login = new Button("Log In");
		login.setOnAction(e -> login(window));  		// Enter login page code here;
		GridPane.setConstraints(login, 0, 2);
		
		Button signup = new Button("Sign Up");
		GridPane.setConstraints(signup, 1, 2);
		signup.setOnAction(e -> register(window));		// Register page here
		
		welcome_grid.getChildren().addAll(welcome_text, new_user, old_user, login, signup);
		Scene scene = new Scene(welcome_grid);
		window.setScene(scene);
		window.show();
	}
	private void login(Stage window) {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(50,50,50,50));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label label = new Label("Enter Username: ");
		GridPane.setConstraints(label, 0, 0);
		
		TextField username = new TextField();
		username.setPromptText("Your Username:");
		GridPane.setConstraints(username, 1, 0);
		
		Label pass_text = new Label("Enter password: ");
		GridPane.setConstraints(pass_text, 0, 1);
		
		PasswordField password = new PasswordField();
		password.setPromptText("Your Password");
		GridPane.setConstraints(password, 1, 1);
		
		Button login = new Button("Log In");
		GridPane.setConstraints(login, 1, 2);
		login.setOnAction(e-> validate(username, password, window));
		
		Button back = new Button("Back");
		GridPane.setConstraints(back, 0, 2);
		back.setOnAction(e -> welcome(window));
		
		grid.getChildren().addAll(label, username, pass_text, password, login, back);
		
		Scene scene  = new Scene(grid);
		window.setScene(scene);
		window.show();
	}
	private void validate(TextField username, TextField password, Stage calling_window) {
		String s1 = username.getText();
		String s2 = password.getText();
		if(s1.length() == 0 || s2.length() == 0) {
			System.out.println("Invalid input");
			return;
		}
		try {
		     Class.forName("oracle.jdbc.OracleDriver");
	         Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE ","SYSTEM","SYS");
	         PreparedStatement ps = con.prepareStatement("select * from info");
	         ResultSet rs = ps.executeQuery();
	         //rs.next();
	         //System.out.println(rs.getString(1) + "  " + rs.getString(2));
	         boolean flag = false;
	         while(rs.next()) {
	        	 String user = rs.getString(1);
	        	 String pass = rs.getString(2);
	        	 if(s1.equals(user) && s2.equals(pass)) {
	        		 flag = true;
	        		 break;
	        	 }
	         }
	         Stage window = new Stage();
	         window.initModality(Modality.APPLICATION_MODAL);
	         
	         if(flag) {
	        	 Alert.display("Sucess", "Logged In sucessfully");
	        	 this.username = s1;
	        	 ocr(calling_window);
	        	 
	        }
	         else {
	        	 Alert.display("Failure", "Incorrect Credentials");
	         }
	         
	         
		}catch(Exception e) {
			System.out.println(e);
			System.out.println("Connection unsucessful");
		}
		
	}
	private void register(Stage window) {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(50,50,50,50));
		grid.setHgap(10);
		grid.setVgap(10);
		
		Label welcome = new Label("Enter your details");
		GridPane.setRowIndex(welcome, 0);
		
	    Label user_text = new Label("UserName: ");
	    GridPane.setConstraints(user_text, 0, 1);
	    
	    TextField username = new TextField();
	    username.setPromptText("Enter New Username");
	    GridPane.setConstraints(username, 1, 1);
	    
	    Label pass_text = new Label("Password: ");
	    GridPane.setConstraints(pass_text, 0, 2);
	    
	    PasswordField password = new PasswordField();
	    password.setPromptText("Enter New Password");
	    GridPane.setConstraints(password, 1, 2);
		
	    Label confirm_pass_text = new Label("Confirm Password: ");
	    GridPane.setConstraints(confirm_pass_text, 0, 3);
	    
	    PasswordField confirm_password = new PasswordField();
	    confirm_password.setPromptText("Confirm New Password");
	    GridPane.setConstraints(confirm_password, 1, 3);
	    
	    Button register = new Button("Register");
	    GridPane.setConstraints(register, 1, 4);
	    register.setOnAction(e -> reg_validate(username, password, confirm_password, window)); // code to add to database
	    
	    Button back = new Button("Back");
		GridPane.setConstraints(back, 0, 4);
		back.setOnAction(e -> welcome(window));
		
		grid.getChildren().addAll(welcome, user_text, username, pass_text, password, confirm_pass_text, confirm_password, register, back);
		Scene scene = new Scene(grid);
		window.setScene(scene);
		
	}
	private void reg_validate(TextField username, TextField password, TextField confirm_password, Stage calling_window) {
		if(username.getText().length() == 0 || password.getText().length() == 0 || confirm_password.getText().length() == 0) {
			System.out.println("Invalid Input");
			return;
		}
		if(password.getText().equals(confirm_password.getText()) == false) {
			Alert.display("Error", "Password and Confirm Password should be same");
			return;
		}
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE ", "SYSTEM", "SYS");
			PreparedStatement ps = con.prepareStatement("insert into info values(?,?)");
			ps.setString(1, username.getText());
			ps.setString(2, password.getText());
			
			ps.executeUpdate();
			Alert.display("Success", "Account created successfully");
			welcome(calling_window);
			
		} catch (Exception e1) {
			System.out.println(e1);
			Alert.display("Failure", "Account not created, check database connection");
			System.out.println("Account not created");
			

		}
		
	}
	private void ocr(Stage window) {
		Insets gap = new Insets(5);
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(50,50,50,50));
		Label welcome = new Label("Steps:\n");
		Label s1 = new Label("1. Upload an image by clicking upload\n2. Click on Scan to start");
		Label s2 = new Label("Welcome " + username);
		
		VBox top = new VBox(5);
		top.getChildren().addAll(s2, welcome, s1);
		
		
	    
		VBox left = new VBox(5);
		Button upload = new Button("Upload");           // open file chooser to chose file and return file path to be displayed in fileurl
		upload.setOnAction(e -> {
		     selectfile(window);
			//System.out.println(path);
		});
		
		
		
		
		Button logout = new Button("Logout");
		logout.setOnAction(e -> welcome(window));
		
		VBox center = new VBox(5);
		
		
		
		VBox bottom = new VBox(20);
		Button scan = new Button("Scan file");			// Scan data from the file and get the result and display in the textfield at the bottom
		scan.setOnAction(e -> image_ocr(window));
		
		
		bottom.getChildren().addAll(upload,scan, logout);
		
		pane.setLeft(left);
		pane.setMargin(left, gap);
		
		pane.setTop(top);
		pane.setMargin(top, gap);
		
		pane.setBottom(bottom);
		pane.setMargin(bottom, gap);
		
		pane.setCenter(center);
		pane.setMargin(center, gap);
		
		Scene scene = new Scene(pane);
		window.setScene(scene);
			
		
		
	}
	private void selectfile(Stage window) {
		FileChooser new_file  = new FileChooser();
		File temp = new_file.showOpenDialog(window);
		path = temp.getPath();
		String format = path.substring(path.length() - 4, path.length());
		
		if(!(format.equals(".jpg") || format.equals(".png") || format.equals(".gif"))) {
			Alert.display("Failure", "Select a valid image file");
			path =  null;
			return;
		}
		
		//return temp.getPath();
		
	}
	private void image_ocr(Stage window) {
		if(path == null) {
			Alert.display("Failure", "Select an image to proceed");
			return;
		}
		File file = new File(path);
		
		
		String p = new File("").getAbsolutePath().concat("\\abc\\tessdata");
		
		
		ITesseract ii = new Tesseract();
		ii.setDatapath(p);
		try {
			res = ii.doOCR(file);
			//System.out.println(res);
			resultscreen(window);
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Operation Unsuccessful");
		}
		
		
	}
	private void resultscreen(Stage window) {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(50,50,50,50));
		grid.setHgap(15);
		grid.setVgap(15);
		
		Label intro = new Label("Image Scanning Sucessful");
		GridPane.setRowIndex(intro, 0);
		
		Label image_text = new Label("Image Path:\n" + path);
		GridPane.setRowIndex(image_text, 1);
		
		
		
		Label scan_text = new Label("Scanned Data: ");
		GridPane.setConstraints(scan_text, 0, 2);
		
		TextArea text = new TextArea(res);
		text.setPrefSize(200, 200);
		GridPane.setConstraints(text, 1, 2);
		
		Button again = new Button("Scan Another file");
		again.setOnAction(e -> ocr(window));
		GridPane.setConstraints(again, 0, 3);
		
		Button logout = new Button("Logout");
		logout.setOnAction(e -> welcome(window));
		GridPane.setRowIndex(logout, 4);
		
		Button save = new Button("Save as Text File");
		save.setOnAction(e -> savefile());
		GridPane.setConstraints(save, 1, 3);
		
		grid.getChildren().addAll(intro, image_text, scan_text, text, again, logout, save);
		Scene scene = new Scene(grid);
		
		window.setScene(scene);
		
		
	}
	private void savefile() {
		try {
			FileWriter file = new FileWriter(username + ".txt");
			file.write(res);
			
			Alert.display("Success", "File was saved successfully");
			file.close();
		}
		catch(Exception e) {
			
		}
		
	}
}
class Alert {
	public static void display(String title, String message) {
		Stage window = new Stage();
		window.setTitle(title);
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(50,50,50,50));
		grid.setVgap(10);
		grid.setHgap(10);
		
		Label label = new Label(message);
		GridPane.setRowIndex(label, 0);
		
		Button exit = new Button("OK");
		exit.setOnAction(e -> window.close());
		GridPane.setConstraints(exit, 1, 1);
		
		grid.getChildren().addAll(label, exit);
		
		Scene scene = new Scene(grid);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setScene(scene);
		window.showAndWait();
		
	}
}