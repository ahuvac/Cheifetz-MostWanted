package cheifetz.mostwanted;

import java.util.Date;
import java.util.List;

public class MostWantedFeed {
    List<Items> items;
    int total;

    public int getTotal(){
        return total;
    }

    static class Items {
        String title;
        String description;
        List<Images> images;
        String sex;
        String race;
        List<String> possible_states;
        String caution;
        String reward_text;
    }

    static class Images {
        String thumb;
    }


}


