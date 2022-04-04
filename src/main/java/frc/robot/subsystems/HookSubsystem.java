// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HookSubsystem extends SubsystemBase {
  private final CANSparkMax m_winchMotor;
  private DigitalInput m_topLimitSwitch;
  private DigitalInput m_bottomLimitSwitch;

  private double m_pow = 0;

  /** Creates a new HookSubsystem. */
  public HookSubsystem(int winchMotor, int topLimitSwitch, int bottomLimitSwitch) {
    m_winchMotor = new CANSparkMax(winchMotor, MotorType.kBrushless);
    m_winchMotor.setIdleMode(IdleMode.kBrake);

    m_topLimitSwitch =  new DigitalInput(topLimitSwitch);
    m_bottomLimitSwitch = new DigitalInput(bottomLimitSwitch);
  }

  /**
   * Extends the hook.
   */
  public void extend(double extendPow) {

    // For some reason switch is reading inverse, false when pushed down and true when not pushed

    if(!m_topLimitSwitch.get()) {
      m_pow = 0;
    } else {
      m_pow = extendPow;
    }
  }

  /**
   * Retracts the hook.
   */
  public void retract(double retractPow) {
    if(!m_bottomLimitSwitch.get()) {
      m_pow = 0;
    } else {
      m_pow = retractPow;
    }
  }

  /**
   * Stops the hook.
   */
  public void stop() {
      m_pow = 0; 
  }

  @Override
  public void periodic() {
    // if either limit switch is pressed (represented by false), set power to zero
    if(m_pow > 0 && !m_topLimitSwitch.get() || m_pow < 0 && !m_bottomLimitSwitch.get()) {
      m_pow = 0;
    }
    m_winchMotor.set(m_pow);
  }
}