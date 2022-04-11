package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FeederSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class dropBall extends CommandBase {
    private ShooterSubsystem m_shooterSubsystem;
    private FeederSubsystem m_feederSubsystem;
    private int cycles = 0;

    public dropBall(ShooterSubsystem shooter, FeederSubsystem feeder){

    m_shooterSubsystem = shooter;
    m_feederSubsystem = feeder;

    this.addRequirements(m_shooterSubsystem);
    this.addRequirements(m_feederSubsystem);

    }

    @Override
    public void initialize() {
        cycles = 0;
    }

    @Override
    public void execute() {
        m_shooterSubsystem.spinIndex(0.3);
        m_feederSubsystem.spinFeeder(0.3);
        cycles++;
    }

    @Override
    public boolean isFinished() {
        return cycles >= 150;
      }

    @Override 
    public void end(boolean cancelled) {
        m_shooterSubsystem.spinIndex(0);
        m_feederSubsystem.spinFeeder(0);
  }  
}
