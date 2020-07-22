package everlinkintl.com.myapp.datatemplate;

import java.util.List;

public class CargoDetailData {
    private String address;
    private String time;
    private String buttonText;
    private int buttonStatus;
    private List<String> imgUrl;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public int getButtonStatus() {
        return buttonStatus;
    }

    public void setButtonStatus(int buttonStatus) {
        this.buttonStatus = buttonStatus;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }
}
