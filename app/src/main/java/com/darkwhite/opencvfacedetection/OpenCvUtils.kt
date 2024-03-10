package com.darkwhite.opencvfacedetection

import android.util.Log
import kotlin.math.roundToInt
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

object OpenCvUtils {
    // 1=No down scale, 2=half size...
    const val SCALE = 3f
    private val BOX_COLOR = Scalar(0.0, 255.0, 0.0)
    private val RIGHT_EYE_COLOR = Scalar(255.0, 0.0, 0.0)
    private val LEFT_EYE_COLOR = Scalar(0.0, 0.0, 255.0)
    private val NOSE_TIP_COLOR = Scalar(0.0, 255.0, 0.0)
    private val MOUTH_RIGHT_COLOR = Scalar(255.0, 0.0, 255.0)
    private val MOUTH_LEFT_COLOR = Scalar(0.0, 255.0, 255.0)
    private const val THICKNESS = 2
    private const val TAG = "OpenCvUtils"
    
    fun visualize(rgba: Mat, faces: Mat) {
        val faceData = FloatArray(faces.cols() * faces.channels())
        for (i in 0 until faces.rows()) {
            faces[i, 0, faceData]
            Log.d(
                TAG,
                "Detected face (" + faceData[0] + ", " + faceData[1] + ", " +
                    faceData[2] + ", " + faceData[3] + ")"
            )
            
            // Draw bounding box
            Imgproc.rectangle(
                rgba,
                Rect(
                    (SCALE * faceData[0]).roundToInt(),
                    (SCALE * faceData[1]).roundToInt(),
                    (SCALE * faceData[2]).roundToInt(),
                    (SCALE * faceData[3]).roundToInt()
                ),
                BOX_COLOR, THICKNESS
            )
            // Draw landmarks
            Imgproc.circle(
                rgba,
                Point(
                    (SCALE * faceData[4]).roundToInt().toDouble(),
                    (SCALE * faceData[5]).roundToInt().toDouble()
                ),
                2,
                RIGHT_EYE_COLOR,
                THICKNESS
            )
            Imgproc.circle(
                rgba,
                Point(
                    (SCALE * faceData[6]).roundToInt().toDouble(),
                    (SCALE * faceData[7]).roundToInt().toDouble()
                ),
                2,
                LEFT_EYE_COLOR,
                THICKNESS
            )
            Imgproc.circle(
                rgba,
                Point(
                    (SCALE * faceData[8]).roundToInt().toDouble(),
                    (SCALE * faceData[9]).roundToInt().toDouble()
                ),
                2,
                NOSE_TIP_COLOR,
                THICKNESS
            )
            Imgproc.circle(
                rgba,
                Point(
                    (SCALE * faceData[10]).roundToInt().toDouble(),
                    (SCALE * faceData[11]).roundToInt().toDouble()
                ),
                2,
                MOUTH_RIGHT_COLOR,
                THICKNESS
            )
            Imgproc.circle(
                rgba,
                Point(
                    (SCALE * faceData[12]).roundToInt().toDouble(),
                    (SCALE * faceData[13]).roundToInt().toDouble()
                ),
                2,
                MOUTH_LEFT_COLOR,
                THICKNESS
            )
        }
    }
}