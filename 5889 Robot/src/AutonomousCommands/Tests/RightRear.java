package AutonomousCommands.Tests;

import APIs.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

public class RightRear extends Command {

	private Chassis chassis;
	private double stopTime;
	private Timer timer;
	
	public RightRear(Chassis chassis, Timer timer, double stopTime) {
    	this.chassis = chassis;
    	this.timer = timer;
    	this.stopTime = stopTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	chassis.rightRear(0.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
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
