package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionCenter extends CommandBase {

  NetworkTableEntry camEntry;

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
          //Get the default instance of NetworkTables that was created automatically
      //when your program starts
      NetworkTableInstance inst = NetworkTableInstance.getDefault();

      //Get the table within that instance that contains the data. There can
      //be as many tables as you like and exist to make it easier to organize
      //your data. In this case, it's a table called datatable.
      NetworkTable table = inst.getTable("datatable");

      //Get the entries within that table that correspond to the X and Y values
      //for some operation in your program.
      camEntry = table.getEntry("CAM");
  }

  @Override
  public void execute() {
    int command = camEntry.getNumber(STOP).intValue(); 
    
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
