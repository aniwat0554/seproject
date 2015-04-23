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
import android.widget.Filter;
import android.widget.Filterable;
import android.support.v7.app.ActionBarActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.* ;

import android.database.Cursor;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;


/**
 * Created by MacBookAir on 4/3/15 AD.
 */

public class MySimpleArrayAdapter extends ArrayAdapter<String> implements Filterable {
    private final Context context;
    private final ArrayList<String> valuesA;
    private ArrayList<String> Filtervalues ;
    private ItemFilter mFilter = new ItemFilter();
    private SQLiteDatabase dataB ;
    private Cursor resultSet ;
    //public SQLiteDatabase mydatabase;


    public MySimpleArrayAdapter(Context context,ArrayList<String> values,SQLiteDatabase data) {

        super(context, R.layout.row_in_list,values);
        this.context = context;
        this.valuesA = values;
        this.dataB = data ;
        this.Filtervalues = values ;
    }
    @Override
    public Filter getFilter() {
        return mFilter;
    }
         /*
    }
        return new Filter() {
            @Override

            public FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<String> results = new ArrayList<String>();

                if (constraint != null) {
                    for(int i = 0  ; i < valuesA.size() ; i++) {
                        String g = valuesA.get(i) ;
                        if (context.toString().toLowerCase()
                                .contains(constraint.toString()))
                            results.add(g) ;
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                valuesA = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    */
         public int getCount() {
             return Filtervalues.size();
         }

    public String getItem(int position) {
        return Filtervalues.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    private class ItemFilter extends Filter {
             @Override
             protected FilterResults performFiltering(CharSequence constraint) {

                 Cursor resultSet2 = dataB.rawQuery("Select ID from SoundInfo6",null);



                 String filterString = constraint.toString().toLowerCase();

                 FilterResults results = new FilterResults();

                 final List<String> list = valuesA;

                 int count = list.size();
                 final ArrayList<String> nlist = new ArrayList<String>(count);

                 String filterableString;

                 for (int i = 0; i < count; i++) {
                     filterableString = list.get(i);
                     String f = "%"+filterString+"%" ;
                     resultSet2.moveToPosition(i);
                     int idpart = resultSet2.getInt(0);
                     if(dataB.rawQuery("Select * from SoundStruct where ID = "+idpart+" AND Name LIKE '"+f+"'",null).getCount() != 0 || filterString == null){
                         nlist.add(filterableString);
                     }
                     /*
                     filterableString = list.get(i);
                     if (filterableString.toLowerCase().contains(filterString)) {
                         nlist.add(filterableString);
                     }*/
                 }


                 results.values = nlist;
                 results.count = nlist.size();
                 if(filterString.length() <= 0) {
                    results.values = valuesA ;
                 }
                 return results;

             }

             @SuppressWarnings("unchecked")
             @Override
             protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                 Filtervalues = (ArrayList<String>) results.values;

                 notifyDataSetChanged();
             }
         }
        @Override
    public View getView(final int position, View convertView,final ViewGroup parent) {
        final String deleter = getItem(position) ;
        //final MySimpleArrayAdapter test = this;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_in_list, parent, false);
        String[] text = Filtervalues.get(position).split(",,") ;
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

