package org.example;

import restaurant.GrabFoodScraper;

public class Main {
    public static void main(String[] args) throws Exception {
        String[] locations = {
                "PT Singapore - Choa Chu Kang North 6, Singapore, 689577",
                "Chong Boon Dental Surgery - Block 456 Ang Mo Kio Avenue 10, #01-1574, Singapore, 560456"
        };

        for (String location : locations) {
            GrabFoodScraper scraper = new GrabFoodScraper(location);
            try {
                scraper.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}