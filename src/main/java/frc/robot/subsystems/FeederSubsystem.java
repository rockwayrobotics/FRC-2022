package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FeederSubsystem extends SubsystemBase {
  //MotorControllerGroup m_feeder;
  private CANSparkMax m_feeder;

  private double m_feederPow = 0;

  private DigitalInput m_track_limit_switch;
  private boolean m_shootStatus = false;

  /** Creates a new feederSubsystem. */
  public FeederSubsystem(int feederMotor, DigitalInput track_limit_switch) {
    m_feeder = new CANSparkMax(feederMotor, MotorType.kBrushless);
    //CANSparkMax feeder2 = new CANSparkMax(feederMotor2, MotorType.kBrushless);
    m_feeder.setIdleMode(IdleMode.kBrake);
    //feeder2.setIdleMode(IdleMode.kBrake);
    //m_feeder = new MotorControllerGroup(feeder1, feeder2);

    m_track_limit_switch = track_limit_switch;
  }

  /**
   * Spins the feeder at a specified power level.
   * @param feedPow Speed to spin the feeder. -1 is full backwards, 1 is full forwards.
   */
  public void spinFeeder(double feedPow) {
    m_feederPow = feedPow;
  }

  public void setShootStatus(boolean shootStatus) {
    m_shootStatus = shootStatus;
  }

  @Override
  public void periodic(){
    //TODO figure out proper speed
    if(!m_track_limit_switch.get() && !m_shootStatus) {
      m_feeder.set(.1);
    } else {
      m_feeder.set(m_feederPow);
    } 
  }
}
