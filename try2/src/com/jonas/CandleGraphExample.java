package com.jonas;

import org.faceless.graph2.*;
import java.awt.Color;
import java.awt.Paint;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.NumberFormat;
import java.util.*;

public class CandleGraphExample
{
    public void create(List data, String sym,float max,float min, Date start,List rsi,List avg,int mavg, int rsitme){
        // Create the graph and set up the Axes
        //
        Main t = new Main();

        AxesGraph graph = new AxesGraph();
        graph.setAxis(Axis.LEFT, new NumericAxis(NumberFormat.getCurrencyInstance(), Axis.DENSITY_NORMAL));
        graph.setAxis(Axis.BOTTOM, new DateAxis());
        graph.setAxis(Axis.RIGHT,new NumericAxis(NumberFormat.getNumberInstance(),Axis.DENSITY_NORMAL));
        TextStyle ts = new TextStyle("Default", 10, Color.BLACK);
        ts.setRotate(-20);
        ts.setAlign(Align.RIGHT);
        ts.setPaddingRight(-6);
        ts.setPaddingTop(-1);
        graph.getAxis(Axis.BOTTOM).setToothTextStyle(ts);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        CandleSeries candle = new CandleSeries(t.symbol);

        LineSeries average = new LineSeries("Moving average");
        graph.getAxis(Axis.RIGHT).setLabel("Moving Average", new TextStyle("Default", 11, Color.black));
        Style uyjh = new Style(new Color(255, 166, 8));
        average.setStyle(uyjh);

        AxesGraph ngraph = new AxesGraph();
        ngraph.setAxis(Axis.BOTTOM, new DateAxis());
        Style style = new Style(Color.blue);
        style.setLineThickness(1);
        LineSeries lineseries1 = new LineSeries("lines");
        lineseries1.setStyle(style);

        ngraph.getAxis(Axis.LEFT).setLabel("RSI", new TextStyle("Default", 11, Color.black));
        ngraph.getAxis(Axis.BOTTOM).setLabel("Date", new TextStyle("Default", 11, Color.black));
        //LineSeries line = new LineSeries("dave");   //<----
        try {
            // Add the data
            ArrayList<String> temp = new ArrayList<String>();
            for(int b=0;b<=data.size()-1;b++){
                try {

                    temp = (ArrayList<String>) data.get(b);
                    double a = Math.round(Float.valueOf(temp.get(1)) * 100d) / 100d; //open
                    double f = Math.round(Float.valueOf(temp.get(2)) * 100d) / 100d; //high
                    double c = Math.round(Float.valueOf(temp.get(3)) * 100d) / 100d; //low
                    double d = Math.round(Float.valueOf(temp.get(4)) * 100d) / 100d; //close
                    candle.set(df.parse(temp.get(0)), a, d, c, f);
                    if(b<=(rsitme-1)){
                        int j=0;
                    }else{
                        try {
                            int q=b-rsitme;
                            String h = String.valueOf(rsi.get(q));
                            double j = Double.parseDouble(h);
                            lineseries1.set(DateAxis.toDouble(df.parse(temp.get(0))), j);
                        }catch (Exception h){
                            System.out.println("Failed at "+b+" because "+h);
                        }
                    }
                    if(b<=(mavg-2)){
                        int j=0;
                    }else{
                        try{
                            int q=b-(mavg-1);
                            String h = String.valueOf(avg.get(q));
                            double j = Double.parseDouble(h);
                            average.set(DateAxis.toDouble(df.parse(temp.get(0))),j);
                        }catch (Exception e){
                            System.out.println(e);
                        }
                    }

                }catch(Exception e){
                    System.out.println(e);
                }

            }

            candle.setBarWidth(1);
            candle.setStyle(new Style(Color.RED));
            candle.addLine(null,DateAxis.toDouble((df.parse(df.format(start)))),max,DateAxis.toDouble((df.parse(df.format(date)))),max,new Style(Color.green));
            candle.addLine(null,DateAxis.toDouble((df.parse(df.format(start)))),min,DateAxis.toDouble((df.parse(df.format(date)))),min,new Style(Color.red));
            ngraph.setBackWallPaint(null, new Color(204,204,204), Axis.LEFT, Axis.BOTTOM, null);
            ngraph.addSeries(lineseries1);

            ImageOutput nimage = new ImageOutput(2000,1000);
            ngraph.draw(nimage);
            FileOutputStream nout = null;
            try {
                nout = new FileOutputStream(String.format("%s_RSI.png",sym));
                nimage.writePNG(nout, 0);
                nout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //candle.addMarker(new Text("May", new TextStyle("Default", 10, Color.RED)),DateAxis.toDouble(df.parse("2018-05-01")), 17.65);
            //candle.addMarker(new Text("May", new TextStyle("Default", 10, Color.RED)),DateAxis.toDouble(df.parse("2017-05-01")), 17.65);

            //candle.addLine(null,896000000,max,922000000,max,new Style(Color.green));
            //line.set(2000,25);   //<----
            //line.setStyle(new Style(Color.green));  //<----/
            /*candle.setStyle(df.parse("23-JAN-2002"), new Style(Color.RED));
            // Create and add markers to the candle series
            //
            Marker m = new Marker("star", 14);
            m.setStyle(new Style(Color.RED));
            candle.addMarker(m, DateAxis.toDouble(df.parse("23-JAN-2002")), 17.85);
            candle.addMarker(new Text("My Birthday", new TextStyle("Default", 10, Color.RED)),DateAxis.toDouble(df.parse("23-JAN-2002")), 17.65);*/
        }
        catch (Exception e) {
            e.printStackTrace();
        } /*catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        // Add the series to the graph and set the back wall paint
        //

        graph.addSeries(candle);
        graph.addSeries(average);
        graph.setBackWallPaint(null, new Color(204,204,204), Axis.LEFT, Axis.BOTTOM, null);

        //graph.addSeries(line);    //<----


        // Add a title
        //
        //graph.addText("January 2002 Yahoo! Inc Stock Prices", new TextStyle("Times", 24, Color.BLUE, Align.CENTER));
        // Write the file
        //
        ImageOutput image = new ImageOutput(2000, 1000);
        graph.draw(image);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(String.format("%s.png",sym));
            image.writePNG(out, 0);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}