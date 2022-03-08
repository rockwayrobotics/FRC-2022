// This is "Following" the code from https://frcteam3255.github.io/FRC-Java-Tutorial/programming/autonomous.html but that is really outdated so you have to recode everything
// This will also not work until it is added ti RobotContainer under Command getAutonomousCommand() so that yourself becuase I don't know how to do that

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DrivebaseSubsystem;


/**

 * A simple command that grabs a hatch with the {@link HatchSubsystem}. Written explicitly for

 * pedagogical purposes. Actual code should inline a command this simple with {@link

 * edu.wpi.first.wpilibj2.command.InstantCommand}.

 */

public class DriveDistance extends CommandBase {

  // The subsystem the command runs on

  private DrivebaseSubsystem m_drivebase;

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
    m_drivebase.set(1, 0, 1);

  }


  @Override
  public boolean isFinished() {
    // This will say to end when the encoders are equal with the distance we want
    double averageDistance = (m_drivebase.getLDistance() + m_drivebase.getRDistance()) / 2; 
    return averageDistance >= distance;  //there are encoders for l and r

  }

  @Override
  public void end(boolean cancelled) {
    m_drivebase.set(0,0,0);
  }

}
