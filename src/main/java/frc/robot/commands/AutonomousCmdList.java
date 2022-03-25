package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.ShooterSubsystem;


public class AutonomousCmdList extends SequentialCommandGroup {

    public AutonomousCmdList(DrivebaseSubsystem m_drivebase, ShooterSubsystem m_shooter) {
        super();
        this.addCommands(new ShootBall(m_shooter, m_drivebase));
        this.addCommands(new DriveDistance(m_drivebase, -25, -0.5));
    }
    
    
}
