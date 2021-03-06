package APIs;

import Libs.ADIS16448_IMU;

public class Gyroscope {
	ADIS16448_IMU gyro;
	
	public Gyroscope() {
		gyro = new ADIS16448_IMU();
	}
	
	public void reset() {
		gyro.reset();
	}
	
	public double getAngle() {
		double angle = gyro.getAngleZ();
		while(angle < 0) {
			angle += 360;
		}
		
		angle %= 360;
		
		return angle;
	}
}
