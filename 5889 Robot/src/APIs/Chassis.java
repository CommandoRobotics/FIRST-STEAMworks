package APIs;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Spark;

public class Chassis {
	Spark leftFront;
	Spark rightFront;
	Spark leftRear;
	Spark rightRear;
		
	private static final double kVoltsPerDegreePerSecond = 0.0128;
	private static final double deadSpace = 0.5;
	
	private Gyroscope gyro;
	
	private double adjustment;
	
	double scale;
	boolean fast;
	boolean reverse;
	
	double leftY;
	double rightY;
	
	public Chassis(int LFPort, int RFPort, int LRPort, int RRPort, int kGyroPort) {
		leftFront = new Spark(LFPort);
		rightFront = new Spark(RFPort);
		leftRear = new Spark(LRPort);
		rightRear = new Spark(RRPort);
		
		gyro = new Gyroscope();
		gyro.reset();
		
		fast = true;
		scale = .9;
		adjustment = 0;
	}
	
	public void drive(double leftY, double rightY) {
		rightY *= -1; //One side of our chassis drives forward and the other backwards with the same value passed
		
		if(fast) scale = 1;
		else scale = 0.3;
		
		//This is to reverse while start button is down
		if(reverse){
		//	scale *= -1;
			double temp = rightY;
			rightY = leftY;
			leftY = temp;
		}
		
		leftY *= scale; rightY *= scale;
		
		leftFront.set(leftY); leftRear.set(leftY);
		rightFront.set(rightY); rightRear.set(rightY);
		
		this.leftY = leftY; this.rightY = rightY * - 1; /*
		We multiply rightY by -1 in order to get the original passed value for simplification of future reference
		*/
	}
	
	public Gyroscope getGyroscope() {
		return gyro;
	}
	
	public void resetGyro() {
		gyro.reset();
	}
	
	public void resetAdjustment() {
		adjustment = 0;
	}
	
	public void update() {
		double angle = gyro.getAngle();
		while(angle < 0) {
			angle += 360;
		}
		
		angle %= 360;
		
		//Negligible angles
		if(-deadSpace < angle && angle < deadSpace) {
			drive(leftY, rightY);
			resetAdjustment();
			return;
		}
		
		if(0 < angle && angle <= 180) {
			adjustment += 0.005;
			drive(leftY - adjustment, rightY);
		} else {
			adjustment += 0.005;
			drive(leftY, rightY - adjustment);
		}
		
	}
	
	public boolean seekRotation(double angle, double power) {
		while(angle < 0) {
			angle += 360;
		}
		
		angle %= 360;
				
		double robotAngle = gyro.getAngle();
		
		while(robotAngle < 0) {
			angle += 360;
		}
		
		robotAngle %= 360;
		
		power = Math.abs(power);
		
		if(0 < angle && angle < 180) drive(power, -power);
		else drive(-power, power);
		
		return (angle - 0.5 < robotAngle && robotAngle < angle + 0.5);
	}
	
	public void setFast(boolean fast) {
		this.fast = fast;
	}
	public void setReverse(boolean reverse){
		this.reverse = reverse;
	}
}
