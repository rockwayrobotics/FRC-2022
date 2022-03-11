package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.DrivebaseSubsystem;


public class Autonomous extends SequentialCommandGroup {

    public Autonomous(DrivebaseSubsystem m_drivebase) {
        super(new DriveDistance(m_drivebase, -15, -0.5));
    }

    
}
