package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FeederSubsystem extends SubsystemBase {
  MotorControllerGroup m_feeder;

  private double m_feederPow = 0;


public FeederSubsystem(int feederMotor, int feederMotor2) {
  CANSparkMax feeder1 = new CANSparkMax(feederMotor, MotorType.kBrushless);
  CANSparkMax feeder2 = new CANSparkMax(feederMotor2, MotorType.kBrushless);
  feeder1.setIdleMode(IdleMode.kBrake);
  feeder2.setIdleMode(IdleMode.kBrake);
  m_feeder = new MotorControllerGroup(feeder1, feeder2);
}

public void spinFeeder(double feedPow) {
  m_feederPow = feedPow;
}

@Override
public void periodic(){
  m_feeder.set(m_feederPow); 
  }
}
