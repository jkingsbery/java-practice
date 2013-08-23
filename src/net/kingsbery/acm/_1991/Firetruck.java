package net.kingsbery.acm._1991;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Firetruck {

    public static class Road{

        private int a;
        private int b;

        public Road(int a, int b) {
            this.a=a;
            this.b=b;
        }

        public boolean startsAt(int x) {
            return this.a==x || this.b==x;
        }

        public Integer endsAt(int x) {
            if(x==this.a){
                return this.b;
            }else{
                return this.a;
            }
        }
        
    }
    
    public static class FireCase{

        private int location;
        private Collection<Road> roads = new ArrayList<Road>();

        public FireCase(int location) {
            this.location=location;
        }

        public void add(Road road) {
            this.roads.add(road);
        }

        public List<Integer> getNearby(int x) {
            List<Integer> result = new ArrayList<Integer>();
            for(Road road : roads){
                if(road.startsAt(x)){
                    result.add(road.endsAt(x));
                }
            }
            return result;
        }
        
        public List<List<Integer>> visit(int x, List<Integer> path){
            if(x==location){
                //Base Case: Print path:
                return Collections.singletonList(path);
            }else{
                List<List<Integer>> result = new ArrayList<List<Integer>>();
                //Get nearby street corners
                for(int i : getNearby(x)){
                    if(!path.contains(i)){
                        List<Integer> copy=new ArrayList<Integer>();
                        copy.addAll(path);
                        copy.add(i);
                        result.addAll(visit(i,copy));
                    }
                }
                return result;
            }
        }
        
        public void start(int i){
            System.out.println("CASE " + i + ":");
            List<List<Integer>> result = visit(1,Collections.singletonList(1));
            for(List<Integer> x : result){
                for(int y : x){
                    System.out.print(y + "\t");
                }
                System.out.println();
            }
            System.out.println("There are " + result.size() + " routes from the firestation to streetcorner " + location);
        }
    }
    
    public static void main(String args[]) throws Exception{
        List<FireCase> cases = readInCases();
        
        for(int i=1; i<= cases.size(); i++){
            cases.get(i-1).start(i);
        }
    }

    private static List<FireCase> readInCases() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("firetruck.txt")));
        String line;
        List<FireCase> cases = new ArrayList<FireCase>();
        FireCase current=null;
        while((line=reader.readLine())!=null){
            line=line.trim();
            if(line.contains(" ")){
                String split[] = line.split(" ");
                current.add(new Road(Integer.parseInt(split[0]),Integer.parseInt(split[1])));
            }else{
                current = new FireCase(Integer.parseInt(line));
                cases.add(current);
            }
        }
        return cases;
    }
    
}
