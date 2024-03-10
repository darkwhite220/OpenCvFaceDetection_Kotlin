package com.darkwhite.opencvfacedetection

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.darkwhite.opencvfacedetection.ui.MainContent
import com.darkwhite.opencvfacedetection.ui.theme.OpenCVFaceDetectionTheme
import org.opencv.android.OpenCVLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        ActivityCompat.requestPermissions(
            this,
            REQUIRED_PERMISSIONS,
            0
        )
        
        if (OpenCVLoader.initLocal()) {
            Log.d(TAG, "onCreate: OpenCVLoader LOADED SUCCESSFULLY")
        } else {
            Log.e(TAG, "onCreate: OpenCVLoader LOADING FAILED")
        }
        
        val faceDetector = MyUtils.initFaceDetector(this)
        
        setContent {
            OpenCVFaceDetectionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (faceDetector == null) {
                        Text(text = "Face Detector is null")
                    } else {
                        MainContent(faceDetector = faceDetector)
                    }
                }
            }
        }
    }
    
    companion object {
        private const val TAG = "MainActivity"
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
            ).toTypedArray()
    }
}
