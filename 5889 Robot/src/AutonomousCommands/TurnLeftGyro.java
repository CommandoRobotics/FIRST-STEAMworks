package AutonomousCommands;

import APIs.Chassis;
import edu.wpi.first.wpilibj.command.Command;

public class TurnLeftGyro extends Command {

	private Chassis chassis;
	private double rotation;

	public TurnLeftGyro(Chassis chassis, double rotation) {
    	this.chassis = chassis;
    	this.rotation = rotation;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	chassis.drive(0.5, -0.5, false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//TODO: Insert gyroscope corrections
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return chassis.seekRotation(360 - Math.abs(rotation), 0.4);
    }

    // Called once after isFinished returns true
    protected void end() {
    	chassis.drive(0, 0, false);
    }

    protected void interrupted() {
    	end();
    }

}
