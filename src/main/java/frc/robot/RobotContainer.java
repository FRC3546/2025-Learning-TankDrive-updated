// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.PrepareLaunch;
import frc.robot.subsystems.PWMDrivetrain;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.PWMDrivetrain.driveState;
import frc.robot.subsystems.Pneumatics.pneumaticStates;

// import frc.robot.subsystems.CANDrivetrain;
// import frc.robot.subsystems.CANLauncher;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  private final PWMDrivetrain m_drivetrain = new PWMDrivetrain();
  private final Pneumatics m_pneumatics = new Pneumatics();
  // private final CANDrivetrain m_drivetrain = new CANDrivetrain();
 
  // private final CANLauncher m_launcher = new CANLauncher();

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final Joystick leftJoystick =
      new Joystick(OperatorConstants.leftJoystick);
  private final Joystick rightJoystick =
      new Joystick(OperatorConstants.rightJoystick);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    Trigger rightTrigger = new Trigger(() -> rightJoystick.getTrigger());
    Trigger leftTrigger = new Trigger(() -> leftJoystick.getTrigger());
    Trigger rightFrontButton = new Trigger(() -> rightJoystick.getRawButton(2));
    Trigger leftFrontButton = new Trigger(() -> leftJoystick.getRawButton(2));

    // Set the default command for the drivetrain to drive using the joysticks
    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () ->
                m_drivetrain.tankDrive((leftJoystick.getY() ), (rightJoystick.getY() )),
            m_drivetrain));


    rightTrigger.onTrue(new InstantCommand(() -> m_drivetrain.setDriveState(driveState.reverse))).onFalse(new InstantCommand(() -> m_drivetrain.setDriveState(driveState.forward)));
    leftTrigger.onTrue(new InstantCommand(() -> m_drivetrain.setDriveState(driveState.halfSpeed))).onFalse(new InstantCommand(() -> m_drivetrain.setDriveState(driveState.forward)));
    rightFrontButton.onTrue(new InstantCommand(() -> m_pneumatics.setPneumaticState(pneumaticStates.open))).onFalse(new InstantCommand(() -> m_pneumatics.setPneumaticState(pneumaticStates.close)));
    leftFrontButton.onTrue(new InstantCommand(() -> m_pneumatics.setPneumaticState(pneumaticStates.toggle)));

    /*Create an inline sequence to run when the operator presses and holds the A (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command */
    // m_operatorController
    //     .a()
    //     .whileTrue(
    //         new PrepareLaunch(m_launcher)
    //             .withTimeout(LauncherConstants.kLauncherDelay)
    //             .andThen(new LaunchNote(m_launcher))
    //             .handleInterrupt(() -> m_launcher.stop()));

    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
    //m_operatorController.leftBumper().whileTrue(m_launcher.getIntakeCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_drivetrain);
  }
}
