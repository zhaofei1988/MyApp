package everlinkintl.com.myapp.datatemplate;

public class LocationData {
    private String aoi;
    private String address;
    private String province;//省信息
    private String city;//城市信息
    private String district;//城区信息
    private String street;//街道信息
    private String treetNum;//街道门牌号信息

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTreetNum() {
        return treetNum;
    }

    public void setTreetNum(String treetNum) {
        this.treetNum = treetNum;
    }


    public String getAoi() {
        return aoi;
    }

    public void setAoi(String aoi) {
        this.aoi = aoi;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
