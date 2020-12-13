package com.example.homeworkcorrect;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import com.mob.MobSDK;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    String APPKEY = "31a00794f87b0";
    String APPSECRET = "bee3fce1f9f4866b8c7fe62b3a58e701";
    //获取用到的控件
    EditText etphone;
    EditText etCode;
    TextView tvlogin;
    Button btCode;
    private int i;
    private Button login;
    private ImageView qqLogin;//QQ登录
    private Tencent tencent;
    private UserInfo userInfo;
    private BaseUiListener baseUiListener;
    private String userIp;//用户ip
    private Handler handler1 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1://获取qq登录的用户名和性别
                    String str = msg.obj.toString();
                    String nickName = str.split(":")[0];
                    String gender = str.split(":")[1];
                    Intent intent = new Intent();
                    intent.putExtra("nickname",nickName);
                    setResult(150,intent);
                    finish();
                    break;
                case 2://手机号验证码登录
                    String str1 = msg.obj.toString();
                    User user = new Gson().fromJson(str1,User.class);
                    if(user.getId()==0){//表示登录失败
                        Toast.makeText(LoginActivity.this,"账号或验证码错误",Toast.LENGTH_LONG).show();
                    }else{
                        UserCache.user = user;
                        //获取数据库连接
                        String token = UserCache.user.getChat_token();
                        RongIMClient.connect(token, new RongIMClient.ConnectCallbackEx() {
                            /**
                             * 数据库回调
                             * @param code 数据库打开状态. DATABASE_OPEN_SUCCESS 数据库打开成功; DATABASE_OPEN_ERROR 数据库打开失败
                             */
                            @Override
                            public void OnDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {
                                Log.e("OnDatabaseOpened","数据库打开");
                            }
                            /**
                             * token 无效
                             */
                            @Override
                            public void onTokenIncorrect() {
                                Log.e("onTokenIncorrect","无效");
                            }
                            /**
                             * 成功回调
                             * @param userId 当前用户 ID
                             */
                            @Override
                            public void onSuccess(String userId) {
                                Log.e("onSuccess",userId+"xcy");
                                //设置当前用户信息
                                io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(userId,UserCache.user.getNickname(), Uri.parse(IP.CONSTANT+"userImage/"+UserCache.user.getImage()));
                                RongIM.getInstance().setCurrentUserInfo(userInfo);
                            }
                            /**
                             * 错误回调
                             * @param errorCode 错误码
                             */
                            @Override
                            public void onError(RongIMClient.ErrorCode errorCode) {
                                Log.e("onError",errorCode+"");
                            }
                        });
                        //跳转到个人页面
                        Intent intent1 = new Intent();
                        setResult(160,intent1);
                        finish();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText editText=findViewById(R.id.et_phone);

        //1.创建OkHttpClient对象
        okHttpClient = new OkHttpClient();

        /*//获取用户的ip
        userIp = getIPAddress();
        Log.e("ip",userIp);*/
        //获取控件
        getViews();
        btCode.setText("获取验证码");
//        调用mob开发者服务
        MobSDK.submitPolicyGrantResult(true, null);

//        初始化短信SDK
        initSDK();
        tvlogin.setOnClickListener(new View.OnClickListener() {//跳转到账号密码登录页面
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginWithPasswordActivity.class);
                startActivity(intent);
            }
        });
        //qq登录
        qqLogin = findViewById(R.id.qq_login);
        tencent = Tencent.createInstance("101920560",this.getApplicationContext());
        qqLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tencent.isSessionValid()){
                    baseUiListener = new BaseUiListener();
                    //第二个参数增量权限scope字符串，这个是获取用户qq基本信息，all代表所有权限
                   tencent.login(LoginActivity.this, "all",baseUiListener);
                }
            }
        });
    }
    /*
    * 获取控件
    * */
    private void getViews() {
        etphone = findViewById(R.id.et_phone); //手机号
        etCode = findViewById(R.id.et_Code);   //验证码
        tvlogin = findViewById(R.id.tv_login);  //账号密码登录
        btCode = findViewById(R.id.bt_getcode); //获取验证码
        login = findViewById(R.id.bt_Login);//登录
    }

    private class BaseUiListener implements IUiListener{
        @Override
        public void onComplete(Object o) {
            //授权成功
            //获取登录账户的个人信息
            Log.e("个人信息",o.toString());
            JSONObject object = (JSONObject) o;
            try {
                String openId = object.getString("openid");
                String accessToken = object.getString("access_token");
                String expires = object.getString("expires_in");
                tencent.setOpenId(openId);
                tencent.setAccessToken(accessToken,expires);
                QQToken qqToken = tencent.getQQToken();
                userInfo = new UserInfo(getApplicationContext(),qqToken);
                userInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        Log.e("LoginActivity","登录成功"+o.toString());
                        JSONObject jsonObject = (JSONObject) o;
                        try {
                            String nickName = jsonObject.getString("nickname");
                            String gender = jsonObject.getString("gender");
                            Message msg = new Message();
                            msg.obj = nickName+":"+gender;
                            msg.what=1;
                            handler1.sendMessage(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e("LoginActivity","登录失败"+o.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e("LoginActivity","登录取消"+o.toString());
                    }

                    @Override
                    public void onWarning(int i) {

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            //授权失败
        }

        @Override
        public void onCancel() {
            //授权取消
        }

        @Override
        public void onWarning(int i) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,baseUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
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

                }else if(etCode.getText().toString().length()!=4){
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
                        //将手机号传给服务器端。服务器端判断该用户是否存在，未存在直接注册。
                        try {
                            postLogin();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                        //验证码成功，向服务器进行发送，获取当前用户信息
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
    /**获得IP地址，分为两种情况，一是wifi下，二是移动网络下，得到的ip地址是不一样的*/
    public String getIPAddress() {
        NetworkInfo info = ((ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //调用方法将int转换为地址字符串
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            Toast.makeText(this,"请打开网络连接",Toast.LENGTH_LONG).show();
        }
        Log.e("yag",123+"");
        return null;
    }
    /**
     * 将得到的int类型的IP转换为String类型
     * @param ip
     * @return
     */
    public  String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    //将对象转换为JSON类型数据
    public String object2JSON(User user) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nickname",user.getNickname());
        jsonObject.put("sex",user.getSex());
        jsonObject.put("img",user.getImage());
        return jsonObject.toString();
    }

    //登录逻辑
    public void postLogin() throws JSONException {
        //2.创建Request请求对象
        //请求体是普通字符串
        User user = new User(etphone.getText().toString());
        Log.e("etphone",etphone.getText().toString());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),this.object2JSON(user));
        //3.创建Call对象
        Request request = new Request.Builder()
                .post(requestBody)//请求方式为POST
                .url(IP.CONSTANT+"UserLoginServlet?phoneNumber="+etphone.getText().toString())
                .build();
        final Call call = okHttpClient.newCall(request);
        //4.提交请求并返回响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时回调
                Log.e("登录请求结果","失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功时回调
                Log.e("登录请求结果","成功");
               //从服务器端获取到JSON格式字符串
                String jsonString = response.body().string();
                Log.e("jsonString",jsonString);
                Message msg = new Message();
                msg.what=2;
                msg.obj = jsonString;
                handler1.sendMessage(msg);
            }
        });
    }
}
