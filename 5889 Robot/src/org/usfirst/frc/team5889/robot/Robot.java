package org.usfirst.frc.team5889.robot;

import java.util.List;

import APIs.Chassis;
import APIs.GearHand4000;
import APIs.Shooter;
import APIs.Visuals;
import APIs.Wench;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//Controls
	List<Joystick> sticks;
	Joystick xbox;
	
	public static final int XBOX_LEFT_Y = 1;
	public static final int XBOX_RIGHT_Y = 5;
	
	public static final int XBOX_TOP = 4;
	public static final int XBOX_BOTTOM = 1;
	public static final int XBOX_LEFT = 3;
	public static final int XBOX_RIGHT = 2;
	public static final int XBOX_LEFT_BUMPER = 5;
	public static final int XBOX_RIGHT_BUMPER = 6;
	
	private boolean aPressed;
	
	Timer timer = new Timer();
	
	//APIs
	Chassis chassis;
	Shooter shooter;
//	Visuals visuals;
	GearHand4000 gearHand;
	Wench wench;
	
	//Joystick varables
	double xValue;
	double yValue;
	
	//TODO: If a limit switch is triggered, push the pneumatics out.
	private static final double SHOOTER_PAN = 0;
	private static final double SHOOTER_TILT = 0;
	private static final double GEARHAND_PAN = 180;
	private static final double GEARHAND_TILT = 180;
	
	//TODO: Try to switch driver controls when camera switches
	int cameraTurn = 1;
	
	/**
	 * 
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	public void robotInit() {
		chassis = new Chassis(9, 8, 7, 6);
		shooter = new Shooter(1, 0);
//		visuals = new Visuals(7, 8);
		gearHand = new GearHand4000(0, 1);
		wench = new Wench(4);
		
		xbox = new Joystick(0);
		aPressed = false;
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
//		visuals.trackContours();
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
//		visuals.angleCamera(SHOOTER_PAN, SHOOTER_TILT);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		drive();
		
//		cameraControl();

		shooterControl();
		
		pneumaticControl();
		
		wenchControl();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void drive() {
		chassis.drive(-xbox.getRawAxis(XBOX_LEFT_Y), xbox.getRawAxis(XBOX_RIGHT_Y));
		
		if(xbox.getRawButton(XBOX_BOTTOM) && !aPressed) {
			aPressed = true;
			chassis.toggleSpeedFactor();
		} else {
			aPressed = false;
		}
	}
	
//	public void cameraControl(){
//		
//		if(xbox.getRawButton(XBOX_TOP)){
//			visuals.angleCamera(SHOOTER_PAN, SHOOTER_TILT);
//		} else if(xbox.getRawButton(XBOX_BOTTOM)){
//			visuals.angleCamera(GEARHAND_PAN, GEARHAND_TILT);
//		}
//		
//	}
	
	public void pneumaticControl(){
		gearHand.update();
		
		if(xbox.getRawButton(XBOX_LEFT)) {
			gearHand.thrustLeft();
		} else if(xbox.getRawButton(XBOX_RIGHT)) {
			gearHand.thrustRight();
		} else {
			gearHand.lock();
		}
		
	}
	
	public void shooterControl() {
		if(xbox.getRawButton(XBOX_TOP)) {
			shooter.activate(-0.8);
		} else {
			shooter.deactivate();
		}
	}
	
	public void wenchControl(){
		
		if(xbox.getRawButton(XBOX_LEFT_BUMPER)) {
			wench.pullUp();
		} else if(xbox.getRawButton(XBOX_RIGHT_BUMPER)) {
			wench.pullDown();
		} else {
			wench.deactivate();
		}
		
	}
	
}

