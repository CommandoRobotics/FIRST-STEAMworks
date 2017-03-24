package AutonomousCommands;

import APIs.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class DriveForward extends Command {

	private Chassis chassis;
	private double stopTime;
	private Timer timer;
	
	public DriveForward(Chassis chassis, Timer timer, double stopTime) {
    	this.chassis = chassis;
    	this.timer = timer;
    	this.stopTime = stopTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	chassis.drive(0.51, 0.5, false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//TODO: Insert gyroscope corrections
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return timer.get() >= stopTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    	chassis.drive(0, 0, false);
    }

    protected void interrupted() {
    	end();
    }

}
