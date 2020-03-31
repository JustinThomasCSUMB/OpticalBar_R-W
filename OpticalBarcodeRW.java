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
               
            
         
         String[] sImageIn_2 =
         {
               "                                          ",
               "                                          ",
               "* * * * * * * * * * * * * * * * * * *     ",
               "*                                    *    ",
               "**** *** **   ***** ****   *********      ",
               "* ************ ************ **********    ",
               "** *      *    *  * * *         * *       ",
               "***   *  *           * **    *      **    ",
               "* ** * *  *   * * * **  *   ***   ***     ",
               "* *           **    *****  *   **   **    ",
               "****  *  * *  * **  ** *   ** *  * *      ",
               "**************************************    ",
               "                                          ",
               "                                          ",
               "                                          ",
               "                                          "

         };
        
         BarcodeImage bc = new BarcodeImage(sImageIn);
         DataMatrix dm = new DataMatrix(bc);
        
         // First secret message
         dm.translateImageToText();
         dm.displayTextToConsole();
         dm.displayImageToConsole();
         
         // second secret message
         bc = new BarcodeImage(sImageIn_2);
         dm.scan(bc);
         dm.translateImageToText();
         dm.displayTextToConsole();
         dm.displayImageToConsole();
         
         // create your own message
         dm.readText("What a great resume builder this is!");
         dm.generateImageFromText();
         dm.translateImageToText();
         dm.displayTextToConsole();
         dm.displayImageToConsole();
      /*
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
      DataMatrix dm = new DataMatrix(barcode1);
      
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
   }// end Main   
}// end OpticalBarcodeRW

interface BarcodeIO
{
   public boolean scan(BarcodeImage bcImage);
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
            boolean value = (strData[strDataRow].charAt(column) != ' ');
            imageData[imageDataRow][column] = value;                  
            setPixel(imageDataRow, column, value);
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
      /* Since imageData is a boolean array we need to check the bounds of
       * the array request to prevent an index out of bounds error.
       */
      if (row < 0 || row > MAX_HEIGHT || col < 0 || col > MAX_WIDTH) {
         System.out.println("Requested pixel is out of bounds!");
         return false;
      }
      else {
         imageData[row][col] = value;
         return true;
      }
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

class DataMatrix implements BarcodeIO
{
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';
   
   private BarcodeImage image;
   private String text;
   private int actualWidth = 0;
   private int actualHeight = 0;
   
   DataMatrix(){
      image = new BarcodeImage();
      text = "";
   }
   
   DataMatrix(BarcodeImage image){
      if(image == null) {
         this.image = new BarcodeImage();
      }
      this.scan(image);
      text = "";

   }
   
   DataMatrix(String text){
      image = new BarcodeImage();
      this.text = text;
   }
   
   public int getActualWidth() {
      return actualWidth;
   }
   
   public int getActualHeight() {
      return actualHeight;
   }
      
   @Override
   public boolean scan(BarcodeImage bcImage)
   {  
      try {
         this.image = (BarcodeImage)bcImage.clone();
         cleanImage();
      }catch(Exception ex) {
         return false;
      }
      this.actualHeight = 0;
      this.actualWidth = 0;
      for(int r = BarcodeImage.MAX_HEIGHT - 1; r >= 0; r--) 
      {
         if(image.getPixel(r, 0)) 
         {
            this.actualHeight++;
         }
      }
      
      for(int c = 0; c <= BarcodeImage.MAX_WIDTH -1; c++) 
      {
         if(image.getPixel(BarcodeImage.MAX_HEIGHT - 1, c)) 
         {
            this.actualWidth++;
         }
      }

      return true;
   }

   @Override
   public boolean readText(String text)
   {
      if(text == null) {
         return false;
      }
      
      this.text = text;
      return true;
   }
   
   private char readCharFromCol(int col) 
   {
      int bitVal = 0;
      int currentRow = 0;
      for(int r = BarcodeImage.MAX_HEIGHT - this.actualHeight + 1; r < BarcodeImage.MAX_HEIGHT - 1; r++) 
      {
         currentRow++;
         if(image.getPixel( r , col)) 
         {
            switch(currentRow) {
            case 1 : 
               bitVal += 128;
               break;
            case 2 :
               bitVal += 64;
               break;
            case 3 :
               bitVal += 32;
               break;
            case 4 :
               bitVal += 16;
               break;
            case 5 :
               bitVal += 8;
               break;
            case 6 :
               bitVal += 4;
               break;
            case 7 :
               bitVal += 2;
               break;
            case 8 :
               bitVal += 1;
               break;
            }
         }
      }
      return (char)bitVal;
   }
   
   private boolean writeCharToCol(int col, int code) 
   {
      int asciiVal = code;
      int currentRow = BarcodeImage.MAX_HEIGHT - this.actualHeight + 1;
      
      if(asciiVal - 128 >= 0) 
      {
         asciiVal -= 128;
         image.setPixel(currentRow, col, true);
      }
      
      currentRow++;
      
      if(asciiVal - 64 >= 0) 
      {
         asciiVal -= 64;
         image.setPixel(currentRow, col, true);
      }
      
      currentRow++;
      
      if(asciiVal - 32 >= 0)
      {
         asciiVal -= 32; 
         image.setPixel(currentRow, col, true);
      }
      
      currentRow++;
      
      if(asciiVal - 16 >= 0) 
      {
         asciiVal -= 16;   
         image.setPixel(currentRow, col, true);
      }
      
      currentRow++;
      
      if(asciiVal - 8 >= 0) 
      {
         asciiVal -= 8;  
         image.setPixel(currentRow, col, true);
      }
      
      currentRow++;
      
      if(asciiVal - 4 >= 0) 
      {
         asciiVal -= 4;   
         image.setPixel(currentRow, col, true);
      }
      
      currentRow++;
      
      if(asciiVal - 2 >= 0) 
      {
         asciiVal -= 2;  
         image.setPixel(currentRow, col, true);
      }
      
      currentRow++;
      
      if(asciiVal - 1 >= 0) 
      {
         asciiVal -= 1;
         image.setPixel(currentRow, col, true);
      }
      
      currentRow++;
      
      if(asciiVal == 0) 
      {
         return true;
      }
      
      return false;
   }

   @Override
   public boolean generateImageFromText()
   {
      this.image = new BarcodeImage();

      for(int row = BarcodeImage.MAX_HEIGHT - this.actualHeight; row < BarcodeImage.MAX_HEIGHT; row ++) 
      {
         image.setPixel(row, 0, true);
      }
      
      for(int col = 0; col < text.length() + 1; col++) 
      {   
         if(col % 2 == 0 ) 
         {
            image.setPixel(BarcodeImage.MAX_HEIGHT - this.actualHeight, col, true);
         }
         
         image.setPixel(BarcodeImage.MAX_HEIGHT - 1, col, true);
      }
      
      for(int i = 1; i <= this.text.length(); i++) 
      {
         writeCharToCol(i, this.text.charAt(i - 1));
      }

      return false;
   }

   @Override
   public boolean translateImageToText()
   {
      for(int c = 1; c < this.actualWidth - 1; c++) 
      {
         this.text += readCharFromCol(c);
      }
      return false;
   }

   @Override
   public void displayTextToConsole()
   {
      System.out.println(text);
   }

   @Override
   public void displayImageToConsole()
   {
      for(int r = BarcodeImage.MAX_HEIGHT - this.actualHeight; r < BarcodeImage.MAX_HEIGHT; r++) 
      {
         
         for(int c = 0; c <= this.actualWidth - 1; c++) 
         {
            if(image.getPixel(r, c)) 
            {
               System.out.print("*");
            }
            else 
            {
               System.out.print(" ");
            }
         }
         
         System.out.println();
         
      }
 
   }
   
   /**
    * walks through the image starting at bottom left corner
    * finds first pixel for starting row and sets the offset
    * writes content to tempRow and fills in offset
    * creates BarcodeImage and assigns to image
    */
   private void cleanImage() {
      int rowStart = -1;
      int columnStart = -1;
      String tempRow;
      String[] cleanedImage= new String[BarcodeImage.MAX_HEIGHT];
      Arrays.fill(cleanedImage, "");
      
      for(int r = BarcodeImage.MAX_HEIGHT - 1; r >= 0; r--) {
         // walk through rows starting at the last row
         tempRow = "";
         for(int c = 0; c < BarcodeImage.MAX_WIDTH; c++) {
            // walk through columns starting at position 0
            boolean pixel = image.getPixel(r, c);
            
            if(columnStart == -1 && pixel) {
               // found beginning of row flag, set only once
               columnStart = c;
            }
            
            if(rowStart == -1 && pixel) {
               rowStart = r;
            }
            
            // copy the row for the image, starting after left Limitation line
            if(columnStart != -1 && c >= columnStart) {
               tempRow = tempRow + (pixel?BLACK_CHAR:WHITE_CHAR);
            }
         }// end c loop
         
         // fill in the row offset
         if(columnStart != -1) {
         // check maxwidth, don't write past max (blank row cases)
            if(tempRow.length() < BarcodeImage.MAX_WIDTH) {
               for(int i = 0; i < columnStart; i++) {
                  tempRow += " ";
               }   
            }            
            int writingRow = (BarcodeImage.MAX_HEIGHT - 1 - rowStart) + r;
            cleanedImage[writingRow] = tempRow;  
         }                         
      }// end r loop
      
      int writingRow = (BarcodeImage.MAX_HEIGHT - 1 - rowStart);
      // fill in remaining rows from offset
      for(int i = writingRow; i >= 0; i--) {
         tempRow = "";
         for(int j = 0; j < BarcodeImage.MAX_WIDTH; j++) {
            tempRow += " ";
         }
         cleanedImage[i] = tempRow;
      }
      
      this.image = new BarcodeImage(cleanedImage);
      
   }
   
}// end DataMatrix

