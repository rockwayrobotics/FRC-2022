// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  private final CANSparkMax m_shooterMotor;
  private final CANSparkMax m_feederMotor;

  private double m_shootPow = 0;
  private double m_feedPow = 0;

    /**
   * Creates a new ShooterSubsystem.
   * @param shooterMotor Set to your Shooter Motor
   * @param feederMotore Set to your Feeder Motor
   */

  public ShooterSubsystem(int shooterMotor, int feederMotor) {
    m_shooterMotor = new CANSparkMax(shooterMotor, MotorType.kBrushless);

    m_feederMotor = new CANSparkMax(feederMotor, MotorType.kBrushless);
  }

  /**
   * Spins the shooter wheel at a specified power level.
   * @param shootPow Speed to spin the wheel. -1 is full backwards, 1 is full forwards.
   */
  public void spinShooter(double shootPow) {
    m_shootPow = shootPow;
  }

  public void spinFeeder(double feedPow) {
    m_feedPow = feedPow;
  }

  @Override
  public void periodic() {
    m_shooterMotor.set(m_shootPow);
    m_feederMotor.set(m_feedPow);
    // m_shootPow = 0;
    // m_feedPow = 0;
  }
}
