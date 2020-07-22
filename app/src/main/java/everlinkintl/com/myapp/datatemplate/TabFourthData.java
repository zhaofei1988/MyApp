package everlinkintl.com.myapp.datatemplate;

import android.graphics.drawable.Drawable;

public class TabFourthData {
    private Drawable bg;
    private String icon;
    private String title;
    private String rigthMessage;
    private boolean isShowLay = false;
    private Class aClass;

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public boolean isShowLay() {
        return isShowLay;
    }

    public void setShowLay(boolean showLay) {
        isShowLay = showLay;
    }

    public Drawable getBg() {
        return bg;
    }

    public void setBg(Drawable bg) {
        this.bg = bg;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRigthMessage() {
        return rigthMessage;
    }

    public void setRigthMessage(String rigthMessage) {
        this.rigthMessage = rigthMessage;
    }
}
