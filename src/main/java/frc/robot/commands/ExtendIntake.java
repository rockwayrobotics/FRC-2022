package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;


public class ExtendIntake extends CommandBase{
  private IntakeSubsystem m_intakeSubsystem;
 
  /**
   * A command that extends the intake
   * @param intake Set this to m_intakeSubsystem
   */
  public ExtendIntake(IntakeSubsystem intake){
    m_intakeSubsystem = intake;
    this.addRequirements(m_intakeSubsystem);
  }

/** 
 * Extends the intake when called upon 
 */
  @Override
  public void execute() {
    m_intakeSubsystem.extend();
  }

/**
 * Checks if the intake is extended, calls end() if it is
 */
  @Override
  public boolean isFinished() {
    return m_intakeSubsystem.isExtended();
  }

/**
 * Turns off the intake and ends command
 */
  public void end() {
    m_intakeSubsystem.off();
  }
  
}