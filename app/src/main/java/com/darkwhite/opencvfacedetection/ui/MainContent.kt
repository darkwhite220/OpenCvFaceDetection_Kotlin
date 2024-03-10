package com.darkwhite.opencvfacedetection.ui

import android.net.Uri
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.darkwhite.opencvfacedetection.MyUtils
import org.opencv.objdetect.FaceDetectorYN


@Composable
fun MainContent(faceDetector: FaceDetectorYN) {
    val context = LocalContext.current
    var contentUri by remember { mutableStateOf<Uri?>(null) }
    ContentPicker(onContentPicked = { contentUri = it })
    
    contentUri?.let { uri ->
        val filePath by remember { mutableStateOf(MyUtils.getRealPathFromURI(context, uri)) }
        
        if (filePath == null) {
            Text(text = "File path is null, Uri: $uri")
        } else {
            VideoFaceDetection(
                faceDetector = faceDetector,
                filePath = filePath!!,
            ) { contentUri = null }
        }
    }
}