/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Gyro;

/**
 *
 * @author Arhowk
 */
public class GyroConfig {
    Gyro g;
    double sensitivity;
    GyroConfig(int channel){
        g = new Gyro(channel);
        sensitivity = 1;
        g.setSensitivity(1);
    }
    double getAngle(){
        return g.getAngle();
    }
    double getSpins(){
        return g.getAngle() / 360;
    }
    void setSensitivity(double sense){
        sensitivity = sense;
        g.setSensitivity(sense);
    }
    double getSensitivity(){
        return sensitivity;
    }
    void reset(){
        g.reset();
    }
}
