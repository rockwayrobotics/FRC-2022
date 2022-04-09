package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.ShooterSubsystem;


public class ShootBall extends CommandBase{
  private ShooterSubsystem m_shooterSubsystem;
  private FeederSubsystem m_feederSubsystem;
  private DrivebaseSubsystem m_drivebase;
  private int cycles = 0;

  private NetworkTableEntry m_flywheelSpeed;
  private NetworkTableEntry m_indexSpeed;
  private NetworkTableEntry m_feederSpeed;
 
  /**
   * A command that shoots the ball once
   * @param shooter Set this to m_shooterSubsystem
   * @param drivebase Set this to m_drivebase
   * @param feeder Set this to m_feederSubsystem 
   * drivebase is added as requirement so command delays without turning it on
   */
   public ShootBall(ShooterSubsystem shooter, DrivebaseSubsystem drivebase, FeederSubsystem feeder, NetworkTableEntry flywheelSpeed, NetworkTableEntry indexSpeed, NetworkTableEntry feederSpeed){
    
    m_drivebase = drivebase;
    m_shooterSubsystem = shooter;
    m_feederSubsystem = feeder;
    this.addRequirements(m_shooterSubsystem);
    this.addRequirements(m_drivebase);
    this.addRequirements(m_feederSubsystem);

    m_flywheelSpeed = flywheelSpeed;
    m_indexSpeed = indexSpeed;
    m_feederSpeed = feederSpeed;
  }

  /**Makes sure that the cycles counter is 0
   * This is so that this command can be reused
   */
  @Override
  public void initialize(){
    cycles = 0;
    m_shooterSubsystem.setShootStatus(true);
    m_feederSubsystem.setShootStatus(true);
  }

  /**Turns on all of the shooter motors
   * Adds 1 to cycles every cycle (20ms) to track time
   */
  @Override
  public void execute() {
    if (cycles >= 75) {
      m_shooterSubsystem.spinIndex(-m_indexSpeed.getDouble(0.3));
      m_feederSubsystem.spinFeeder(-m_feederSpeed.getDouble(0.4));
    }
    m_shooterSubsystem.spinFlywheel(m_flywheelSpeed.getDouble(0.8));
    cycles++;
  }

  /**Checks to see if cycles ran enough, then calls end 
   * Change the cycles number here for a longer or shorter run time
   * Every cycle is 20ms
   */
  @Override
  public boolean isFinished() {
    return cycles >= 400;
  }

 /**Turns off all the shooter motors and ends command
  */
  @Override 
  public void end(boolean cancelled) {
    m_shooterSubsystem.spinIndex(0);
    m_feederSubsystem.spinFeeder(0);
    m_shooterSubsystem.spinFlywheel(0);
    m_shooterSubsystem.setShootStatus(false);
    m_feederSubsystem.setShootStatus(false);
  }  
}