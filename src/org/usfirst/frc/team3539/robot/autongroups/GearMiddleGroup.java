package org.usfirst.frc.team3539.robot.autongroups;

import org.usfirst.frc.team3539.robot.autoncommands.AutoWait;
import org.usfirst.frc.team3539.robot.autoncommands.AutonDrive;
import org.usfirst.frc.team3539.robot.autoncommands.AutonDriveGentle;
import org.usfirst.frc.team3539.robot.autoncommands.AutonGearClose;
import org.usfirst.frc.team3539.robot.autoncommands.AutonGearOpen;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearMiddleGroup extends CommandGroup
{
	public GearMiddleGroup()
	{

		addSequential(new AutonDrive(68, 1)); // 85

		addSequential(new AutonDriveGentle(20, .5));

		addSequential(new AutonGearOpen());

		addSequential(new AutoWait(1));
		
		addSequential(new AutonDrive(-20 ,1));

		addSequential(new AutonGearClose());
	}
}