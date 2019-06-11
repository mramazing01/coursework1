package com.jonas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextFile {
    public List<String> readFile(String filename) {
        List<String> records = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
            reader.close();
            return records;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }

    public List<String> removeComma(String g){
        List<String> k = new ArrayList<String>();
        String a="";
        String b="";
        String c="";
        String d="";
        String e="";
        String f="";
        String h="";
        int z=g.indexOf(",");
        int y=g.indexOf(",",z+2);
        int x=g.indexOf(",",y+2);
        int w=g.indexOf(",",x+2);
        int v=g.indexOf(",",w+2);
        int u=g.indexOf(",",v+2);
        for(int i =0;i<=(z-1);i++){
            a=a+g.charAt(i);
        }
        for(int i =z+1;i<=(y-1);i++){
            b=b+g.charAt(i);
        }
        for(int i =y+1;i<=(x-1);i++){
            c=c+g.charAt(i);
        }
        for(int i =x+1;i<=(w-1);i++){
            d=d+g.charAt(i);
        }
        for(int i =w+1;i<=(v-1);i++){
            e=e+g.charAt(i);
        }
        for(int i =v+1;i<=(u-1);i++){
            f=f+g.charAt(i);
        }
        for(int i =u+1;i<=(g.length()-1);i++){
            h=h+g.charAt(i);
        }
        k.add(a);
        k.add(b);
        k.add(c);
        k.add(d);
        k.add(e);
        k.add(f);
        k.add(h);

        return k;
    }

    public int lines(String file) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(String.format("%s.txt", file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }

}
