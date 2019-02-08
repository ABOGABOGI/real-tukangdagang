package tukangdagang.id.co.tukangdagang_koperasi.daftarsimpanan;
public class ModelDaftarsimpanan {

    String title;
    String jumlah;
    String icon;
    String id;
    String waktu;
    String nama;
    String avatar;
    String pokok;
    String wajib;
    String sukarela;


    //constructor
    public ModelDaftarsimpanan(String title, String jumlah, String icon, String id, String waktu, String nama, String avatar, String pokok, String wajib, String sukarela) {
        this.title = title;
        this.jumlah = jumlah;
        this.icon = icon;
        this.id = id;
        this.waktu = waktu;
        this.nama = nama;
        this.avatar = avatar;
        this.pokok = pokok;
        this.wajib = wajib;
        this.sukarela = sukarela;
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
    public String getNama() {
        return this.nama;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public String getPokok() {
        return this.pokok;
    }
    public String getWajib() {
        return this.wajib;
    }
    public String getSukarela() {
        return this.sukarela;
    }
}
