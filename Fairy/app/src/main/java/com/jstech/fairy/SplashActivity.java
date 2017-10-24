package com.jstech.fairy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class SplashActivity extends AppCompatActivity {

    //  액티비티 전환 시, 애니메이션 추가
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
        setContentView(R.layout.activity_splash);

        ImageView iv_splash = (ImageView)findViewById(R.id.splash_iv);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(iv_splash);
        Glide.with(this).load(R.drawable.fairy_splash).into(gifImage);

        Handler handle = new Handler();
        handle.postDelayed(new splashhandler(), 2700);  //일단 2초 후 Main으로


    }

    private class splashhandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class));
            SplashActivity.this.finish();
        }
    }

    //  액티비티 전환 시, 애니메이션 추가
    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.end_enter, R.anim.end_enter);
    }

    @Override
    public void onBackPressed() {
        //  Splash 에서 뒤로가기 막기 위함.
    }
}
