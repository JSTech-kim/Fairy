package com.jstech.fairy.Navigation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jstech.fairy.R;

public class Navi_LicenseInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi__license_info);

        TextView tvPicasso = (TextView)findViewById(R.id.license_picasso);
        tvPicasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://github.com/square/picasso");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        TextView tvFloating1 = (TextView)findViewById(R.id.license_floating1);
        tvFloating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://github.com/makovkastar/FloatingActionButton");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        TextView tvFloating2 = (TextView)findViewById(R.id.license_floating2);
        tvFloating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://github.com/futuresimple/android-floating-action-button");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        TextView tvGlide = (TextView)findViewById(R.id.license_glide);
        tvGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://github.com/bumptech/glide");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        TextView tvScissors = (TextView)findViewById(R.id.license_scissors);
        tvScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://github.com/lyft/scissors");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        TextView tvPasscode = (TextView)findViewById(R.id.license_passcode);
        tvPasscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse("https://github.com/wordpress-mobile/PasscodeLock-Android");
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }
}
