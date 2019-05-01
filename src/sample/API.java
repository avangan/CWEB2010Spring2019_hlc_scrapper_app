package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class API {


    public static String getVerse(){

        JSONParser parser = new JSONParser();

        String verse =null;
        Document doc = null;
        try {

            URL url = new URL("https://api.scripture.api.bible/v1/bibles/06125adad2d5898a-01/verses/ROM.8.28");//API Connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("api-key", "db67f326154c77aa97ae6b30a7b14ad9");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }

            Object obj = new JSONParser().parse(new InputStreamReader(conn.getInputStream()));
            JSONObject jo = (JSONObject) obj;
            //JSONObject jsonObject = (JSONObject) parser.parse(new InputStreamReader(conn.getInputStream()));
            //System.out.println(jo);
            JSONObject refined = (JSONObject) jo.get("data");
            verse = (String) refined.get("content");
            doc = Jsoup.parse(verse);
            verse = doc.text();

            conn.disconnect();




        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }

        return verse;
    }
}
