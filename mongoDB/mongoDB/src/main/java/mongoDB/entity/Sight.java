package mongoDB.entity;

public class Sight {

    private String sightName;
    private String zone;
    private String category;
    private String photoURL;
    private String description;
    private String address;

    public Sight(){
        this.sightName = "";
        this.zone = "";
        this.category = "";
        this.photoURL = "";
        this.description = "";
        this.address = "";
    };
    public Sight(String sightName, String zone, String category, String photoURL, String description, String address){
        this.sightName = sightName;
        this.zone = zone;
        this.category = category;
        this.photoURL = photoURL;
        this.description = description;
        this.address = address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public void setSightName(String sightName) {
        this.sightName = sightName;
    }

    public void setDescription(String description) { this.description = description; }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getSightName() {
        return sightName;
    }

    public String getDescription() { return description; }

    public String getZone() {
        return zone;
    }

    public String toString(){
        return String.format("SightName: %s%nZone: %s%nCategory: %s%nPhotoURL: %s%nDescription: %s%nAddress: %s%n", sightName, zone, category, photoURL, description, address);
    }
}
