package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.ShooterSubsystem;


public class ShootBall extends CommandBase{
  private ShooterSubsystem m_shooterSubsystem;
  private FeederSubsystem m_feederSubsystem;
  private DrivebaseSubsystem m_drivebase;
  private int cycles = 0;
 
  /**
   * A command that shoots the ball once
   * @param shooter Set this to m_shooterSubsystem
   * @param drivebase Set this to m_drivebase
   * @param feeder Set this to m_feederSubsystem 
   * drivebase is added as requirement so command delays without turning it on
   */
   public ShootBall(ShooterSubsystem shooter, DrivebaseSubsystem drivebase, FeederSubsystem feeder){
    
    m_drivebase = drivebase;
    m_shooterSubsystem = shooter;
    m_feederSubsystem = feeder;
    this.addRequirements(m_shooterSubsystem);
    this.addRequirements(m_drivebase);
    this.addRequirements(m_feederSubsystem);
  }

  @Override
  public void initialize(){
    cycles = 0; 
  }

  @Override
  public void execute() {
    m_shooterSubsystem.spinIndex(-0.3);
    m_feederSubsystem.spinFeeder(-0.4);
    m_shooterSubsystem.spinFlywheel(0.5);
    cycles++;
  }

  @Override
  public boolean isFinished() {
    return cycles >= 100;
  }

  @Override 
  public void end(boolean cancelled) {
    m_shooterSubsystem.spinIndex(0);
    m_feederSubsystem.spinFeeder(0);
    m_shooterSubsystem.spinFlywheel(0);
  }  
}