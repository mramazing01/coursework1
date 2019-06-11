package com.jonas;

import org.faceless.graph2.*;
import java.awt.LinearGradientPaint;    // Java 1.6 class
import java.awt.Color;
import java.awt.Paint;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple demonstration showing how to use
 * the Java 1.6 java.awt.LinearGradientPaint
 * class.  This example required Java 1.6
 * to compile.
 */
public class test
{
    public void create() {
        Paint paint = Style.createLinearGradientPaint(new double[] { 0, 5, 5.01 },
                new Color[] { Color.green, Color.yellow, Color.red });

        Style style = new Style(paint);
        BarSeries series = new BarSeries("2001");
        series.setStyle(style);
        series.setBarWidth(0.8);
        series.set("apples",5);
        series.set("bananas",9);
        series.set("oranges",2);
        series.set("grapes",6);

        AxesGraph graph = new AxesGraph();
        graph.addSeries(series);

        ImageOutput image = new ImageOutput(500, 500);
        graph.draw(image);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("LinearGradientExample.png");
            image.writePNG(out, 0);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
