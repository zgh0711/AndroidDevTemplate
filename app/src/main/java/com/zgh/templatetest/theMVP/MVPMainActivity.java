package com.zgh.templatetest.theMVP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zgh.templatetest.R;
import com.zgh.templatetest.theMVP.presenter.BindDataActivity;
import com.zgh.templatetest.theMVP.presenter.CommonMVPActivity;
import com.zgh.templatetest.theMVP.presenter.ListMVPActivity;

public class MVPMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvpmain);

        findViewById(R.id.btn_common).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MVPMainActivity.this, CommonMVPActivity.class));
            }
        });
        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MVPMainActivity.this, ListMVPActivity.class));
            }
        });
        findViewById(R.id.btn_dataBind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MVPMainActivity.this, BindDataActivity.class));
            }
        });
    }
}
