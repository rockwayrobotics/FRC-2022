package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;


public class RetractIntake extends CommandBase{
  private IntakeSubsystem m_intakeSubsystem;
 
  /**
   * A command that extends the intake
   * @param intake Set this to m_intakeSubsystem
   */
  public RetractIntake(IntakeSubsystem intake){
    m_intakeSubsystem = intake;
    this.addRequirements(m_intakeSubsystem);
  }

  @Override
  public void execute() {
    m_intakeSubsystem.extend();
  }

  @Override
  public boolean isFinished() {
    return m_intakeSubsystem.isRetracted();
  }

  public void end() {
    m_intakeSubsystem.off();
  }
  
}