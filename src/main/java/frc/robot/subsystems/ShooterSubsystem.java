// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  CANSparkMax m_indexer;
  MotorControllerGroup m_flywheel;

  private double m_flywheelPow = 0;
  private double m_indexerPow = 0; 


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
  }

  /**
   * Spins the shooter wheel at a specified power level.
   * @param shootPow Speed to spin the wheel. -1 is full backwards, 1 is full forwards.
   */
  public void spinFlywheel(double shootPow) {
    m_flywheelPow = shootPow;
  }

  public void spinIndex(double indexPow) {
    m_indexerPow = indexPow;
  }

  @Override
  public void periodic() {
    m_flywheel.set(m_flywheelPow);
    m_indexer.set(m_indexerPow);
   
  }
}
