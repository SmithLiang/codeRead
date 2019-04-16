package com.dfxh.wang.coderead;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dfxh.wang.coderead.utils.L;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity" ;

    //mac 折叠所有方法是 command alt -
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.d("daodishinali");
        int i=100;
        for (int i1 = 0; i1 < i; i1++) {

        }
        boolean b=false;
        if (b) {

        }

    }
    void show(){
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "show: ");
    }
}
