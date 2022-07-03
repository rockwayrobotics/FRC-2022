package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivebaseSubsystem;
import edu.wpi.first.networktables.NetworkTable;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CenterTarget extends CommandBase {

    NetworkTableEntry m_camX;
    NetworkTableEntry m_camY;
    NetworkTableEntry m_camD;
    NetworkTableEntry m_camA;

    private DrivebaseSubsystem m_drivebase;
    private double targetAngle; 

    public CenterTarget(DrivebaseSubsystem drivebase){
        m_drivebase = drivebase;
    }

    @Override
    public void initialize() {
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

        SmartDashboard.putNumber("Cam X", m_camX.getDouble(0));
        SmartDashboard.putNumber("Cam Y", m_camY.getDouble(0));
        SmartDashboard.putNumber("Cam D", m_camD.getDouble(0));
        SmartDashboard.putNumber("Cam A", m_camA.getDouble(0));
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Cam X", m_camX.getDouble(0));
        SmartDashboard.putNumber("Cam Y", m_camY.getDouble(0));
        SmartDashboard.putNumber("Cam D", m_camD.getDouble(0));
        SmartDashboard.putNumber("Cam A", m_camA.getDouble(0));

        System.out.println("CamA Target" + m_camA.getDouble(0));
        System.out.println("CamX Target" + m_camX.getDouble(0));
        System.out.println("CamY Target" + m_camY.getDouble(0));
        System.out.println("CamD Target" + m_camD.getDouble(0));

        targetAngle = m_camA.getDouble(0);

        if(targetAngle > -1 && targetAngle < 1) {
          m_drivebase.set(0, 0);
        } else if(targetAngle > 0) {
          m_drivebase.set(0.5, 0);
        } else if(targetAngle < 0) {
          m_drivebase.set(-0.5, 0);
        }
    }

    @Override
    public boolean isFinished() {
        if (targetAngle == 0){
            return true;
        }
        return false;
    }

    @Override 
    public void end(boolean cancelled) {

  }  
}
