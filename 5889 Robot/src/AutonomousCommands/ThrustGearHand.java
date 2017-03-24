package AutonomousCommands;

import APIs.GearHand4000;
import edu.wpi.first.wpilibj.command.Command;

public class ThrustGearHand extends Command {

	private GearHand4000 gearHand;
	
	public ThrustGearHand(GearHand4000 gearHand) {
    	this.gearHand = gearHand;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gearHand.thrustForward();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	gearHand.thrustForward();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       return true;
    }

    // Called once after isFinished returns true
    protected void end() {

    }

    protected void interrupted() {
    	end();
    }

}
