package everlinkintl.com.myapp.datatemplate;

public class SendLoction {
    private String gps_string;
    private String name;
    private String report_time;
    private String remark="";

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGps_string() {
        return gps_string;
    }

    public void setGps_string(String gps_string) {
        this.gps_string = gps_string;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }
}
