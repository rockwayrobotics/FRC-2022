// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.CAN;
import frc.robot.Constants.Controllers;
import frc.robot.Constants.Digital;
import frc.robot.Constants.Pneumatics;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.FeederSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Button;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.commands.AutonomousCmdList;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.HookSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

// import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private DigitalInput shooter_track_limit = new DigitalInput(Digital.SHOOTER_TRACK_LIMIT);

  /* Contstructor for subsystems */
  private DrivebaseSubsystem m_drivebase = new DrivebaseSubsystem(
    CAN.LEFT_MOTOR_1, CAN.LEFT_MOTOR_2,
    CAN.RIGHT_MOTOR_1, CAN.RIGHT_MOTOR_2,
    Digital.LEFT_ENCODER_1, Digital.LEFT_ENCODER_2,
    Digital.RIGHT_ENCODER_1, Digital.RIGHT_ENCODER_2
  );

  //private FeederSubsystem m_feeder = new FeederSubsystem(CAN.FEEDER_MOTOR, CAN.FEEDER_MOTOR2);
  private FeederSubsystem m_feeder = new FeederSubsystem(CAN.FEEDER_MOTOR, shooter_track_limit);

  private ShooterSubsystem m_shooter = new ShooterSubsystem(
    CAN.INDEX_MOTOR,
    CAN.FLYWHEEL_MOTOR, CAN.FLYWHEEL_MOTOR2, shooter_track_limit
    );
  
  private IntakeSubsystem m_intake = new IntakeSubsystem(
    Pneumatics.INTAKE_EXTEND, Pneumatics.INTAKE_RETRACT, 
    CAN.INTAKE_MOTOR
  );

  private HookSubsystem m_hook = new HookSubsystem(CAN.WINCH_MOTOR, Digital.TOP_CLIMB_LIMIT, Digital.BOTTOM_CLIMB_LIMIT);


  private XboxController m_xboxController = new XboxController(Controllers.XBOX);
  private Joystick m_flightStick = new Joystick(Controllers.FLIGHT);

  ShuffleboardTab tab = Shuffleboard.getTab("Speeds");
  private NetworkTableEntry flywheelSpeed =
        tab.add("Flywheel Speed", 0.8)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", -1, "max", 1)) // specify widget properties here
          .getEntry();
  private NetworkTableEntry intakeSpeed =
        tab.add("Intake Speed", 0.5)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", -1, "max", 1)) // specify widget properties here
          .getEntry();
  private NetworkTableEntry indexSpeed =
        tab.add("Index Speed", 0.5)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", -1, "max", 1)) // specify widget properties here
          .getEntry();
  private NetworkTableEntry feederSpeed =
        tab.add("Feeder Speed", 0.4)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", -1, "max", 1)) // specify widget properties here
          .getEntry();
  private NetworkTableEntry hookSpeed =
        tab.add("Hook Speed", 0.8)
          .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", 0, "max", 1)) // specify widget properties here
          .getEntry();
  private NetworkTableEntry autoSpeed =
        tab.add("Auto Speed", 0.5)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", -1, "max", 1)) // specify widget properties here
            .getEntry();
  private NetworkTableEntry autoDistance =
        tab.add("Auto Distance", 50) // specify widget properties here
            .getEntry();

  public final Command m_autoCommand = new AutonomousCmdList(m_drivebase, m_shooter, m_feeder, autoDistance, autoSpeed, flywheelSpeed, indexSpeed, feederSpeed); //pass in drivebase here


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_drivebase.setDefaultCommand(
      new RunCommand(
        () -> m_drivebase.set(m_xboxController.getLeftX(), -m_xboxController.getLeftY()),
        m_drivebase
      )
    );  // Runs drivebase off left stick
  
    new JoystickButton(m_xboxController, XboxController.Button.kLeftBumper.value)
    .whenPressed(() -> m_drivebase.setScale(0.5))
    .whenReleased(() -> m_drivebase.setScale(1));  // Sets drivebase to half speed, for more precise and slow movement (likely going to be used inside hangar)
 
    new JoystickButton(m_xboxController, XboxController.Button.kRightBumper.value) // Drops intake, spins intake wheels and feeder wheels
    .whenPressed(() -> {
      m_shooter.setShootStatus(true);
      m_feeder.setShootStatus(true);
      m_shooter.spinFlywheel(flywheelSpeed.getDouble(0.8));
    })
    .whenReleased(() -> {
      m_shooter.spinFlywheel(0);
      m_shooter.setShootStatus(false);
      m_feeder.setShootStatus(false);
    });

    new JoystickButton(m_xboxController, XboxController.Button.kY.value) // Feeds ball to flywheel, spinning feeder and indexer wheels
    .whenPressed(new InstantCommand(() -> {
      m_shooter.spinIndex(-indexSpeed.getDouble(0.3));
      m_feeder.spinFeeder(-feederSpeed.getDouble(0.4));
    }, m_shooter, m_feeder))
    .whenReleased(new InstantCommand(() -> {
      m_shooter.spinIndex(0);
      m_feeder.spinFeeder(0);
    }, m_shooter, m_feeder));

    new JoystickButton(m_xboxController, XboxController.Button.kStart.value) // Manually jogs indexer wheel towards the flywheel
    .whenPressed(new InstantCommand(() -> m_shooter.spinIndex(-0.3), m_shooter))
    .whenReleased(new InstantCommand(() -> m_shooter.spinIndex(0), m_shooter));

    new JoystickButton(m_xboxController, XboxController.Button.kBack.value) // Manually jogs indexer wheel away from the flywheel
    .whenPressed(new InstantCommand(() -> m_shooter.spinIndex(0.3), m_shooter))
    .whenReleased(new InstantCommand(() -> m_shooter.spinIndex(0), m_shooter));

    new JoystickButton(m_xboxController, XboxController.Button.kA.value) // Drops intake, spins intake wheels and feeder wheels
    .whenPressed(() -> {
      if(m_intake.getExtended() == Value.kReverse){
        m_intake.extend();
      }
      m_intake.spin(intakeSpeed.getDouble(0.7));
      m_feeder.spinFeeder(-feederSpeed.getDouble(0.4));
      // m_intake.spin(0.5);
      // m_feeder.spinFeeder(-0.4);
    })
    .whenReleased(() -> {
      m_intake.spin(0);
      m_feeder.spinFeeder(0);
    });

    new JoystickButton(m_xboxController, XboxController.Button.kX.value)  // Retracts intake
    .whenPressed(() -> m_intake.retract());
    
    new JoystickButton(m_xboxController, XboxController.Button.kB.value)  // ejects ball out by running blue feeder wheels backwards
    .whenPressed(() -> {
      m_feeder.spinFeeder(feederSpeed.getDouble(0.4)); // Checks if feeder is extended before spinning
      if(m_intake.getExtended() == Value.kForward){
        m_intake.spin(-intakeSpeed.getDouble(0.8));
      }
    }, m_intake, m_shooter)
    .whenReleased(() -> {
      m_feeder.spinFeeder(0);
      m_intake.spin(0);
    });

    new Button(() -> {return m_xboxController.getPOV() == 0;})  // Extends hook up
      .whenPressed(() -> m_hook.extend(hookSpeed.getDouble(0.8)))
      .whenReleased(() -> m_hook.stop());

    new Button(() -> {return m_xboxController.getPOV() == 180;})  // Retracts hook down / Climbs robot
      .whenPressed(() -> m_hook.retract(-hookSpeed.getDouble(0.8)/1.5))
      .whenReleased(() -> m_hook.stop());


    new JoystickButton(m_flightStick, 11) // Spins feeder without any other motors
    .whenPressed(() -> m_feeder.spinFeeder(feederSpeed.getDouble(0.4)))
    .whenReleased(() -> m_feeder.spinFeeder(0));

    new JoystickButton(m_flightStick, 3) // Spins intake backwards without any other motors
    .whenPressed(() -> m_intake.spin(-intakeSpeed.getDouble(0.4)))
    .whenReleased(() -> m_intake.spin(0));

    new JoystickButton(m_flightStick, 4) // Spins intake without any other motors
    .whenPressed(() -> m_intake.spin(intakeSpeed.getDouble(0.4)))
    .whenReleased(() -> m_intake.spin(0));

    new JoystickButton(m_flightStick, 12) // Spins flywheel backwards
    .whenPressed(() -> m_shooter.spinFlywheel(-flywheelSpeed.getDouble(0.4)))
    .whenReleased(() -> m_shooter.spinFlywheel(0));

    new JoystickButton(m_flightStick, 9) // Manually jogs indexer wheel towards the flywheel
    .whenPressed(new InstantCommand(() -> m_shooter.spinIndex(-0.3), m_shooter))
    .whenReleased(new InstantCommand(() -> m_shooter.spinIndex(0), m_shooter));

    new JoystickButton(m_flightStick, 10) // Manually jogs indexer wheel away from the flywheel
    .whenPressed(new InstantCommand(() -> m_shooter.spinIndex(0.3), m_shooter))
    .whenReleased(new InstantCommand(() -> m_shooter.spinIndex(0), m_shooter));

    // new Button(() -> {return m_xboxController.getLeftTriggerAxis() > 0.5;})
    // .whenPressed(() -> m_intake.retract());
    
    // new Button(() -> {return m_xboxController.getRightTriggerAxis() > 0.5;})
    // .whenPressed(() -> m_intake.extend());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Uses m_autoCommand which was created earlier
    return m_autoCommand;
  }

  public void onDisable() {
    m_drivebase.disable();
  }
  
  public void onEnable() {
    m_drivebase.enable();
  }
}
