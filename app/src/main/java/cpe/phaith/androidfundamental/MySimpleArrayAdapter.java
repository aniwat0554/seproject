package cpe.phaith.androidfundamental;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.* ;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Intent;
import android.app.Activity ;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button ;
import android.support.v7.app.ActionBarActivity;
import java.util.* ;

import android.database.Cursor;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;


/**
 * Created by MacBookAir on 4/3/15 AD.
 */

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;

    private SQLiteDatabase dataB ;
    private Cursor resultSet ;
    //public SQLiteDatabase mydatabase;


    public MySimpleArrayAdapter(Context context,ArrayList<String> values,SQLiteDatabase data) {

        super(context, R.layout.row_in_list,values);
        this.context = context;
        this.values = values;
        this.dataB = data ;
    }

    @Override
    public View getView(final int position, View convertView,final ViewGroup parent) {
        final String deleter = getItem(position) ;
        //final MySimpleArrayAdapter test = this;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_in_list, parent, false);
        String[] text = values.get(position).split(",,") ;
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
                 resultSet = dataB.rawQuery("Select ID,Filename from SoundInfo6",null);
                resultSet.moveToPosition(position);
                String songid = resultSet.getString(0);
                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+resultSet.getString(1);
                File file = new File(path);
                boolean deleted = file.delete();
                if(deleted) Toast.makeText(getContext(), "DELETE OK", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(getContext(), "DELETE FAILED", Toast.LENGTH_LONG).show();
                }
                dataB.delete("SoundInfo6","ID ="+songid,null) ;
                remove(getItem(position)) ;

               //notifyDataSetChanged();
              //  v.destroyDrawingCache();
                //this.setVisibility(ListView.INVISIBLE);
              //  v.setVisibility(ListView.VISIBLE);
            }
        });
        Button playbut = (Button)rowView.findViewById(R.id.play);
        playbut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "PLAY", Toast.LENGTH_LONG).show();
                resultSet = dataB.rawQuery("Select ID,Filename from SoundInfo6",null);
                resultSet.moveToPosition(position);
                String songid = resultSet.getString(0);
                MediaPlayer m = new MediaPlayer();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + resultSet.getString(1);
                Intent i = new Intent(context.getApplicationContext(), edit.class);
                i.putExtra("send",path);
                context.startActivity(i);
                try {
                    m.setDataSource(path);
                    m.prepare();
                 //   m.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                notifyDataSetChanged();
            }
        });
        // change the icon for Windows and iPhone

        return rowView;
    }
}
