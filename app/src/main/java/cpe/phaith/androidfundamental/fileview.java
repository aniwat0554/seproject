package cpe.phaith.androidfundamental;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.widget.SearchView;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView.OnQueryTextListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.text.DateFormat ;
import java.util.* ;
import java.io.IOException;
import java.text.SimpleDateFormat ;



public class fileview extends Activity {

    public SQLiteDatabase mydatabase;
    Cursor resultSet;
    SearchView search;
     ListView listv ;

    public String fromMillitoTime(int Duration) {
        String returner =  "" ;
        if(Duration < 60*1000) {
            returner = ""+(Duration / 1000)+" sec";
        }else if (Duration < 60*60*1000) {
            returner = ""+(Duration / 1000 / 60)+" mins" ;
        }else {
            returner = ""+(Duration /1000 / 60 / 60)+" hours" ;
        }
        return returner ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileview);
      //  search = new SearchView(fileview.this);
        listv =(ListView)findViewById(R.id.listView);
        search=(SearchView)findViewById(R.id.searchView11);

        mydatabase = openOrCreateDatabase("song database",MODE_PRIVATE,null);
        //mydatabase.execSQL("CREATE TABLE IF NOT EXISTS SoundInfo3(ID INTEGER PRIMARY KEY,Filename VARCHAR,Name VARCHAR);");
        resultSet = mydatabase.rawQuery("Select Name,Filename,timestamp,duration from SoundInfo6",null);
        resultSet.moveToFirst();
        String[] kuy = new String[resultSet.getCount()];
        for(int i = 0;i != resultSet.getCount();i++){
            kuy[i] = resultSet.getString(0);
            kuy[i]+=",," ;
            kuy[i]+= resultSet.getString(2)+",,";
            int temp = Integer.parseInt(resultSet.getString(3)) ;
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date tt = new Date((long) temp) ;
            kuy[i]+=formatter.format(tt) ;
            resultSet.moveToNext();
        }
        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this,new ArrayList<String>(Arrays.asList(kuy)),mydatabase) ;
        //***setOnQueryTextFocusChangeListener***
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), String.valueOf(hasFocus),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //***setOnQueryTextListener***
        search.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub

                Toast.makeText(getBaseContext(), newText,
                Toast.LENGTH_SHORT).show();
                adapter.getFilter().filter(newText);
                return false;
            }
        });






       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,kuy);
        listv.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fileview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
