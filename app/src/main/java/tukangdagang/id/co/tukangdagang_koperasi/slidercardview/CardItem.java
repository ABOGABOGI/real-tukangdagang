package tukangdagang.id.co.tukangdagang_koperasi.slidercardview;


public class CardItem {

    private String mImgResource;
    private String titleResorce;

    public CardItem(String img,String title) {
        mImgResource = img;
        titleResorce = title;
    }

    public String getImg() {
        return mImgResource;
    }

    public String getTitle() {
        return titleResorce;
    }
}
