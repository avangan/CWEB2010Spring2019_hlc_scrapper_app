package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Institution {
    private final SimpleStringProperty Name;
    private final SimpleStringProperty Address;
    private final SimpleStringProperty Website;
    private final SimpleStringProperty Phone;
    private  SimpleStringProperty Type;
    private final SimpleStringProperty Setting;
    private final SimpleStringProperty Housing;
    private final SimpleStringProperty Population;
    private final SimpleStringProperty Ratio;
    private final SimpleStringProperty Enrollment;

    //Constructor
    public Institution(String name, String address, String website, String phone, String setting, String housing, String population, String ratio, String enrollment) {
        Name = new SimpleStringProperty(name);
        Address = new SimpleStringProperty(address);
        Website = new SimpleStringProperty(website);
        Phone = new SimpleStringProperty(phone);

        Setting = new SimpleStringProperty(setting);
        Housing = new SimpleStringProperty(housing);
        Population = new SimpleStringProperty(population);
        Ratio = new SimpleStringProperty(ratio);
        Enrollment = new SimpleStringProperty(enrollment);
    }
    public void setType(String type){
        Type = new SimpleStringProperty(type);
    }

    public StringProperty getName() {
        return Name;
    }

    public StringProperty getAddress() {
        return Address;
    }
    public StringProperty getWebsite() {
        return Website;
    }
    public StringProperty getPhone() {
        return Phone;
    }
    public StringProperty getType() {
        return Type;
    }

    public StringProperty getSetting() {
        return Setting;
    }
    public StringProperty getHousing() {
        return Housing;
    }
    public StringProperty getPopulation() { return Population; }
    public StringProperty getRatio() {
        return Ratio;
    }
    public StringProperty getEnrollment() {
        return Enrollment;
    }





}
