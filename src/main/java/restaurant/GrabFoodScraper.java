package restaurant;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;



public class GrabFoodScraper {
    private String location;
    private List<Restaurant> restaurantList;

    public GrabFoodScraper(String location) {
        this.location = location;
        this.restaurantList = new ArrayList<>();
    }

    public void fetchRestaurants() throws Exception {
        String url = "https://www.grab.com/sg/food/";
        Document doc = Jsoup.connect(url).data("q", location).get();
        Elements restaurants = doc.select("div.restaurant-info");
        for (Element restaurant : restaurants) {
            String name = restaurant.select("h6.restaurant-name").text().trim();
            String address = restaurant.select("p.address").text().trim();
            String cuisine = restaurant.select("p.cuisine").text().trim();
            restaurantList.add(new Restaurant(name, address, cuisine));
        }
    }

    public void scrapeDeliveryInfo(Restaurant restaurant) {
        // Dummy method to simulate scraping of delivery info
        restaurant.deliveryFee = 5.0;
        restaurant.deliveryTime = 30;
    }

    public void scrapeAllDeliveryInfo() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10); // Adjust thread pool size as needed
        for (Restaurant restaurant : restaurantList) {
            executor.execute(() -> scrapeDeliveryInfo(restaurant));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }
    }

    public void saveData() throws Exception {
//        try (OutputStream os = new GZIPOutputStream(new FileOutputStream(location + "_restaurants.ndjson.gz"));
//             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {
//            Gson gson = new Gson();

            for (Restaurant restaurant : restaurantList) {
//                writer.write(gson.toJson(restaurant));
//                writer.newLine();
                System.out.println(restaurant);
            }
            System.out.println("Executed");
//        }
    }

    public void run() throws Exception {
        fetchRestaurants();
        scrapeAllDeliveryInfo();
        saveData();

    }
}
