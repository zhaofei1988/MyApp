package everlinkintl.com.myapp.adapter;
import android.content.Context;

/***
 *    List<Pon> mList = new ArrayList<Pon>();
 *     ListView mLv ;
 *     MyBaseAdapter<Pon> mAdapter;
 *    for(int i = 0 ; i < 50 ; i++) {
 *             Pon book = new Pon();
 *             i++;
 *             book.setAaa("aaaa"+i);
 *             book.setBb(i);
 *             mList.add(book);
 *         }
 *         mAdapter = new MyAdapter(this);
 *         mAdapter.setData(mList);
 *         mLv.setAdapter(mAdapter);
 *         mAdapter.notifyDataSetChanged();
 */
public class MyAdapter extends MyBaseAdapter {
    public MyAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemLayoutId(int getItemViewType) {
//        return R.layout.item_layout;
        return 0;
    }

    @Override
    public void handleItem(int itemViewType, int position, Object item, ViewHolder holder, boolean isRecycle) {
//        TextView leftTv = holder.get(R.id.left_tv, TextView.class);
//        TextView righTv = holder.get(R.id.right_tv, TextView.class);
//        Pon book = (Pon) item;
//        leftTv.setText(book.getAaa());
//        righTv.setText(book.getBb());
    }
}
