import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeelungSightsCrawler {

    private List<Sight> tmpList;

    public Sight[] getItems(String inputZone) {
        String url = "https://www.travelking.com.tw/tourguide/taiwan/keelungcity/";
        String webSiteUrl = "https://www.travelking.com.tw";
        boolean found = false;
        List<String> sightUrls = new ArrayList<String>(); //store the sight urls
        try {
            Document document = Jsoup.connect(url).get();   //get doc
            Elements elementId = document.getElementsByClass("box").select("h4, li");  // get sight element

            for (Element element : elementId) {
                if (element.text().indexOf(inputZone) != -1) found = true;
                else if (element.tagName().equals("h4") && found) break;

                if (element.tagName().equals("li") && found) {
                    sightUrls.add(webSiteUrl + element.getElementsByTag("a").attr("href"));  //add url
                }
            }
            tmpList = new ArrayList<Sight>();
            for(String sightUrl: sightUrls) {
                document = Jsoup.connect(sightUrl).get();
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
                tmpList.add(new Sight(sightName, zone, category, photoUrl, description, address));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpList.toArray(new Sight[0]);
    }
}
