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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MostWantedController {

    @FXML
    List<Label> names;
    @FXML
    List<Label> races;
    @FXML
    List<ImageView> images;
    @FXML
    List<Label> rewards;
    @FXML
    List<Label> cautions;

    @FXML
    Label title;

    MostWantedService service;

    public MostWantedController(MostWantedService service) {
        this.service = service;
    }

    @FXML
    public void initialize() {
        title.setText("FBI Crimes");
        doService();
    }

    public void doService() {
        Disposable disposable = service.getMostWantedFeed("main")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe(this::onMostWantedFeed, this::onError);
    }

    public void onMostWantedFeed(MostWantedFeed feed) {
        Platform.runLater(() ->
                setTexts(feed));
    }

    public void setTexts(MostWantedFeed feed) {
        Random rand = new Random();
        int index = 0;
        ArrayList<MostWantedFeed.Items> itemList = new ArrayList<>();

        for (Label lbl : names) {
            while (itemList.size() < names.indexOf(lbl) + 1) {
                MostWantedFeed.Items item = feed.items.get(rand.nextInt(feed.items.size()));
                if (item.title != null && item.caution != null && item.description != null  && item.race != null  && !itemList.contains(item)) {
                    itemList.add(item);
                    lbl.setText(item.title);
                }
            }
        }

        for (Label lbl : races) {
            String str = "";
            if (itemList.get(index).race != null) str = itemList.get(index).race + "";
            lbl.setText(str);
            index++;
        }
        index = 0;

        for (ImageView img : images) {
            img.setImage(new Image(itemList.get(index).images.get(0).thumb));
            index++;
        }

        index = 0;

        for (Label lbl : rewards) {
            String str = "";
            if (itemList.get(index).reward_text != null) str = itemList.get(index).reward_text + "";
            lbl.setText(str);
            index++;
        }

        index = 0;

        for (Label lbl : cautions) {
            String str = "";
            if (itemList.get(index).caution != null) str = itemList.get(index).caution + "";
            lbl.setText(str.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " "));
            index++;
        }


    }


    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    public void refresh() {
        doService();
    }

}

