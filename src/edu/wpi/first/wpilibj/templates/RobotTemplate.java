/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    
    static DriveTrain d;
    static GyroConfig g;
    static Targeting t;
    static Joystick j;
    static SpinFiveTimes spin;
    static TargetingAutomaticCalibration tac;
    static String sensKey = "Sensitivity";
    static String valKey = "Value";
    public void robotInit() {
        Scheduler.getInstance().enable();
        d = new DriveTrain(1,2,2,3,4,5);
        g = new GyroConfig(2);
        t = new Targeting(NetworkTable.getTable("zcv"),g,d);
        j = new Joystick(1);
        tac = new TargetingAutomaticCalibration(g,d);
        spin = new SpinFiveTimes();
        SmartDashboard.putData(spin);
        SmartDashboard.putData(new CalculateSensitivity());
        SmartDashboard.putData(new ResetGyro());
        SmartDashboard.putNumber(sensKey, 1);
        SmartDashboard.putNumber(valKey, 0);
        
    }
    private static class cbase extends Command{
        cbase(String name){
            super(name);
        }
        cbase(){}
        protected void initialize() {
        }

        protected void execute() {
        }

        protected boolean isFinished() {
            return false;
        }

        protected void end() {
        }

        protected void interrupted() {
        }
        
    }
    public static class CalculateSensitivity extends cbase{
        CalculateSensitivity(){
            super("Calculate Sensitivty");
        }
        protected boolean isFinished(){
            g.setSensitivity(1 / g.getAngle());
            return true;
            
        }
        
    }
    public static class ResetGyro extends cbase{
        ResetGyro(){
            super("Reset Gyro");
        }
        protected boolean isFinished(){
            g.reset();
            return true;
        }
        
    }
    public static class SpinFiveTimes extends cbase{
        SpinFiveTimes(){
            super("Spin Five Times");
        }
        protected void execute() {
            
            d.arcade(0, 0.7);
         }

        protected boolean isFinished() {
            
            return g.getSpins() >= 4.9;
         }
    }
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    double prev = 0;
    boolean wasPressed = false;
    public void teleopPeriodic() {
        if(!spin.isRunning()){
            if(j.getRawButton(1)){
                t.run();
            }else if(j.getRawButton(2)){
                if(!wasPressed){
                    tac.start();
                }
                tac.run();
            }else{
                d.arcade(j.getRawAxis(1), j.getRawAxis(2));
            }
        }
        wasPressed = j.getRawButton(2);
        SmartDashboard.putNumber(valKey, g.getAngle());
        if(SmartDashboard.getNumber(sensKey, 0) != prev){
            prev = SmartDashboard.getNumber(sensKey,0);
            g.setSensitivity(prev);
        }
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
