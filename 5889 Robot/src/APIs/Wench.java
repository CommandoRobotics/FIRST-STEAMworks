package APIs;

import edu.wpi.first.wpilibj.Talon;

public class Wench {
	
	Talon motor;
	
	public Wench(int motorPort) {
		motor = new Talon(motorPort);
	}
	
	public void pullUp() {
		motor.set(0.95);
	}
	
	public void pullDown() {
		motor.set(-0.95);
	}
	
	public void deactivate() {
		motor.set(0);
	}
	
}
