package org.usfirst.frc.team3539.robot.subsystems;

import org.usfirst.frc.team3539.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends BulldogSystem
{
	private CANTalon shooterOneMotor;
	private CANTalon shooterTwoMotor;
	private CANTalon shooterHoodMotor;
	private CANTalon agitatorMotor;

	//private Encoder servoEncoder;

	public Shooter()
	{
		super("Shooter");

		shooterOneMotor = new CANTalon(RobotMap.shooterOneMotorTalon);
		shooterTwoMotor = new CANTalon(RobotMap.shooterTwoMotorTalon);

		shooterHoodMotor = new CANTalon(RobotMap.shooterServoTalon);

		agitatorMotor = new CANTalon(RobotMap.agitatorTalon);

		shooterHoodMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);

		shooterOneMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);

		shooterTwoMotor.changeControlMode(TalonControlMode.Follower);
		shooterTwoMotor.set(RobotMap.shooterOneMotorTalon);

		//shooterHoodMotor.setForwardSoftLimit(/*put something in here*/);
		//shooterHoodMotor.enableForwardSoftLimit(true);
		//shooterHoodMotor.setReverseSoftLimit(/*put something in here*/);
		//shooterHoodMotor.enableReverseSoftLimit(true);

		shooterOneMotor.setEncPosition(0);
	}

	public void setMotorPower(double power)
	{
		shooterOneMotor.set(power);
		shooterTwoMotor.set(power);
	}

	@Override
	@SuppressWarnings("deprecation")
	public void Update()
	{
		SmartDashboard.putDouble("Shooter RPM", shooterTwoMotor.getPulseWidthVelocity());
		SmartDashboard.putDouble("Shooter Encoder", shooterHoodMotor.getPulseWidthPosition());
	}

	@Override
	@SuppressWarnings("deprecation")
	public void SmartInit()
	{
		SmartDashboard.putNumber("Shooter Speed", (RobotMap.shootSpeed * -1));

		RobotMap.shootSpeed = SmartDashboard.getNumber("Shooter Speed");
	}

	public void setHoodAngle(double angle)
	{
		shooterHoodMotor.set(angle); //Sets "outputValue", Might be wrong method
	}

	public void setAgitatorMotorPower(double power)
	{
		agitatorMotor.set(power);
	}

	public void initDefaultCommand()
	{
	}
}