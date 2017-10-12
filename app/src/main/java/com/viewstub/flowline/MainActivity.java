package com.viewstub.flowline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FlowLineView mFlowLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlowLineView = (FlowLineView) findViewById(R.id.mFlowLineView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFlowLineView.start();
    }
}
