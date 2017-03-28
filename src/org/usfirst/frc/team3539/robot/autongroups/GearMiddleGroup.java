package org.usfirst.frc.team3539.robot.autongroups;

import org.usfirst.frc.team3539.robot.RobotMap;
import org.usfirst.frc.team3539.robot.autoncommands.AutoWait;
import org.usfirst.frc.team3539.robot.autoncommands.AutonDrive;
import org.usfirst.frc.team3539.robot.autoncommands.AutonGearClose;
import org.usfirst.frc.team3539.robot.autoncommands.AutonGearOpen;
import org.usfirst.frc.team3539.robot.autoncommands.HoodReset;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearMiddleGroup extends CommandGroup
{
	public GearMiddleGroup()
	{	
		addParallel(new HoodReset(3));
		
		addSequential(new AutonDrive(RobotMap.whiteLineDistance, 3));

//		addSequential(new AutoWait(.5));

		addSequential(new AutonGearOpen());

	//	addSequential(new AutoWait(.5));

		addSequential(new AutonDrive(-40, 3));

		addSequential(new AutonGearClose());
	}
}
