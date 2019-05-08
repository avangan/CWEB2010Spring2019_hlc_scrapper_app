package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    private static final String USERNAME = "cifulton";
    private static final String PASSWORD = "4978621e";
    //Helpful clarification on error I was receiving - https://stackoverflow.com/questions/34189756/warning-about-ssl-connection-when-connecting-to-mysql-database
    private static final String CONN_STRING = "jdbc:mysql://www.db4free.net:3306/hclinstitution?verifyServerCertificate=false&useSSL=false";

    @FXML
    private GridPane grid_parent;
    @FXML
    private PieChart pie_chart;

    @FXML
    private BarChart<?, ?> bar_chart;


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

    @FXML
    private TableView<Institution> school_table;

        @FXML
        private TableColumn<Institution, String> institution_name_col;

        @FXML
        private TableColumn<Institution, String> institution_address_col;

        @FXML
        private TableColumn<Institution, String> institution_website_col;

        @FXML
        private TableColumn<Institution, String> institution_phone_col;

        @FXML
        private TableColumn<Institution, String> institution_population_col;

        @FXML
        private TableColumn<Institution, String> institution_setting_col;

        @FXML
        private TableColumn<Institution, String> institution_enrollment_col;




    HashMap<String, String> institutionNameURL;
    public void initialize() throws IOException, SQLException {
        updateInstitution();
        //Create a hashmap that holds the institution name and corresponding url
        institutionNameURL = new HashMap<String, String>();
        institutionNameURL.put("Dunwoody College of Technology", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&pg=1&id=175227" );
        institutionNameURL.put("Augsburg College", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&pg=1&id=173045");
        institutionNameURL.put("North Central University", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&id=174437");
        institutionNameURL.put("Univ. Of Minnesota", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&id=174066");
        institutionNameURL.put("Hamline University", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&pg=2&id=173665");
        institutionNameURL.put("Macalester College", "https://nces.ed.gov/collegenavigator/?s=all&zc=55403&zd=10&of=3&pg=2&id=173902");


        institution_dropdown.getItems().addAll(institutionNameURL.keySet());

        //Implementing Verse API
        scripturelb.setText(API.getVerse());

        //Declare List of existing institutions in database
        ObservableList<Institution> institutionObservableList = FXCollections.observableArrayList();

        // Initialize the institution table columns.
       /* institution_name_col.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        institution_address_col.setCellValueFactory(cellData -> cellData.getValue().hoursAttendedProperty());
        institution_website_col.setCellValueFactory(cellData -> cellData.getValue().hoursMissedProperty());
        institution_phone_col.setCellValueFactory(cellData -> cellData.getValue().percentAttendedProperty()); */

    }
    @FXML
    void delete_institution(ActionEvent event) {

    }

    @FXML
    void enter_institution(ActionEvent event) throws SQLException {

        Connection conn = null;

        conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        System.out.println("Connected!");


        //Prepared SQL Statement using paramertized queries
        String sql = "INSERT INTO Institution (instName, instAddress, instWebsite, instPhone, instCampSetting, instHousing, instPopulation, instStuFacRatio, instEnrollment)" +
                "VALUES (?, ?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, institute_name_txtbx.getText());
        preparedStatement.setString(2, address_txtbx1.getText());
        preparedStatement.setString(3, website_txtbx.getText());
        preparedStatement.setString(4, phone_txtbx.getText());
        preparedStatement.setString(5, campus_setting_txtbx.getText());
        preparedStatement.setString(6, housing_txtbx.getText());
        preparedStatement.setString(7, stu_population_txtbx.getText());
        preparedStatement.setString(8, ratio_txtbx.getText());
        preparedStatement.setString(9, enrollment_txtbx1.getText());

        preparedStatement.executeUpdate();
        System.out.println("Record Inserted");




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


        /**WebEngine webEngine = scripture_webv.getEngine();
        webEngine.loadContent(API.getVerse());**/
    }

    @FXML
    void website_clicked(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://" +website_txtbx.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    void updateInstitution() throws SQLException {


        ObservableList<Institution> instList = FXCollections.observableArrayList();
        String name ="", address="", website="", instPhone="", instCampSetting="", instHousing="", instPopulation="", instStuFacRatio="", instEnrollment="";

        Connection conn1 = null; Statement institution_stmt = null; ResultSet rs1 = null;
        // Initialize the database institution table columns.
        institution_name_col.setCellValueFactory(cellData -> cellData.getValue().getName());
        institution_address_col.setCellValueFactory(cellData -> cellData.getValue().getAddress());
        institution_website_col.setCellValueFactory(cellData -> cellData.getValue().getWebsite());
        institution_phone_col.setCellValueFactory(cellData -> cellData.getValue().getPhone());
        institution_population_col.setCellValueFactory(cellData -> cellData.getValue().getPopulation());
        institution_setting_col.setCellValueFactory(cellData -> cellData.getValue().getSetting());
        institution_enrollment_col.setCellValueFactory(cellData -> cellData.getValue().getEnrollment());

        try {
            conn1 = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            System.out.println("Connected!");

            //generate query command to retrieve records
            String sql_getInstitution_query = "SELECT instName, instAddress, instWebsite, instPhone, instCampSetting, instHousing, instPopulation, instStuFacRatio, instEnrollment FROM Institution";
            rs1 = null;
            // create the java statement
            institution_stmt = conn1.createStatement();
            rs1 = institution_stmt.executeQuery(sql_getInstitution_query);




            //Iterate through database and grab all records
            while(rs1.next()) {

                name = rs1.getString(1);
                address = rs1.getString(2);
                website = rs1.getString(3);
                instPhone = rs1.getString(4);
                instCampSetting = rs1.getString(5);
                instHousing = rs1.getString(6);
                instPopulation = rs1.getString(7);
                instStuFacRatio = rs1.getString(8);
                instEnrollment = rs1.getString(9);

                instList.add(new Institution(name, address, website, instPhone, instCampSetting, instHousing, instPopulation, instStuFacRatio, instEnrollment));
                System.out.println(name+ address+ website+ instPhone+ instCampSetting + instHousing + instPopulation + instStuFacRatio+ instEnrollment);
            }//End of while
            school_table.setItems(instList);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.err.println(e);
        }finally{
            if(rs1 != null){
                rs1.close();
            }
            if(institution_stmt != null){
                institution_stmt.close();
            }
            if(conn1 != null){
                conn1.close();
            }
        }



    }
}
