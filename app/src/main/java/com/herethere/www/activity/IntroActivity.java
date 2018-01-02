package com.herethere.www.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.herethere.www.R;
import com.herethere.www.common.Constants;
import com.herethere.www.common.HttpTaskCallback;
import com.herethere.www.util.UpdateTask;

public class IntroActivity extends AppCompatActivity implements Constants, HttpTaskCallback {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setLayout();
        finishActivity();
    }

    @Override
    public void next() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    @Override
    public void update() {
        updateDialog();
    }

    private void setLayout() {
        mImageView = (ImageView) findViewById(R.id.intro_imageView);
        Glide.with(this).load(R.mipmap.intro).into(mImageView);
    }

    private void finishActivity() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(isConnectingToInternet()) {
                    // 인터넷 연결되어있으면 업데이트 체크
                    checkUpdate();
                }else {
                    finishDialog();
                }
            }
        };
        handler.sendEmptyMessageDelayed(0, INTRO_FINISH_TIME);
    }

    private void checkUpdate() {
        UpdateTask asnyc = new UpdateTask();
        asnyc.setInit(this, this);
        asnyc.execute();
    }


    private boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo  = connectivityManager.getActiveNetworkInfo();
            if ( (networkInfo != null) && (networkInfo.isAvailable() == true) )
            {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                {
                    if ( (networkInfo.getState() == NetworkInfo.State.CONNECTED) || (networkInfo.getState() == NetworkInfo.State.CONNECTING) )
                    {
                        return true;
                    }
                }else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if ( (networkInfo.getState() == NetworkInfo.State.CONNECTED) || (networkInfo.getState() == NetworkInfo.State.CONNECTING) )
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void finishDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setMessage("서버에 연결할 수 없습니다. 인터넷 연결 상태를 확인하시고 다시 시도해주세요.");
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        ad.show();
    }

    private void updateDialog() {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setMessage("업데이트가 필요합니다. 업데이트를 누르면 앱스토어로 연결됩니다. ");
        ad.setPositiveButton("업데이트", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.herethere.www"));
                startActivity(intent);
            }
        });
        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        ad.show();
    }
}
