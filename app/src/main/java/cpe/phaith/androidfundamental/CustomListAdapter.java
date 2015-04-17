package cpe.phaith.androidfundamental;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Aniwat on 17/4/2558.
 */
public class CustomListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflator;
    final int[] images = { R.drawable.magnolia,
            R.drawable.orchid, R.drawable.rose,
    };
    public CustomListAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.mContext=context;
        this.inflator= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return images.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        MainListHolder mHolder;

        View v = convertView;
        if (convertView == null)
        {
            mHolder = new MainListHolder();

            v = inflator.inflate(R.layout.inflate, null);
            mHolder.image=  (ImageView) v.findViewById(R.id.imageView1);

            v.setTag(mHolder);
        } else {
            mHolder = (MainListHolder) v.getTag();
        }

        mHolder.image.setImageResource(images[position]);
        mHolder.image.setPadding(20, 20, 20, 20);
        return v;
    }
    class MainListHolder
    {
        private ImageView image;
    }


}