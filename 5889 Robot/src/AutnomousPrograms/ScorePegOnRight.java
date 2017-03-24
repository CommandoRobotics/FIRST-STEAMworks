package AutnomousPrograms;

import APIs.Chassis;
import APIs.GearHand4000;
import AutonomousCommands.DriveForward;
import AutonomousCommands.ThrustGearHand;
import AutonomousCommands.TurnLeftTime;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScorePegOnRight extends CommandGroup{

	public ScorePegOnRight(Chassis chassis, GearHand4000 gearHand, Timer timer) {
		addSequential(new ThrustGearHand(gearHand));
		addSequential(new DriveForward(chassis, timer, 2.0));
		addSequential(new TurnLeftTime(chassis, timer, 3.0));
		addSequential(new DriveForward(chassis, timer, 5.2));
	}
	
}
