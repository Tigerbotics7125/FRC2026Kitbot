package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import static frc.robot.Constants.ShooterConstants.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANShooterSubsystem extends SubsystemBase {
    private final SparkMax shooter;
    private final SparkMax shooterFollow;

    // Constants are not final. Please update them in Constants.java(51) if needed!

    public CANShooterSubsystem() {
        shooter = new SparkMax(SHOOTER_Motor_ID, MotorType.kBrushless);
        shooterFollow = new SparkMax(SHOOTER_MotorFollow_ID, MotorType.kBrushless);

        // put default values for various shooter operations onto the dashboard
        // all methods in this subsystem pull their values from the dashboard to allow
        // you to tune the values easily, and then replace the values in Constants.java
        // with your new values. For more information, see the Software Guide.
        SmartDashboard.putNumber("Shooter value", SHOOTER_VOLTAGE);

        // create the configuration for the shooter, set a current limit and apply
        // the config to the controller
        SparkMaxConfig shooterConfig = new SparkMaxConfig();
        shooterConfig.smartCurrentLimit(SHOOTER_MOTOR_CURRENT_LIMIT);
        shooter.configure(shooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        shooterConfig.follow(shooter);
        shooterFollow.configure(shooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public Command shootCommand() {
        return this.run(() -> shoot(1));
  }

    public void shoot(double speed) {
        shooter.setVoltage(SmartDashboard.getNumber("Shooter value", SHOOTER_VOLTAGE)*speed);
    }

    public void stop() {
    shooter.set(0);
  }

  @Override
  public void periodic() {
  }
}
