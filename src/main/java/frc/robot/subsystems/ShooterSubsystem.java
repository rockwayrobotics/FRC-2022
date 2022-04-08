// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.DigitalInput;

public class ShooterSubsystem extends SubsystemBase {
  CANSparkMax m_indexer;
  MotorControllerGroup m_flywheel;

  private double m_flywheelPow = 0;
  private double m_indexerPow = 0; 

  private DigitalInput m_track_limit_switch;
  private boolean m_shootStatus = false;

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

  public void setShootStatus(boolean shootStatus) {
    m_shootStatus = shootStatus;
  }

  public boolean getShootStatus() {
    return m_shootStatus;
  }

  @Override
  public void periodic() {
    // TODO Find proper speed for getting ball away from flywheel
    if(!m_shootStatus && !m_track_limit_switch.get()) {
      m_indexer.set(.2);
    } else {
      m_indexer.set(m_indexerPow);
    }

    m_flywheel.set(m_flywheelPow);
  }
}
