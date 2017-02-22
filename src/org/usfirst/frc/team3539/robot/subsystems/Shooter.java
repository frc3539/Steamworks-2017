package org.usfirst.frc.team3539.robot.subsystems;

import org.usfirst.frc.team3539.robot.Robot;
import org.usfirst.frc.team3539.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter extends BulldogSystem
{
	private CANTalon shooterOneMotor;
	private CANTalon shooterTwoMotor;
	private CANTalon shooterHoodMotor;
	private CANTalon agitatorMotor;

	// private Encoder servoEncoder;

	public static void init(DigitalInput lightSensor_in)
	{
		RobotMap.light = lightSensor_in;
	}

	public Shooter()
	{
		super("Shooter");

		shooterOneMotor = new CANTalon(RobotMap.shooterOneMotorTalon);
		shooterTwoMotor = new CANTalon(RobotMap.shooterTwoMotorTalon);

		shooterHoodMotor = new CANTalon(RobotMap.shooterServoTalon);

		agitatorMotor = new CANTalon(RobotMap.agitatorTalon);

		shooterHoodMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
		
		shooterTwoMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);

/*		shooterHoodMotor.setForwardSoftLimit(94);
		shooterHoodMotor.enableForwardSoftLimit(false); 	 // Needs To Be Fixed
		shooterHoodMotor.setReverseSoftLimit(944);
		shooterHoodMotor.enableReverseSoftLimit(false);
*/
		shooterOneMotor.setEncPosition(0);
	}

	public void setMotorPower(double power)
	{
		shooterOneMotor.set(power);
		shooterTwoMotor.set(power);
	}
	public double GetPosition()
	{
		return shooterHoodMotor.getEncPosition();
	}
	@Override
	@SuppressWarnings("deprecation")
	public void Update()
	{
		SmartDashboard.putBoolean("Lightt", RobotMap.lightt.get());
		SmartDashboard.putBoolean("Light", RobotMap.light.get());
		SmartDashboard.putDouble("Shooter RPM", shooterTwoMotor.getPulseWidthVelocity());
		SmartDashboard.putDouble("Shooter Hood Encoder", shooterHoodMotor.getPulseWidthPosition());
		SmartDashboard.putInt("Ball Count", RobotMap.ballCount);
		SmartDashboard.putDouble("Agitator Speed", RobotMap.agitatorSpeed);
		RobotMap.shootSpeed = SmartDashboard.getNumber("Shooter Speed");
		SmartDashboard.putNumber("Shooter Speed", (RobotMap.shootSpeed * -1));
	}
	
	public void SmartInit()
	{
	}

	public void setHoodAngle(double angle)
	{
		shooterHoodMotor.set(angle*-1);
		System.out.println("Angle" + angle);
	}

	public void setAgitatorMotorPower(double power)
	{
		agitatorMotor.set(power);
	}

	public void initDefaultCommand()
	{
	}
}