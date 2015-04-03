package cpe.phaith.androidfundamental;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import java.text.SimpleDateFormat ;
import java.util.Date ;
import java.io.File;
import java.sql.* ;
import java.lang.* ;
import java.util.* ;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private Context context;
    private Button btnSave;
    private EditText editText;
    public MediaRecorder recorder;
    private String outputFile = null;
    private String oF = null;
    private String fileName = null;
    private Button play;
    private String tagtext = "test";
    private Button fileview;
    private Button tan;
    private String id;
    private String idfortag;
    private Button test;
    private String filenamesave;
    private String tagtime;
    private SQLiteDatabase mydatabase;
    public static  String starttime = "" ;
   public static Calendar c = Calendar.getInstance();
    public static Calendar c_fin ;
    public static Calendar c_tag ;
   public static Timestamp a = new Timestamp(c.getTimeInMillis()) ;
    public static Time c_time;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        oF = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sound.aac";
        context = this;

        mydatabase = openOrCreateDatabase("song database",MODE_PRIVATE,null);
        btnSave = (Button)findViewById(R.id.btnSave);
        play = (Button)findViewById(R.id.play);
        fileview = (Button)findViewById(R.id.file);
         editText = (EditText)findViewById(R.id.username);
        tan = (Button)findViewById(R.id.rectag);
        editText.setText(getText("username"));
        test = (Button)findViewById(R.id.test);
        btnSave.setText("Record");
        c = Calendar.getInstance() ;
        a = new Timestamp(c.getTimeInMillis()) ;
        tan.setEnabled(false);
        String temp = a.toString() ;
        editText.setText(""+temp.substring(0,temp.length()-4)) ;

        //c.get(Calendar.DATE)
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS SoundInfo6(ID INTEGER PRIMARY KEY,Filename VARCHAR,Name VARCHAR,timestamp VARCHAR,duration INTEGER);");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS SoundStruct(ID INTEGER,PART INTEGER,Name VARCHAR,TagTime INTEGER);");
        Cursor resultSet = mydatabase.rawQuery("Select max(ID) from SoundInfo6",null);
        resultSet.moveToFirst();
        if(resultSet.getString(0) == null){
           mydatabase.execSQL("INSERT INTO SoundInfo6 (ID,Filename, Name,timestamp,duration) VALUES (0,'test','test','Soo',0);;");
        }

        //mydatabase.execSQL("INSERT INTO SoundInfo2 (ID,Filename, Name) VALUES (1,'test','test');;");
        //mydatabase.execSQL("CREATE TABLE IF NOT EXISTS TagTable(Filename VARCHAR,Type VARCHAR,Start VARCHAR,End VARCHAR);");
        //Cursor resultSet = mydatabase.rawQuery("Select ID from SoundInfo",null);
        //resultSet.getString(resultSet.getColumnIndex("ID"));
        //resultSet.toString();

        //String id = resultSet.getString(1);

        //Toast.makeText(context, resultSet.toString(), Toast.LENGTH_SHORT).show();

        tan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                c_tag = Calendar.getInstance() ;

                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());
                                        tagtext = userInput.getText().toString();
                                        /*Toast.makeText(getApplicationContext(), tagtext,
                                                Toast.LENGTH_LONG).show();*/

                                        Cursor tempset = mydatabase.rawQuery("Select max(PART) from SoundStruct where ID = "+idfortag,null);
                                        tempset.moveToFirst();
                                        String part;
                                        if(tempset.getString(0) == null){
                                            part = "1";
                                        }else {
                                            part =""+ (tempset.getInt(0)+1);
                                        }
                                        mydatabase.execSQL("INSERT INTO SoundStruct (ID,PART,Name,TagTime) VALUES ("+idfortag+","+part+",'"+tagtext+"',"+(int)(c_tag.getTime().getTime()-c.getTime().getTime())+");;");

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                //saveText("username", editText.getText().toString());
                tan.setEnabled(true);
                fileName = editText.getText().toString();
                if (btnSave.getText() == "Record") {
                //    c = Calendar.getInstance() ;
                  //  editText.setText(c.toString()) ;
                    recorder= new MediaRecorder();
                    recorder.reset();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
                    recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

                    Cursor resultSet = mydatabase.rawQuery("Select max(ID) from SoundInfo6",null);
                    //resultSet.getString(resultSet.getColumnIndex("ID"));

                    resultSet.moveToFirst();
                    //resultSet.moveToPosition(3);

                    id = ""+(resultSet.getInt(0)+1);
                    /*if(id == null){
                        id = "1";
                    }*/
                    idfortag = id;
                    //id = Integer.parseInt(id);
                    Toast.makeText(getApplicationContext(),""+ id,
                            Toast.LENGTH_LONG).show();
                    //id = "test";
                    createFolder();
                    outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lecord/sound "+id+".aac";
                    filenamesave = "/Lecord/sound "+id+".aac";
                    recorder.setOutputFile(outputFile);
                    c = Calendar.getInstance() ;
                    String string = "3 Jan 2015" ;
                    SimpleDateFormat format = new SimpleDateFormat("EEE d MMMM, yyyy", Locale.ENGLISH);
                    Date date ;
                    try {
                        date = format.parse(c.toString());
                       // date+=Calendar. ;
                    }catch(Exception e) {
                        date = new Date() ;
                    }
                    System.out.println(format.format(date)); // Sat Jan 02 00:00:00 GMT 2010
                    a = new Timestamp(c.getTimeInMillis()) ;
                    String temp = a.toString() ;

                    starttime = format.format(date) ;
                    c.getTime().getTime() ;
                    start(v);
                    btnSave.setText("Stop");
                } else {
                    stop(v);
                    c_fin = Calendar.getInstance() ;
                    editText.setText(starttime) ;
                   // editText.setText(""+(c_fin.getTime().getTime()-c.getTime().getTime())) ;
                    int duration = (int)(c_fin.getTime().getTime()-c.getTime().getTime()) ;
                    mydatabase.execSQL("INSERT INTO SoundInfo6 (ID,Filename, Name,timestamp,duration) VALUES ((SELECT max(ID) FROM SoundInfo6)+1,'"+filenamesave+"','"+fileName+"','"+starttime+"',"+duration+");;");
                    btnSave.setText("Record");
                    tan.setEnabled(false);
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    play(v);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        fileview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), fileview.class);
                startActivity(in);
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor testSet = mydatabase.rawQuery("Select max(ID) from SoundInfo6",null);
                testSet.moveToFirst();
                String id = testSet.getString(0);
                //Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
                testSet = mydatabase.rawQuery("Select Name,PART,TagTime from SoundStruct where ID = "+id,null);
                //testSet.moveToFirst();
                //testSet.moveToPosition(5);
                //testSet.moveToLast();
                for(int i = 1;i != testSet.getCount();i++) {
                    Toast.makeText(getApplicationContext(), testSet.getString(0) + " " + testSet.getString(1) + " " + testSet.getString(2), Toast.LENGTH_LONG).show();
                    //testSet.moveToNext();
                }
                /*Cursor testSet = mydatabase.rawQuery("Select Name from SoundStruct",null);
                testSet.moveToFirst();
                Toast.makeText(getApplicationContext(), testSet.getString(0), Toast.LENGTH_LONG).show();*/
            }
        });

    }
    public void start(View view){
        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

    }
    public void stop(View view){
        recorder.stop();
        recorder.release();
        recorder  = null;
        Toast.makeText(getApplicationContext(), "Audio recorded successfully",
                Toast.LENGTH_LONG).show();
    }
    public void play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException{

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

    }
    private void saveText(String key, String text){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, text);
        editor.commit();
        Toast.makeText(context, "username is saved.", Toast.LENGTH_SHORT).show();
    }

    private String getText(String key){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key,"");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public boolean createFolder(){
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Lecord");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            return true;
        } else {
            return false;
        }
    }
}
