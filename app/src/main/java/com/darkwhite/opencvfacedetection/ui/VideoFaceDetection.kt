package com.darkwhite.opencvfacedetection.ui

import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import com.darkwhite.opencvfacedetection.OpenCvUtils
import com.darkwhite.opencvfacedetection.OpenCvUtils.visualize
import kotlin.math.roundToInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.FaceDetectorYN
import org.opencv.videoio.VideoCapture
import org.opencv.videoio.Videoio


private const val TAG = "VideoFaceDetection"

@Composable
fun VideoFaceDetection(
    faceDetector: FaceDetectorYN,
    filePath: String,
    onBackClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var inputSize by remember { mutableStateOf<Size?>(null) }
    val videoFrame = remember { Mat() }
    val rgba = remember { Mat() }
    val bgr = remember { Mat() }
    val bgrScaled = remember { Mat() }
    val faces = remember { Mat() }
    
    Column {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(), contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
    
    val videoCapture = remember { VideoCapture(filePath, Videoio.CAP_ANDROID) } // CAP_OPENCV_MJPEG
    
    LaunchedEffect(key1 = Unit) {
        scope.launch(Dispatchers.Default) {
            openCvDetection(
                videoCapture = videoCapture,
                videoFrame = videoFrame,
                faceDetector = faceDetector,
                rgba = rgba,
                bgr = bgr,
                bgrScaled = bgrScaled,
                faces = faces,
                inputSize = { inputSize },
                setImageInputSize = { inputSize = it },
                setImageBitmap = { bitmap = it },
            )
        }
    }
    
    BackHandler {
        videoFrame.release()
        rgba.release()
        bgr.release()
        bgrScaled.release()
        faces.release()
        onBackClick()
    }
}

private fun openCvDetection(
    videoCapture: VideoCapture,
    videoFrame: Mat,
    faceDetector: FaceDetectorYN,
    rgba: Mat,
    bgr: Mat,
    bgrScaled: Mat,
    faces: Mat,
    inputSize: () -> Size?,
    setImageInputSize: (Size) -> Unit,
    setImageBitmap: (Bitmap) -> Unit,
) {
    if (!videoCapture.isOpened) {
        Log.e(TAG, "VideoFaceDetection: Can't open video, videoCapture= $videoCapture")
        return
    }
    
    // for full video, while(true){
    (0..50).forEach { _ -> // first 50 frames
        // 1. extract a frame
        videoCapture.read(videoFrame)
        
        if (videoFrame.empty()) {
            return
        }
        
        // 2. change color to display this frame later
        // VideoCapture with CAP_ANDROID generates RGB frames instead of BGR
        // https://github.com/opencv/opencv/issues/24687
        Imgproc.cvtColor(videoFrame, rgba, Imgproc.COLOR_RGB2RGBA)
        
        // 3. resize based on selected scale
        if (inputSize() == null) {
            val tempSize = Size(
                (rgba.cols() / OpenCvUtils.SCALE).roundToInt().toDouble(),
                (rgba.rows() / OpenCvUtils.SCALE).roundToInt().toDouble()
            )
            faceDetector.inputSize = tempSize
            setImageInputSize(tempSize)
        }
        
        // 4. faceDetector need's images in BGR
        Imgproc.cvtColor(rgba, bgr, Imgproc.COLOR_RGBA2BGR)
        // 5. downscale the BRG image
        Imgproc.resize(bgr, bgrScaled, inputSize())
        // 6. detect faces, the result will be in "faces" variable
        faceDetector.detect(bgrScaled, faces)
        // 7. draw box and landmark points on detected faces
        visualize(rgba, faces)
        
        // 8. create a bitmap from "rgba" to display
        val bmp = Bitmap.createBitmap(
            rgba.cols(),
            rgba.rows(),
            Bitmap.Config.ARGB_8888
        )
        Utils.matToBitmap(rgba, bmp)
        
        setImageBitmap(bmp)
    }
}