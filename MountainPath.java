import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;

public class MountainPath {
      public static int[][] grid;
  
  public static void init(String filename, int rows, int cols) 
  { 
    File file = new File(filename);
    Scanner in = null;
    try 
    {
      in = new Scanner(file);
    } 
    catch (FileNotFoundException e) 
    {
      e.printStackTrace();
      System.exit(1);    
    }
    
    grid = new int[rows][cols];
    for (int i = 0; i < grid.length; i++) 
    {
      for (int j = 0; j < grid[0].length; j++) 
      {
        grid[i][j] = in.nextInt();
      }
    }
  }
  
  public static int findMin() 
  {
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < grid.length; i++) 
    {
      for (int j = 0; j < grid[0].length; j++) 
      {
        if (grid[i][j] < min)
          min = grid[i][j];
      }
    }
    return min;
  }
  
  public static int findMax() 
  {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < grid.length; i++) 
    {
      for (int j = 0; j < grid[0].length; j++) 
      {
        if (grid[i][j] > max)
          max = grid[i][j];
      }
    }
    return max;
  }
  
  public static void drawMap()
  {
    StdDraw.setCanvasSize(grid[0].length, grid.length);
    StdDraw.setXscale(0.0, (double) grid[0].length);
    StdDraw.setYscale(0.0, (double) grid.length);
    int range = findMax() - findMin();
    for (int i = 0; i < grid.length; i++)
    {
      for (int j = 0; j < grid[0].length; j++) 
      {
        int diff = grid[grid.length - i - 1][j] - findMin();
        int c = (int) ((double) diff/range * 255.0);
        StdDraw.setPenColor(new Color(c, c, c));
        StdDraw.filledSquare((double) j, (double) i, 0.5);
      }
    }
  }
  
  public static int indexOfMinRow(int col) 
  {
    int index = 0;
    int row = 0;
    for (int j = 0; j < col; j++) 
    {
      if (grid[row][j] == findMin())
        index = row;
      row++;
    }
    return index;
  }
  
  public static int drawLowestElevPath(int row) 
  {
    int elev = 0;
    for (int col = 0; col < grid[row].length-1; col++) 
    {
      if (row == 0)
      {
        //a = fwd; b = fwd_up;
        int a = Math.abs(grid[row][col+1] - grid[row][col]);
        int b = Math.abs(grid[row+1][col+1] - grid[row][col]);
        if ((a < b) || (a == b))
        {
          StdDraw.filledSquare((double) col + 1, (double) row, 0.5);
          elev += a;
        }
        else
        {
          StdDraw.filledSquare((double) col + 1, (double) row + 1, 0.5);
          row++;
          elev += b;
        }
      }
      else if (row == grid.length - 1)
      {
        //c = fwd; d = fwd_down;
        int c = Math.abs(grid[row][col+1] - grid[row][col]);
        int d = Math.abs(grid[row-1][col+1] - grid[row][col]);
        if ((c < d) || (c == d))
        {
          StdDraw.filledSquare((double) col + 1, (double) row, 0.5);
          elev += c;
        }
        else
        {
          StdDraw.filledSquare((double) col + 1, (double) row - 1, 0.5);
          row--;
          elev += d;
        }
      }
      else
      {
        int fwd = Math.abs(grid[row][col+1] - grid[row][col]);
        int fwd_up = Math.abs(grid[row+1][col+1] - grid[row][col]);
        int fwd_down = Math.abs(grid[row-1][col+1] - grid[row][col]);
        boolean a = ((fwd == fwd_up) || (fwd == fwd_down)); boolean b = ((fwd == fwd_up) && (fwd == fwd_down));
        boolean c = ((fwd == fwd_up) && (fwd < fwd_down)); boolean d = ((fwd == fwd_down) && (fwd < fwd_up));
        boolean e = ((fwd < fwd_up) && (fwd < fwd_down));
        if ((((a || b) || c) || d) || e)
        {
          StdDraw.filledSquare((double) col + 1, (double) row, 0.5);
          elev += fwd;
        }
        else if ((fwd_up < fwd) && (fwd_up < fwd_down))
        {
          StdDraw.filledSquare((double) col + 1, (double) row + 1, 0.5);
          row++;
          elev += fwd_up;
        }
        else if ((fwd_down < fwd) && (fwd_down < fwd_up)) 
        {
          StdDraw.filledSquare((double) col + 1, (double) row - 1, 0.5);
          row--;
          elev += fwd_down;
        }
        else
        {
          if (Math.random() < 0.5) 
          {
            StdDraw.filledSquare((double) col + 1, (double) row - 1, 0.5);
            row--;
            elev += fwd_down;
          }
          else 
          {
            StdDraw.filledSquare((double) col + 1, (double) row + 1, 0.5);
            row++;
            elev += fwd_up;
          }
        }
      }
    }
    return elev;
  }
  
  public static int indexOfLowestElevPath()
  {
    int minelev = Integer.MAX_VALUE;
    int indx = 0;
    for (int i = 0; i < grid.length; i++) 
    {
      int elev = drawLowestElevPath(i);
      if (elev < minelev)
      {
        minelev = elev;
        indx = i;
      }
    }
    System.out.println("The lowest elevation is " + minelev + ".");
    return indx;
  }
  
  public static void main(String[] args) 
  {
    StdDraw.enableDoubleBuffering();
    Scanner input = new Scanner(System.in);
    System.out.print("Enter the file name/directory: ");
    String filename = input.nextLine();
    System.out.print("Enter the number of rows: ");
    int rows = input.nextInt();
    System.out.print("Enter the number of columns: ");
    int cols = input.nextInt();
    init(filename, rows, cols);
    drawMap();
    StdDraw.setPenColor(StdDraw.RED);
    System.out.println("The lowest elevation occurs at row number " + (rows - indexOfLowestElevPath() - 1) + ".");
    System.out.print("Enter the row for lowest elevation path between 0 and 479 inclusive: ");
    int row = input.nextInt();
    StdDraw.setPenColor(StdDraw.GREEN);
    System.out.println("The elevation at row " + row + " is: " + drawLowestElevPath(row) + ".");
    StdDraw.show();
  }
}
