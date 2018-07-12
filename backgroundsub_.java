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


public class backgroundsub_ implements PlugIn, Measurements {

	public void run(String arg) {
		
		ImagePlus image = WindowManager.getCurrentImage();

		if (image==null)
		{IJ.noImage(); return;}

        Roi roi = image.getRoi();
        if(roi==null) {
            IJ.showMessage("no selected ROI");
             return;}
        ImageStack stack = image.getStack();
        int size = stack.getSize();
        double[] values = new double[size];
        Rectangle r = roi.getBoundingRect();
        Calibration cal = image.getCalibration();

        for (int i=1; i<=size; i++) {
            ImageProcessor ip = stack.getProcessor(i);
            ip.setRoi(roi);
            ImageStatistics stats = ImageStatistics.getStatistics(ip, MEAN, cal);
            values[i-1] = (double)stats.mean;
        }
        image.killRoi();
        for (int i=1; i<=(size); i++) {
            image.setSlice(i);
            IJ.run("Subtract...", "value=" + values[i-1]);
		}

	}

}


