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

import edu.wpi.first.wpilibj.DigitalInput;

public class ShooterSubsystem extends SubsystemBase {
  CANSparkMax m_indexer;
  MotorControllerGroup m_flywheel;

  private double m_flywheelPow = 0;
  private double m_indexerPow = 0; 
  private SparkMaxPIDController m_pidController;
  private RelativeEncoder m_flywheelEncoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, velocityTarget;
  
private DigitalInput m_track_limit_switch;
  private boolean m_shootStatus = true;

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem(
    int indexMotor,
    int flywheelMotor, int flywheelMotor2,
    DigitalInput track_limit_switch
  ) {
    m_indexer = new CANSparkMax(indexMotor, MotorType.kBrushed);
    m_indexer.setIdleMode(IdleMode.kBrake);

    m_indexer.setInverted(true); 	

    CANSparkMax flywheel1 = new CANSparkMax(flywheelMotor, MotorType.kBrushless);
    CANSparkMax flywheel2 = new CANSparkMax(flywheelMotor2, MotorType.kBrushless);
    m_flywheel = new MotorControllerGroup(flywheel1, flywheel2);

    m_flywheelEncoder = flywheel1.getEncoder();
    m_pidController = flywheel1.getPIDController();

    //Found this brief explanation here -> https://www.chiefdelphi.com/t/spark-max-w-neos-pid-not-getting-to-setpoint/381728/2
  
    // PID coefficients
    kP = 0.0001;
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = 0.000187;
    kMaxOutput = 1; 
    kMinOutput = -1;
    velocityTarget = 4100;
  
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

    SmartDashboard.putNumber("Flywheel Target RPM", velocityTarget);
    m_track_limit_switch = track_limit_switch;
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

  public void spinFlywheelSpeed () {
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);
    double setpoint = SmartDashboard.getNumber("Flywheel Target RPM", 0);
    
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

    m_pidController.setReference(setpoint, CANSparkMax.ControlType.kVelocity);
    //m_flywheelPow = 0.70;
  }

  public void stopAll() {
    m_pidController.setReference(0, CANSparkMax.ControlType.kVoltage);
    m_indexerPow = 0;
  }

  public double getVelocity() {
    return m_flywheelEncoder.getVelocity();
  }
  
  public void setShootStatus(boolean shootStatus) {
    m_shootStatus = shootStatus;
  }

  public boolean getShootStatus() {
    return m_shootStatus;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Flywheel RPM", m_flywheelEncoder.getVelocity());

    //m_flywheel.set(m_flywheelPow);
    m_indexer.set(m_indexerPow);

    // TO DO Find proper speed for getting ball away from flywheel
    if(!m_shootStatus && !m_track_limit_switch.get()) {
      m_indexer.set(.2);
    } else {
      m_indexer.set(m_indexerPow);
    }
    //m_flywheel.set(m_flywheelPow);
  }
}
