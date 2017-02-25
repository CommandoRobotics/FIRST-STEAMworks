package APIs;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import VisualTracking.ReflectiveTapePipeline;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class Visuals {
	//Camera dimensions
	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private static final int ADJUSTED_IMG_WIDTH = IMG_WIDTH / 4;
	private static final int ADJUSTED_IMG_HEIGHT = IMG_HEIGHT / 4;
	
	private UsbCamera camera;
	
	//These rotate the camera
	private double xCenter;
	private double yCenter;
	private Servo panServo;
	private Servo tiltServo;
	
	//Tracking Variables
	private VisionThread visionThread;
	private final Object imgLock = new Object();
	
	//If the camera hasn't needed to move for a while, it is stable
	private static final int RESET_STABILIZATION = 40;
	private int stabilizationCount;
	
	public Visuals(int panPort, int tiltPort) {
		
		panServo = new Servo(panPort);
		tiltServo = new Servo(tiltPort);
		
//		//Set position early to keep the camera from setting to 0 the first time it senses a light.
		panServo.setPosition(0.5); tiltServo.setPosition(0.5);
		
		xCenter = 0.0; yCenter = 0.0;
		
	    camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(IMG_WIDTH, IMG_HEIGHT);	
		
		stabilizationCount = RESET_STABILIZATION;
		
		startThread();
	}
	
	public void startThread() {
		visionThread = new VisionThread(camera, new ReflectiveTapePipeline(), pipeline -> {
			if(!pipeline.filterContoursOutput().isEmpty()) {
				//TODO: Cycle through all of the contours and focus on the largest one.
				Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
				for(int i = 1; i < pipeline.filterContoursOutput().size(); i++) {
					Rect test = Imgproc.boundingRect(pipeline.filterContoursOutput().get(i));
					if(test.area() > r.area()) r = test;
				}
				synchronized(imgLock) {
					xCenter = r.x;
					yCenter = r.y;
				}
			} else {
				xCenter = Double.NaN;
				yCenter = Double.NaN;
			}
		});
		
		visionThread.start();
	}
	
	public UsbCamera getCamera() {
		return camera;
	}
	
	public void setXCenter(double xCenter) {
		this.xCenter = xCenter;
	}
	
	public double getXCenter() {
		return xCenter;
	}
	
	public void setYCenter(double yCenter) {
		this.yCenter = yCenter;
	}
	
	public double getYCenter() {
		return yCenter;
	}
	
	public void trackContours() {
		/*Every time we track countours, we decrement our stabilization count.
		 * If it reaches zero, then the camera hasn't had to move in 40 iterations,
		 * so we know the camera is stable.
		 */
		decrementStabilizationCount();
		
		//Rotate the camera towards our green light.
		if(Double.isNaN(xCenter)) {
			//do nothing
		} else if(xCenter > (ADJUSTED_IMG_WIDTH / 2) + 3 && panServo.getPosition() != 1) {
			//Move up
			 double pos = panServo.getPosition() + .003;
			 if(pos > 1) pos = 1;
			 panServo.setPosition(pos);
			 resetStabilization();
		} else if(xCenter < (ADJUSTED_IMG_WIDTH / 2) - 3 && panServo.getPosition() != 0) {
			//Move down
			 double pos = panServo.getPosition() - .003;
			 if(pos < 0) pos = 0;
			 panServo.setPosition(pos);
			 resetStabilization();
		} else {
			//do nothing
		}
		
		if(Double.isNaN(yCenter)) {
			//do nothing
		} else if(yCenter > (ADJUSTED_IMG_HEIGHT / 2) + 3 && tiltServo.getPosition() != 1) {
			//Move up
			 double pos = tiltServo.getPosition() + .003;
			 if(pos > 1) pos = 1;
			 tiltServo.setPosition(pos);
			 resetStabilization();
		} else if(yCenter < (ADJUSTED_IMG_HEIGHT / 2) - 3 && tiltServo.getPosition() != 0) {
			//Move down
			 double pos = tiltServo.getPosition() - .003;
			 if(pos < 0) pos = 0;
			 tiltServo.setPosition(pos);
			 resetStabilization();
		} else {
			//do nothing
		}
	}
	
	public void decrementStabilizationCount() {
		stabilizationCount--;
		if(stabilizationCount < 0) stabilizationCount = 0;
	}
	
	public void resetStabilization() {
		stabilizationCount = RESET_STABILIZATION;
	}
	
	public void angleCamera(double pan, double tilt){
		panServo.setAngle(pan);
		tiltServo.setAngle(tilt);
	}
	
	public boolean getCameraStable() {
		return stabilizationCount == 0;
	}
}
