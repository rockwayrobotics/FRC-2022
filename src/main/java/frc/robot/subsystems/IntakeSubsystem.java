// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Pneumatics;

public class IntakeSubsystem extends SubsystemBase {
  private DoubleSolenoid m_extend;
  //private WPI_VictorSPX m_spin; 
  private CANSparkMax m_spin;
  private boolean m_extended = false;
  private double m_speed = 0; 


  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem(
    int intakeExtend, int intakeRetract, int intakeMotor
  ){
    m_extend = new DoubleSolenoid(Pneumatics.PNEUMATICS_MODULE_TYPE, intakeExtend, intakeRetract);
    //m_spin = new WPI_VictorSPX(intakeMotor);
    m_spin = new CANSparkMax(intakeMotor, MotorType.kBrushless);
  }

  /**
   * Moves the intake to the extended position.
   */
  public void extend() {
    m_extend.set(DoubleSolenoid.Value.kForward);
  }

  /**
   * Moves the intake to the retracted position.
   */
  public void retract() {
    m_extend.set(DoubleSolenoid.Value.kReverse);
  }

  /** Toggles the extended/retracted state of the intake. */
  public void toggle() {
    if (m_extended) {
        this.retract();
    } else {
        this.extend();
    }
    m_extended = !m_extended;
  }

  /** Turns off the intake. */
  public void off() {
    m_extend.set(DoubleSolenoid.Value.kOff);
  }

  /**
   * Gets the state of the intake.
   * @return kForward if extended, kReverse if retracted, kOff if off.
   */
  public DoubleSolenoid.Value getExtended() {
    return m_extend.get();
  }

  public boolean isExtended() {
    return this.getExtended() == Value.kForward;
  }

  public boolean isRetracted() {
    return this.getExtended() == Value.kReverse;
  }

  /**
   * Sets the intake motor wheel speed 
   * @param speed Speed between 0 and 1 
   */
  public void spin(double speed){
    m_speed = speed;
  }

  @Override
  public void periodic(){
    m_spin.set(m_speed);
  }
}
