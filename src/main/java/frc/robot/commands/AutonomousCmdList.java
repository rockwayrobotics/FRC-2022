package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.DrivebaseSubsystem;


public class AutonomousCmdList extends SequentialCommandGroup {

    public AutonomousCmdList(DrivebaseSubsystem m_drivebase) {
        super();
        this.addCommands(new DriveDistance(m_drivebase, -25, -0.5));
    }
    
    
}
