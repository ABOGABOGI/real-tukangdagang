package tukangdagang.id.co.tukangdagang_koperasi.slidercardview;


public class CardItem {

    private String mImgResource;
    private String titleResorce;
    private String idnilai;

    public CardItem(String img,String title, String id) {
        mImgResource = img;
        titleResorce = title;
        idnilai = id;
    }

    public String getImg() {
        return mImgResource;
    }

    public String getTitle() {
        return titleResorce;
    }
    public String getIdnilai() {
        return idnilai;
    }
}
