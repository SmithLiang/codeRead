package com.dfxh.wang.coderead;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dfxh.wang.coderead.fragment.FragmentA;
import com.dfxh.wang.coderead.fragment.FragmentB;
import com.dfxh.wang.coderead.utils.ShareUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FragmentManager mFragmentManager;
    //   List<Fragment> mFragmentList=new ArrayList<>();
    private FragmentA mFragmentA;
    private FragmentB mFragmentB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_a).setOnClickListener(this);
        findViewById(R.id.btn_b).setOnClickListener(this);
        createFragments();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(MainActivity.this)){//加上判断
            EventBus.getDefault().register(MainActivity.this);
        }
    }

    void createFragments(){
        mFragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction= mFragmentManager.beginTransaction();

        mFragmentA = new FragmentA();
//        mFragmentList.add(mFragmentA);
        transaction.add(R.id.fl_container, mFragmentA);

        mFragmentB = new FragmentB();
//        mFragmentList.add(mFragmentB);
        transaction.add(R.id.fl_container, mFragmentB);

        transaction.show(mFragmentA);
        transaction.hide(mFragmentB);
        transaction.commit();
    }
    void selectFragment(int id){
        FragmentTransaction transaction= mFragmentManager.beginTransaction();
        if (id==R.id.btn_a){
            transaction.show(mFragmentA);
            transaction.hide(mFragmentB);
        }else {
            transaction.show(mFragmentB);
            transaction.hide(mFragmentA);
        }
        //忘了提交所以才会重叠
        transaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_a:
                selectFragment(R.id.btn_a);
                break;
            case R.id.btn_b:
                selectFragment(R.id.btn_b);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(MainActivity.this)){//加上判断
            EventBus.getDefault().unregister(MainActivity.this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMessage(@NonNull MessageEvent event) {
        if (event.what==100){
            Log.d("wuliang","activity");
        }
    }
}

