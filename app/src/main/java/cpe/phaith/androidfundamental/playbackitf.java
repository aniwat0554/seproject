package cpe.phaith.androidfundamental;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class playbackitf extends ActionBarActivity {
    private SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
        mydatabase = openOrCreateDatabase("song database",MODE_PRIVATE,null);
    }
    public class MyView extends View {
        private Bitmap mBack;
        private Paint mPaint;
        private RectF mOval;
        private Paint mTextPaint;

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public MyView(Context context) {
            super(context);
            Resources res = getResources();
            mBack = BitmapFactory.decodeResource(res, R.drawable.back);
           // mBack.setHeight(20);
           // mBack.setWidth(20);
            //mBack.
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Bitmap ring = BitmapFactory.decodeResource(res, R.drawable.ring);
            mPaint.setShader(new BitmapShader(ring, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            mOval = new RectF(0, 0, mBack.getWidth()/2, mBack.getHeight()/2);
            mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTextPaint.setTextSize(24);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.translate((getWidth() - mBack.getWidth()) / 2, (getHeight() - mBack.getHeight()) / 2);
            //canvas.drawBitmap(mBack, 0, 0, null);
            float angle = 45;
            Cursor tempset1 = mydatabase.rawQuery("Select duration from SoundInfo6 where ID = 19",null);
            tempset1.moveToFirst();
            int duration = tempset1.getInt(0);

            Cursor tempset = mydatabase.rawQuery("Select TagTime from SoundStruct where ID = 19",null);
            tempset.moveToFirst();
            Toast.makeText(getApplicationContext(), ""+tempset.getCount(),
                    Toast.LENGTH_LONG).show();
            float startangle = -90;
            for(int i = 0;i != tempset.getCount();i++){
                angle = ((float)tempset.getInt(0)/(float)duration)*360;
                canvas.drawArc(mOval, startangle, angle, true, mPaint);
                mPaint.setColor(0x33033000);
                startangle = startangle + angle;
                tempset.moveToNext();
            }
            //canvas.drawArc(mOval, -90, angle, true, mPaint);
            //mPaint.setColor(0x33033000);
            //canvas.drawArc(mOval, -90+angle, angle, true, mPaint);
            canvas.drawText("Text",
                    mBack.getWidth() / 2,
                    (mBack.getHeight() - mTextPaint.ascent()) / 2,
                    mTextPaint);
        }
    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_playbackitf, menu);
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
