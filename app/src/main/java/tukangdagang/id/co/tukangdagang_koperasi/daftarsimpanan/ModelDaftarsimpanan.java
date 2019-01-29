package tukangdagang.id.co.tukangdagang_koperasi.daftarsimpanan;
public class ModelDaftarsimpanan {

    String title;
    String jumlah;
    String icon;
    String id;
    String waktu;


    //constructor
    public ModelDaftarsimpanan(String title, String jumlah, String icon, String id, String waktu) {
        this.title = title;
        this.jumlah = jumlah;
        this.icon = icon;
        this.id = id;
        this.waktu = waktu;
    }

    //getters


    public String getTitle() {
        return this.title;
    }

    public String getJumlah() {
        return this.jumlah;
    }

    public String getIcon() {
        return this.icon;
    }
    public String getId() {
        return this.id;
    }
    public String getWaktu() {
        return this.waktu;
    }
}
