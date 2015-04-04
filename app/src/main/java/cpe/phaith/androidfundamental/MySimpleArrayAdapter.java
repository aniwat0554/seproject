package cpe.phaith.androidfundamental;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button ;

import android.database.Cursor;
import android.widget.Toast;


/**
 * Created by MacBookAir on 4/3/15 AD.
 */

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public SQLiteDatabase mydatabase;

    public MySimpleArrayAdapter(Context context,String[] values) {

        super(context, R.layout.row_in_list,values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_in_list, parent, false);
        String[] text = values[position].split(",,") ;
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        textView.setText(text[0]) ;
        TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine) ;
        textView2.setText(text[1]) ;
        TextView textView3 = (TextView) rowView.findViewById(R.id.secondLine2) ;
        textView3.setText(text[2]) ;
        Button deleteBtn = (Button)rowView.findViewById(R.id.delbut);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "DELETE", Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        });
        Button playbut = (Button)rowView.findViewById(R.id.play);
        playbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "PLAY", Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        });
        // change the icon for Windows and iPhone
        String s = values[position];

        return rowView;
    }
}
