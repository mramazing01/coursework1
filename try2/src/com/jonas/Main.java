package com.jonas;
//plz work
//it works
//update
import java.io.IOException;
import java.util.*;

public class Main {
    List words = new ArrayList();
    List values = new ArrayList();
    List data = new ArrayList();
    List rsi = new ArrayList();
    List movingaverage = new ArrayList();
    List upward = new ArrayList();
    List downward = new ArrayList();
    List d = new ArrayList();
    String symbol;
    int mavg;
    int rsitime;
    long new_p;
    public static void main (String[] args) {
        while (true) {

            Main t = new Main();
            Scanner k = new Scanner(System.in);
            while (true) {
                try {
                    System.out.println("Enter the symbol you want ");
                    t.symbol = k.nextLine();
                    System.out.println("Enter moving average ");
                    t.mavg = k.nextInt();
                    System.out.println("Enter rsi time ");
                    t.rsitime = k.nextInt();
                    break;
                }catch(Exception e){
                    continue;
                }
            }
            System.out.println("symbol = "+t.symbol+" mavg = "+t.mavg+" rsi = "+t.rsitime);
            if(t.symbol.equals("q")){
                break;
            }
            CandleGraphExample j = new CandleGraphExample();
            long p = (System.currentTimeMillis()-(31536000000l));   //epoch time one year ago
            t.new_p = Long.parseLong(Long.toString(p).substring(0,10)); //10
            t.getPrice(t.symbol,t.new_p);   //get the stock price and put to txt file
            //1527807600
            t.listed(t.symbol); //get the txt and put it into a array
            float f1 = t.maxx(t.data);      //find where to put max line
            float f2 = t.minn(t.data);      //find where to put min line
            t.new_p=t.new_p*1000;
            Date result = new Date(t.new_p);
            //t.rsi=t.findRsi(t.data);
            System.out.println("part 1"); //zxcv
            j.create(t.data, t.symbol,f1,f2,result,t.findRsi(t.data,t.rsitime),t.mavg(t.data,t.mavg),t.mavg,t.rsitime);    //plot the graph

            //j.lineplot(t.rsi,result,t.symbol);
        }
    }

    public void listed(String symbol){
        int l=0;
        TextFile p = new TextFile();
        String i = String.format("%s.txt",symbol);
        words = p.readFile(i);
        try {
            l=p.lines(symbol)-1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int u=1;u<=l;u++){
            values.add(p.removeComma((String) words.get(u)));
            data.add(values.get(u-1));
        }
    }

    public void getPrice(String symbol,long startTime){
        GetYahooQuotes c = new GetYahooQuotes();
        String crumb = c.getCrumb(symbol);
        if (crumb != null && !crumb.isEmpty()) {
            System.out.println(String.format("Downloading data to %s", symbol));
            System.out.println("Crumb: " + crumb);
            c.downloadData(symbol, startTime, System.currentTimeMillis(), crumb);
        } else {
            System.out.println(String.format("Error retrieving data for %s", symbol));
        }
    }

    private float maxx(List dataa){
        float t1=0;
        for(int i=0;i<=dataa.size()-1;i++){
            try {
                List z = new ArrayList();
                z = (ArrayList<String>) dataa.get(i);
                String j = (String) z.get(4);
                float h = Float.parseFloat(j);
                if (h > t1) {
                    t1 = h;
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
        return t1;
    }

    private float minn(List dataa){
        float t1=1000000;
        for(int i=0;i<=dataa.size()-1;i++){
            try {
                List z = new ArrayList();
                z = (ArrayList<String>) dataa.get(i);
                String j = (String) z.get(4);
                float h = Float.parseFloat(j);
                if (h < t1) {
                    t1 = h;
                }
            }catch(Exception e){
                System.out.println(e);
            }
        }
        return t1;
    }

    private List findRsi(List dataa,int time){
        List c = new ArrayList(); //upward movement
        List d = new ArrayList(); //downward movement
        List e = new ArrayList(); //Average up
        List f = new ArrayList(); //Average down
        List g = new ArrayList(); //Relative Strength
        List h = new ArrayList(); //RSI
        double averageu = 0;
        double averaged = 0;
        for(int i =1;i<=dataa.size()-1;i++){
            List z = new ArrayList();
            List y = new ArrayList();
            z = (ArrayList<String>) dataa.get(i);
            y= (ArrayList<String>) dataa.get(i-1);
            String v=(String) z.get(4);
            String w=(String) y.get(4);
            double a = Math.round(Double.parseDouble(v) * 100d) / 100d; //current
            double b = Math.round(Double.parseDouble(w) * 100d) / 100d; //previous
            if(a > b){
                c.add(a-b);
            }else{
                c.add(0);
            }
            if(a < b){
                d.add(b-a);
            }else{
                d.add(0);
            }
        }
        for(int i=(time-1);i<=c.size();i++){
            averageu = 0;
            for(int x=0;x<time;x++){
                try{
                    averageu += (double) c.get(i-x);
                }catch(Exception grsseg){
                    int egssg=0;
                }

            }
            averageu=averageu/time;
            e.add(averageu);
        }
        for(int i=(time-1);i<=d.size();i++){
            averaged = 0;
            for(int x=0;x<time;x++){
                try {
                    averaged += (double) d.get(i-x);
                }catch (Exception gesg){
                    int jryj =0;
                }
            }
            averaged=averaged/time;
            f.add(averaged);
        }
        //System.out.println("upward movement "+c);
        //System.out.println("downward movement "+d);
        for(int i =0;i<=e.size()-1;i++){
            String ihatethis = String.valueOf(e.get(i));
            String ihatethis2 = String.valueOf(f.get(i));
            double j = Double.parseDouble(ihatethis);
            double k = Double.parseDouble(ihatethis2);
            g.add(j/k);
        }
        for(int i =0;i<g.size()-1;i++){
            String gg = String.valueOf(g.get(i));
            double jj = Double.parseDouble(gg);
            double kk = 100-(100/(jj+1));
            h.add(kk);
        }
        return h;
    }

    private List mavg(List data,int time){
        List h = new ArrayList();
        System.out.println(data.size());
        for(int i=(time-1);i<=data.size()-1;i++){
            double total=0;
            double ntotal;
            for(int o=i-(time-1);o<=i;o++){
                List y = new ArrayList();
                y = (ArrayList<String>) data.get(o);
                String v=(String) y.get(4);
                //double a = Math.round(Double.parseDouble(v) * 100d) / 100d; //current
                double a = Double.parseDouble(v);
                total += (int) a;
            }
            ntotal=total/time;
            ntotal = Math.round(ntotal*100d)/100d;
            h.add(ntotal);
        }
        return h;
    }
}



