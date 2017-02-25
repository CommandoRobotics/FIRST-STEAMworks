package APIs;

import edu.wpi.first.wpilibj.Spark;

public class Chassis {
	Spark leftFront;
	Spark rightFront;
	Spark leftRear;
	Spark rightRear;
	
	double scale;
	boolean fast;
	
	public Chassis(int LFPort, int RFPort, int LRPort, int RRPort) {
		leftFront = new Spark(LFPort);
		rightFront = new Spark(RFPort);
		leftRear = new Spark(LRPort);
		rightRear = new Spark(RRPort);
		
		fast = true;
		scale = 0.6;
	}
	
	public void drive(double leftY, double rightY) {
		if(Math.abs(leftY) < 0.04) leftY = 0;
		if(Math.abs(rightY) < 0.04) rightY = 0;
		
		leftY *= scale; rightY *= scale;
		
		
		
		leftFront.set(leftY); leftRear.set(leftY);
		rightFront.set(rightY); rightRear.set(rightY);
	}
	
	public void toggleSpeedFactor() {
		if(fast) {
			fast = false;
			scale = 0.3;
		} else {
			fast = true;
			scale = 0.6;
		}
	}
}
