package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;


public class RetractIntake extends CommandBase{
  private IntakeSubsystem m_intakeSubsystem;
 
  /**
   * A command that retracts the intake
   * @param intake Set this to m_intakeSubsystem
   */
  public RetractIntake(IntakeSubsystem intake){
    m_intakeSubsystem = intake;
    this.addRequirements(m_intakeSubsystem);
  }

/**
 * Retracts the intake when called upon 
 */
  @Override
  public void execute() {
    m_intakeSubsystem.extend();
  }

/**
 * Checks if the intake is retracted, calls end() if it is
 */
  @Override
  public boolean isFinished() {
    return m_intakeSubsystem.isRetracted();
  }

/**
 * Turns off the intake and ends command
 */
  public void end() {
    m_intakeSubsystem.off();
  }
  
}