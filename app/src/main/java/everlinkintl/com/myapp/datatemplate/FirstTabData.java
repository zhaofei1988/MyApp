package everlinkintl.com.myapp.datatemplate;

import java.util.List;

public class FirstTabData {
    private int errcode;
    private String errmsg;
    private List<FirstTab> data;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public List<FirstTab> getData() {
        return data;
    }

    public void setData(List<FirstTab> data) {
        this.data = data;
    }
}
