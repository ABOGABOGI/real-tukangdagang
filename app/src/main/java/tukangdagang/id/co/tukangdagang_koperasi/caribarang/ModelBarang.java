package tukangdagang.id.co.tukangdagang_koperasi.caribarang;

public class ModelBarang {

    private String Title;
    private String Category ;
    private String Harga ;
    private String Alamat ;
    private String Berat ;
    private String Description ;
    private String Thumbnail ;


    public ModelBarang(String title, String category, String harga, String alamat, String berat, String description, String thumbnail) {
        Title = title;
        Category = category;
        Harga = harga;
        Alamat = alamat;
        Berat = berat;
        Description = description;
        Thumbnail = thumbnail;
    }


    public String getTitle() {
        return Title;
    }

    public String getCategory() {
        return Category;
    }

    public String getHarga() {
        return Harga;
    }

    public String getAlamat() {
        return Alamat;
    }

    public String getBerat() {
        return Berat;
    }

    public String getDescription() {
        return Description;
    }

    public String getThumbnail() {
        return Thumbnail;
    }


    public void setTitle(String title) {
        Title = title;
    }

    public void setCategory(String category) {
        Category = category;
    }


    public void setHarga(String harga) {
        Harga = harga;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public void setBerat(String berat) {
        Berat = berat;
    }

    public void setDescription(String description) {
        Description = description;
    }
    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }
}

