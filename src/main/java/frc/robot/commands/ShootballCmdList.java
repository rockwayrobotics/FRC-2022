package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CameraSubsystem;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

//Commands added here will be run in sequence when AutonomousCmdList is called upon 
public class ShootballCmdList extends SequentialCommandGroup {

    DrivebaseSubsystem m_drivebase;
    ShooterSubsystem m_shooter;
    FeederSubsystem m_feeder;
    CameraSubsystem m_camera;

    public ShootballCmdList(DrivebaseSubsystem drivebase, ShooterSubsystem shooter, FeederSubsystem feeder, CameraSubsystem camera, boolean secondBall) {
        super();
        m_drivebase = drivebase;
        m_shooter = shooter;
        m_feeder = feeder;
        m_camera = camera;
        
        m_shooter.setShootStatus(true);
        m_feeder.setShootStatus(true);
        m_camera.checkVision();
        
        boolean autoTarget = SmartDashboard.getBoolean("Auto Target", true);
        //if(autoTarget) this.addCommands(new VisionCenter(m_drivebase));

        double velocityTarget = SmartDashboard.getNumber("Flywheel Target RPM", 0);
       
        // get flywheel to desired velocity in RPM
        this.addCommands(new SpinFlywheel(m_shooter,m_drivebase,m_feeder,velocityTarget));
        // slight pause to prevent momentum from feeder
        this.addCommands(new WaitCommand(0.5));
        // roll indexer to shoot ball
        this.addCommands(new InstantCommand(() -> m_shooter.spinIndex(-0.3), m_shooter));

        if(secondBall) {
            // slight pause to prevent momentum from feeder
            this.addCommands(new WaitCommand(0.5));
            // stop indexer
            this.addCommands(new InstantCommand(() -> m_shooter.spinIndex(0), m_shooter));
            // feed second ball to indexer
            this.addCommands(new InstantCommand(() -> m_feeder.spinFeeder(-0.3), m_feeder));
            
            // slight pause to prevent momentum from feeder
            this.addCommands(new WaitCommand(2));
            
            // stop feeder
            this.addCommands(new InstantCommand(() -> m_feeder.spinFeeder(0), m_feeder));
            // ensure flywheel is at desired velocity
            this.addCommands(new SpinFlywheel(m_shooter,null,m_feeder,velocityTarget));
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
