import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku{
    public static void main(String[] args) {

        int[][] matrix1 = {
                {0, 3, 0, 0, 0, 0, 0, 0, 0},
                {9, 0, 0, 0, 3, 0, 5, 0, 0},
                {0, 0, 8, 0, 0, 5, 1, 0, 3},
                {0, 6, 0, 5, 0, 0, 7, 0, 0},
                {5, 0, 0, 3, 6, 7, 2, 4, 0},
                {4, 0, 0, 8, 0, 0, 6, 0, 0},
                {3, 0, 7, 0, 8, 0, 9, 1, 6},
                {0, 0, 2, 6, 9, 0, 4, 7, 5},
                {6, 9, 4, 7, 5, 0, 0, 0, 2}
        };

        ArrayList<Integer>[][] matrix = new ArrayList[9][9];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new ArrayList<Integer>();
                matrix[i][j].add(matrix1[i][j]);
            }
        }

        // Yeni bir listeye 1-9 arası rakamları ekle
        List<List<Integer>> lists = new ArrayList<>();
        List<List<Integer>> removedLists = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }

        // Matrisi tarayarak işlem yap
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix1[i][j] == 0) {
                    // 0 olan eleman için yeni bir liste oluştur
                    List<Integer> tempNumbers = new ArrayList<>(numbers);

                    // Satırı tarayarak listeden elemanları çıkart
                    for (int k = 0; k < matrix.length; k++) {
                        tempNumbers.remove((Integer) matrix1[k][j]); // Sütun elemanları
                    }

                    // Sütunu tarayarak listeden elemanları çıkart
                    for (int k = 0; k < matrix[i].length; k++) {
                        tempNumbers.remove((Integer) matrix1[i][k]); // Satır elemanları
                    }

                    // 3x3'lük alanı tarayarak listeden elemanları çıkart
                    int startRow = i - i % 3;
                    int startCol = j - j % 3;
                    for (int row = startRow; row < startRow + 3; row++) {
                        for (int col = startCol; col < startCol + 3; col++) {
                            tempNumbers.remove((Integer) matrix1[row][col]);
                        }
                    }

                    System.out.println("(" + i + ", " + j + "): " + tempNumbers);
                    matrix[i][j] = (ArrayList<Integer>) tempNumbers;

                }
            }
        }
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                if (matrix1[i][j] == 0) {
                    lists.clear();
                    int startRow = i - i % 3;
                    int startCol = j - j % 3;
                    for (int row = startRow; row < startRow + 3; row++) {
                        for (int col = startCol; col < startCol + 3; col++) {
                            if (matrix1[row][col] == 0) {
                                lists.add(new ArrayList<>(matrix[row][col]));
                            }
                        }
                    }

                    // Her 3x3'lük alt dizinin ilk elemanı için listeyi temizle
                    if (i % 3 == 0 && j % 3 == 0) {
                        for (int row = startRow; row < startRow + 3; row++) {
                            for (int col = startCol; col < startCol + 3; col++) {
                                if (matrix1[row][col] == 0) {
                                lists.clear();
                                }
                            }
                        }
                    }
                }

            }

        }
        System.out.println(lists);
            }

        }




