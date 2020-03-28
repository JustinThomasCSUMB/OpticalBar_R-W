import java.util.Arrays;

public class OpticalBarcodeRW
{
   public static void main( String [] args)
   {     
      // Clone Test
      BarcodeImage barcode1 = new BarcodeImage();
      BarcodeImage barcode2 = new BarcodeImage();
      
      barcode2 = (BarcodeImage) barcode1.clone();
      
      if (barcode1 == barcode2)
      {
         System.out.println("Clone was unsuccessful");
      }
      else
      {
         System.out.println("Clone was successful");
      }
   }
}

interface BarcodeIO
{
   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();

}

class BarcodeImage implements Cloneable
{
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;
   private boolean[][] imageData;
  
   public BarcodeImage()
   {
      imageData = new boolean[MAX_WIDTH][MAX_HEIGHT];
      
      for (boolean[] row: imageData) {
         Arrays.fill(row, false);
      }
      System.out.println(Arrays.deepToString(imageData));
   }
   
   public Object clone()
   {
      try
      {
         BarcodeImage copy = (BarcodeImage) super.clone();
         return copy;
      }
      catch (CloneNotSupportedException e)
      {
         return null;
      }
   }
}

/**class DataMatrix implements BarcodeIO
{
   
}**/

