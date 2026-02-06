package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import static frc.robot.Constants.ShooterConstants.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    private final SparkMax temp;

    public ShooterSubsystem() {
    temp = new SparkMax(SHOOTER_MOTOR_ID, MotorType.kBrushless);
    }
}
