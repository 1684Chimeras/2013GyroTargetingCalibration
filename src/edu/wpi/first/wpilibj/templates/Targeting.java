
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Arhowk
 */
public class Targeting
{
    final int fieldOfView = 67; //Axis M1013 
    final int contraFieldOfView = 293; //360-fieldOfVie
    final int cameraWidthChunk = contraFieldOfView / fieldOfView; //for calculating FoV circumfrence
    
    final double[][] cameraAnglePairs = new double[][]{
      new double[]{
          5,
          10,
          15,
          20,
          25,
          30
      },
      new double[]{
          0.2f,
          0.3f,
          0.4f,
          0.5f,
          0.6f,
          0.7f
      }
    };
    
    //from here now on, lets assume the camera width = 640
    
    int width = 640;
    int chunkConstant = 640 + (640 * cameraWidthChunk);
    
    NetworkTable networkTable = null;
    GyroConfig gyro;
    DriveTrain d;
    public Targeting(NetworkTable NT, GyroConfig g, DriveTrain dr) 
    {
        d=dr;
        gyro = g;
        networkTable = NT;
    }
    
    public void setWidth(int w){
        width = w;
        chunkConstant = w + (w * cameraWidthChunk);
    } 
    
    private int getAngle(int xerr){
        xerr += width/2;
        return (360 * (xerr / chunkConstant)) - (fieldOfView / 2);
    }
    private double lookup(double val){ //Credits to Steve Malson
        if (val <= this.cameraAnglePairs[0][0])
                return this.cameraAnglePairs[0][1];
        if (val >= this.cameraAnglePairs[this.cameraAnglePairs.length-1][0])
                return this.cameraAnglePairs[this.cameraAnglePairs.length-1][1];

        int index = 0;
        for(int i=1; i<this.cameraAnglePairs.length; i++)
        {
                index = i-1;
                if (val < this.cameraAnglePairs[i][0])
                        break;   
        }
        if (index > this.cameraAnglePairs.length-2)
        index = this.cameraAnglePairs.length-2;
        double out = this.interpolate(this.cameraAnglePairs[index][0],this.cameraAnglePairs[index][1],this.cameraAnglePairs[index+1][0],this.cameraAnglePairs[index+1][1],val);
        return out;
    }
    private double interpolate(double x1,double val1,double x2,double val2,double x) { //Credits to Steve Malson
        return val1+(x-x1)/(x2-x1)*(val2 - val1); 
    }
    public void run(){
        double xerr = networkTable.getNumber("xerr", -1);
        double yerr = networkTable.getNumber("yerr", -1);
        if(xerr == -1) return;
        
        //double gyroAngle = gyro.getAngle();
        double angleError =getAngle((int)xerr);
        boolean negative = (angleError < 0);
        
        angleError = Math.abs(angleError);
        
        double motor = lookup(angleError);
        
        System.out.println("Angle : " + angleError);
        System.out.println("Lookup : " + motor);
        
        d.arcade(0,motor * (negative ? -1 : 1));
        
        
    }
    
}