package tukangdagang.id.co.tukangdagang_koperasi.daganganku;

public class Model {
    String title;
    int icon;


    //constructor
    public Model(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    //getters


    public String getTitle() {
        return this.title;
    }
    public int getIcon() {
        return this.icon;
    }

}
