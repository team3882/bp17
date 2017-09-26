package org.usfirst.frc.team3882.robot;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.CameraServerJNI;

public class VisProc {

	public static Mat vidProc(Mat s0, double[] hue, double[] sat, double[] lum) {
        Scalar lower = new Scalar(hue[0], sat[0], lum[0]);
        Scalar upper = new Scalar(hue[1], sat[1], lum[1]);
        Mat out = new Mat();
        Mat s1 = new Mat();
        Mat s2 = new Mat();
        Mat s3 = new Mat();
        //Color Filtering
		Imgproc.cvtColor(s0, s1, Imgproc.COLOR_BGR2HLS);
        Core.inRange(s1, lower, upper, s2);
        //Filtering and Cleaning Up Noise
        Imgproc.erode(s2, s3, new Mat(2,2,2), new Point(0,0), 2);
        Imgproc.dilate(s2, s3, new Mat(2,2,2), new Point(0,0), 2);
        return out;
	}
   }
