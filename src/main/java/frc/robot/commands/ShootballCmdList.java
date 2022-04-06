package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

//Commands added here will be run in sequence when AutonomousCmdList is called upon 
public class ShootballCmdList extends SequentialCommandGroup {

    ShooterSubsystem shooter;

    public ShootballCmdList(DrivebaseSubsystem m_drivebase, ShooterSubsystem m_shooter, FeederSubsystem m_feeder) {
        super();
        
        // I want to get to 4000 RPM
        double velocityTarget = SmartDashboard.getNumber("Desired Flywheel Velocity", 0);

        this.addCommands(new SpinFlywheel(m_shooter,m_drivebase,m_feeder,velocityTarget));
        this.addCommands(new InstantCommand(() -> m_shooter.spinIndex(-0.3), m_shooter));
        this.addCommands(new WaitCommand(1));

        shooter = m_shooter;
    }
    
    @Override
    public void end(boolean cancelled) {
        shooter.stopAll();
    }
}
