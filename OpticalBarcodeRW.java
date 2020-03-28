import java.util.Arrays;

public class OpticalBarcodeRW
{
   public static void main( String [] args)
   {     
      // Clone Test
      BarcodeImage barcode1 = new BarcodeImage();
      BarcodeImage barcode2 = new BarcodeImage();
      
      barcode2 = (BarcodeImage) barcode1.clone();
      
      /* TODO: Delete Clone Confirmation
      if (barcode1 == barcode2)
      {
         System.out.println("Clone was unsuccessful");
      }
      else
      {
         System.out.println("Clone was successful");
      } */
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
  
   BarcodeImage()
   {
      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];
      
      for (boolean[] row: imageData) {
         Arrays.fill(row, false);
      }
      // TODO: Marcos delete test
      //System.out.println(Arrays.deepToString(imageData));
   }
   
   BarcodeImage(String[] strData) {
      
   }
   
   private Boolean checkSize(String[] data) {
      // Check for a null string
      if (data == null) {
         return false;
      }
      // Make sure the array doesn't have more strings than would fit MAX_HEIGHT
      if (data.length > MAX_HEIGHT) {
         return false;
      }
      /* Check each string for any nulls as well as make sure each string is not
       * longer than MAX_WIDTH */
      for (int i = 0; i < data.length; i++) {
         if (data[i] == null) {
            return false;
         }
         if (data[i].length() > MAX_WIDTH) {
            return false;
         }
      }
      // If non of our checks get triggered return true
      return true;
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

