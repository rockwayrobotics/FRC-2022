// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.CAN;
import frc.robot.Constants.Controllers;
import frc.robot.Constants.Digital;
import frc.robot.Constants.Drive;
import frc.robot.Constants.Pneumatics;
import frc.robot.subsystems.DrivebaseSubsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Button;

import java.time.Instant;

import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.commands.AutonomousCmdList;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.HookSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

// import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();


  /* Contstructor for subsystems */
  private DrivebaseSubsystem m_drivebase = new DrivebaseSubsystem(
    CAN.LEFT_MOTOR_1, CAN.LEFT_MOTOR_2,
    CAN.RIGHT_MOTOR_1, CAN.RIGHT_MOTOR_2,
    Digital.LEFT_ENCODER_1, Digital.LEFT_ENCODER_2,
    Digital.RIGHT_ENCODER_1, Digital.RIGHT_ENCODER_2
  );

  private ShooterSubsystem m_shooter = new ShooterSubsystem(
    CAN.FEEDER_MOTOR, CAN.FEEDER_MOTOR2,
    CAN.INDEX_MOTOR,
    CAN.FLYWHEEL_MOTOR, CAN.FLYWHEEL_MOTOR2
    );
  
  private IntakeSubsystem m_intake = new IntakeSubsystem(
    Pneumatics.INTAKE_EXTEND, Pneumatics.INTAKE_RETRACT, 
    CAN.INTAKE_MOTOR
  );

  private XboxController m_xboxController = new XboxController(Controllers.XBOX);
  private Joystick m_flightStick = new Joystick(Controllers.FLIGHT);

  public final Command m_autoCommand = new AutonomousCmdList(m_drivebase); //pass in drivebase here

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
    );
  
    new JoystickButton(m_xboxController, XboxController.Button.kLeftBumper.value)
    .whenPressed(() -> m_drivebase.setScale(0.5))
    .whenReleased(() -> m_drivebase.setScale(1));
 
    new JoystickButton(m_xboxController, XboxController.Button.kRightBumper.value)
    .whenPressed(new InstantCommand(() -> m_shooter.spinFeeder(-0.2), m_shooter))
    .whenReleased(new InstantCommand(() -> m_shooter.spinFeeder(0), m_shooter));

    new JoystickButton(m_xboxController, XboxController.Button.kA.value)

    .whenPressed(() -> m_shooter.spinFlywheel(-0.8))
    .whenReleased(() -> m_shooter.spinFlywheel(0));

    new JoystickButton(m_xboxController, XboxController.Button.kY.value)
    .whenPressed(() -> m_intake.extend());
    
    new JoystickButton(m_xboxController, XboxController.Button.kX.value)
    .whenPressed(() -> m_intake.retract());
    
    new JoystickButton(m_xboxController, XboxController.Button.kB.value)
    .whenPressed(() -> m_intake.spin(0.5))
    .whenReleased(() -> m_intake.spin(0));

    new Button(() -> {return m_xboxController.getLeftTriggerAxis() > 0.5;})
      .whenPressed(() -> m_intake.retract());
    
    new Button(() -> {return m_xboxController.getRightTriggerAxis() > 0.5;})
      .whenPressed(() -> m_intake.extend());

    // new JoystickButton(m_xboxController, Button.kRightBumper.value)
    // .whenPressed(new InstantCommand(() -> m_shooter.spinFeeder(0.5), m_shooter))
    // .whenReleased(new InstantCommand(() -> m_shooter.spinFeeder(0), m_shooter));

    // new JoystickButton(m_xboxController, Button.kA.value)
    // .whenPressed(new InstantCommand(() -> m_shooter.spinShooter(0.5), m_shooter))
    // .whenReleased(new InstantCommand(() -> m_shooter.spinShooter(0), m_shooter));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous\
    return m_autoCommand;
  }
}
