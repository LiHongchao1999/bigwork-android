package com.example.homeworkcorrect;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Marlon
 * @desc Camera2Activity 基于Camera2 API 自定义相机
 * @date 2018/6/13
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Activity extends AppCompatActivity {


    @BindView(R.id.texture)
    AutoFitTextureView texture;
    @BindView(R.id.takephoto)
    ImageView takephoto;
    @BindView(R.id.auto)
    RadioButton auto;
    @BindView(R.id.open)
    RadioButton open;
    @BindView(R.id.close)
    RadioButton close;
    @BindView(R.id.flash_rg)
    RadioGroup flashRg;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.photoAlbum)
    ImageView photoAlbum;
    /*** 相机管理类*/
    CameraManager mCameraManager;


    /*** 指定摄像头ID对应的Camera实体对象*/
    CameraDevice mCameraDevice;




    /**
     * 预览尺寸
     */
    private Size mPreviewSize;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private ArrayList<String> photoList = new ArrayList<>();
    private Handler mainHandler;
    /*** 打开摄像头的ID{@link CameraDevice}.*/
    private int mCameraId = CameraCharacteristics.LENS_FACING_FRONT;


    /*** 处理静态图像捕获的ImageReader。{@link ImageReader}*/
    private ImageReader mImageReader;


    /*** 用于相机预览的{@Link CameraCaptureSession}。*/
    private CameraCaptureSession mCaptureSession;


    /*** {@link CaptureRequest.Builder}用于相机预览请求的构造器*/
    private CaptureRequest.Builder mPreviewRequestBuilder;


    /***预览请求, 由上面的构建器构建出来*/
    private CaptureRequest mPreviewRequest;
    /**
     * 从屏幕旋转图片转换方向。
     */
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();


    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }


    /***判断是否支持闪关灯*/
    private boolean mFlashSupported;


    /*** 用于运行不应阻塞UI的任务的附加线程。*/
    private HandlerThread mBackgroundThread;


    /*** 用于在后台运行任务的{@link Handler}。*/
    private Handler mBackgroundHandler;


    /**
     * 文件存储路径
     */
    private File mFile;
    /**
     * 预览请求构建器, 用来构建"预览请求"(下面定义的)通过pipeline发送到Camera device
     * 这是{@link ImageReader}的回调对象。 当静止图像准备保存时，将会调用“onImageAvailable”。
     */
    private final ImageReader.OnImageAvailableListener mOnImageAvailableListener
            = new ImageReader.OnImageAvailableListener() {


        @Override
        public void onImageAvailable(ImageReader reader) {
            mFile = new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera/",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg");
            photoList.add(mFile.getAbsolutePath());
            mBackgroundHandler.post(new ImageSaver(reader.acquireNextImage(), mFile));
        }
    };


    /*** {@link CameraDevice.StateCallback}打开指定摄像头回调{@link CameraDevice}*/
    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {


        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            mCameraDevice = cameraDevice;
            createCameraPreview();
        }


        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }


        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }


    };


    /**
     * TextureView 生命周期响应
     */
    private final TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override //创建
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //当TextureView创建完成，打开指定摄像头相机
            openCamera(width, height, mCameraId);
        }


        @Override //尺寸改变
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {


        }


        @Override //销毁
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }


        @Override //更新
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {


        }
    };
    private int CONTROL_AE_MODE;
    private CameraCharacteristics characteristics;
    private Context mcontext;


    /**
     * 打开指定摄像头ID的相机
     *
     * @param width
     * @param height
     * @param cameraId
     */
    private void openCamera(int width, int height, int cameraId) {
        if (ActivityCompat.checkSelfPermission(Camera2Activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        try {
            mSurfaceWidth = width;
            mSurfaceHeight = height;
//            getCameraId(cameraId);
            characteristics = mCameraManager.getCameraCharacteristics(mCameraId + "");
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            // 获取设备方向
            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int totalRotation = sensorToDeviceRotation(characteristics, rotation);
            boolean swapRotation = totalRotation == 90 || totalRotation == 270;
            int rotatedWidth = mSurfaceWidth;
            int rotatedHeight = mSurfaceHeight;
            if (swapRotation) {
                rotatedWidth = mSurfaceHeight;
                rotatedHeight = mSurfaceWidth;
            }
            // 获取最佳的预览尺寸
            //mPreviewSize = getOptimalSize(SurfaceTexture.class, rotatedWidth, rotatedHeight);
            mPreviewSize = getPreferredPreviewSize(map.getOutputSizes(SurfaceTexture.class),rotatedWidth,rotatedHeight);
            if (swapRotation) {
                texture.setAspectRatio(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            } else {
                texture.setAspectRatio(mPreviewSize.getHeight(), mPreviewSize.getWidth());
            }
            if (mImageReader == null) {
                // 创建一个ImageReader对象，用于获取摄像头的图像数据,maxImages是ImageReader一次可以访问的最大图片数量
                mImageReader = ImageReader.newInstance(mPreviewSize.getWidth(), mPreviewSize.getHeight(),
                        ImageFormat.JPEG, 2);
                mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, mBackgroundHandler);

            }

            //检查是否支持闪光灯
            Boolean available = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            mFlashSupported = available == null ? false : available;
            mCameraManager.openCamera(mCameraId + "", mStateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }




    /**
     * 设置相机闪关灯模式
     *
     * @param AE_MODE 闪关灯的模式
     * @throws CameraAccessException
     */
    private void setFlashMode(int AE_MODE) {
        if (mFlashSupported) {
            this.CONTROL_AE_MODE = AE_MODE;
            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, AE_MODE);
            if (AE_MODE == CaptureRequest.CONTROL_AE_MODE_OFF) {
                mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, CaptureRequest.FLASH_MODE_OFF);
            }
        }


        // 构建上述的请求
        mPreviewRequest = mPreviewRequestBuilder.build();
        // 重复进行上面构建的请求, 用于显示预览
        try {
            mCaptureSession.setRepeatingRequest(mPreviewRequest, null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建预览对话
     */
    private void createCameraPreview() {
        try {
            // 获取texture实例
            SurfaceTexture surfaceTexture = texture.getSurfaceTexture();
            //Size previewSize = getOptimalSize(characteristics, SurfaceTexture.class, texture.getWidth(), texture.getHeight());
            surfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
//            assert surfaceTexture != null;
//            //我们将默认缓冲区的大小配置为我们想要的相机预览的大小。
//            surfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            // 用来开始预览的输出surface
            Surface surface = new Surface(surfaceTexture);
            //创建预览请求构建器
            mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //将TextureView的Surface作为相机的预览显示输出
            mPreviewRequestBuilder.addTarget(surface);
            //在这里，我们为相机预览创建一个CameraCaptureSession。
            mCameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {


                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            // 相机关闭时, 直接返回
                            if (null == mCameraDevice) {
                                return;
                            }
                            //会话准备就绪后，我们开始显示预览。
                            // 会话可行时, 将构建的会话赋给field
                            mCaptureSession = cameraCaptureSession;


                            //相机预览应该连续自动对焦。
                            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                            //设置闪关灯模式
                            setFlashMode(CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                        }


                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                            showToast("预览失败了");


                        }
                    }, null
            );

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 拍照时调用方法
     */
    private void captureStillPicture() {
        try {
            if (mCameraDevice == null) {
                return;
            }
            // 创建作为拍照的CaptureRequest.Builder
            mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            // 将imageReader的surface作为CaptureRequest.Builder的目标
            mPreviewRequestBuilder.addTarget(mImageReader.getSurface());
/*            // 设置自动对焦模式
            mBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 设置自动曝光模式
            mBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);*/
            //设置为自动模式
//            mPreviewRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            int deviceOrientation = new DeviceOrientationListener(this).orientation;
            int jpegOrientation = getJpegOrientation(characteristics, deviceOrientation);
            mPreviewRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, jpegOrientation);

            setFlashMode(CONTROL_AE_MODE);
            // 停止连续取景
            mCaptureSession.stopRepeating();
            // 捕获静态图像
            mCaptureSession.capture(mPreviewRequestBuilder.build(), new CameraCaptureSession.CaptureCallback() {
                // 拍照完成时激发该方法
                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    //重新打开预览
                    createCameraPreview();

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取设备方向
     *
     * @param characteristics
     * @param deviceOrientation
     * @return
     */
    private static int sensorToDeviceRotation(CameraCharacteristics characteristics, int deviceOrientation) {
        int sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        deviceOrientation = ORIENTATIONS.get(deviceOrientation);
        return (sensorOrientation + deviceOrientation + 360) % 360;
    }


    /**
     * 获取可用设备可用摄像头列表
     */
    private void getCameraId(int ID) {
        try {
            for (String cameraId : mCameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(cameraId);
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == ID) {
                    continue;
                }
                mCameraId = Integer.valueOf(cameraId);
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置最佳尺寸
     *
     * @param sizes
     * @param width
     * @param height
     * @return
     */
    private Size getPreferredPreviewSize(Size[] sizes, int width, int height) {
        List<Size> collectorSizes = new ArrayList<>();
        for (Size option : sizes) {
            if (width > height) {
                if (option.getWidth() > width && option.getHeight() > height) {
                    collectorSizes.add(option);
                }
            } else {
                if (option.getHeight() > width && option.getWidth() > height) {
                    collectorSizes.add(option);
                }
            }
        }
        if (collectorSizes.size() > 0) {
            return Collections.min(collectorSizes, new Comparator<Size>() {
                @Override
                public int compare(Size s1, Size s2) {
                    return Long.signum(s1.getWidth() * s1.getHeight() - s2.getWidth() * s2.getHeight());
                }
            });
        }
//
//        Collections.sort(collectorSizes, new Comparator<Size>() {
//            @Override
//            public int compare(Size o1, Size o2) {
//                return o1.getWidth() * o1.getHeight() - o2.getWidth() * o2.getHeight();
//            }
//        });
//        Collections.reverse(collectorSizes);
        return sizes[0];
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        ButterKnife.bind(this);
        initView();
        mcontext = this;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("12",photoList.size()+"");
                if(photoList.size()!=0){
                    Intent intent = new Intent(Camera2Activity.this,SubmitHomeWorkActivtiy.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("photoList",photoList);
                    intent.putExtra("photoList",bundle);
                    startActivity(intent);
                }else{

                    InfoDialog infoDialog = new InfoDialog.Builder(mcontext,R.layout.dialog)
                        .setTitle("UnDone")
                        .setMessage("您还未选择照片")
                        .setButton("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(mcontext, "OK Clicked.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        ).create();
                    infoDialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_white);
                    infoDialog.show();
                }
            }
        });
        mainHandler  = new Handler();
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }
    private void initView() {
        flashRg.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.auto:
                    //自动闪光灯
                    setFlashMode(CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                    break;
                case R.id.open:
                    //开启闪光灯
                    setFlashMode(CaptureRequest.CONTROL_AE_MODE_ON_ALWAYS_FLASH);
                    break;
                case R.id.close:
                    //关闭闪光灯
                    setFlashMode(CaptureRequest.CONTROL_AE_MODE_OFF);
                    break;
                default:
                    break;


            }
        });
        // 获取CameraManager 相机设备管理器
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
    }


    /**
     * 初试化拍照线程
     */
    public void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }


    public void stopBackgroundThread() {
        if (mBackgroundThread != null) {
            mBackgroundThread.quitSafely();
            try {
                mBackgroundThread.join();
                mBackgroundThread = null;
                mBackgroundHandler = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Closes the current {@link CameraDevice}.
     * 关闭正在使用的相机
     */
    private void closeCamera() {
        // 关闭捕获会话
        if (null != mCaptureSession) {
            mCaptureSession.close();
            mCaptureSession = null;
        }
        // 关闭当前相机
        if (null != mCameraDevice) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
        // 关闭拍照处理器
        if (null != mImageReader) {
            mImageReader.close();
            mImageReader = null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (texture.isAvailable()) {
            openCamera(texture.getWidth(), texture.getHeight(), mCameraId);
        } else {
            texture.setSurfaceTextureListener(textureListener);
        }
        startBackgroundThread();
    }


    @Override
    protected void onPause() {
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }


    @OnClick(R.id.takephoto)
    public void onViewClicked() {
        captureStillPicture();
    }


    /**
     * Shows a {@link Toast} on the UI thread.
     * 在UI上显示Toast的方法
     *
     * @param text The message to show
     */
    /**
     * Shows a {@link Toast} on the UI thread.
     * 在UI上显示Toast的方法
     *
     * @param text The message to show
     */
    private void showToast(final String text) {
        runOnUiThread(() -> Toast.makeText(Camera2Activity.this, text, Toast.LENGTH_SHORT).show());
    }


    /**
     * Saves a JPEG {@link Image} into the specified {@link File}.
     * 保存图片到自定目录
     * 保存jpeg到指定的文件夹下, 开启子线程执行保存操作
     */
    private class ImageSaver implements Runnable {


        /**
         * The JPEG image
         * 要保存的图片
         */
        private final Image mImage;
        /**
         * The file we save the image into.
         * 图片存储的路径
         */
        private final File mFile;


        ImageSaver(Image image, File file) {
            mImage = image;
            mFile = file;
        }


        @Override
        public void run() {

            ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            FileOutputStream output = null;
            try {
                output = new FileOutputStream(mFile);
                output.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mImage.close();
                if (null != output) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(mFile); //out is your output file
                mediaScanIntent.setData(contentUri);
                Camera2Activity.this.sendBroadcast(mediaScanIntent);
            } else {
                sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://"
                                + Environment.getExternalStorageDirectory())));
            }
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapFactory.decodeFile(mFile.getAbsolutePath());
                    photoAlbum.setImageBitmap(bitmap);
                }
            };
            //上面代码中的runnable线程体经过post后会直接传送到主线程中执行修改字体的操作。
            //post直接可以把一段代码当做变量一样传递，但是请不要传送耗时操作的代码到主线程中
            mainHandler.post(r);
        }
    }
    private int getJpegOrientation(CameraCharacteristics cameraCharacteristics, int deviceOrientation) {
        int myDeviceOrientation = deviceOrientation;
        if (myDeviceOrientation == android.view.OrientationEventListener.ORIENTATION_UNKNOWN) {
            return 0;
        }
        int sensorOrientation = cameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);

                // Round device orientation to a multiple of 90
        myDeviceOrientation = (myDeviceOrientation + 45) / 90 * 90;

        // Reverse device orientation for front-facing cameras
        Boolean facingFront = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT;
        if (facingFront) {
            myDeviceOrientation = -myDeviceOrientation;
        }

        // Calculate desired JPEG orientation relative to camera orientation to make
        // the image upright relative to the device orientation
        return (sensorOrientation + myDeviceOrientation + 360) % 360;
    }

    private class DeviceOrientationListener extends OrientationEventListener{
        private int orientation = 0;
        public DeviceOrientationListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int i) {
            this.orientation = i;
        }
    }
}