// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CameraSubsystem extends SubsystemBase {
    private final WPI_VictorSPX m_ledRing;
    private double m_led_brightness = 0;
    
    NetworkTable table;

    NetworkTableEntry m_camX;
    NetworkTableEntry m_camY;
    NetworkTableEntry m_camD;
    NetworkTableEntry m_camA;

    NetworkTableEntry m_test;


  /** Creates a new CameraSubsystem. */
  public CameraSubsystem(int ledRing) {
    m_ledRing = new WPI_VictorSPX(ledRing);
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    table = inst.getTable("dataTable");
  }
  
  public void checkVision() {
    m_camX = table.getEntry("camX");
    m_camY = table.getEntry("camY");
    m_camD = table.getEntry("camD");
    m_camA = table.getEntry("camA");

    m_test = table.getEntry("test");

    double m_camtest = 100;
    SmartDashboard.putNumber("Cam test", m_camtest);
    SmartDashboard.putNumber("Nick Test", m_test.getDouble(0));

    SmartDashboard.putNumber("Cam X", m_camX.getDouble(0));
    SmartDashboard.putNumber("Cam Y", m_camY.getDouble(0));
    SmartDashboard.putNumber("Cam D", m_camD.getDouble(0));
    SmartDashboard.putNumber("Cam A", m_camA.getDouble(0));

    SmartDashboard.putBoolean("Auto Target", true);
  }

  public double[] getVisionValues() {
      double[] visionValues = new double[4];
      visionValues[0] = m_camX.getDouble(0);
      visionValues[1] = m_camY.getDouble(0);
      visionValues[2] = m_camD.getDouble(0);
      visionValues[3] = m_camA.getDouble(0);
      return visionValues;
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
    checkVision();
  }
}