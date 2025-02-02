// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DrivetrainConstants.*;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.RobotContainer;

/* This class declares the subsystem for the robot drivetrain if controllers are connected via PWM. If using SPARK MAX
 * controllers connected to CAN, go to RobotContainer and comment out the line declaring this subsystem and uncomment
 * the line for the CANDrivetrain.
 *
 * The subsystem contains the objects for the hardware contained in the mechanism and handles low level logic
 * for control. Subsystems are a mechanism that, when used in conjuction with command "Requirements", ensure
 * that hardware is only being used by 1 command at a time.
 */
public class PWMDrivetrain extends SubsystemBase {
   public boolean leftFlipped = true;
  public boolean rightFlipped = false;
  public boolean inverted = false;
  public double speed = .75;
  /*Class member variables. These variables represent things the class needs to keep track of and use between
  different method calls. */
  DifferentialDrive m_drivetrain;

  public enum driveState {
    reverse,
    forward,
    halfSpeed
  }

  /*Constructor. This method is called when an instance of the class is created. This should generally be used to set up
   * member variables and perform any configuration or set up necessary on hardware.
   */
  public PWMDrivetrain() {
    /*Create MotorControllerGroups for each side of the drivetrain. These are declared here, and not at the class level
     * as we will not need to reference them directly anymore after we put them into a DifferentialDrive.
     */

    VictorSP frontLeftMotorController = new VictorSP(DrivetrainConstants.kLeftFrontID);
    VictorSP frontRightMotorController = new VictorSP(DrivetrainConstants.kRightFrontID);
    VictorSP backLeftMotorController = new VictorSP(DrivetrainConstants.kLeftRearID);
    VictorSP backRightMotorController = new VictorSP(DrivetrainConstants.kRightRearID);

    frontLeftMotorController.addFollower(backLeftMotorController);
    frontRightMotorController.addFollower(backRightMotorController);

    frontLeftMotorController.setInverted(leftFlipped);
    frontRightMotorController.setInverted(rightFlipped);


    



    // MotorControllerGroup leftMotors =
    //     new MotorControllerGroup(new PWMSparkMax(kLeftFrontID), new PWMSparkMax(kLeftRearID));
    // MotorControllerGroup rightMotors =
    //     new MotorControllerGroup(new PWMSparkMax(kRightFrontID), new PWMSparkMax(kRightRearID));

    // Invert left side motors so both sides drive forward with positive output values

    // Put our controller groups into a DifferentialDrive object. This object represents all 4 motor
    // controllers in the drivetrain
    m_drivetrain = new DifferentialDrive(frontLeftMotorController, frontRightMotorController);
  }

  /*Method to control the drivetrain using arcade drive. Arcade drive takes a speed in the X (forward/back) direction
   * and a rotation about the Z (turning the robot about it's center) and uses these to control the drivetrain motors */
  public void tankDrive(double leftSpeed, double rightSpeed) {
    
      m_drivetrain.tankDrive(leftSpeed * speed, rightSpeed * speed);
    
  }

  public void setDriveState(driveState desiredState){
    switch (desiredState) {
      case reverse:
        speed = -.75;
        break;
      case forward:
        speed = .75;
        break;
      case halfSpeed:
        speed = .4;

      default:
        break;
    }
  }

  @Override
  public void periodic() {
    /*This method will be called once per scheduler run. It can be used for running tasks we know we want to update each
     * loop such as processing sensor data. Our drivetrain is simple so we don't have anything to put here */
  }
}
