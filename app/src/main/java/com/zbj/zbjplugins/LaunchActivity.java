package com.zbj.zbjplugins;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.wequick.small.Small;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mGoPayButton;
    private Button mGoImButton;
    private Button mGoUserButton;
    private TextView mResultTextView;
    private BroadcastReceiver receiver;
    private LocalBroadcastManager broadcastManager;
    private final String  PAY_RESULT_BROADCAST = "com.zbj.zbjplugins.payresult";
    private final String PAY_RESULT = "pay_result";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initView();

        //注册和接受广播
        handleBroadcast();
    }

    /**
     * 注册和接受广播
     */
    private void handleBroadcast() {
        /**LocalBroadcastManager 介绍
         *发送的广播只会在自己App内传播，不会泄露给其他App，确保隐私数据不会泄露
         *其他App也无法向你的App发送该广播，不用担心其他App会来搞破坏
         *比系统全局广播更加高效
         */
        broadcastManager  = LocalBroadcastManager.getInstance(this);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int payResult = intent.getIntExtra(PAY_RESULT, -1);
                if (payResult == 1) {
                    mResultTextView.setText("支付成功！");
                } else if (payResult == 0) {
                    mResultTextView.setText("支付失败！");
                }
            }
        };
        broadcastManager.registerReceiver(receiver, new IntentFilter(PAY_RESULT_BROADCAST));
    }

    private void initView() {
        mGoImButton = (Button) findViewById(R.id.goIm_btn);
        mGoPayButton = (Button) findViewById(R.id.goPay_btn);
        mGoUserButton = (Button) findViewById(R.id.goUser_btn);
        mResultTextView = (TextView) findViewById(R.id.result_tv);

        mGoPayButton.setOnClickListener(this);
        mGoImButton.setOnClickListener(this);
        mGoUserButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        broadcastManager.unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goIm_btn://去聊天
                Small.setUp(this, new net.wequick.small.Small.OnCompleteListener() {

                    @Override
                    public void onComplete() {
                        Small.openUri("chart", LaunchActivity.this);//启动默认的Activity，参考wiki中的UI route启动其他Activity
                    }
                });
                break;
            case R.id.goPay_btn://去支付
                Small.setUp(this, new net.wequick.small.Small.OnCompleteListener() {

                    @Override
                    public void onComplete() {
                        Small.openUri("pay?orderId=12345&uid=111", LaunchActivity.this);//启动默认的Activity，参考wiki中的UI route启动其他Activity
                    }
                });
                break;
            case R.id.goUser_btn://去用户中心
                Small.setUp(this, new net.wequick.small.Small.OnCompleteListener() {

                    @Override
                    public void onComplete() {
                        Small.openUri("user", LaunchActivity.this);//启动默认的Activity，参考wiki中的UI route启动其他Activity
                    }
                });
                break;
            default:
                break;
        }
    }
}
