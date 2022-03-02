// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  private final CANSparkMax m_shooterMotor;

  private double m_spinPow = 0;

  /** Creates a new WheelSpinnerSubsystem. */
  public ShooterSubsystem(int shooterMotor) {
    m_shooterMotor = new CANSparkMax(shooterMotor, MotorType.kBrushed);
  }

  /**
   * Spins the wheel spinner at a specified power level.
   * @param spinPow Speed to spin the wheel. -1 is full backwards, 1 is full forwards.
   */
  public void spin(double spinPow) {
    m_spinPow = spinPow;
  }

  @Override
  public void periodic() {
    m_shooterMotor.set(m_spinPow);
    m_spinPow = 0;
  }
}
