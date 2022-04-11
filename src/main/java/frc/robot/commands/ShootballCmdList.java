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

    ShooterSubsystem m_shooter;
    FeederSubsystem m_feeder;
    DrivebaseSubsystem m_drivebase;

    public ShootballCmdList(DrivebaseSubsystem drivebase, ShooterSubsystem shooter, FeederSubsystem feeder, boolean secondBall) {
        super();
        m_shooter = shooter;
        m_feeder = feeder;
        m_drivebase = drivebase;

        // I want to get to 4000 RPM
        double velocityTarget = SmartDashboard.getNumber("Flywheel Velocity Target", 0);

        // get flywheel to desired velocity in RPM
        this.addCommands(new SpinFlywheel(m_shooter,m_drivebase,m_feeder,velocityTarget));
        // roll indexer to shoot ball
        this.addCommands(new InstantCommand(() -> m_shooter.spinIndex(-0.3), m_shooter));

        if(secondBall) {
            // ensure flywheel is at desired velocity
            this.addCommands(new SpinFlywheel(m_shooter,m_drivebase,m_feeder,velocityTarget));
            // feed second ball to indexer
            this.addCommands(new InstantCommand(() -> m_feeder.spinFeeder(-0.3), m_feeder));
            // shoot second ball by indexing second ball
            this.addCommands(new InstantCommand(() -> m_shooter.spinIndex(-0.3), m_shooter));
        }
        this.addCommands(new WaitCommand(1));
    }
    
    @Override
    public void end(boolean cancelled) {
        m_shooter.stopAll();
        m_feeder.spinFeeder(0);
    }
}
