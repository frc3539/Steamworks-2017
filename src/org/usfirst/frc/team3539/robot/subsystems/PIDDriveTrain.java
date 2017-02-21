package org.usfirst.frc.team3539.robot.subsystems;

import org.usfirst.frc.team3539.robot.Robot;
import org.usfirst.frc.team3539.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
*@author Kenny T.
*/
@SuppressWarnings("unused")
public class PIDDriveTrain extends PIDSubsystem
{
	private CANTalon lfMotor, lbMotor, rfMotor, rbMotor;

	private CANTalon driveCan;

	private RobotDrive drive;
	private DoubleSolenoid manipulatorSol;
	private boolean manipulatorStatus;
	private ADXRS450_Gyro gyro;
	private int persistentTick, eGyro;
	private double distanceTraveled;

	public PIDDriveTrain()
	{
		super(0, 0, 0);
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);

		lfMotor = new CANTalon(RobotMap.lfMotorTalon);
		lbMotor = new CANTalon(RobotMap.lbMotorTalon);
		rfMotor = new CANTalon(RobotMap.rfMotorTalon);
		rbMotor = new CANTalon(RobotMap.rbMotorTalon);

		lfMotor.changeControlMode(TalonControlMode.Position);
		rfMotor.changeControlMode(TalonControlMode.Position);

		lfMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		rfMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);

		lbMotor.changeControlMode(TalonControlMode.Follower);
		rbMotor.changeControlMode(TalonControlMode.Follower);
		lbMotor.set(RobotMap.lfMotorTalon);
		rbMotor.set(RobotMap.rfMotorTalon);

		drive = new RobotDrive(lfMotor, lbMotor, rfMotor, rbMotor);
		drive.setSafetyEnabled(false);

		lfMotor.setSafetyEnabled(false);
		rfMotor.setSafetyEnabled(false);
		lbMotor.setSafetyEnabled(false);
		rbMotor.setSafetyEnabled(false);

		driveCan = new CANTalon(RobotMap.pcm);

		manipulatorSol = new DoubleSolenoid(RobotMap.pcm, RobotMap.driveSolOn, RobotMap.driveSolOff);

		manipulatorStatus = false;

		manipulatorSol.set(DoubleSolenoid.Value.kOff);

		//lfMotor.setPID(RobotMap.drivePea, RobotMap.driveEye, RobotMap.driveDee, RobotMap.driveEff, RobotMap.driveEyeZone,
		//		RobotMap.driveLoopRamp, RobotMap.driveProfile);
		//rfMotor.setPID(RobotMap.drivePea, RobotMap.driveEye, RobotMap.driveDee, RobotMap.driveEff, RobotMap.driveEyeZone,
		//		RobotMap.driveLoopRamp, RobotMap.driveProfile);

		//lfMotor.enableControl();
		//rfMotor.enableControl();

		lfMotor.setEncPosition(0);
		rfMotor.setEncPosition(0);

		//lfMotor.setAllowableClosedLoopErr(RobotMap.driveLoopError);
		//rfMotor.setAllowableClosedLoopErr(RobotMap.driveLoopError);

		//lfMotor.setPIDSourceType(PIDSourceType.kDisplacement);
		//rfMotor.setPIDSourceType(PIDSourceType.kDisplacement);

		persistentTick = 0;
		distanceTraveled = 0;
		eGyro = 0;
	}

	public void driveXTicks(double ticks)
	{
		persistentTick += ticks;
		lfMotor.set(ticks);
		//rfMotor.set(-ticks);
		System.out.println("ticks = " + ticks);
	}

	public void driveLinear(double speed)
	{
		lfMotor.set(speed);
		rfMotor.set(-speed);
	}

	public double getLeftEncoderPosition()
	{
		return lfMotor.getEncPosition();
	}

	public double getRightEncoderPosition()
	{
		return rfMotor.getEncPosition();
	}

	public void zeroItOut()
	{
		lfMotor.setEncPosition(0);
		rfMotor.setEncPosition(0);
	}

	public void disableControl()
	{
		lfMotor.disableControl();
		rfMotor.disableControl();
	}

	public void turnToAngle(double angle)
	{
	}

	public double getTotalDistance()
	{
		distanceTraveled = Math.abs((lfMotor.getEncPosition() + rfMotor.getEncPosition()) / 2);
		return distanceTraveled;
	}

	public void talonControlVBus()
	{
		lfMotor.changeControlMode(TalonControlMode.PercentVbus);
		rfMotor.changeControlMode(TalonControlMode.PercentVbus);
	}

	public void driveArcade(double leftStick, double rightStick)
	{
		if(Robot.oi.invertTrigger.checkValue())
		{
			drive.arcadeDrive(-leftStick, rightStick);
		}
		else
		{
			drive.arcadeDrive(leftStick, rightStick);
		}
	}

	public void changeGears()
	{
		manipulatorStatus = !manipulatorStatus;

		if(manipulatorStatus == true)
		{
			manipulatorSol.set(DoubleSolenoid.Value.kForward);
		}
		if(manipulatorStatus == false)
		{
			manipulatorSol.set(DoubleSolenoid.Value.kReverse);
		}
	}

	public void gyroReset()
	{
		eGyro = 0;
		gyro.reset();
		System.out.println("gyro was reset");
	}

	public double getGyroRelative()
	{
		return gyro.getAngle() % 360;
	}

	public double getGyroAngle()
	{
		return gyro.getAngle();
	}

	public void initDefaultCommand()
	{
	}

	@Override
	protected double returnPIDInput()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output)
	{
		// TODO Auto-generated method stub
		
	}
}