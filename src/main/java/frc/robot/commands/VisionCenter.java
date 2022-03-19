package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

import javax.lang.model.util.ElementScanner6;

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
  private final int CENTERX = 100; //Note we will need to configure this once we determine the res we are going to run at
  private final int xTHRESH = 10; //This is so we dont over steer, may need to get configured


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
      NetworkTable table = inst.getTable("dataTable");

      //Get the entries within that table that correspond to the X and Y values
      //for some operation in your program.
      camEntry = table.getEntry("camX");
  }
  //takes the value from the x reading of the camera and turns it into a command
  public int parseNTX(double val)
  {
    if (val < CENTERX - xTHRESH)
    {
      return 0;
    }
    else if (val > CENTERX + xTHRESH)
    {
      return 1;
    }
    else
    {
      return 2;
    }
  }

  @Override
  public void execute() {
    double commandD = camEntry.getNumber(CENTERX).doubleValue();
    int command = parseNTX(commandD);
    
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
