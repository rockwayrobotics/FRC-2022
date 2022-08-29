package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    // public ShootballCmdList(DrivebaseSubsystem drivebase, ShooterSubsystem shooter, FeederSubsystem feeder, CameraSubsystem camera, boolean secondBall, NetworkTableEntry velocityTarget) {
    public ShootballCmdList(DrivebaseSubsystem drivebase, ShooterSubsystem shooter, FeederSubsystem feeder, boolean secondBall, NetworkTableEntry velocityTarget) {
        super();
        m_drivebase = drivebase;
        m_shooter = shooter;
        m_feeder = feeder;
        // m_camera = camera;
        
        m_camera.checkVision();
        
        // boolean autoTarget = SmartDashboard.getBoolean("Auto Target", true);
        //if(autoTarget) this.addCommands(new VisionCenter(m_drivebase));

        //double velocityTarget = SmartDashboard.getNumber("Flywheel Target RPM", 0);
       
        // get flywheel to desired velocity in RPM
        this.addCommands(new SpinFlywheel(m_shooter,m_drivebase,m_feeder,velocityTarget));
        // slight pause to prevent momentum from feeder
        this.addCommands(new WaitCommand(0.1));
        if(!secondBall) {
            // roll indexer to shoot ball
            this.addCommands(new InstantCommand(() -> m_shooter.spinIndex(-0.3), m_shooter));
            this.addCommands(new InstantCommand(() -> m_feeder.spinFeeder(-0.3), m_feeder));
        } else {
            // spin feeder to push first ball into shooter
            this.addCommands(new InstantCommand(() -> m_feeder.spinFeeder(-0.3), m_feeder));
            // slight pause to prevent balls from interfering
            this.addCommands(new WaitCommand(0.25));
            // spin indexer to shoot ball
            this.addCommands(new InstantCommand(() -> m_shooter.spinIndex(-0.3), m_shooter));
            // keep feeder spinning
            this.addCommands(new InstantCommand(() -> m_feeder.spinFeeder(-0.3), m_feeder));
        }
        this.addCommands(new WaitCommand(1));
    }
    
    @Override
    public void end(boolean cancelled) {
        m_shooter.stopAll();
        m_feeder.spinFeeder(0);
        m_shooter.setShootStatus(false);
        m_feeder.setShootStatus(false);
    }
}
