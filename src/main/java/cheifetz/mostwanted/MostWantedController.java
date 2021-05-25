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

    static final Random RAND = new Random();

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
        ArrayList<MostWantedFeed.Items> itemList = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            Label label = names.get(i);
            // running until we find an item with a title
            while (true) {
                MostWantedFeed.Items item = feed.items.get(RAND.nextInt(feed.items.size()));
                if (item.title != null && !itemList.contains(item)) {
                    itemList.add(item);
                    String fullName = item.title;
                    //separating title into name and details by dashes
                    if (fullName.contains(" - ")) {
                        label.setText(fullName.substring(0, fullName.indexOf(" - ")));
                        locals.get(i).setText(fullName.substring(fullName.indexOf("-") + 1));
                    } else {
                        label.setText(fullName);
                    }
                    break;
                }
            }
        }

        for (int i = 0; i < names.size(); i++) {
            MostWantedFeed.Items item = itemList.get(i);
            races.get(i).setText(fixNulls(item.race));
            cautions.get(i).setText(fixNulls(item.caution));
            rewards.get(i).setText(fixNulls(item.reward_text));
            images.get(i).setImage(new Image(item.images.get(0).getImageURL()));
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

