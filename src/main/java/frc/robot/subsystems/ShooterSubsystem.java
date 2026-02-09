package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import static frc.robot.Constants.ShooterConstants.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    private final SparkMax shooter;

    // All constants are currently set to 0. Please update them in Constants.java(50)!

    public ShooterSubsystem() {
        shooter = new SparkMax(SHOOTER_MOTOR_ID, MotorType.kBrushless);

        // put default values for various shooter operations onto the dashboard
        // all methods in this subsystem pull their values from the dashbaord to allow
        // you to tune the values easily, and then replace the values in Constants.java
        // with your new values. For more information, see the Software Guide.
        SmartDashboard.putNumber("Shooter roller value", SHOOTER_VOLTAGE);

        // create the configuration for the feeder roller, set a current limit and apply
        // the config to the controller
        SparkMaxConfig shooterConfig = new SparkMaxConfig();
        shooterConfig.smartCurrentLimit(SHOOTER_MOTOR_CURRENT_LIMIT);
        shooter.configure(shooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void shoot() {
        shooter.setVoltage(SmartDashboard.getNumber("Shooter value", SHOOTER_VOLTAGE));
    }
}
