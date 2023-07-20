package com.example.mycameraview

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cameralib.CameraView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

const val TAG: String = "MainActivity"

class MainActivity : ComponentActivity() {

    private lateinit var mCameraView: CameraView
    private val REQUEST_CAMERA_PERMISSION = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mCameraView = findViewById(R.id.camera)
        mCameraView.addCallback(mCallback)
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            mCameraView.start()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }
    }

    override fun onPause() {
        mCameraView.stop()
        super.onPause()
    }

    private val mCallback: CameraView.Callback = object : CameraView.Callback() {
        override fun onCameraOpened(cameraView: CameraView?) {
            Log.d(TAG, "onCameraOpened")
        }

        override fun onCameraClosed(cameraView: CameraView?) {
            Log.d(TAG, "onCameraClosed")
        }

        override fun onPictureTaken(cameraView: CameraView, data: ByteArray) {
            Log.d(TAG, "onPictureTaken " + data.size)
        }

        override fun onPreviewFrame(data: ByteArray, camera: Camera) {
            Log.d(TAG, "onPreviewFrame " + data.size)
            camera.addCallbackBuffer(data)
        }
    }
}