package cheifetz.mostwanted;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;

import java.util.List;

public class MostWantedController {

    @FXML
    Label a;


    MostWantedService service;

    public MostWantedController(MostWantedService service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        doService();
    }

    public void doService() {


        Disposable disposable = service.getMostWantedFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe(this::onMostWantedFeed, this::onError);
    }

    public void onMostWantedFeed(MostWantedFeed feed) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setTexts(feed);
            }
        });

    }

    public void setTexts(MostWantedFeed feed) {
        //a.setText(feed.name);
    }

    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }


}

