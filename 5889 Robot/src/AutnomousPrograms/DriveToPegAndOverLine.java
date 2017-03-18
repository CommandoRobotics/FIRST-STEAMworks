package AutnomousPrograms;

import org.usfirst.frc.team5889.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveToPegAndOverLine extends Command{

	private boolean finished;
	
	public DriveToPegAndOverLine(Robot robot) {
		finished = false;
	}
	
	protected void execute() {
		
	}
	
	protected boolean isFinished() {
		return finished;
	}
	
	protected void end() {
		finished = true;
	}
	
	protected void interrupted() {
		end();
	}
}
