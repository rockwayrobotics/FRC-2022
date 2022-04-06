// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  CANSparkMax m_indexer;
  MotorControllerGroup m_flywheel;

  private double m_flywheelPow = 0;
  private double m_indexerPow = 0; 
  private SparkMaxPIDController m_pidController;
  private RelativeEncoder m_flywheelEncoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, velocityTarget;
  
  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem(
    int indexMotor,
    int flywheelMotor, int flywheelMotor2
  ) {
    m_indexer = new CANSparkMax(indexMotor, MotorType.kBrushed);
    m_indexer.setIdleMode(IdleMode.kBrake);

    m_indexer.setInverted(true); 	

    CANSparkMax flywheel1 = new CANSparkMax(flywheelMotor, MotorType.kBrushless);
    CANSparkMax flywheel2 = new CANSparkMax(flywheelMotor2, MotorType.kBrushless);
    m_flywheel = new MotorControllerGroup(flywheel1, flywheel2);

    m_flywheelEncoder = flywheel1.getEncoder();
    m_pidController = flywheel1.getPIDController();

    /*
    Your PID constants are too small. An F of 0.000015 * 5700 RPM = 0.0855 motor output. A P of 0.00006 * (5700-1800) = .234 motor output for a total output of .32. That’s reasonable for 1800 RPM.
    Since you know that at 1 output the RPM is 5700, set F to 1/5700. Then you can tune P from there.
    A PID with I will get there eventually, but with just P,D, and F it won’t get there if the PID constants are too low.
    I also wouldn’t run two separate PIDs for each motor. If one of them misses encoder pulses the motors could start fighting each other.
    I would set one as a follower.
    */

    // PID coefficients
    //kP = 6e-5; 
    kP = 0.00006;
    kI = 0;
    kD = 0; 
    kIz = 0; 
    //kFF = 0.000015; 
    kFF = 1/5700;
    kMaxOutput = 1; 
    kMinOutput = -1;
    velocityTarget = 40000;

    // set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);
    SmartDashboard.putNumber("Max Output", kMaxOutput);
    SmartDashboard.putNumber("Min Output", kMinOutput);

    SmartDashboard.putNumber("Desired Flywheel Velocity", velocityTarget);
  }

  /**
   * Spins the shooter wheel at a specified power level.
   * @param shootPow Speed to spin the wheel. -1 is full backwards, 1 is full forwards.
   */
  public void spinFlywheel(double shootPow) {
    m_flywheelPow = shootPow;
  }

  /**
   * Spins the index wheel at a specified power level.
   * @param indexPow Speed to spin the wheel. -1 is full backwards, 1 is full forwards.
   */
  public void spinIndex(double indexPow) {
    m_indexerPow = indexPow;
  }

  public void spinFlywheelSpeed (double RPMSetPoint) {
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);
    
    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP)) { m_pidController.setP(p); kP = p; }
    if((i != kI)) { m_pidController.setI(i); kI = i; }
    if((d != kD)) { m_pidController.setD(d); kD = d; }
    if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
    if((max != kMaxOutput) || (min != kMinOutput)) { 
      m_pidController.setOutputRange(min, max); 
      kMinOutput = min; kMaxOutput = max; 
    }

    m_pidController.setReference(RPMSetPoint, CANSparkMax.ControlType.kVelocity);
    //m_flywheelPow = 0.70;

    SmartDashboard.putNumber("SetPoint", RPMSetPoint);
  }

  public void stopAll() {
    m_pidController.setReference(0, CANSparkMax.ControlType.kVoltage);
    m_indexerPow = 0;
  }

  public double getVelocity() {
    return m_flywheelEncoder.getVelocity();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Flywheel RPM", m_flywheelEncoder.getVelocity());

    //m_flywheel.set(m_flywheelPow);
    m_indexer.set(m_indexerPow);
  }
}
