// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CameraSubsystem extends SubsystemBase {
    private final WPI_VictorSPX m_ledRing;
    private double m_led_brightness = 0;

  /** Creates a new CameraSubsystem. */
  public CameraSubsystem(int ledRing) {
    m_ledRing = new WPI_VictorSPX(ledRing);
  }

  public void ledON() {
      m_led_brightness = 1;
      System.out.println("LED ON");
  }

  public void ledOFF() {
      m_led_brightness = 0;
      System.out.println("LED OFF");
  }

  @Override
  public void periodic() {
    m_ledRing.set(m_led_brightness);
  }
}