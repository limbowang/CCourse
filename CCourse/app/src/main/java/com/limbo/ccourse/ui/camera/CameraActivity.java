package com.limbo.ccourse.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.limbo.ccourse.R;
import com.limbo.ccourse.ui.ActivityCode;

public class CameraActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mCameraPreview;
    private Button mButtonCameraCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // fullscreen activity
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera);

        // bind photo capture button
        mButtonCameraCapture = (Button) findViewById(R.id.button_camera_capture);
        mButtonCameraCapture.setOnClickListener(new CameraCaptureListener());
    }

    private boolean checkCameraHardware() {
        return this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    protected void onResume() {
        super.onRestart();

        mCamera = getCameraInstance();
        mCameraPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.removeAllViews();
        preview.addView(mCameraPreview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            Toast.makeText(this, R.string.no_access_camera, Toast.LENGTH_SHORT).show();
        }
        return camera;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    public Activity getActivity() {
        return this;
    }

    class CameraCaptureListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mCamera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    Intent intent = new Intent();
                    intent.putExtra("picture", data);
                    getActivity().setResult(ActivityCode.CAMERA_CAPTURE_END, intent);
                    getActivity().finish();
                }
            });
        }
    }
}
