/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 *
 * @author Arhowk
 */
public class DriveTrain {
    Jaguar tl;
    Jaguar tr;
    
    Encoder l;
    Encoder r;
    RobotDrive drive;
    DriveTrain(int tlx, int trx, int el1, int el2, int er1, int er2){
        tl = new Jaguar(tlx);
        tr = new Jaguar(trx);
        l = new Encoder(el1, el2);
        r = new Encoder(er1, er2);
        drive = new RobotDrive(tl,tr);
    }
    void arcade(double move, double rotate){
        drive.arcadeDrive(move, rotate, false);
    }
    double rightEncoder(){
        return r.getRate();
    }
    double leftEncoder(){
        return l.getRate();
    }
}
