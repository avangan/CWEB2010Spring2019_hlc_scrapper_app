package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    @FXML
    private GridPane grid_parent;
    @FXML
    private PieChart pie_chart;

    @FXML
    private BarChart<?, ?> bar_chart;

    @FXML
    private ListView<?> school_database_listview;

    @FXML
    private JFXTextField institute_name_txtbx;

    @FXML
    private JFXTextField address_txtbx1;

    @FXML
    private JFXTextField website_txtbx;

    @FXML
    private JFXTextField phone_txtbx;

    @FXML
    private JFXTextField type_txtbx;

    @FXML
    private JFXTextField campus_setting_txtbx;

    @FXML
    private JFXTextField housing_txtbx;

    @FXML
    private JFXTextField stu_population_txtbx;

    @FXML
    private JFXTextField ratio_txtbx;

    @FXML
    private JFXTextField enrollment_txtbx1;

    @FXML
    private ImageView institution_logo_graphic;

    @FXML
    private JFXComboBox<String> institution_dropdown;

    @FXML
    private ImageView app_logo;
    String imageURL;

    @FXML
    private Label scripturelb;

    /**@FXML
    private WebView scripture_webv;**/


    HashMap<String, String> institutionNameURL;
    public void initialize() throws IOException {

        //Create a hashmap that holds the institution name and corresponding url
        institutionNameURL = new HashMap<String, String>();
        institutionNameURL.put("Dunwoody College of Technology", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&pg=1&id=175227" );
        institutionNameURL.put("Augsburg College", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&pg=1&id=173045");
        institutionNameURL.put("North Central University", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&id=174437");
        institutionNameURL.put("Univ. Of Minnesota", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&id=174066");
        institutionNameURL.put("Hamline University", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&pg=2&id=173665");
        institutionNameURL.put("Macalester College", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&pg=2&id=173902");


        institution_dropdown.getItems().addAll(institutionNameURL.keySet());



    }
    @FXML
    void delete_institution(ActionEvent event) {

    }

    @FXML
    void enter_institution(ActionEvent event) {

    }

    @FXML
    void institution_dropdown(ActionEvent event) throws IOException {
        institution_logo_graphic.setImage(null);

        //List to hold url's to school logos
        ArrayList<String> schoolLogo = new ArrayList<>();



        //Gets the URL and stores in String variable
        String selectedURL = institutionNameURL.get(institution_dropdown.getSelectionModel().getSelectedItem());

        //Targets the document and stores it in a Document object
        Document doc = Jsoup.connect(selectedURL).get();

        //Selecting the first instance of the "span.headerlg" selector
        Element institutionInfo = doc.select("span.headerlg").first();

        //Selecting the addreess
        String instAddress = institutionInfo.nextSibling().nextSibling().toString();
        address_txtbx1.setText(instAddress);

        //Set's the text name textbox to targeted value in previous statement
        institute_name_txtbx.setText(institutionInfo.text());

        //Targeting the table where most of my information is contained in the second table of the document
        Element infoTable = doc.getElementsByTag("tbody").get(1);

        //Targeting each of the "tr" elements within the tbody
        Elements tableBodyChildren = infoTable.children();

        //Targets and sets phone
        phone_txtbx.setText(tableBodyChildren.get(1).child(1).text());

        //Targets and sets website
        website_txtbx.setText(tableBodyChildren.get(2).child(1).text());

        //Targets and sets Type
        type_txtbx.setText(tableBodyChildren.get(3).child(1).text());

        //Targets and sets Campus Setting
        campus_setting_txtbx.setText(tableBodyChildren.get(5).child(1).text());

        //Targets and sets Campus housing
        housing_txtbx.setText(tableBodyChildren.get(6).child(1).text());

        //Targets and sets student population
        stu_population_txtbx.setText(tableBodyChildren.get(7).child(1).text());

        //Targets and sets student to faculty ratio
        ratio_txtbx.setText(tableBodyChildren.get(8).child(1).text());

        //Target and set enrollment
        Element enrollmentValue = doc.select(".mainrow tr th").get(32);
        enrollment_txtbx1.setText(enrollmentValue.text());


        switch (institution_dropdown.getSelectionModel().getSelectedItem()) {
            case "Dunwoody College of Technology":
                imageURL = "https://www.toyota.com/usa/tten/pub/images/school_profile/Dunwoody/desktop/dunwoody_logo.png";
                break;
            case "Augsburg College":
                imageURL = "https://www.augsburg.edu/wp-content/themes/augsburg-2016-home/augsburg-logo-maroon.png";
                break;
            case "North Central University":
                imageURL = "https://www.filepicker.io/api/file/kPRljyYYTbWelHxRchIK";
                break;
            case "Univ. Of Minnesota":
                imageURL = "https://twin-cities.umn.edu/sites/twin-cities.umn.edu/themes/umn_homesite/images/wordmark-m-d2d-black-maroon-maroon-576w.png";
                break;
            case "Hamline University":
                imageURL = "https://1tskcg39n5iu1jl9xp2ze2ma-wpengine.netdna-ssl.com/wp-content/uploads/2018/07/Hamline-University-User-Story-Logo.png";
                break;
            case "Macalester College":
                imageURL = "https://jobs.diglib.org/wp-content/uploads/sites/20/wpjobboard-20/company/363/company-logo/xmac-primary-logo-spot-blue.png.pagespeed.ic.m710yjg-kI.png";
                break;

        }

        //Institution Logo

        Image inst_logo = new Image(imageURL);
        institution_logo_graphic = new ImageView(inst_logo);
        grid_parent.getChildren().add(institution_logo_graphic);
        institution_logo_graphic.setFitHeight(75);
        institution_logo_graphic.setFitWidth(175);
        institution_logo_graphic.setPreserveRatio(true);

    }

    @FXML
    void update_institution(ActionEvent event) {

        scripturelb.setText(API.getVerse());
        API.getVerse();

        /**WebEngine webEngine = scripture_webv.getEngine();
        webEngine.loadContent(API.getVerse());**/
    }

    @FXML
    void website_clicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URL(website_txtbx.getText()).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
