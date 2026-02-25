package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FuelConstants.*;

import java.util.function.DoubleSupplier;

public class CANTurretSubsystem extends SubsystemBase {
    private final SparkMax turretTurner;
    //private final SparkMax turretEncoder;
    private final AbsoluteEncoder absoluteEncoder;

    public CANTurretSubsystem() {
        // create brushed motor
        turretTurner = new SparkMax(TURRET_MOTOR_ID, MotorType.kBrushed);
        //turretEncoder = new SparkMax(TURRET_ENCODER_MOTOR_ID, MotorType.kBrushed);
        absoluteEncoder = turretTurner.getAbsoluteEncoder();

        //SmartDashboard.putNumber("Spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE);
    }
    
      // A method to stop the rollers
  public void stop() {
    turretTurner.set(0);

  }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void setSpeed(double leftX) {
        turretTurner.setVoltage(leftX);
    }
    public Command rotate(DoubleSupplier speed) {
        return this.run(() -> setSpeed(speed.getAsDouble()));
    }
}
