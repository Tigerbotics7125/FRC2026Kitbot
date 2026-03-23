// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    // Motor controller IDs for drivetrain motors
    public static final int LEFT_LEADER_ID = 4;
    public static final int LEFT_FOLLOWER_ID = 5;
    public static final int RIGHT_LEADER_ID = 6;
    public static final int RIGHT_FOLLOWER_ID = 7;

    // Current limit for drivetrain motors. 60A is a reasonable maximum to reduce
    // likelihood of tripping breakers or damaging CIM motors
    public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;

    public enum ControlType {
      ARCADE,
      ARCADE_ROCKETLEAGUE,
      TANK;
    }
  }

  public static final class FuelConstants {
    // Motor controller IDs for Fuel Mechanism motors
    public static final int FEEDER_MOTOR_ID = 1;
    public static final int INTAKE_LAUNCHER_MOTOR_ID = 2;

    // Current limit and nominal voltage for fuel mechanism motors.
    public static final int FEEDER_MOTOR_CURRENT_LIMIT = 40;
    public static final int LAUNCHER_MOTOR_CURRENT_LIMIT = 40;

    // Voltage values for various fuel operations. These values may need to be tuned
    // based on exact robot construction.
    // See the Software Guide for tuning information
    public static final double INTAKING_FEEDER_VOLTAGE = 12;
    public static final double INTAKING_INTAKE_VOLTAGE = -5;
    public static final double LAUNCHING_FEEDER_VOLTAGE = -9;
    public static final double LAUNCHING_LAUNCHER_VOLTAGE = -8;
    public static final double SPIN_UP_FEEDER_VOLTAGE = 6;
    public static final double SPIN_UP_SECONDS = 1;
  }

  public static final class ShooterConstants {
    // Something
    public static final int SHOOTER_Motor_ID = 10;
    public static final int SHOOTER_MotorFollow_ID = 11;

    // Current limit and nominal voltage for shooter motors
    public static final int SHOOTER_MOTOR_CURRENT_LIMIT = 40;

    // Voltage values for shooting operations
    public static final double SHOOTER_VOLTAGE = -8;
  }

  public static final class OperatorConstants {
    // Port constants for driver and operator controllers. These should match the
    // values in the Joystick tab of the Driver Station software
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;

    // This value is multiplied by the joystick value when driving the robot to
    // help avoid driving and turning too fast and being difficult to control
    public static final double DRIVE_SCALING = .7;
    public static final double ROTATION_SCALING = .8;

  }

  public static final class TurretConstants {
    public static final int TURRET_MOTOR_ID = 8;
    public static final int TURRET_ENCODER_MOTOR_ID = 9;
    public static final int TURRET_MOTOR_CURRENT_LIMIT = 40;
    public static final double TURRET_SCALING = .7;
    public static final double turret_kP = .025;
    public static final double turret_kI = 0;
    public static final double turret_kD = .001;
    public static final double turret_kIz = 0;
    public static final double turret_kFF = 0.000156;
    public static final double turret_kMaxOutput = 1;
    public static final double turret_kMinOutput = -1;
    public static final double turret_maxRPM = 5700;
  }

  public static class VisionConstants {
    public static final String kCameraName = "YOUR CAMERA NAME";
    // Cam mounted facing forward, half a meter forward of center, half a meter up
    // from center.
    public static final Transform3d kRobotToCam = new Transform3d(new Translation3d(0.5, 0.0, 0.5),
        new Rotation3d(0, 0, 0));

    // The layout of the AprilTags on the field
    public static final AprilTagFieldLayout kTagLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

    // The standard deviations of our vision estimated poses, which affect
    // correction rate
    // (Fake values. Experiment and determine estimation noise on an actual robot.)
    public static final Matrix<N3, N1> kSingleTagStdDevs = VecBuilder.fill(4, 4, 8);
    public static final Matrix<N3, N1> kMultiTagStdDevs = VecBuilder.fill(0.5, 0.5, 1);
  }
}
