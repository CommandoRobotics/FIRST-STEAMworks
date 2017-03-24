package APIs;

import edu.wpi.first.wpilibj.Spark;

public class Chassis {
	Spark leftFront;
	Spark rightFront;
	Spark leftRear;
	Spark rightRear;
		
	private static final double deadSpace = 0.5;
	
	private Gyroscope gyro;
	
	private double adjustment;
	
	double scale;
	boolean fast;
	boolean reverse;
	
	//Power of wheels
	double leftY;
	double rightY;
	
	//Gradual acceleration
	double leftLimit;
	double rightLimit;
	
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
	
	public void drive(double leftY, double rightY, boolean accelerateGradually) {
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
		
		//Only accelerate up to 10% of maximum per frame
		if(accelerateGradually) {
			leftY = scaleLeft(leftY); rightY = scaleRight(rightY);
		}
		
		leftFront.set(leftY); leftRear.set(leftY);
		rightFront.set(rightY); rightRear.set(rightY);
		
		this.leftY = leftY; this.rightY = rightY; /*
		We multiply rightY by -1 in order to get the original passed value for simplification of future reference
		*/
	}
	
	private double scaleLeft(double leftPower) {
		if(leftPower == 0 || leftPower > 0 && leftFront.get() < 0 || leftPower < 0 && leftFront.get() > 0) {
			return 0;
		}
		
		double scaledPower = 0;
		
		if(leftPower > leftFront.get()) {
			if(leftPower - leftFront.get() < 0.1) scaledPower = leftPower;
			else scaledPower = leftFront.get() + 0.1;
		} else if(leftPower < leftFront.get()) {
			if(leftFront.get() - leftPower > -0.1) scaledPower = leftPower;
			else scaledPower = leftFront.get() - 0.1;
		}
		
		if(scaledPower > 1) scaledPower = 1;
		else if(scaledPower < -1) scaledPower = -1;
		
		return scaledPower;
	}
	
	private double scaleRight(double rightPower) {
		if(rightPower == 0 || rightPower > 0 && rightFront.get() < 0 || rightPower < 0 && rightFront.get() > 0) {
			return 0;
		}
		
		double scaledPower = 0;
		
		if(rightPower > rightFront.get()) {
			if(rightPower - rightFront.get() < 0.1) scaledPower = rightPower;
			else scaledPower = rightFront.get() + 0.1;
		} else if(rightPower < rightFront.get()) {
			if(rightFront.get() - rightPower > -0.1) scaledPower = rightPower;
			else scaledPower = rightFront.get() - 0.1;
		}
		
		if(scaledPower > 1) scaledPower = 1;
		else if(scaledPower < -1) scaledPower = -1;
		
		return scaledPower;
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
			drive(leftY, rightY, false);
			resetAdjustment();
			return;
		}
		
		if(0 < angle && angle <= 180) {
			adjustment += 0.005;
			drive(leftY - adjustment, rightY, false);
		} else {
			adjustment += 0.005;
			drive(leftY, rightY - adjustment, false);
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
		
		if(0 < angle && angle < 180) drive(power, -power, false);
		else drive(-power, power, false);
		
		return (angle - 0.5 < robotAngle && robotAngle < angle + 0.5);
	}
	
	public void setFast(boolean fast) {
		this.fast = fast;
	}
	public void setReverse(boolean reverse){
		this.reverse = reverse;
	}
	
	public void leftFront(double power) {
		leftFront.set(power);
	}
	
	public void rightFront(double power) {
		rightFront.set(-power);
	}
	
	public void leftRear(double power) {
		leftRear.set(power);
	}
	
	public void rightRear(double power) {
		rightRear.set(-power);
	}
}
