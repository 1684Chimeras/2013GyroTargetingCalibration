
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.TableKeyNotDefinedException;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * @author Arhowk
 */
public class Targeting
{
    final int fieldOfView = 67; //Axis M1013 
    final int contraFieldOfView = 293; //360-fieldOfVie
    final int cameraWidthChunk = contraFieldOfView / fieldOfView; //for calculating FoV circumfrence
    
    final float[] cameraAnglePairs = new float[]{
      5,  0.4f,
      10, 0.5f,
      15, 0.6f,
      20, 0.7f,
      25, 0.8f,
      30, 1
    };
    
    //from here now on, lets assume the camera width = 640
    
    int width = 640;
    int chunkConstant = 640 + (640 * cameraWidthChunk);
    
    NetworkTable networkTable = null;
    
    
    public Targeting(NetworkTable NT) 
    {
        networkTable = NT;
    }
    
    public void setWidth(int w){
        width = w;
        chunkConstant = w + (w * cameraWidthChunk);
    } 
    
    private int getAngle(int xerr){
        xerr += width/2;
        return 360 * (xerr / chunkConstant);
    }
    
    public void run(){
        double xerr = networkTable.getNumber("xerr", -1);
        double yerr = networkTable.getNumber("yerr", -1);
        if(xerr == -1) return;
        
        double gyro = 0;
        //gyro = Robot.getAxisGyro();
        
        System.out.println(getAngle((int)xerr));
        
        
    }
    
}