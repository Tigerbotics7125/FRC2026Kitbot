// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.DriveConstants.*;

public class CANDriveSubsystem extends SubsystemBase {
  private final SparkMax leftLeader;
  private final SparkMax leftFollower;
  private final SparkMax rightLeader;
  private final SparkMax rightFollower;

  private final DifferentialDrive drive;
  private final DifferentialDrivePoseEstimator dPoseEst;
  private final DifferentialDriveKinematics driveKinematics;

  private AHRS ahrs;

  public CANDriveSubsystem() {
    // create brushed motors for drive
    leftLeader = new SparkMax(LEFT_LEADER_ID, MotorType.kBrushless);
    leftFollower = new SparkMax(LEFT_FOLLOWER_ID, MotorType.kBrushless);
    rightLeader = new SparkMax(RIGHT_LEADER_ID, MotorType.kBrushless);
    rightFollower = new SparkMax(RIGHT_FOLLOWER_ID, MotorType.kBrushless);

    // set up differential drive class
    drive = new DifferentialDrive(leftLeader, rightLeader);
    driveKinematics = new DifferentialDriveKinematics(Units.inchesToMeters(21.651));
    // Set can timeout. Because this project only sets parameters once on
    // construction, the timeout can be long without blocking robot operation. Code
    // which sets or gets parameters during operation may need a shorter timeout.
    leftLeader.setCANTimeout(250);
    rightLeader.setCANTimeout(250);
    leftFollower.setCANTimeout(250);
    rightFollower.setCANTimeout(250);

    // Create the configuration to apply to motors. Voltage compensation
    // helps the robot perform more similarly on different
    // battery voltages (at the cost of a little bit of top speed on a fully charged
    // battery). The current limit helps prevent tripping
    // breakers.
    SparkMaxConfig config = new SparkMaxConfig();
    config.voltageCompensation(12);
    config.smartCurrentLimit(DRIVE_MOTOR_CURRENT_LIMIT);

    // Calculate factor: (Wheel Diameter * PI) / Gear Ratio
    double gearRatio = 8.46;
    double wheelDiameterInches = 6.0;
    double factor = (wheelDiameterInches * Math.PI) / gearRatio;
    config.encoder.positionConversionFactor(factor);

    // Set configuration to follow each leader and then apply it to corresponding
    // follower. Resetting in case a new controller is swapped
    // in and persisting in case of a controller reset due to breaker trip
    config.follow(leftLeader);
    leftFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    config.follow(rightLeader);
    rightFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Remove following, then apply config to right leader
    config.disableFollowerMode();
    rightLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // Set config to inverted and then apply to left leader. Set Left side inverted
    // so that postive values drive both sides forward
    config.inverted(true);
    leftLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    try {
      /* Communicate w/navX-MXP via the MXP SPI Bus. */
      /* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB */
      /*
       * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
       * details.
       */
      ahrs = new AHRS(NavXComType.kMXP_SPI);
    } catch (RuntimeException ex) {
      DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
    }

    dPoseEst = new DifferentialDrivePoseEstimator(
        driveKinematics,
        ahrs.getRotation2d(),
        leftLeader.getEncoder().getPosition(),
        rightLeader.getEncoder().getPosition(),
        new Pose2d(),
        VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(5)),
        VecBuilder.fill(0.5, 0.5, Units.degreesToRadians(30)));
  }

  @Override
  public void periodic() {
    dPoseEst.update(
        ahrs.getRotation2d(),
        leftLeader.getEncoder().getPosition(),
        rightLeader.getEncoder().getPosition());
  }

  // Command factory to create command to drive the robot with joystick inputs.
  public Command driveArcade(DoubleSupplier xSpeed, DoubleSupplier zRotation) {
    return this.run(
        () -> drive.arcadeDrive(xSpeed.getAsDouble(), zRotation.getAsDouble()));
  }

  public Command driveTank(DoubleSupplier lSpeed, DoubleSupplier rSpeed) {
    return this.run(() -> drive.tankDrive(lSpeed.getAsDouble(), rSpeed.getAsDouble(), true));
  }

  public void addVisionMeasurement(Pose2d visionMeasurement, double timestampSeconds) {
    dPoseEst.addVisionMeasurement(visionMeasurement, timestampSeconds);
  }

  public void addVisionMeasurement(
      Pose2d visionMeasurement, double timestampSeconds, Matrix<N3, N1> stdDevs) {
    dPoseEst.addVisionMeasurement(visionMeasurement, timestampSeconds, stdDevs);
  }

  /** Get the estimated pose of the differential drive on the field. */
  public Pose2d getPose() {
    return dPoseEst.getEstimatedPosition();
  }

  /** The heading of the differential drive's estimated pose on the field. */
  public Rotation2d getHeading() {
    return getPose().getRotation();
  }

  /** Raw gyro yaw (this may not match the field heading!). */
  public Rotation2d getGyroYaw() {
    return ahrs.getRotation2d();
  }

}
