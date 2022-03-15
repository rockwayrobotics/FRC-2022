// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Pneumatics;

public class PneumaticSubsystem extends SubsystemBase {
  private DoubleSolenoid m_hopper;

  private boolean m_dumped = false;

  /** Creates a new HopperSubsystem. */
  public void HopperSubsystem(
    int hopperForward, int hopperReverse
  ){
    m_hopper = new DoubleSolenoid(Pneumatics.PNEUMATICS_MODULE_TYPE, hopperForward, hopperReverse);
  }

  /**
   * Moves the hopper to the loaded position.
   */
  public void load() {
    m_hopper.set(DoubleSolenoid.Value.kForward);
  }

  /**
   * Moves the hopper to the dumped position.
   */
  public void dump() {
    m_hopper.set(DoubleSolenoid.Value.kReverse);
  }

  /** Toggles the loaded/dumped state of the hopper. */
  public void toggle() {
    if (m_dumped) {
        this.load();
    } else {
        this.dump();
    }
    m_dumped = !m_dumped;
  }

  /** Turns off the hopper. */
  public void off() {
    m_hopper.set(DoubleSolenoid.Value.kOff);
  }

  /**
   * Gets the state of the hopper.
   * @return kForward if loaded, kReverse if dumped, kOff if off.
   */
  public DoubleSolenoid.Value getState() {
    return m_hopper.get();
  }
}
