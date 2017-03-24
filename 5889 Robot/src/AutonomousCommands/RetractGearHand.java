package AutonomousCommands;

import APIs.GearHand4000;
import edu.wpi.first.wpilibj.command.Command;

public class RetractGearHand extends Command {

	private GearHand4000 gearHand;
	
	public RetractGearHand(GearHand4000 gearHand) {
    	this.gearHand = gearHand;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	gearHand.thrustBackward();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

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
