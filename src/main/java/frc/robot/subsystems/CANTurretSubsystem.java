package frc.robot.subsystems;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.TurretConstants.*;

import java.util.function.DoubleSupplier;

public class CANTurretSubsystem extends SubsystemBase {
    private final SparkMax turretTurner;
    // private final SparkMax turretEncoder;
    private final RelativeEncoder relativeEncoder;
    private final SparkClosedLoopController turretController;
    //private double goal;
    public CANTurretSubsystem() {
        // create brushed motor
        turretTurner = new SparkMax(TURRET_MOTOR_ID, MotorType.kBrushless);
        // turretEncoder = new SparkMax(TURRET_ENCODER_MOTOR_ID, MotorType.kBrushed);
        relativeEncoder = turretTurner.getEncoder();
        turretController = turretTurner.getClosedLoopController();
        //goal=0;
        SparkMaxConfig config = new SparkMaxConfig();
        config
        .voltageCompensation(12)
        .smartCurrentLimit(TURRET_MOTOR_CURRENT_LIMIT)
        .idleMode(IdleMode.kCoast)
        .closedLoop.pid(turret_kP, turret_kI, turret_kD);
        turretTurner.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
    }

    // A method to stop the turret
    public void stop() {
       // turretTurner.set(0);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
       // SmartDashboard.putNumber("Turret Goal Position",turretController.getSetpoint());
       // SmartDashboard.putNumber("Turret Current Position",relativeEncoder.getPosition());
    }

    public void setPosition(double angle) {
        double goal=50.0*angle;
        //goal=25;
        turretController.setSetpoint(goal,ControlType.kPosition);
//        turretTurner.setVoltage(leftX);
    }

    public Command rotate(DoubleSupplier angle) {
        //double rotationAngle = angle.getAsDouble() * 90 + 90;
        return this.run(() -> setPosition(angle.getAsDouble()));
    }
}
