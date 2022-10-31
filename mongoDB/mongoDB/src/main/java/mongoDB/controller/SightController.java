package mongoDB.controller;

import mongoDB.entity.Sight;
import mongoDB.parameter.SightQueryParameter;
import mongoDB.service.SightService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/SightAPI", produces = MediaType.APPLICATION_JSON_VALUE)


public class SightController {


    @Autowired
    private SightService sightService;

    @GetMapping
    public ResponseEntity<List<Sight>> getSights(@ModelAttribute SightQueryParameter param) {
        List<Sight> sights = sightService.getSights(param);
        return ResponseEntity.ok(sights);
    }

    @EventListener(ContextStartedEvent.class)
    private void initDB(ContextStartedEvent e) {
        sightService.deleteSight();

        String url = "https://www.travelking.com.tw/tourguide/taiwan/keelungcity/";
        String webSiteUrl = "https://www.travelking.com.tw";

        List<String> sightUrls = new ArrayList<String>(); //store the sight urls
        try {
            Document document = Jsoup.connect(url).get();   //get doc
            Elements elementId = document.getElementsByClass("box").select("li");  // get sight element

            for (Element element : elementId) {
                sightUrls.add(webSiteUrl + element.getElementsByTag("a").attr("href"));  //add url
            }

            for (String sightUrl : sightUrls) {
                document = Jsoup.connect(sightUrl).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com").timeout(15000).get();
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

                sightService.createSight(new Sight(sightName, zone, category, photoUrl, description, address));

                System.out.println(sightName);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
