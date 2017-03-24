package AutnomousPrograms;

import APIs.Chassis;
import APIs.GearHand4000;
import AutonomousCommands.Delay;
import AutonomousCommands.DriveForward;
import AutonomousCommands.ThrustGearHand;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddlePeg extends CommandGroup {

	public ScoreMiddlePeg(Chassis chassis, GearHand4000 gearHand, Timer timer) {
		addSequential(new ThrustGearHand(gearHand));
		addSequential(new DriveForward(chassis, timer, 1.75));
		addSequential(new Delay(chassis, timer, 5));
//		addSequential(new DriveBackwards(chassis, timer, 9.25));
//		addSequential(new TurnLeftTime(chassis, timer, 10.25));
//		addSequential(new DriveForward(chassis, timer, 11.45));
//		addSequential(new TurnRightTime(chassis, timer, 12.45));
//		addSequential(new DriveForward(chassis, timer, 14.00));
		chassis.drive(0, 0, false);
	}
	
}
