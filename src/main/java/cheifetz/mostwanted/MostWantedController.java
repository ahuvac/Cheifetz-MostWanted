package cheifetz.mostwanted;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    List<Label> locals;

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
        Disposable disposable = service.getMostWantedFeed()
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
        ArrayList<MostWantedFeed.Items> itemList = new ArrayList<>();

        for (int i = 0 ; i < names.size(); i ++){
            Label lbl = names.get(i);
            while (itemList.size() < i + 1) {
                MostWantedFeed.Items item = feed.items.get(rand.nextInt(feed.items.size()));
                if (item.title != null && !itemList.contains(item)) {
                    itemList.add(item);
                    String fullName = item.title;
                    if (fullName.contains(" - ")) {
                        lbl.setText(fullName.substring(0, fullName.indexOf(" - ")));
                        locals.get(i).setText(fullName.substring(fullName.indexOf("-") + 1));
                    } else {
                        lbl.setText(fullName);
                    }
                }
            }
        }

        for (int i = 0; i < names.size(); i++) {
            races.get(i).setText(fixNulls(itemList.get(i).race));
            cautions.get(i).setText(fixNulls(itemList.get(i).caution));
            rewards.get(i).setText(fixNulls(itemList.get(i).reward_text));
            images.get(i).setImage(new Image(itemList.get(i).images.get(0).thumb));
        }
    }

    private String fixNulls(String str) {
        return str == null ? "" : str.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
    }


    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    public void refresh(ActionEvent actionEvent) {
        doService();
    }

}

