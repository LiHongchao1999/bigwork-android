package com.example.homeworkcorrect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.homeworkcorrect.alipay.OrderInfoUtil2_0;
import com.example.homeworkcorrect.alipay.PayActivity;
import com.example.homeworkcorrect.alipay.PayResult;
import com.example.homeworkcorrect.cache.IP;
import com.example.homeworkcorrect.cache.UserCache;
import com.example.homeworkcorrect.entity.User;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MoneyActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private ImageView back1;//返回
    private Button m1;
    private Button m2;
    private Button m3;
    private Button m4;
    private Button m5;
    private Button m6;
    private Button pay;
    private LinearLayout zf1;
    private LinearLayout zf2;
    private String payMethod;//支付方式
    private TextView account;
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2021000117649274";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088621955695774";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDHXCA5y8whkN7NBGvb9on1a5htt2K9RcPPo13AsJbyR4NK9JRpZtHuD9UYpSeFM5DKNYZpg1JVEcNtvBUjhR/gGABaT+kZVaiIIvruDua7x8IxPmutIW9Ey54Yyn3nKkDPk5ECzdYRSeR8RPtHeCW8kF/rEmJRBcam31EkcaQUBVEPNuZB9vl+bRXUeBn8oVNSaYByIWVsZrkgXxTul1hXdYnULQWowgEElhtQGrYqgKD1bZjh02hVTCi/H92Ls5E783K0blJjgI0KKd7r9XpdrUuUsdZhdZUqug/3/IgN19mlZdQiPelxdUTgFQrZR8xYPzOsEUHmKf2VT5hRAqujAgMBAAECggEAOW0CI2PfEzQl2aySXTQxHS63I7OVj6vO8bq/QMgdUwDigu7c6Vei1Q5eddDtB2OJK5Yrd61KRBLv1mY9OJQaUHTZdSpbnN6dw0lAOt6z/BdYitLONXrSEGEO01lQ1wvGy4RYWxRru2Mrm9hLAZVMWXrFBnOvOmONi3f8aaEKgHFdTPb0BqtfhChp0wwrO1F6B2rLbLLvW99PHjL2aSCuRYLzj9g5zCM5ZaXNl3Q1jIhKlpL/VlXUmAORddvAXAkX+uGxb8GDM2EYAZfx9vPmiGHxTfne7ITEfshcUguE74wSKoSOHmBw7Qb4vnjVyQ4i1khjfLCn+P1wyOP1qcbvEQKBgQDtpPk0+WGqP0amKjTB3Jy28vLqq+91AmbY5H4jvB7DSP9+b19idZFPMTQtaiYAzj1Q/QhZpjeNMcahcjCqVdfsO5l34tNtb8S+/a3nFG+G2BvcpEHCZtED6qWxrR6wDxCsC2s5Vk2zxFUKPXFoJVEtShW92Z84HGEQCDqz2tBENwKBgQDWwiNs6lEmkh2mKNbauN2CrTyPiwnFeEGh1X5ttfftCTy44Rqj7WSHuKbNF1++U9uAze3HJddt7CoU9seuWE9KXJGu298JqZtEc7Wbex8/wam2G2/EewpQRfQNOIHqZXJKqvJ8mQYNTK7kLfDmuajyXe/UhAkDxaSrZbDqPf419QKBgQC1aDYERkpTl7HqrlVPl4TZ82OT2GQWwhqYfvP7e2NQV60Raxs5Id4mwby+3kTcYJNV+5IgPU4yrkiefXjAzn7hii2A4tnatHNQCH5z45mvKdE7APhh2fK8Nqb9ltXSN+3WJ3FWd5chH0J7aqPjiHkLRX6LSlDMmH1Pq7iECwjoyQKBgE7A+L67GrE7SfYh6dBhlJGUN64n/ZhVpjPTl75hyZ46EfN2UQ+mQYRn2XtCRBEGrWsJmVW7ccnUngX8DXPI3KLXWXE3Hg5sDQ1IJWPz/ZyixzqJyy3RNRIE5R9oBnRPaV8qWywH7ak44rW4hywFD4h5nDZhiPDCLMXRz9PGkg91AoGBANuV2UaMuGehdzOuBxhYJa36BBBIA11ic4Ei6d+tTQ8Rcw3eD2SLm46FMR5gGuUvCqCNWaUCQBRLhe/0cz4kn7iX1hQ/FTze0Ktoosu3NyUT958FPqk5ASoks3xTFBpaetc9Mnw9In82K519DvWn6BAY1y/dneRtjrQNrM6RhZZq";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        InfoDialog infoDialog = new InfoDialog.Builder(MoneyActivity.this,R.layout.info_dialog_green)
                                .setTitle("Done")
                                .setMessage("支付成功")
                                .setButton("OK", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                //先让服务端进行修改
                                                try {
                                                    updateMoney();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                ).create();
                        infoDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_white);
                        infoDialog.show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MoneyActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    String str = msg.obj.toString();
                    Log.e("修改拍拍币返回的结果",str);
                    Toast.makeText(MoneyActivity.this,str,Toast.LENGTH_LONG).show();
                    //修改拍拍币数量
                    UserCache.user.setClapping_money(UserCache.clapMoneyNum+UserCache.user.getClapping_money());
                    //跳转到个人界面
                    Intent intent = new Intent();
                    setResult(220,intent);
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        okHttpClient = new OkHttpClient();
        getViews();
        Log.e("acount",account.toString());
        account.setText(UserCache.user.getClapping_money()+"");
    }
    /*获取控件引用*/
    private void getViews() {
        back1 = findViewById(R.id.money_return);
        m1=findViewById(R.id.m1);
        m2=findViewById(R.id.m2);
        m3=findViewById(R.id.m3);
        m4=findViewById(R.id.m4);
        m5=findViewById(R.id.m5);
        m6=findViewById(R.id.m6);
        pay = findViewById(R.id.immediate_pay);
        zf1 = findViewById(R.id.zf1);
        zf2 = findViewById(R.id.zf2);
        account = findViewById(R.id.account_user);
    }
    /**
     * 支付宝支付业务示例
     */
    public void payV2(View v) {
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MoneyActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    public void onClicked(View view){
        switch (view.getId()){
            case R.id.m1:
                UserCache.clapMoneyNum = 1;
                m1.setTextColor(getResources().getColor(R.color.btn_select));
                m2.setTextColor(getResources().getColor(R.color.blackhao));
                m3.setTextColor(getResources().getColor(R.color.blackhao));
                m4.setTextColor(getResources().getColor(R.color.blackhao));
                m5.setTextColor(getResources().getColor(R.color.blackhao));
                m6.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.m2:
                UserCache.clapMoneyNum = 10;
                m2.setTextColor(getResources().getColor(R.color.btn_select));
                m1.setTextColor(getResources().getColor(R.color.blackhao));
                m3.setTextColor(getResources().getColor(R.color.blackhao));
                m4.setTextColor(getResources().getColor(R.color.blackhao));
                m5.setTextColor(getResources().getColor(R.color.blackhao));
                m6.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.m3:
                UserCache.clapMoneyNum = 50;
                m3.setTextColor(getResources().getColor(R.color.btn_select));
                m2.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setTextColor(getResources().getColor(R.color.blackhao));
                m4.setTextColor(getResources().getColor(R.color.blackhao));
                m5.setTextColor(getResources().getColor(R.color.blackhao));
                m6.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.m4:
                UserCache.clapMoneyNum = 100;
                m4.setTextColor(getResources().getColor(R.color.btn_select));
                m2.setTextColor(getResources().getColor(R.color.blackhao));
                m3.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setTextColor(getResources().getColor(R.color.blackhao));
                m5.setTextColor(getResources().getColor(R.color.blackhao));
                m6.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.m5:
                UserCache.clapMoneyNum = 500;
                m5.setTextColor(getResources().getColor(R.color.btn_select));
                m2.setTextColor(getResources().getColor(R.color.blackhao));
                m3.setTextColor(getResources().getColor(R.color.blackhao));
                m4.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setTextColor(getResources().getColor(R.color.blackhao));
                m6.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.m6:
                UserCache.clapMoneyNum = 1000;
                m6.setTextColor(getResources().getColor(R.color.btn_select));
                m2.setTextColor(getResources().getColor(R.color.blackhao));
                m3.setTextColor(getResources().getColor(R.color.blackhao));
                m4.setTextColor(getResources().getColor(R.color.blackhao));
                m5.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setTextColor(getResources().getColor(R.color.blackhao));
                m1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m3.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m4.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m5.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                m6.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                break;
            case R.id.zf1:
                payMethod = "微信支付";
                zf1.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                zf2.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                break;
            case R.id.zf2:
                payMethod = "支付宝支付";
                zf1.setBackground(getResources().getDrawable(R.drawable.edit_border2));
                zf2.setBackground(getResources().getDrawable(R.drawable.edit_border3));
                break;
            case R.id.money_return:
                finish();
                break;
        }
    }
    //修改逻辑
    public void updateMoney() throws JSONException {
        //2.创建Request请求对象
        //请求体是字符串
        User user = new User();
        user.setId(UserCache.user.getId());
        user.setClapping_money(UserCache.clapMoneyNum+UserCache.user.getClapping_money());
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),new Gson().toJson(user));
        //3.创建Call对象
        Request request = new Request.Builder()
                .post(requestBody)//请求方式为POST
                .url(IP.CONSTANT+"user/money")
                .build();
        final Call call = okHttpClient.newCall(request);
        //4.提交请求并返回响应
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败时回调
                Log.e("修改请求结果","失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //从服务器端获取到JSON格式字符串
                String str = response.body().string();
                Log.e("Money",str);
                Message msg = new Message();
                msg.what=1;
                msg.obj=str;
                handler.sendMessage(msg);
            }
        });
    }
}