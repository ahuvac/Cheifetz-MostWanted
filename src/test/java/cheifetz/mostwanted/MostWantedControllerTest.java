package cheifetz.mostwanted;

import io.reactivex.rxjava3.core.Single;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MostWantedControllerTest {

    private List<Label> names;
    private List<Label> races;
    private List<ImageView> images;
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
        doReturn(Single.never()).when(service).getMostWantedFeed("main");

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
        images = mock(List.class);

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


        images = Arrays.asList(
                mock(ImageView.class),
                mock(ImageView.class),
                mock(ImageView.class)
        );
        controller.images = images;
    }





}
