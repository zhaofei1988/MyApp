package everlinkintl.com.myapp.newdata;

import java.util.List;

public class BizNoList {
    private String biz_no;
    private String gross_wt;
    private String hawb_no;
    private String mawb_no;
    private String package_info;
    private String d_decl_date;
    private String seq;
    private List<Pois> pois;

    public String getD_decl_date() {
        return d_decl_date;
    }

    public void setD_decl_date(String d_decl_date) {
        this.d_decl_date = d_decl_date;
    }

    public String getBiz_no() {
        return biz_no;
    }

    public void setBiz_no(String biz_no) {
        this.biz_no = biz_no;
    }

    public String getGross_wt() {
        return gross_wt;
    }

    public void setGross_wt(String gross_wt) {
        this.gross_wt = gross_wt;
    }

    public String getHawb_no() {
        return hawb_no;
    }

    public void setHawb_no(String hawb_no) {
        this.hawb_no = hawb_no;
    }

    public String getMawb_no() {
        return mawb_no;
    }

    public void setMawb_no(String mawb_no) {
        this.mawb_no = mawb_no;
    }

    public String getPackage_info() {
        return package_info;
    }

    public void setPackage_info(String package_info) {
        this.package_info = package_info;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public List<Pois> getPois() {
        return pois;
    }

    public void setPois(List<Pois> pois) {
        this.pois = pois;
    }
}
