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
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	/*
	 * TODO: Gyroscope
	 * The back of our robot is the positive y-direction
	 * The left side of our robot is the positive x-direction
	 * Towards the sky is the positive z-direction
	 * */
	
	
	//Controls
	List<Joystick> sticks;
	Joystick joystickL;
	Joystick joystickR;
	Joystick xbox;
	
	public static final int XBOX_LEFT_Y = 1;
	public static final int XBOX_RIGHT_Y = 5;
	
	public static final int XBOX_TOP = 4;
	public static final int XBOX_BOTTOM = 1;
	public static final int XBOX_LEFT = 3;
	public static final int XBOX_RIGHT = 2;
	public static final int XBOX_LEFT_BUMPER = 5;
	public static final int XBOX_RIGHT_BUMPER = 6;
		
	Timer timer = new Timer();
	
	//APIs
	Chassis chassis;
	Shooter shooter;
	Visuals visuals;
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
	
	//Autonomous Programs
	private static final int DRIVE_TO_PEG_AND_BACK_OVER_LINE = 0;
	private static final int SCORE_PEG_ON_LEFT = 1;
	private static final int SCORE_PEG_ON_RIGHT = 2;
	
	int autonomousCommand;
	SendableChooser<Integer> autoChooser;
	
	/**
	 * 
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	public void robotInit() {
		chassis = new Chassis(9, 8, 7, 6, 0);
		shooter = new Shooter(1, 0);
		visuals = new Visuals(2, 3);
		gearHand = new GearHand4000(0, 1);
		wench = new Wench(4);
		
		joystickR = new Joystick(0);
		joystickL = new Joystick(1);
		xbox = new Joystick(2);
//		
		autoChooser = new SendableChooser<Integer>();
		autoChooser.addDefault("Straight for Gear", DRIVE_TO_PEG_AND_BACK_OVER_LINE);
		autoChooser.addObject("Score Peg On Left", SCORE_PEG_ON_LEFT);
		autoChooser.addObject("Score Peg On Right", SCORE_PEG_ON_RIGHT);
		SmartDashboard.putData("Autonomous Mode Selection", autoChooser);
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start(); 
		
		chassis.resetGyro();
		
		autonomousCommand = (int) autoChooser.getSelected();
		if(autonomousCommand == DRIVE_TO_PEG_AND_BACK_OVER_LINE) driveToPegAndBackOverLine();
		else if(autonomousCommand == SCORE_PEG_ON_LEFT) scorePegOnLeft();
		else if(autonomousCommand == SCORE_PEG_ON_RIGHT) scorePegOnRight();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	
	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("Gyro", chassis.getGyroscope().getAngle());
		Scheduler.getInstance().run();
//		visuals.trackContours();
		//driveToPeg();
		//driveToLine();
//		driveLeftScorePeg();
//
//		
		if(timer.get() < 3) {
			//forward(4);
			scorePegOnRight();
		}
		
		chassis.drive(0, 0);
	}
	 
	void forward(double time){
		double startTime = timer.get();
		chassis.resetGyro();
		gearHand.thrustForward();
		
		chassis.drive(0.5, 0.5);
		while(timer.get() - startTime  < time){
//			chassis.update();
		}
		
		chassis.drive(0, 0);
	}
	
	void backwards(double time){
		double startTime = timer.get();
		chassis.resetGyro();
		gearHand.thrustForward();
		
		chassis.drive(-0.5, -0.5);
		while(timer.get() - startTime < time){
//			chassis.update();
		}
		
		chassis.drive(0, 0);
	}
	
	void turnLeft(double rotation) {
		chassis.resetGyro();
		gearHand.thrustForward();
		
		while(!chassis.seekRotation(360 - Math.abs(rotation), 0.4)){
			//continue turning
		}
		
		chassis.drive(0, 0);
	}
	
	void turnRight(double rotation) {
		chassis.resetGyro();
		gearHand.thrustForward();
		
		while(!chassis.seekRotation(Math.abs(rotation), 0.4)){
			//continue turning
		}
		
		chassis.drive(0, 0);
	}
	
	void delay(double delayTime) {
		double startTime = timer.get();
		chassis.drive(0, 0);
		while(timer.get() - startTime < delayTime) {
			//do nothing
		}
	}
	
	void driveToPegAndBackOverLine() {
		forward(3.75);
		delay(1.25);
		while(timer.get() < 9.25){
			chassis.drive(-.5, -.5);
		}
		while(timer.get() < 10.45){
			chassis.drive(.8, -.8);
		}
		while(timer.get() < 11.65){
			chassis.drive(.8, .8);
		}
		while(timer.get() < 12.65){
			chassis.drive(-.8, .8);
		}
		while(timer.get() < 14.2){
			chassis.drive(.8, .8);
		}
		chassis.drive(0, 0);
	}
	
	void scorePegOnLeft(){
		gearHand.thrustForward();
		forward(2.0);
		turnRight(45);
		forward(2.2);
	}
	
	void scorePegOnRight(){
		gearHand.thrustForward();
		forward(2.1);
		turnLeft(45);
		forward(2.2);
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
//		visuals.angleCamera(SHOOTER_PAN, SHOOTER_TILT);
		chassis.drive(0, 0);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		drive();
		
		cameraControl();

		shooterControl();
		
		pneumaticControl();
		
		wenchControl();
		
		//SmartDashboard.putNumber("Gyro", chassis.getGyroscope().getAngle());
		
//		SmartDashboard.putNumber("Gyro", 45.0);
		SmartDashboard.putNumber("Gyro", chassis.getGyroscope().getAngle());
//		DriverStation.reportError("Gyro: " + chassis.getGyroscope().getAngle(), false);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void drive() {
		chassis.drive(-joystickL.getY(), -joystickR.getY());

		
//		if(xbox.getRawButton(8)){
//			chassis.setReverse(true);
//		} else{
//			chassis.setReverse(false);
//		}
	}
	
	public void cameraControl(){
//		
//		if(xbox.getRawButton(XBOX_TOP)){
//			visuals.angleCamera(SHOOTER_PAN, SHOOTER_TILT);
//		} else if(xbox.getRawButton(XBOX_BOTTOM)){
//			visuals.angleCamera(GEARHAND_PAN, GEARHAND_TILT);
//		}
//		
	}
	
	public void pneumaticControl(){
		gearHand.update();
		
		if(xbox.getRawButton(XBOX_LEFT)) {
			gearHand.thrustBackward();
		} else if(xbox.getRawButton(XBOX_RIGHT)) {
			gearHand.thrustForward();
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
		} /*else if(xbox.getRawButton(XBOX_RIGHT_BUMPER)) {
			wench.pullDown();
		}*/ else {
			wench.deactivate();
		}
		
	}
	
}

