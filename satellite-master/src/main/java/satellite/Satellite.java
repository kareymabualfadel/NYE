package satellite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Satellite {

    static int[][] oldImage;
    static int[][] newImage;
    static int noOfRows, noOfCols;

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            init(reader);      // read N, M and both images
            solve();           // find rectangle and print result
            reader.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // read sizes and both images (ONE method for reading)
    static void init(BufferedReader reader) throws IOException {
        noOfRows = Integer.parseInt(reader.readLine().trim());
        noOfCols = Integer.parseInt(reader.readLine().trim());
        oldImage = new int[noOfRows][noOfCols];
        newImage = new int[noOfRows][noOfCols];
        readImage(reader, oldImage);
        readImage(reader, newImage);
    }

    // main logic
    static void solve() {
        // ONE method used to compute all 4 boundaries:
        int x1 = findBoundary(true,  true);   // top row
        int x2 = findBoundary(true,  false);  // bottom row
        int y1 = findBoundary(false, true);   // left column
        int y2 = findBoundary(false, false);  // right column

        if (x1 > x2 || y1 > y2) {
            System.out.println("The two images are the same");
        } else {
            // +1 because output needs 1-based indices
            System.out.println((x1 + 1) + " " + (y1 + 1) + " " + (x2 + 1) + " " + (y2 + 1));
        }
    }

    // read one image (N rows * M columns) into the given array
    static void readImage(BufferedReader reader, int[][] image) throws IOException {
        for (int i = 0; i < noOfRows; i++) {
            String[] parts = reader.readLine().trim().split("\\s+");
            for (int j = 0; j < noOfCols; j++) {
                image[i][j] = Integer.parseInt(parts[j]);
            }
        }
    }

    // check if whole row is identical in both images
    static boolean equalRow(int row) {
        for (int col = 0; col < noOfCols; col++) {
            if (oldImage[row][col] != newImage[row][col]) return false;
        }
        return true;
    }

    // check if whole column is identical in both images
    static boolean equalCol(int col) {
        for (int row = 0; row < noOfRows; row++) {
            if (oldImage[row][col] != newImage[row][col]) return false;
        }
        return true;
    }

    // ONE method that can find x1, x2, y1, y2
    // isRow: true = work on rows, false = columns
    // fromStart: true = search from top/left, false = from bottom/right
    static int findBoundary(boolean isRow, boolean fromStart) {
        int index = fromStart ? 0 : (isRow ? noOfRows - 1 : noOfCols - 1);
        int limit = fromStart ? (isRow ? noOfRows : noOfCols) : -1;
        int step  = fromStart ? 1 : -1;

        while (index != limit && (isRow ? equalRow(index) : equalCol(index))) {
            index += step;
        }
        return index;
    }
}
