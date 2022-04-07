// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CameraSubsystem extends SubsystemBase {
    private final Relay m_ledRing;

  /** Creates a new CameraSubsystem. */
  public CameraSubsystem(int ledRing) {
    m_ledRing = new Relay(ledRing);
  }

  public void ledON() {
      m_ledRing.set(Relay.Value.kForward);
      System.out.println("LED ON");
  }

  public void ledOFF() {
      m_ledRing.set(Relay.Value.kOff);
      System.out.println("LED OFF");
  }

  @Override
  public void periodic() {

  }
}