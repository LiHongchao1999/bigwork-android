package com.example.homeworkcorrect.alipay;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.homeworkcorrect.R;

import java.util.Map;

public class PayActivity extends AppCompatActivity {
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
                        Toast.makeText(PayActivity.this, "成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //使用沙箱测试
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
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
                PayTask alipay = new PayTask(PayActivity.this);
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
}