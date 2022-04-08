package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DrivebaseSubsystem;



public class DriveDistance extends CommandBase {

  // The subsystem the command runs on

  private DrivebaseSubsystem m_drivebase;

  private NetworkTableEntry m_distance;
  private NetworkTableEntry m_speed;
  /**
   * A command that spins the wheels for a certain distance
   * @param subsystem Set this to m_drivebase
   * @param distance Set to distance in inches
   * @param speed Set to speed from -1 to 1 (must match sign of distance)
   */
  public DriveDistance(DrivebaseSubsystem subsystem, NetworkTableEntry distance, NetworkTableEntry speed) {

    m_drivebase = subsystem;
    m_distance = distance;
    m_speed = speed;

    addRequirements(m_drivebase);
  }


  @Override
  public void initialize() {
    // Resets encoder values to default
    m_drivebase.resetEncoders();
  }

  @Override
  public void execute() {
    // Sets the drivebase to go forward from the speed variable
    m_drivebase.set(0, m_speed.getDouble(0.5));
  }


  @Override
  public boolean isFinished() {
    // This takes the values from encoder L and R and averages them out
    double averageDistance = (m_drivebase.getLDistance() + m_drivebase.getRDistance()) / 2; 
    // This will say to end when the encoders are equal with the distance we want

    if (m_speed.getDouble(0.5) < 0) {
      return averageDistance <= m_distance.getDouble(50);
    } else {
      return averageDistance >= m_distance.getDouble(50);  
    }
  }

  @Override
  public void end(boolean cancelled) {
    m_drivebase.set(0,0); // Resets the drivebase to 0, ends command
  }

}
