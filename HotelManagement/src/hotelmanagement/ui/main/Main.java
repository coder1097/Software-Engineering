package hotelmanagement.ui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Laptop
 */
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        //Full screen
        Screen screen = Screen.getPrimary();
        Rectangle2D bound = screen.getVisualBounds();
        stage.setX(bound.getMinX());
        stage.setY(bound.getMinY());
        stage.setWidth(bound.getWidth());
        stage.setHeight(bound.getHeight());
        stage.setMaximized(true);
        
        System.out.println(bound.getMaxX());
        System.out.println(bound.getMaxY());
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
