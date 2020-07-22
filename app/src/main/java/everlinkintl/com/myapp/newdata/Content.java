package everlinkintl.com.myapp.newdata;

import java.util.List;

public class Content {
    private List<BizNoList> biz_no_list;
    private String driver;
    private String driver_tel;
    private String info;
    private String remark;
    private String route_name;
    private String trans_no;
    private String trans_type;
    private String trans_type_name;
    private String valid;
    private String veh_no;
    private List<Routing>  routing;

    public List<Routing> getRouting() {
        return routing;
    }

    public void setRouting(List<Routing> routing) {
        this.routing = routing;
    }

    public List<BizNoList> getBiz_no_list() {
        return biz_no_list;
    }

    public void setBiz_no_list(List<BizNoList> biz_no_list) {
        this.biz_no_list = biz_no_list;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriver_tel() {
        return driver_tel;
    }

    public void setDriver_tel(String driver_tel) {
        this.driver_tel = driver_tel;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public String getTrans_type_name() {
        return trans_type_name;
    }

    public void setTrans_type_name(String trans_type_name) {
        this.trans_type_name = trans_type_name;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getVeh_no() {
        return veh_no;
    }

    public void setVeh_no(String veh_no) {
        this.veh_no = veh_no;
    }
}
