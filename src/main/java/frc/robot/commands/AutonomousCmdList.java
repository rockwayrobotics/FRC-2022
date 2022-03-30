package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.ShooterSubsystem;


public class AutonomousCmdList extends SequentialCommandGroup {

    public AutonomousCmdList(DrivebaseSubsystem m_drivebase, ShooterSubsystem m_shooter, FeederSubsystem m_feeder) {
        super();
        this.addCommands(new ShootBall(m_shooter, m_drivebase, m_feeder));
        this.addCommands(new DriveDistance(m_drivebase, -50, -0.5));
    }
    
    
}
