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
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
  private boolean shooting = false;

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

    // PID coefficients
    kP = 6e-5; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000015; 
    kMaxOutput = 1; 
    kMinOutput = -1;
    maxRPM = 1000;
    // default was 5700;

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

  public void spinFlywheelSpeed (double maxRPM) {
    double setPoint = maxRPM;
      m_pidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
      
      SmartDashboard.putNumber("SetPoint", setPoint);
      
  }

  public void stopAll() {
    m_flywheelPow = 0;
    m_indexerPow = 0;
  }

  public double getVelocity() {
    return m_flywheelEncoder.getVelocity();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Flywheel RPM", m_flywheelEncoder.getVelocity());

    m_flywheel.set(m_flywheelPow);
    m_indexer.set(m_indexerPow);

  }
}
