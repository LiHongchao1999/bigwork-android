package com.example.homeworkcorrect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity {
    String APPKEY = "319a4d9f828f8";
    String APPSECRET = "54f8a204b312c31507ed4533104f51ed";
    //获取用到的控件
    EditText etphone;
    EditText etCode;
    TextView tvlogin;
    Button btCode;

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText editText=findViewById(R.id.et_phone);

        //获取控件
        etphone = findViewById(R.id.et_phone);
        etCode = findViewById(R.id.et_Code);
        tvlogin = findViewById(R.id.tv_login);
        btCode = findViewById(R.id.bt_getcode);
        btCode.setText("获取验证码");
//        调用mob开发者服务
        MobSDK.submitPolicyGrantResult(true, null);

//        初始化短信SDK
        initSDK();

        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginWithPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化短信SDK
     */
    private void initSDK() {
        MobSDK.init(LoginActivity.this,APPKEY,APPSECRET);
        EventHandler eventHandler = new EventHandler() {
            /**
             * 在操作之后被触发
             *
             * @param event
             *            参数1
             * @param result
             *            参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.
             *            RESULT_ERROR表示操作失败
             * @param data
             *            事件操作的结果
             */
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    public void onClick(View v) {
        String phoneNums = etphone.getText().toString();
        switch (v.getId()) {
            case R.id.bt_getcode:
                i=30;
                // 1. 通过规则判断手机号
                if (!judgePhoneNums(phoneNums)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", phoneNums);
                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                btCode.setClickable(false);
                btCode.setText("重新发送(" + i-- + ")");
                Log.e("phonenumber",phoneNums);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int  j= 30; j > 0; j--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                // 4. 打开广播来接受读取短信
                break;
            case R.id.bt_Login:
                if(judgePhoneNums(phoneNums)&&etCode.getText().toString().length()==4){
                    SMSSDK.submitVerificationCode("86",phoneNums, etCode
                            .getText().toString());
                }if(judgePhoneNums(phoneNums)&&etCode.getText().toString().length()!=4){
                Toast.makeText(getApplicationContext(), "验证码位数错误",
                        Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     *
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                btCode.setText("重新发送(" + i-- + ")");
            } else if (msg.what == -8) {
                btCode.setText("获取验证码");
                btCode.setClickable(true);
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Log.e("result",result+"");
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示新好友
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "验证码已经发送",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        ((Throwable) data).printStackTrace();
                    }
                }else if(result==SMSSDK.RESULT_ERROR){
                    Toast.makeText(getApplicationContext(), "验证码错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！",Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断一个字符串的位数
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }


    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }

}
