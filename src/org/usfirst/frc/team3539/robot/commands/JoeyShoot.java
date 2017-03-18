package org.usfirst.frc.team3539.robot.commands;

import org.usfirst.frc.team3539.robot.Robot;
import org.usfirst.frc.team3539.robot.RobotMap;
import org.usfirst.frc.team3539.robot.autoncommands.AutoAim;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class JoeyShoot extends Command
{
    private boolean isTeleop;
    private boolean visionTurn;
    private boolean visionDistance;
    private double hoodAngle;
    private double agitatorRpm;
    private double shooterRpm;
    private Button button;
    private int breakoutCounter;
    private double shootTime;

    // Teleop Button Shooting
    public JoeyShoot(boolean visionTurn, Button button, double hoodAngle, double agitatorRpm, double shooterRpm)
    {
        super("JoeyShoot");
        requires(Robot.shooter);
        requires(Robot.hoodSubsystem);

        this.isTeleop = true;
        this.visionTurn = visionTurn;
        this.button = button;
        this.hoodAngle = hoodAngle;
        this.agitatorRpm = agitatorRpm;
        this.shooterRpm = shooterRpm;

        // this.hoodAngle = RobotMap.hoodTarget; // for Tuning
        // this.agitatorRpm = RobotMap.agitatorRpm;
        // this.shooterRpm = RobotMap.shooterRpm;
    }

    // superman button
    public JoeyShoot(Button superman)
    {
        super("JoeyShoot");
        requires(Robot.shooter);
        requires(Robot.hoodSubsystem);

        System.out.println("Supa man!");
        
        this.hoodAngle = 0;
        this.shooterRpm = 0;
        this.agitatorRpm = 250;
        this.visionDistance = true;
        this.isTeleop = true;
        this.visionTurn = true;
        this.button = superman;
    }

    // Auton Distance + Turn vision shooting
    public JoeyShoot(double seconds)
    {
        super("JoeyShoot");
        requires(Robot.shooter);
        requires(Robot.hoodSubsystem);
        
        System.out.println("Supa man!");

        this.hoodAngle = 0;
        this.shooterRpm = 0;
        this.agitatorRpm = 150;
        this.isTeleop = false;
        this.visionTurn = true;
        this.visionDistance = true;
        this.shootTime = seconds;

    }

    // auton Shooting
    public JoeyShoot(boolean visionTurn, double hoodAngle, double agitatorRpm, double shooterRpm, double seconds)
    {
        super("JoeyShoot");
        requires(Robot.shooter);
        requires(Robot.hoodSubsystem);

        this.isTeleop = false;
        this.visionTurn = visionTurn;

        this.hoodAngle = hoodAngle;
        this.agitatorRpm = agitatorRpm;
        this.shooterRpm = shooterRpm;
        this.shootTime = seconds;
    }

    protected void initialize()
    {
        Robot.raspberry.UpdateCamera(1);
        Robot.shooter.resetShooterPID();
        Robot.shooter.resetAgitatorPID();

        Robot.shooter.setShooterPID();
        Robot.shooter.setAgitatorPID();
        
        if (visionTurn)
        {
        	Scheduler.getInstance().add(new AutoAim());
        }
        
        if(visionDistance)
        {
            this.hoodAngle = Robot.raspberry.getneededHoodAngle();
            this.shooterRpm = Robot.raspberry.getneededShooterRPM();
        }

        // this.hoodAngle = RobotMap.hoodTarget; // for Tuning
        // this.agitatorRpm = RobotMap.agitatorRpm;
        // this.shooterRpm = RobotMap.shooterRpm;
    }

    protected void execute()
    {
        Robot.hoodSubsystem.setAngle(hoodAngle);

        if(visionDistance)
        {
            this.hoodAngle = Robot.raspberry.getneededHoodAngle();
            this.shooterRpm = Robot.raspberry.getneededShooterRPM();
        }
        
        Robot.shooter.startShooter(shooterRpm);

        if (RobotMap.triggerModified)
        {
            Robot.shooter.startAgitator(agitatorRpm);
        }
        else if (Robot.shooter.getShooterRPM() <= shooterRpm * .9)
        {
            Robot.shooter.startAgitator(-agitatorRpm);
        }
        
        breakoutCounter ++;
    }

    protected boolean isFinished()
    {
        if (isTeleop)
        {
            return !button.get();
        }
        else if (breakoutCounter * 20 > shootTime * 1000)
            return true;
        else
            return false;
    }

    protected void end()
    {
        Robot.hoodSubsystem.disableHoodPid();
        Robot.shooter.resetShooterPID();
        Robot.shooter.resetAgitatorPID();
    }

    protected void interrupted()
    {
    }
}
