package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FuelConstants.*;

public class CANTurretSubsystem extends SubsystemBase {
    private final SparkMax turretTurner;
    private final SparkMax turretEncoder;
    private final AbsoluteEncoder absoluteEncoder;

    public CANTurretSubsystem() {
        // create brushed motor
        turretTurner = new SparkMax(TURRET_MOTOR_ID, MotorType.kBrushed);
        turretEncoder = new SparkMax(TURRET_ENCODER_MOTOR_ID, MotorType.kBrushed);
        absoluteEncoder = turretEncoder.getAbsoluteEncoder();
        SmartDashboard.putNumber("Turret encoder value", absoluteEncoder.getPosition());
    }
// A method to stop the rollers
  public void stop() {
    turretTurner.set(0);
    
  }

  // A method to spin up the launcher roller while spinning the feeder roller to
  // push Fuel away from the launcher
  public void spinUp() {
    turretTurner
        .setVoltage(SmartDashboard.getNumber("Spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE));
    
  }
    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
