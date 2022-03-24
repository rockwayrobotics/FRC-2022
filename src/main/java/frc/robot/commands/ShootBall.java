package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;


public class ShootBall extends CommandBase{
  private ShooterSubsystem m_shooterSubsystem;
 
  /**
   * A command that shoots the ball once
   * @param shooter Set this to m_shooterSubsystem
   */
  public ShootBall(ShooterSubsystem shooter){
    m_shooterSubsystem = shooter;
    this.addRequirements(m_shooterSubsystem);
  }

  @Override
  public void execute() {
    m_shooterSubsystem.spinIndex(0.3);
    m_shooterSubsystem.spinFeeder(0.4);
    m_shooterSubsystem.spinFlywheel(0.8);
  }

  @Override
  public boolean isFinished() {
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return true;
  }

  public void end() {
    m_shooterSubsystem.spinIndex(0);
    m_shooterSubsystem.spinFeeder(0);
    m_shooterSubsystem.spinFlywheel(0);
  }
  
}