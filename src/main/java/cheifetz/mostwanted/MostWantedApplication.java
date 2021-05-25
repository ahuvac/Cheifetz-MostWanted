package cheifetz.mostwanted;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MostWantedApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        MostWantedService service = new MostWantedServiceFactory().newInstance();
        MostWantedController controller = new MostWantedController(service);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/weather_application.fxml"));
        loader.setController(controller);

        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 850);

        stage.setTitle("FBI Most Wanted");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


