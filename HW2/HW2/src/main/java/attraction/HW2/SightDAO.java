package attraction.HW2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

@Repository
public class SightDAO {

    private final List<Sight> sightDB = new ArrayList<>();

    @PostConstruct
    private void initDB() {
        String url = "https://www.travelking.com.tw/tourguide/taiwan/keelungcity/";
        String webSiteUrl = "https://www.travelking.com.tw";

        List<String> sightUrls = new ArrayList<String>(); //store the sight urls
        try {
            Document document = Jsoup.connect(url).get();   //get doc
            Elements elementId = document.getElementsByClass("box").select("li");  // get sight element

            for (Element element : elementId) {
                sightUrls.add(webSiteUrl + element.getElementsByTag("a").attr("href"));  //add url
            }

            for(String sightUrl: sightUrls) {
                GetInfo sightCallable = new GetInfo(sightUrl);
                FutureTask<Sight> futureTask = new FutureTask<Sight>(sightCallable);
                ExecutorService executor = Executors.newCachedThreadPool();
                executor.execute(futureTask);
                sightDB.add(futureTask.get());
                /*document = Jsoup.connect(sightUrl).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
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

                System.out.println(sightName);

                sightDB.add(new Sight(sightName, zone, category, photoUrl, description, address));*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Sight> find(SightQueryParameter param){
        String zone = Optional.ofNullable(param.getZone()).orElse("");

        return sightDB.stream()
                .filter(p -> p.getZone().contains(zone))
                .collect(Collectors.toList());
    }
}
