
public class mainClass {
    public static void main(String[] args) {
        KeelungSightsCrawler crawler = new KeelungSightsCrawler();
        Sight [] sights = crawler.getItems("中正");
        for (Sight s: sights) {
            System.out.println(s);
        }
        if(sights.length==0) System.out.println("未找到景點");
    }
}