package tukangdagang.id.co.tukangdagang_koperasi.daftarpinjaman;
public class ModelDaftarpinjaman {

    String title;
    String icon;
    String totalpinjaman;
    String totalbayar;
    String tenor;
    String tagihan;
    String jatuhtempo;
    String sisabayar;


    //constructor
    public ModelDaftarpinjaman(String title, String icon, String totalpinjaman, String totalbayar, String tenor, String tagihan, String jatuhtempo, String sisabayar) {
        this.title = title;
        this.icon = icon;
        this.totalpinjaman = totalpinjaman;
        this.totalbayar = totalbayar;
        this.tenor = tenor;
        this.tagihan = tagihan;
        this.jatuhtempo = jatuhtempo;
        this.sisabayar = sisabayar;
    }

    //getters


    public String getTitle() {
        return this.title;
    }

    public String getTotalpinjaman() {
        return this.totalpinjaman;
    }
    public String getIcon() {
        return this.icon;
    }
    public String getTotalbayar() {
        return this.totalbayar;
    }
    public String getTenor() {
        return this.tenor;
    }
    public String getTagihan() {
        return this.tagihan;
    }
    public String getJatuhtempo() {
        return this.jatuhtempo;
    }
    public String getSisabayar() {
        return this.sisabayar;
    }
}
