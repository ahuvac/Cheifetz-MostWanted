package cheifetz.mostwanted;

import org.junit.Test;

import static org.junit.Assert.*;

public class MostWantedServiceTest {

    MostWantedServiceFactory factory = new MostWantedServiceFactory();

    @Test
    public void getMostWantedFeed() {
        // given
        MostWantedService service = factory.newInstance();

        // when
        MostWantedFeed feed = service.getMostWantedFeed()
                .blockingGet();

        // then
        assertNotNull(feed);
        assertNotNull(feed.items);
        assertNotEquals("", feed.items.get(0).title);

    }

}