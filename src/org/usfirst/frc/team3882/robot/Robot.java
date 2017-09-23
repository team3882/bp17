package org.usfirst.frc.team3882.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Range;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.MjpegServer;

public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();

	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);

		new Thread(() -> {
            UsbCamera cam0 = CameraServer.getInstance().startAutomaticCapture("cam0", 0); //grabs mscam
            UsbCamera cam1 = CameraServer.getInstance().startAutomaticCapture("cam1", 1); //grabs mscam
            MjpegServer server = new MjpegServer("server", 1181); //Sets port to stream server on 1181 -- within legal limits.
            cam0.setResolution(640, 480); 
            CvSink sink = CameraServer.getInstance().getVideo("cam0");
            CvSource outStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
            double[] hue = {43.70503597122302, 81.70648464163823};
            double[] sat = {43.57014388489208, 163.61774744027304};
            double[] lum = {43.57014388489208, 220.1877133105802};
            Mat s0 = new Mat();
            Mat out = new Mat();
            while(!Thread.interrupted()) {
                sink.grabFrame(s0);
                //Pipeline begins here:
                Theta.vidProc(s0, hue, sat, lum);
                outStream.putFrame(out);
                server.setSource(outStream);
            }
        }).start();
		
	}

	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		System.out.println("Auto selected: " + autoSelected);
	}

	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			break;
		case defaultAuto:
		default:
			break;
		}
	}

	@Override
	public void teleopPeriodic() {
	}

	@Override
	public void testPeriodic() {
	}
}

