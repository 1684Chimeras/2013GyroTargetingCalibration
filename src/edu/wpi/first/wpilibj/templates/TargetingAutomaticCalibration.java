/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Arhowk
 */
public class TargetingAutomaticCalibration {
    GyroConfig g;
    DriveTrain d;
    
    double lastMotor = -1;
    int ticker = 0;
    final int tickerTo = 10;
    final double minMotor = 0.3;
    final double motorIncrement = 0.03;
    boolean finished = false;
    
    TargetingAutomaticCalibration(GyroConfig gx, DriveTrain dx){
        g=gx;
        d=dx;
    }
    void start(){
        g.reset();
        lastMotor = -1;
        ticker = 0;
    }
    void reset(){
        lastMotor = -1;
        ticker = 0;
    }
    void run(){
        if(finished)return;
        
        if(lastMotor == -1){
            lastMotor = minMotor;
        }else{
            if(d.leftEncoder() <= 0.1){
                if(ticker < tickerTo){
                    ticker++;
                }else{
                    System.out.println("Motor " + lastMotor + " Angle " + g.getAngle());
                    g.reset();
                    ticker = 0;
                    lastMotor += motorIncrement;
                    if(lastMotor > 1){
                        finished = true;
                        return;
                    }
                    d.arcade(0, lastMotor);
                }
            }else{
                ticker = 0;
            }
        }
    }
    
    
}
