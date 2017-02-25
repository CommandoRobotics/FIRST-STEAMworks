package APIs;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;

public class GearHand4000 {
	Solenoid solenoidLeft;
	Solenoid solenoidRight;
	Compressor compressor;
	
	Servo leftTopServo;
	Servo rightTopServo;
	Servo leftBottomServo;
	Servo rightBottomServo;
	
	boolean pressureSwitch;
	public GearHand4000(int solenoidLeftPort, int solenoidRightPort/*, int leftTop, int rightTop, int leftBottom, int rightBottom*/) {
		solenoidLeft = new Solenoid(solenoidLeftPort);
		solenoidRight = new Solenoid(solenoidRightPort);
		compressor = new Compressor(0);
		
		/*
		leftTopServo = new Servo(leftTop);
		rightTopServo = new Servo(rightTop);
		leftBottomServo = new Servo(leftBottom);
		rightBottomServo = new Servo(rightBottom);
		*/
	}
	
	public void activate() {
		compressor.start();
	}
	
	public void deactivate() {
		compressor.stop();
	}
	
	public void update() {
		pressureSwitch = compressor.getPressureSwitchValue();
		if(pressureSwitch) deactivate();
		else activate();
		
		System.out.println("Pressure Switch: " + pressureSwitch);
	}
	

	public void toggleState() {
		
	}
	
	public void thrustLeft() {
		solenoidLeft.set(true);
		solenoidRight.set(false);
	}
	
	public void thrustRight() {
		solenoidLeft.set(false);
		solenoidRight.set(true);
	}
	
	public void toggleThrust() {
		
	}
	
	public void lock() {
		solenoidLeft.set(false);
		solenoidRight.set(false);
	}
	
	public void open(){
		leftTopServo.setAngle(180);
		rightTopServo.setAngle(0);
		leftBottomServo.setAngle(180);
		rightBottomServo.setAngle(0);
	}
	
	public void close(){
		leftTopServo.setAngle(0);
		rightTopServo.setAngle(180);
		leftBottomServo.setAngle(0);
		rightBottomServo.setAngle(180);
	}
	
	
}
