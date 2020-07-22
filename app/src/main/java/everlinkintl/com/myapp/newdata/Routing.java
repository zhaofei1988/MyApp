package everlinkintl.com.myapp.newdata;

public class Routing {
    private String address;//"江桥收费站",
    private String name;//江桥收费站",
    private String id;//39966,
    private String seq;//"10",
    private String desc;//"行驶路径",
    private String pointType;//TRANS_POINT",
    private String remark;//
    private Location location;
    private boolean isDot=false;

    public boolean isDot() {
        return isDot;
    }

    public void setDot(boolean dot) {
        isDot = dot;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
