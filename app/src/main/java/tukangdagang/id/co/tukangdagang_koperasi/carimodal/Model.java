package tukangdagang.id.co.tukangdagang_koperasi.carimodal;
public class Model {

    String title;
    String desc;
    String icon;
    String id;
    String rating;
    String alamat;

    //constructor
    public Model(String title, String desc, String icon, String id, String rating, String alamat) {
        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.id = id;
        this.rating = rating;
        this.alamat = alamat;
    }

    //getters


    public String getTitle() {
        return this.title;
    }

    public String getDesc() {
        return this.desc;
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
