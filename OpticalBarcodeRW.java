import java.util.Arrays;

public class OpticalBarcodeRW
{
   public static void main( String [] args)
   {
      String[] sImageIn =
         {
            "                                               ",
            "                                               ",
            "                                               ",
            "     * * * * * * * * * * * * * * * * * * * * * ",
            "     *                                       * ",
            "     ****** **** ****** ******* ** *** *****   ",
            "     *     *    ****************************** ",
            "     * **    * *        **  *    * * *   *     ",
            "     *   *    *  *****    *   * *   *  **  *** ",
            "     *  **     * *** **   **  *    **  ***  *  ",
            "     ***  * **   **  *   ****    *  *  ** * ** ",
            "     *****  ***  *  * *   ** ** **  *   * *    ",
            "     ***************************************** ",
            "                                               ",
            "                                               ",
            "                                               "
         };
      // TODO: Delete Marcos Bad String Test
      String[] badImageIn =
         {
            "                                               ",
       "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890QWERTYALLYOURBASEBELONGTOUSAB12345",
            "                                               ",
            "     * * * * * * * * * * * * * * * * * * * * * ",
            "     *                                       * ",
            "     ****** **** ****** ******* ** *** *****   ",
            "     *     *    ****************************** ",
            "     * **    * *        **  *    * * *   *     ",
            "     *   *    *  *****    *   * *   *  **  *** ",
            "     *  **     * *** **   **  *    **  ***  *  ",
            "     ***  * **   **  *   ****    *  *  ** * ** ",
            "     *****  ***  *  * *   ** ** **  *   * *    ",
            "     ***************************************** ",  
            "                                               ",
            "                                               ",
            "                                               "
         };
      // Clone Test
      BarcodeImage barcode1 = new BarcodeImage(sImageIn);
      BarcodeImage barcode2 = new BarcodeImage();
      
      barcode2 = (BarcodeImage) barcode1.clone();
      
      // TODO: Delete bad Image Test
      //BarcodeImage badbarcode = new BarcodeImage(badImageIn);
      
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
      // Chain the non parameterized constructor to pull in populated imageData
      this();
      // First check strData to make sure there is good data
      if (!checkSize(strData)) {
         System.out.println("There is an error with the data input!");
      }
      // We are going to be looping through a lot of ints so let's declare
      int imageDataRow, strDataRow;
      
      /*
       * We have two multidimensional arrays if we consider a string to be an
       * array of chars. We need to keep track of our position in both arrays.
       * Outer Loop: Loop from the bottom row of each array until we reach row 0
       * of strData
       */
      for (imageDataRow = MAX_HEIGHT - 1, strDataRow = strData.length - 1;
            strDataRow >= 0; imageDataRow--, strDataRow--) {
         /*
          * Inner Loop: Loop through each column of strData until you reach the
          * length of each string (This will deal with variable string lengths).
          * The integer column will be the same for each array in the inner loop
          * If the char at the same column position in strData is not blank
          * then change the column position in imageData to True; 
          */
         for (int column = 0; column < strData[strDataRow].length(); column++) {
            imageData[imageDataRow][column] = 
                  (strData[strDataRow].charAt(column) != ' ');
         }
      }
   }
   
   // Accessors and Mutators (Getters and Setters)
   public boolean getPixel(int row, int col) {
      /* Since imageData is a boolean array we need to check the bounds of
       * the array request to prevent an index out of bounds error.
       */
      if (row < 0 || row > MAX_HEIGHT || col < 0 || col > MAX_WIDTH) {
         System.out.println("Requested pixel is out of bounds!");
         return false;
      }
      else {
         return imageData[row][col];
      }
   }
   
   public boolean setPixel(int row, int col, boolean value) {
      return value;
      
   }
   
   // Private Helper Methods
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

