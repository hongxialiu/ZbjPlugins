package com.zbj.zbjplugins.app.pay;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.wequick.small.Small;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mContentTextView;
    private Button mPaySuccessButton;
    private Button mPayFailedButton;
    private LocalBroadcastManager localBroadcastManager;
    private final String  PAY_RESULT_BROADCAST = "com.zbj.zbjplugins.payresult";
    private final String PAY_RESULT = "pay_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        initView();
        initData();
    }

    private void initData() {
        Uri uri = Small.getUri(this);
        if (uri != null) {
            String orderIdStr = uri.getQueryParameter("orderId");
            String uidStr = uri.getQueryParameter("uid");
            if (orderIdStr != null) {
                mContentTextView.setText("订单号为" + orderIdStr +"***" + "用户id为：" +  uidStr);
            }
        }
    }

    private void initView() {
        mContentTextView = (TextView) findViewById(R.id.content_tv);
        mPayFailedButton = (Button) findViewById(R.id.pay_failed_btn);
        mPaySuccessButton = (Button) findViewById(R.id.pay_success_btn);

        mPaySuccessButton.setOnClickListener(this);
        mPayFailedButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_failed_btn://支付失败
                Intent payFailedIntent = new Intent();
                payFailedIntent.setAction(PAY_RESULT_BROADCAST);
                payFailedIntent.putExtra(PAY_RESULT, 0);
                localBroadcastManager.sendBroadcast(payFailedIntent);
                finish();
                break;
            case R.id.pay_success_btn://支付成功
                Intent paySucceedIntent = new Intent();
                paySucceedIntent.setAction(PAY_RESULT_BROADCAST);
                paySucceedIntent.putExtra(PAY_RESULT, 1);
                localBroadcastManager.sendBroadcast(paySucceedIntent);
                finish();
                break;
            default:
                break;
        }
    }
}
