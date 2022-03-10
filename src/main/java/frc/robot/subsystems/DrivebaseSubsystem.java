// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.Drive;

public class DrivebaseSubsystem extends SubsystemBase {
  private final DifferentialDrive m_drive;

  private final Encoder m_leftEncoder;
  private final Encoder m_rightEncoder;

  private double m_y = 0;
  private double m_x = 0;
  private double m_l = 0;
  private double m_r = 0;
  private double m_scale = 1;
  private boolean m_usingLR = false;

  /** Creates a new DrivebaseSubsystem. */
  public DrivebaseSubsystem(
    int leftMotor1, int leftMotor2,
    int rightMotor1, int rightMotor2,
    int leftEncoder1, int leftEncoder2,
    int rightEncoder1, int rightEncoder2
  ) 
  
  /* Create motor controller groups for left and right side of drivebase */
  {
    MotorControllerGroup rightDrive = new MotorControllerGroup(
        new CANSparkMax(rightMotor1, MotorType.kBrushed),
        new CANSparkMax(rightMotor2, MotorType.kBrushed)
    );
    MotorControllerGroup leftDrive = new MotorControllerGroup(
      new CANSparkMax(leftMotor1, MotorType.kBrushed),
      new CANSparkMax(leftMotor2, MotorType.kBrushed)
    );
    // MotorControllerGroup leftDrive = new MotorControllerGroup(
    //   new WPI_VictorSPX(leftMotor1),
    //   new WPI_VictorSPX(leftMotor2)
    // );
    // MotorControllerGroup rightDrive = new MotorControllerGroup(
    //   new WPI_VictorSPX(rightMotor1),
    //   new WPI_VictorSPX(rightMotor2)
    // );

    /* Create new DifferentialDrive from the previously created MotorControllerGroups */
    m_drive = new DifferentialDrive(leftDrive, rightDrive);

    m_leftEncoder = new Encoder(leftEncoder1, leftEncoder2);
    m_rightEncoder = new Encoder(rightEncoder1, rightEncoder2);
    
    // when robot goes forward, left encoder spins positive and right encoder spins negative
    m_leftEncoder.setDistancePerPulse(Drive.DISTANCE_PER_ENCODER_PULSE);
    m_rightEncoder.setDistancePerPulse(-Drive.DISTANCE_PER_ENCODER_PULSE);
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }

  /**
   * Sets the speed of the drivebase.
   * @param y Y speed. -1 is full backwards, 1 is full forwards.
   * @param x X speed. -1 is full left, 1 is full right.
   */
  public void set(double y, double x) {
    m_y = y;
    m_x = x;
    m_usingLR = false;
  }

  /**
   * Sets the left and right side speeds of the drivebase.
   * @param l Speed for the left side wheels. -1 is full backwards, 1 is full forwards.
   * @param r Speed for the right side wheels. -1 is full backwards, 1 is full forwards.
   */
  public void setLR(double l, double r) {
      m_l = l;
      m_r = r;
      m_usingLR = true;
  }

  /**
   * Sets the scale for the drivebase. Speeds are multiplied by the scale before being sent to the motors.
   * @param scale New scale to multiply speed by.
   */
  public void setScale(double scale) {
    m_scale = scale;
  }

  /**
   * Gets the distance travelled by the left-side wheels of the drivebase since last reset.
   * @return Distance, in inches.
   */
  public double getLDistance() {
    return m_leftEncoder.getDistance();
  }
  
  /**
   * Gets the distance travelled by the right-side wheels of the drivebase since last reset.
   * @return Distance in inches.
   */
  public double getRDistance() {
    return m_rightEncoder.getDistance();
  }
  
  /**
   * Gets the speed of the left-side wheels of the drivebase.
   * @return Speed in inches / second.
   */
  public double getLRate() {
    return m_leftEncoder.getRate();
  }
  
  /**
   * Gets the speed of the left-side wheels of the drivebase.
   * @return Speed in inches / second.
   */
  public double getRRate() {
    return m_rightEncoder.getRate();
  }

  /**
   * Gets whether the drivebase is currently stopped.
   * @return true if stopped, false if moving.
   */
  public boolean getStopped() {
    return m_leftEncoder.getStopped() && m_rightEncoder.getStopped();
  }

  /** Resets drivebase encoder distances to 0. */
  public void resetEncoders() {
    m_leftEncoder.reset();
    m_rightEncoder.reset();
  }


  /* Periodic method that runs once every cycle */
  @Override
  public void periodic() {
    if (m_usingLR) {
      m_drive.tankDrive(m_scale * m_l, m_scale * m_r, false);
    } else {
      m_drive.arcadeDrive(m_scale * m_y, m_scale * m_x);
    }
  

    m_x = 0;
    m_y = 0;
    m_l = 0;
    m_r = 0;
  }
}
