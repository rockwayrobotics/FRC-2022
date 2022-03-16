package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

public class VisionCenter extends CommandBase {
  private DrivebaseSubsystem m_drivebase;
  private final int LEFT = 0;
  private final int RIGHT = 1; 
  private final int STOP = 2;
  private boolean m_stop = false; 


  public VisionCenter(DrivebaseSubsystem drivebase) {
    m_drivebase = drivebase;
    this.addRequirements(m_drivebase);
  }

  @Override
  public void initialize() {
    m_stop = false;
  }

  @Override
  public void execute() {
    int command = LEFT; 

    if (command == LEFT) {
      m_drivebase.set(-0.5, 0);
    }
    else if (command == RIGHT) {
      m_drivebase.set(0.5, 0);
    }
    else if (command == STOP) {
      m_stop = true;
    }    
  }
  
  @Override 
  public boolean isFinished() {
    return m_stop;
  }

  @Override 
  public void end(boolean cancelled){
    m_drivebase.set(0, 0);
  }
}
