import ij.*;
import ij.process.*;
import ij.gui.*;
import ij.measure.Calibration;
import ij.plugin.PlugIn;
import java.awt.*;
import ij.plugin.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import ij.plugin.filter.PlugInFilter;
import ij.util.Tools;
import java.util.*;
import ij.measure.*;
import java.awt.Rectangle;
import java.text.*;

/* this pluging is a small modification of the RGB_measure by Wayne Rasband: https://imagej.nih.gov/ij/plugins/rgb-measure.html 
the first value is the slice number, the second the red, the third the green and the fourth the blue of the RGB*/

    
public class RGB_Measure3 implements PlugIn, Measurements {

	
	public void run(String arg) {
		ImagePlus image = WindowManager.getCurrentImage();
        
		int i=image.getSlice();
        
		if (image==null)
			{IJ.noImage(); return;}
        
		Roi roi = image.getRoi();
        if(roi==null) {
            IJ.showMessage("no selected ROI");
             return;}
        
        ImageStack stack = image.getStack();

        Calibration cal = image.getCalibration();
        NumberFormat formatter = new DecimalFormat("#.0000");
        
        ColorProcessor.setWeightingFactors(1.0, 0.0, 0.0);
        ImageProcessor ip = stack.getProcessor(i);
        ip.setRoi(roi);
        ImageStatistics stats = ImageStatistics.getStatistics(ip, MEAN, cal);
        double value1 = (double)stats.mean;
        ColorProcessor.setWeightingFactors(0.0, 1.0, 0.0);
        ip = stack.getProcessor(i);
        ip.setRoi(roi);
        stats = ImageStatistics.getStatistics(ip, MEAN, cal);
        double value2 = (double)stats.mean;  
        ColorProcessor.setWeightingFactors(0.0, 0.0, 1.0);
        ip = stack.getProcessor(i);
        ip.setRoi(roi);
        stats = ImageStatistics.getStatistics(ip, MEAN, cal);
        double value3 = (double)stats.mean;  
        IJ.write(Integer.toString(i) + "\t" + formatter.format(value1) + "\t" + formatter.format(value2)+ "\t" + formatter.format(value3));
        
        

	}

}