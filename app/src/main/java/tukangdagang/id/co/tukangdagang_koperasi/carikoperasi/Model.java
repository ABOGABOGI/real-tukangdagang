package tukangdagang.id.co.tukangdagang_koperasi.carikoperasi;
public class Model {

    String title;
    String minimal;
    String maximal;
    String icon;
    String id;
    String rating;
    String alamat;

    //constructor
    public Model(String title, String minimal, String maximal, String icon, String id, String rating, String alamat) {
        this.title = title;
        this.minimal = minimal;
        this.maximal = maximal;
        this.icon = icon;
        this.id = id;
        this.rating = rating;
        this.alamat = alamat;
    }

    //getters


    public String getTitle() {
        return this.title;
    }

    public String getMinimal() {
        return this.minimal;
    }
    public String getMaximal() {
        return this.maximal;
    }
    public String getIcon() {
        return this.icon;
    }
    public String getId() {
        return this.id;
    }
    public String getRating() {
        return this.rating;
    }
    public String getAlamat() {
        return this.alamat;
    }
}
