package frc.robot.subsystems;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.TurretConstants.*;

import java.util.function.DoubleSupplier;

public class CANTurretSubsystem extends SubsystemBase {
    private final SparkMax turretTurner;
    //private final SparkMax turretEncoder;
    private final AbsoluteEncoder absoluteEncoder;
    private final SparkClosedLoopController turretController;

    public CANTurretSubsystem() {
        // create brushed motor
        turretTurner = new SparkMax(TURRET_MOTOR_ID, MotorType.kBrushed);
        //turretEncoder = new SparkMax(TURRET_ENCODER_MOTOR_ID, MotorType.kBrushed);
        absoluteEncoder = turretTurner.getAbsoluteEncoder();
        turretController= turretTurner.getClosedLoopController();
        //SmartDashboard.putNumber("Spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE);
        SparkMaxConfig config = new SparkMaxConfig();
        config.voltageCompensation(12);
        config.smartCurrentLimit(TURRET_MOTOR_CURRENT_LIMIT);
        
    
    
    
    }
    
    
      // A method to stop the rollers
  public void stop() {
    turretTurner.set(0);

  }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void setPosition(double leftX) {
        turretTurner.setVoltage(leftX);
    }
    public Command rotate(DoubleSupplier angle) {
        double rotationAngle=angle.getAsDouble()*90+90;
        return this.run(() -> setPosition(rotationAngle));
    }
}
