package everlinkintl.com.myapp.datatemplate;

public class FeedbackSelectData {
   private String selectString;
   private int selectType;
   private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getSelectString() {
        return selectString;
    }

    public void setSelectString(String selectString) {
        this.selectString = selectString;
    }

    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }
}
