package org.usfirst.frc.team3539.robot.auton;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Red1 extends CommandGroup {

    public Red1(int type)
    {
    	addSequential(new TurnLeftGearPlaceGroup());
    }
}