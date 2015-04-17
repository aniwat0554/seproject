package cpe.phaith.androidfundamental;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class test extends ActionBarActivity {
    private AlertDialog dialog;
    private Context mContext;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext=test.this;
        Button btn=(Button) findViewById(R.id.button1);
        btn.setText("Listview in Dialog");
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder=new AlertDialog.Builder(test.this);
                builder.setTitle("Flowers");
                ListView list=new ListView(test.this);
                list.setAdapter(new CustomListAdapter(test.this));
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int position, long arg3) {
                        // TODO Auto-generated method stub
                        if(dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        Toast.makeText(mContext, "Clicked at Position" + position, Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setView(list);
                dialog=builder.create();
                dialog.show();
            }
        });

        Button btn2=(Button) findViewById(R.id.button2);

        btn2.setText("Simple Dialog");
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder=new AlertDialog.Builder(test.this);
                builder.setTitle("Colors");

                final CharSequence str[]={"Red","Yellow","Green","Orange","Blue"};

                builder.setItems(str, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        // TODO Auto-generated method stub
                        Toast.makeText(mContext, "You are selected: "+str[position], Toast.LENGTH_SHORT).show();
                    }
                });

                dialog=builder.create();
                dialog.show();
            }
        });


    }
}