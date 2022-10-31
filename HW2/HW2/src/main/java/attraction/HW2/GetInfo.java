package attraction.HW2;

import java.util.concurrent.Callable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GetInfo implements Callable<Sight>{

    private String sightUrl;

    public GetInfo(String sightUrl) {
        this.sightUrl = sightUrl;
    }

    @Override
    public Sight call() throws Exception {
        try {
            Document document = Jsoup.connect(sightUrl).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .timeout(10000).get();
            //get zone
            String zone = document.getElementsByClass("bc_last").first().text();
            //get sight name
            String sightName = document.select("meta[itemprop=name]").first().attr("content");
            //get category
            String category = document.getElementsByTag("strong").first().text();
            //get photo url
            String photoUrl = document.select("meta[itemprop=image]").first().attr("content");
            //get description:
            String description = document.select("meta[itemprop=description]").first().attr("content");
            //get address
            String address = document.select("meta[itemprop=address]").first().attr("content");
            System.out.println(sightName);
            return new Sight(sightName, zone, category, photoUrl, description, address);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
