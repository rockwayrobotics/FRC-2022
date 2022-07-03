package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionCenter extends CommandBase {

  NetworkTableEntry m_camX;
  NetworkTableEntry m_camY;
  NetworkTableEntry m_camD;
  NetworkTableEntry m_camA;

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
      m_camX = table.getEntry("camX");
      m_camY = table.getEntry("camY");
      m_camD = table.getEntry("camD");
      m_camA = table.getEntry("camA");
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

  // CamA is 0 when target is aquired
  // if Cam A is Negative then robot needs to turn left
  // if Cam A is Positive then robot needs to turn right
  @Override
  public void execute() {
    SmartDashboard.putNumber("Cam X", m_camX.getDouble(0));
    SmartDashboard.putNumber("Cam Y", m_camY.getDouble(0));
    SmartDashboard.putNumber("Cam D", m_camD.getDouble(0));
    SmartDashboard.putNumber("Cam A", m_camA.getDouble(0));
    
    System.out.println("CamX " + m_camX);
    System.out.println("CamY " + m_camY);
    System.out.println("CamD " + m_camD);
    System.out.println("CamA " + m_camA);

    boolean autoTarget = SmartDashboard.getBoolean("Auto Target", true);
    //double commandD = m_camX.getNumber(CENTERX).doubleValue();

    boolean processTargetting = true;
    String directionToTarget = "Ready";
    do {
      double targetAngle = m_camA.getDouble(0);

      if(targetAngle > -1 && targetAngle < 1) {
        directionToTarget = "STOP";
        if(autoTarget) m_drivebase.set(0, 0);
      } else if(targetAngle > 0) {
        directionToTarget = "RIGHT";
        if(autoTarget) m_drivebase.set(0.5, 0);
      } else if(targetAngle < 0) {
        directionToTarget = "LEFT";
        if(autoTarget) m_drivebase.set(-0.5, 0);
      }
      if(directionToTarget == "STOP" || autoTarget == false) {
        processTargetting = false;
      }
    }
    while (processTargetting);


    SmartDashboard.putString("Target Direction", directionToTarget);
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
