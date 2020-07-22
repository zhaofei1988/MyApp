package everlinkintl.com.myapp.common;

import java.util.ArrayList;
import java.util.List;

import everlinkintl.com.myapp.datatemplate.TransTypeData;

public class TransTypeDataPackage {
    public List<TransTypeData> packageData() {
        List<TransTypeData> list = new ArrayList();
        TransTypeData transTypeData = new TransTypeData();
        transTypeData.setTransType(721);
        transTypeData.setTransTypeName("车辆业务-出口运输-空运出口");
        list.add(transTypeData);
        transTypeData = new TransTypeData();
        transTypeData.setTransType(711);
        transTypeData.setTransTypeName("车辆业务-进口运输-空运进口");
        list.add(transTypeData);
        transTypeData = new TransTypeData();
        transTypeData.setTransType(715);
        transTypeData.setTransTypeName("车辆业务-进口运输-短驳运输");
        list.add(transTypeData);
        transTypeData = new TransTypeData();
        transTypeData.setTransType(716);
        transTypeData.setTransTypeName("车辆业务-进口运输-提货运输");
        list.add(transTypeData);
        transTypeData = new TransTypeData();
        transTypeData.setTransType(725);
        transTypeData.setTransTypeName("车辆业务-出口运输-短驳运输");
        list.add(transTypeData);
        transTypeData = new TransTypeData();
        transTypeData.setTransType(732);
        transTypeData.setTransTypeName("车辆业务-物流运输-短驳运输");
        list.add(transTypeData);
        transTypeData = new TransTypeData();
        transTypeData.setTransType(724);
        transTypeData.setTransTypeName("车辆业务-出口运输-车辆借出");
        list.add(transTypeData);
        transTypeData = new TransTypeData();
        transTypeData.setTransType(731);
        transTypeData.setTransTypeName("车辆业务-物流运输-国内运输");
        list.add(transTypeData);
        transTypeData = new TransTypeData();
        transTypeData.setTransType(717);
        transTypeData.setTransTypeName("车辆业务-进口运输-托盘运输");
        list.add(transTypeData);
        transTypeData = new TransTypeData();
        transTypeData.setTransType(761);
        transTypeData.setTransTypeName("车辆业务-结转车辆-结转车辆");
        list.add(transTypeData);
        transTypeData = new TransTypeData();
        transTypeData.setTransType(714);
        transTypeData.setTransTypeName("车辆业务-进口运输-车辆借出");
        list.add(transTypeData);
        return list;
    }
}
