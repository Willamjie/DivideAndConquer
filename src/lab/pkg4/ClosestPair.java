/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.pkg4;

/**
 *
 * @author Jared Kamp
 */
public class ClosestPair {
        
    public static class point{
        public final int x;
        public final int y;
        public point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    public static class pair{
        public point p1 = null;
        public point p2 = null;
        public double hypo = 0.0;
        public pair(){}
        public pair(point pointOne, point pointTwo){
            this.p1 = pointOne;
            this.p2 = pointTwo;
            calcDist();
        }
        public void update(point pointOne, point pointTwo, double distance){
            this.p1 = pointOne;
            this.p2 = pointTwo;
            this.hypo = distance;
        }
        public void calcDist(){
            this.hypo = distance(p1, p2);
        }
        public static double distance(point pointOne, point pointTwo){
            int xDist = pointOne.x - pointTwo.x;
            int yDist = pointOne.y - pointTwo.y;
            return Math.sqrt(xDist*xDist + yDist*yDist);
        }       
    }    
}
