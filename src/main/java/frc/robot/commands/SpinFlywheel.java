package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.ShooterSubsystem;


public class SpinFlywheel extends CommandBase{
  private ShooterSubsystem m_shooterSubsystem;
  private FeederSubsystem m_feederSubsystem;
  private DrivebaseSubsystem m_drivebase;
  private NetworkTableEntry m_RPM;
 
  /**
   * A command that shoots the ball once
   * @param shooter Set this to m_shooterSubsystem
   * @param drivebase Set this to m_drivebase
   * @param feeder Set this to m_feederSubsystem 
   * drivebase is added as requirement so command delays without turning it on
   */
   public SpinFlywheel(ShooterSubsystem shooter, DrivebaseSubsystem drivebase, FeederSubsystem feeder, NetworkTableEntry RPM){
    
    m_drivebase = drivebase;
    m_shooterSubsystem = shooter;
    m_feederSubsystem = feeder;
    this.addRequirements(m_shooterSubsystem);
    //this.addRequirements(m_drivebase);
    this.addRequirements(m_feederSubsystem);

    m_RPM = RPM;
  }

  /**Makes sure that the cycles counter is 0
   * This is so that this command can be reused
   */
  @Override
  public void initialize(){
    m_shooterSubsystem.spinFlywheelSpeed();
    m_shooterSubsystem.setShootStatus(true);
    m_feederSubsystem.setShootStatus(true);
  }

  /**Turns on all of the shooter motors
   * Adds 1 to cycles every cycle (20ms) to track time
   */
  @Override
  public void execute() {
  }

  /**Checks to see if cycles ran enough, then calls end 
   * Change the cycles number here for a longer or shorter run time
   * Every cycle is 20ms
   */
  @Override
  public boolean isFinished() {
    // TODO MAKE THAT A VARIABLE
    return m_shooterSubsystem.getVelocity() >= m_RPM.getDouble(4100);
  }

 /**Turns off all the shooter motors and ends command
  */
  @Override 
  public void end(boolean cancelled) {
    if(cancelled) {
      m_shooterSubsystem.stopAll();
    }
  }  
}