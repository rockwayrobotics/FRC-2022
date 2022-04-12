package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

//Commands added here will be run in sequence when AutonomousCmdList is called upon 
public class AutonomousCmdList extends SequentialCommandGroup {
  /**
   * A command that spins the wheels for a certain distance
   * @param m_drivebase Set this to m_drivebase
   * @param m_shooter Set this to m_shooter
   * @param m_feeder Set this to m_feeder
   * @param drivedistance Set to distance in inches
   * @param driveSpeed Set to speed from -1 to 1 (must match sign of distance)
   */
    public AutonomousCmdList(DrivebaseSubsystem m_drivebase, ShooterSubsystem m_shooter, FeederSubsystem m_feeder, NetworkTableEntry driveDistance, NetworkTableEntry driveSpeed, NetworkTableEntry flywheelSpeed, NetworkTableEntry indexSpeed, NetworkTableEntry feederSpeed) {
        super();
        this.addCommands(new ShootBall(m_shooter, m_drivebase, m_feeder, flywheelSpeed, indexSpeed, feederSpeed));
        this.addCommands(new DriveDistance(m_drivebase, driveDistance, driveSpeed));
    }
}
