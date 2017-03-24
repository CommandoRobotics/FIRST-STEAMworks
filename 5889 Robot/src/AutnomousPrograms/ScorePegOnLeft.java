package AutnomousPrograms;

import APIs.Chassis;
import APIs.GearHand4000;
import AutonomousCommands.DriveForward;
import AutonomousCommands.ThrustGearHand;
import AutonomousCommands.TurnRightTime;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScorePegOnLeft extends CommandGroup{

	public ScorePegOnLeft(Chassis chassis, GearHand4000 gearHand, Timer timer) {
		
		addSequential(new ThrustGearHand(gearHand));
		addSequential(new DriveForward(chassis, timer, 2.0));
		addSequential(new TurnRightTime(chassis, timer, 3.0));
		addSequential(new DriveForward(chassis, timer, 5.2));

	}
	
}
