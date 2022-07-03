// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.PneumaticsModuleType;
import com.revrobotics.CANSparkMax.IdleMode;


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {


    // Motor controller IDs, defined from DriverStation
    public static final class Controllers {
        public static final int XBOX = 0;
        public static final int FLIGHT = 1;
    }


    // CAN IDs for motor controllers
    public static final class CAN {
        public static final int LEFT_MOTOR_1 = 1;
        public static final int LEFT_MOTOR_2 = 2;
        public static final int RIGHT_MOTOR_1 = 3;
        public static final int RIGHT_MOTOR_2 = 4;
        public static final int FEEDER_MOTOR = 5;
        //public static final int FEEDER_MOTOR2 = 6;
        public static final int INDEX_MOTOR = 7;
        public static final int FLYWHEEL_MOTOR = 8;
        public static final int FLYWHEEL_MOTOR2 = 9;
        public static final int INTAKE_MOTOR = 10;
        public static final int WINCH_MOTOR = 11;
        public static final int LED_CONTROLLER = 12;
    }
    
    // Information on digital pins on RoboRio
    public static final class Digital {
        public static final int LEFT_ENCODER_1 = 0;
        public static final int LEFT_ENCODER_2 = 1;
        public static final int RIGHT_ENCODER_1 = 2;
        public static final int RIGHT_ENCODER_2 = 3;
        public static final int TOP_CLIMB_LIMIT = 4;
        public static final int BOTTOM_CLIMB_LIMIT = 5;
        public static final int SHOOTER_TRACK_LIMIT_BACK = 6;
        public static final int SHOOTER_TRACK_LIMIT_FRONT = 7;
    }
    
    // Constants related to robot driving
    public static final class Drive {
        public final static double ENCODER_PULSES_PER_REVOLUTION = 360;
        public final static double WHEEL_DIAMETER = 6;
        public final static double DISTANCE_PER_ENCODER_PULSE = WHEEL_DIAMETER * Math.PI / ENCODER_PULSES_PER_REVOLUTION;
        public final static IdleMode ACTIVE_MODE = IdleMode.kBrake;
        public final static IdleMode DISABLED_MODE = IdleMode.kCoast;
    }

    // Constants for spike relays
    public static final class Relay {
        public final static int CAMERA_RELAY = 1;
    }


  /**
   * Constants for Pneumatics Control Module ports
   */
    public static final class Pneumatics {
      public static final PneumaticsModuleType PNEUMATICS_MODULE_TYPE = PneumaticsModuleType.CTREPCM;
      public static final int INTAKE_EXTEND = 1;
      public static final int INTAKE_RETRACT = 0;
    


  }
}
