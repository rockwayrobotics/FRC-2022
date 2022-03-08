// This is "Following" the code from https://frcteam3255.github.io/FRC-Java-Tutorial/programming/autonomous.html but that is really outdated so you have to recode everything
// This will also not work until it is added ti RobotContainer under Command getAutonomousCommand() so that yourself becuase I don't know how to do that

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants.CAN;
import frc.robot.Constants.Controllers;
import frc.robot.Constants.Digital;
import frc.robot.subsystems.DrivebaseSubsystem;


/**

 * A simple command that grabs a hatch with the {@link HatchSubsystem}. Written explicitly for

 * pedagogical purposes. Actual code should inline a command this simple with {@link

 * edu.wpi.first.wpilibj2.command.InstantCommand}.

 */

public class DriveDistance extends CommandBase {

  // The subsystem the command runs on

  private DrivebaseSubsystem m_drivebase = new DrivebaseSubsystem( 
// This code was stolen from container becuase I can't code we might be able to call RobotContainer.Drivebase or something but I am clueless as to how to do that
    CAN.LEFT_MOTOR_1, CAN.LEFT_MOTOR_2,
    CAN.RIGHT_MOTOR_1, CAN.RIGHT_MOTOR_2,
    Digital.LEFT_ENCODER_1, Digital.LEFT_ENCODER_2,
    Digital.RIGHT_ENCODER_1, Digital.RIGHT_ENCODER_2
  );

  private double distance;

  public DriveDistance(DrivebaseSubsystem subsystem) {

    m_drivebase = subsystem;

    addRequirements(m_drivebase);

  }


  @Override

  public void initialize() {
    // Resets encoder values to default
    m_drivebase.resetEncoders();

  }

  @Override

  public void execute() {
    // This needs to be constantly forward
    m_drivebase.set(-m_xboxController.getLeftX(), m_xboxController.getLeftY(), 0),
        m_drivebase

  }


  @Override

  public boolean isFinished() {
    // This will say to end when the encoders are equal with the distance we want
    return m_drivebase.getDriveEncoderDistance() == distance;

  }

}
