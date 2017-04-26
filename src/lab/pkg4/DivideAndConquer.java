/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab.pkg4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import lab.pkg4.ClosestPair.pair;
import lab.pkg4.ClosestPair.point;
/**
 * *
 * @author Jared Kamp
 * CSCI 232
 * Lab 4 - divide & conquer
 */

public class DivideAndConquer {
    public static void main(String[] args) throws IOException {
        char choice;
        int points = 0;
        while(true)
        {
            if(points == 0) 
            {
                System.out.println("Welcome to Lab 4 - Divide & Conquer.");
                choice = 'n';
            }
            else
            {
                System.out.print("Enter first letter of ");
                System.out.print("Number (of points) or Exit: ");
                choice = getChar();
            }
            switch(choice)
            {
                case 'n':
                    System.out.println("Enter the number of points:");
                    points = getSize();
                    if(points <= 0)
                    {
                        System.out.println("Please enter a positive integer value.");
                        points = getSize();
                    }
                    new DivideAndConquer(points);
                    break;
                case 'e':
                    System.out.println("Thank you for playing!");
                    System.exit(0);
                    break;
                default:
                    System.out.print("Invalid Entry.\n");
            }  // end switch
        }  // end while
    }  // end main()
    // -------------------------------------------------------------
    public static String getString() throws IOException
    {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String s = br.readLine();
            return s;
    }
    // -------------------------------------------------------------
    public static int getSize() throws IOException
    {
            int size = 0;
            size = getInt();
            return size;
    }
    // -------------------------------------------------------------
    public static char getChar() throws IOException
    {
            String s = getString();
            return s.charAt(0);
    }
    //-------------------------------------------------------------
    public static int getInt() throws IOException
    {
            String s = getString();
            return Integer.parseInt(s);
    }
    private int totalPoints;
    private Random rand;
    private point[] xLeft;
    private point[] xRight;
    private point[] yUp;
    private point[] yDown;
    private PriorityQ queY; 
    private PriorityQ queX; 
    private int midway;
    public point[] allCoords(){
        return allCoords;
    }
    private point[] allCoords;
    private double closestHypo;
    private pair lPair;
    private pair rPair;
    private pair closestPair;
    
    public DivideAndConquer(int points){
        //this is where we start, so intialize
        this.totalPoints = points;
        rand = new Random();
        this.lPair = new pair();
        this.rPair = new pair();
        this.xLeft = new point[points];
        this.xRight = new point[points];
        this.yUp = new point[points];
        this.yDown = new point[points];
        this.queX = new PriorityQ(totalPoints);
        this.queY = new PriorityQ(totalPoints);
        this.midway = 0;
        this.closestHypo = Integer.MAX_VALUE;
        System.out.println("Input Points: ");
        this.allCoords = new point[points];
        this.allCoords = generatePoints(this);
        this.closestPair = dAndQ(this);
    }
    public point[] generatePoints(DivideAndConquer dac)
    {
        for(int i = 0; i < dac.totalPoints; i++){
            dac.allCoords[i] = new point(rand.nextInt(100), rand.nextInt(100));
            System.out.println("(" + dac.allCoords[i].x + "," + dac.allCoords[i].y + ") ");
        }
        return dac.allCoords;
    }
    //sorts coordinates by x values
    private point[] sortCoordsByX(point[] coords, DivideAndConquer dac){
        dac.queX = new PriorityQ(coords.length);
        
        for(int i = 0; i < coords.length - 1; i++){
            dac.queX.insertX(coords[i]);
        }     
        point[] temp = new point[coords.length];
        for(int i = 0; i < coords.length - 1; i++){
            temp[i] = dac.queX.peekMin();
            dac.queX.remove();
        }
        return temp;
    }
    //sorts coordinates by y values
    private point[] sortCoordsByY(point[] coords, DivideAndConquer dac){
        dac.queY = new PriorityQ(coords.length);
        
        for(int i = 0; i < coords.length - 1; i++){
            dac.queY.insertY(coords[i]);
        }
        point[] temp = new point[coords.length];
        for(int i = 0; i < coords.length - 1; i++){
            temp[i] = dac.queY.peekMin();
            dac.queY.remove();
        }
        return temp;
    }
    //sets the left and right coords
    private void divideByX(point[] coords, DivideAndConquer dac){
        dac.midway = coords[coords.length/2].x;
        System.out.println("Dividing at Point[" + coords.length/2 + "]");
        int j = 0;
        int k = 0;
        for(int i = 0; i < coords.length - 1; i++){
            if((int)coords[i].x <= dac.midway){
                dac.xLeft[j] = coords[i];
                j++;
            }else{
                dac.xRight[k] = coords[i];
                k++;
            }
        }
    }
    private void divideByY(point[] coords, DivideAndConquer dac){
        dac.midway = coords[coords.length/2].y;
        int j = 0;
        int k = 0;
        for(int i = 0; i < coords.length - 1; i++){
            if((int)coords[i].y <= dac.midway){
                dac.yUp[j] = coords[i];
                j++;
            }else{
                dac.yDown[k] = coords[i];
                k++;
            }
        }
    }
    private point[] spanDiv = new point[totalPoints];
    private pair conquer(point[] xSorted, point[] ySorted, DivideAndConquer dac){
        System.out.println("Solving Problem: Point[" + 0 + "]...Point[" + (xSorted.length - 1) + "]");
        if(xSorted.length <= 3)
            return conquerBase(xSorted);
        xSorted = sortCoordsByX(xSorted, dac);
        divideByX(xSorted, dac);
        point[] temp = new point[xLeft.length + 1];
        temp = sortCoordsByY(xLeft, dac);
        dac.lPair = conquer(xLeft, temp, dac);
        temp = new point[xRight.length + 1];
        //reinitialize
        dac.queY = new PriorityQ(totalPoints);
        temp = sortCoordsByY(xRight, dac);
        dac.rPair = conquer(xRight, temp, dac);
        if(rPair.hypo < lPair.hypo)
            closestPair = rPair;
        else
            closestPair = lPair;
        ArrayList posCoords = new ArrayList();
        closestHypo = closestPair.hypo;
        midway = xRight[0].x;
        for(int i = 0; i < ySorted.length; i++){
            if(Math.abs(midway - ySorted[i].x) < closestHypo){
                posCoords.add(ySorted[i]);
            }
        }
        for(int i = 0; i < posCoords.size() - 1; i++){
            point coord1 = (point)posCoords.get(i);
            for (int j = i + 1; j < posCoords.size(); j++)
            {
                point coord2 = (point)posCoords.get(j);
                if ((coord2.y - coord1.y) >= closestHypo)
                    break;
                pair newPair = new pair();
                newPair.p1 = coord1;
                newPair.p2 = coord2;
                newPair.calcDist();
                if (newPair.hypo < closestHypo)
                {
                    closestHypo = newPair.hypo;
                    closestPair.update(coord1, coord2, closestHypo);
                }
            }
      }
      return closestPair;
    }
    public pair dAndQ(DivideAndConquer dac){
        point[] allCoordsXSort = new point[dac.allCoords.length];
        allCoordsXSort = sortCoordsByX(dac.allCoords, dac);

        point[] allCoordsYSort = new point[dac.allCoords.length];
        allCoordsYSort = sortCoordsByY(dac.allCoords, dac);
        
        return conquer(allCoordsXSort, allCoordsYSort, dac);
    }
    public pair conquerBase(point[] points)
    {
      if (points.length < 2)
        return null;
      pair closest = new pair();
      closest.p1 = points[0];
      closest.p2 = points[1];
      closest.calcDist();
      if(points.length > 2)
      {
        for(int i = 0; i < points.length - 1; i++)
        {
          point coord1 = points[i];
          for(int j = i + 1; j < points.length; j++)
          {
            point coord2 = points[j];
            pair pair = new pair();
            pair.p1 = coord1;
            pair.p2 = coord2;
            if (pair.hypo < closest.hypo)
              closest.update(coord1, coord2, pair.hypo);
          }
        }
      }
      return closest;
    }
}
