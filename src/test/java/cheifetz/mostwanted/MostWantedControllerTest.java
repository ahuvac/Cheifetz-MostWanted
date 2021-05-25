package cheifetz.mostwanted;

import io.reactivex.rxjava3.core.Single;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MostWantedControllerTest {

    private List<Label> names;
    private List<Label> races;
    private List<ImageView> imageList;
    private List<Label> rewards;
    private List<Label> cautions;
    private Label title;


    private MostWantedController controller;
    MostWantedService service;

    @BeforeClass
    public static void beforeClass() {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });
    }

    @Test
    public void initialize() {
        // given
        givenMostWantedController();
        doReturn(Single.never()).when(service).getMostWantedFeed();

        // when
        controller.initialize();
        // then
        verify(controller.title).setText("FBI Crimes");
    }

    public void givenMostWantedController() {
        service = mock(MostWantedService.class);
        controller = new MostWantedController(service);

        rewards = mock(List.class);
        races = mock(List.class);
        cautions = mock(List.class);
        names = mock(List.class);
        imageList = mock(List.class);

        controller.title = mock(Label.class);

        rewards = Arrays.asList(
                mock(Label.class),
                mock(Label.class),
                mock(Label.class)
        );
        controller.rewards = rewards;

        races = Arrays.asList(
                mock(Label.class),
                mock(Label.class),
                mock(Label.class)
        );
        controller.races = races;

        cautions = Arrays.asList(
                mock(Label.class),
                mock(Label.class),
                mock(Label.class)
        );
        controller.cautions = cautions;

        names = Arrays.asList(
                mock(Label.class),
                mock(Label.class),
                mock(Label.class)
        );
        controller.names = names;


        imageList = Arrays.asList(
                mock(ImageView.class),
                mock(ImageView.class),
                mock(ImageView.class)
        );
        controller.images = imageList;
    }

    @Test
    public void setTexts() {
        //given
        givenMostWantedController();
        MostWantedFeed feed = mock(MostWantedFeed.class);
        feed.items = Arrays.asList(
                mock(MostWantedFeed.Items.class),
                mock(MostWantedFeed.Items.class),
                mock(MostWantedFeed.Items.class));

        for (int i = 0; i < 3; i++) {
            feed.items.get(i).images = Arrays.asList(
                    mock(MostWantedFeed.Images.class),
                    mock(MostWantedFeed.Images.class),
                    mock(MostWantedFeed.Images.class));
        }

        feed.items.get(0).title = "John";
        feed.items.get(1).title = "Joe";
        feed.items.get(2).title = "Tom";

        doReturn("https://www.fbi.gov/wanted/seeking-info/structure-fire---arson/@@images/image/thumb").
                when(feed.items.get(0).images.get(0)).getImageURL();
        doReturn("https://www.fbi.gov/wanted/seeking-info/structure-fire---arson/@@images/image/thumb").
                when(feed.items.get(1).images.get(0)).getImageURL();
        doReturn("https://www.fbi.gov/wanted/seeking-info/structure-fire---arson/@@images/image/thumb").
                when(feed.items.get(2).images.get(0)).getImageURL();
        //when
        controller.setTexts(feed);

        //then
        for (int i = 0; i < controller.names.size(); i++) {
            verify(names.get(i), times(1)).setText(anyString());
            verify(races.get(i), times(1)).setText(anyString());
            verify(rewards.get(i), times(1)).setText(anyString());
            verify(cautions.get(i), times(1)).setText(anyString());
            verify(imageList.get(i), times(1)).setImage(any(Image.class));
        }
    }

    @Test
    public void doService() {
        //given
        givenMostWantedController();
        doReturn(Single.never()).when(service).getMostWantedFeed();

        //when
        controller.doService();

        //then
        verify(service).getMostWantedFeed();
    }

    @Test
    public void refresh() {
        //given
        controller = mock(MostWantedController.class);
        //when
        controller.refresh(mock(ActionEvent.class));
        //then
        verify(controller, never()).doService();
    }
}
