package APIs;

import edu.wpi.first.wpilibj.Spark;

public class Shooter {
	Spark lowWheel;
	Spark highWheel;
	
	public Shooter(int lowWheelPort, int highWheelPort) {
		lowWheel = new Spark(lowWheelPort);
		highWheel = new Spark(highWheelPort);
	}
	
	public void activate(double power) {
		//Clamp the values to the range from -1 to 1
		if(power < -1) power = -1;
		else if(power > 1) power = 1;
		
		lowWheel.setSpeed(power);
		highWheel.setSpeed(-power);
	}
	
	public void activate(double lowWheelPower, double highWheelPower) {
		//Clamp the values to the range from -1 to 1
		if(lowWheelPower < -1) lowWheelPower = -1;
		else if(lowWheelPower > 1) lowWheelPower = 1;
		
		if(highWheelPower < -1) highWheelPower = -1;
		else if(highWheelPower > 1) highWheelPower = 1;
		
		lowWheel.setSpeed(lowWheelPower);
		highWheel.setSpeed(highWheelPower);
	}
	
	public void deactivate() {
		lowWheel.setSpeed(0);
		highWheel.setSpeed(0);
	}
}
