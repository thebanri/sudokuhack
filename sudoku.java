import java.io.IOError;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class sudoku {
    public static void main(String[] args) {

        int[][] matrix = {
                {0, 0, 0, 5, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 9, 0, 0, 8, 3},
                {0, 0, 0, 0, 4, 2, 0, 0, 5},
                {0, 0, 0, 0, 0, 0, 1, 3, 0},
                {0, 2, 6, 7, 0, 0, 0, 0, 8},
                {0, 8, 0, 2, 0, 0, 7, 0, 0},
                {3, 0, 4, 0, 0, 0, 8, 0, 1},
                {0, 6, 0, 8, 5, 0, 0, 4, 0},
                {0, 0, 0, 0, 0, 0, 2, 0, 0}
        };


        ArrayList<Integer>[][] matrixlist = new ArrayList[9][9];


        solveSudoku(matrix, matrixlist);


        for (int i = 0; i < matrix.length; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("---------------------");
            }
            for (int j = 0; j < matrix[i].length; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print(" | ");
                }
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static void solveSudoku(int[][] matrix, ArrayList<Integer>[][] matrixlist) {
        int count = 10;
        while (count > 0) {
            count--;

            for (int i = 0; i < matrixlist.length; i++) {
                for (int j = 0; j < matrixlist[i].length; j++) {
                    matrixlist[i][j] = new ArrayList<Integer>();
                    matrixlist[i][j].add(matrix[i][j]);
                }
            }


            List<List<Integer>> lists = new ArrayList<>();
            List<List<Integer>> cordinats = new ArrayList<>();
            List<List<Integer>> listsmatrix = lists;


            putNumbers(matrix, matrixlist);
            double_singles(matrix, matrixlist);
            hidden_pairs_3x3(matrix, matrixlist);
            hidden_pairs_colrow(matrix, matrixlist);
            justtwo_3x3(matrix, matrixlist);
            justtwo_colrow(matrix, matrixlist);
            naked_single(matrix, matrixlist);
            three_cells(matrix, matrixlist);
            double_singles(matrix, matrixlist);
            rowcolList(matrix, matrixlist);


            for (int i = 0; i < matrixlist.length; i++) {
                for (int j = 0; j < matrixlist[i].length; j++) {


                    if ((i % 3 == 0 && j % 3 == 0)) {

                        cleanMatrix(listsmatrix);
                        placeonlist(listsmatrix, cordinats, matrix);
                        lists.clear();
                        cordinats.clear();
                    }
                    if (matrix[i][j] == 0) {
                        placeonlist(listsmatrix, cordinats, matrix);
                        cordinats.clear();
                        lists.clear();
                        int startRow = i - i % 3;
                        int startCol = j - j % 3;
                        for (int row = startRow; row < startRow + 3; row++) {
                            for (int col = startCol; col < startCol + 3; col++) {
                                if (matrix[row][col] == 0) {
                                    lists.add(new ArrayList<>(matrixlist[row][col]));
                                    cordinats.add(new ArrayList<>(List.of(row, col)));
                                }

                            }

                        }
                        cleanMatrix(listsmatrix);
                        placeonlist(listsmatrix, cordinats, matrix);
                    }
                    cleanMatrix(listsmatrix);
                    placeonlist(listsmatrix, cordinats, matrix);
                }
            }
            cleanMatrix(listsmatrix);
            placeonlist(listsmatrix, cordinats, matrix);

        }
    }

    public static void singles_in_colrow(int[][] matrix, ArrayList<Integer>[][] matrixlist) {

        for (int i = 0; i < matrixlist.length; i++) {
            for (int j = 0; j < matrixlist[i].length; j++) {
                int startRow = i - i % 3;
                int startCol = j - j % 3;
                if (matrix[i][j] == 0) {
                    for (int x = 1; x <= 9; x++) {
                        List<List<Integer>> lists = new ArrayList<>();
                        int value = 0;
                        for (int k = 0; k < 9; k++) {
                            if (matrix[i][k] == 0 && matrixlist[i][k].contains(x)) {
                                value++;
                                lists.add(List.of(i, k));
                            }
                        }

                    }
                }
            }
        }
    }


    public static void double_singles(int[][] matrix, ArrayList<Integer>[][] matrixlist) {
        for (int i = 0; i < matrixlist.length; i++) {
            for (int j = 0; j < matrixlist[i].length; j++) {
                int startRow = i - i % 3;
                int startCol = j - j % 3;
                if (matrix[i][j] == 0) {
                    for (int x = 1; x <= 9; x++) {
                        List<List<Integer>> lists = new ArrayList<>();
                        int value = 0;
                        for (int row = startRow; row < startRow + 3; row++) {
                            for (int col = startCol; col < startCol + 3; col++) {
                                if (matrix[row][col] == 0 && matrixlist[row][col].contains(x)) {
                                    value++;
                                    lists.add(List.of(row, col));
                                }
                            }
                        }
                        if (value == 2) {
                            if (lists.get(0).get(0).equals(lists.get(1).get(0))) {
                                for (int k = 0; k < 9; k++) {
                                    if (k != lists.get(0).get(1) && k != lists.get(1).get(1)) {
                                        matrixlist[lists.get(0).get(0)][k].remove(Integer.valueOf(x));
                                        if (matrixlist[lists.get(0).get(0)][k].size() == 1) {
                                            matrix[lists.get(0).get(0)][k] = matrixlist[lists.get(0).get(0)][k].get(0);
                                            putNumbers(matrix, matrixlist);
                                        }
                                    }
                                }
                            } else if (lists.get(0).get(1).equals(lists.get(1).get(1))) {

                                for (int k = 0; k < 9; k++) {
                                    if (k != lists.get(0).get(0) && k != lists.get(1).get(0)) {
                                        matrixlist[k][lists.get(0).get(1)].remove(Integer.valueOf(x));
                                        if (matrixlist[k][lists.get(0).get(1)].size() == 1) {
                                            matrix[k][lists.get(0).get(1)] = matrixlist[k][lists.get(0).get(1)].get(0);
                                            putNumbers(matrix, matrixlist);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    public static void naked_single(int[][] matrix, ArrayList<Integer>[][] matrixlist) {
        for (int i = 0; i < matrixlist.length; i++) {
            for (int j = 0; j < matrixlist[i].length; j++) {
                int startRow = i - i % 3;
                int startCol = j - j % 3;
                if (matrix[i][j] == 0) {
                    for (int x = 1; x <= 9; x++) {
                        List<List<Integer>> lists = new ArrayList<>();
                        int value = 0;
                        for (int row = startRow; row < startRow + 3; row++) {
                            for (int col = startCol; col < startCol + 3; col++) {
                                if (matrix[row][col] == 0 && matrixlist[row][col].contains(x)) {
                                    value++;
                                    lists.add(List.of(row, col));
                                }
                            }
                        }
                        if (value == 1) {
                            int a = lists.get(0).get(0);
                            int b = lists.get(0).get(1);
                            matrix[a][b] = x;
                            solveSudoku(matrix, matrixlist);

                        }
                    }
                }


            }
        }


    }

    public static void hidden_pairs_colrow(int[][] matrix, ArrayList<Integer>[][] matrixlist) {
        for (int i = 0; i < matrixlist.length; i++) {
            for (int j = 0; j < matrixlist[i].length; j++) {
                List<List<List<Integer>>> list = new ArrayList<>();
                List<Integer> numbs = new ArrayList<>();

                if (matrix[i][j] == 0) {
                    for (int x = 1; x <= 9; x++) {
                        List<List<Integer>> lists = new ArrayList<>();
                        int value = 0;
                        for (int k = 0; k < 9; k++) {
                            if (matrix[i][k] == 0 && matrixlist[i][k].contains(x)) {
                                value++;
                                lists.add(List.of(i, k));
                            }
                        }
                        if (value == 2) {
                            numbs.add(x);
                            list.add(lists);
                            System.out.println(list);
                        }

                        if (list.size() == 2 && !list.get(0).isEmpty() && numbs.size() == 2) {
                            if (list.get(0).equals(list.get(1))) {
                                for (int k = 0; k < 9; k++) {

                                    if ((i == list.get(0).get(0).get(0) && k == list.get(0).get(0).get(1)) || (i == list.get(0).get(1).get(0) && k == list.get(0).get(1).get(1))) {
                                        matrixlist[i][k].retainAll(numbs);
                                        System.out.println("Here" + matrixlist[i][k]);
                                    }

                                }

                            }

                        }
                    }

                    for (int x = 1; x <= 9; x++) {
                        List<List<Integer>> lists = new ArrayList<>();
                        int value = 0;
                        for (int k = 0; k < 9; k++) {
                            if (matrix[k][j] == 0 && matrixlist[k][j].contains(x)) {
                                value++;
                                lists.add(List.of(k, j));
                            }
                        }
                        if (value == 2) {
                            numbs.add(x);
                            list.add(lists);
                            System.out.println(list);
                        }

                        if (list.size() == 2 && !list.get(0).isEmpty() && numbs.size() == 2) {
                            if (list.get(0).equals(list.get(1))) {
                                for (int k = 0; k < 9; k++) {
                                    if ((k == list.get(0).get(0).get(0) && j == list.get(0).get(0).get(1)) || (k == list.get(0).get(1).get(0) && j == list.get(0).get(1).get(1))) {
                                        matrixlist[k][j].retainAll(numbs);
                                        System.out.println("Here" + matrixlist[k][j]);
                                    }

                                }

                            }

                        }
                    }
                }


            }
        }


    }


    public static void hidden_pairs_3x3(int[][] matrix, ArrayList<Integer>[][] matrixlist) {
        for (int i = 0; i < matrixlist.length; i++) {
            for (int j = 0; j < matrixlist[i].length; j++) {
                List<List<List<Integer>>> list = new ArrayList<>();
                List<Integer> numbs = new ArrayList<>();
                int startRow = i - i % 3;
                int startCol = j - j % 3;
                if (matrix[i][j] == 0) {
                    for (int x = 1; x <= 9; x++) {
                        List<List<Integer>> lists = new ArrayList<>();
                        int value = 0;
                        for (int row = startRow; row < startRow + 3; row++) {
                            for (int col = startCol; col < startCol + 3; col++) {
                                if (matrix[row][col] == 0 && matrixlist[row][col].contains(x)) {
                                    value++;
                                    lists.add(List.of(row, col));
                                }
                            }
                        }
                        if (value == 2) {
                            numbs.add(x);
                            list.add(lists);
                            System.out.println(list);
                        }

                        if (list.size() == 2 && !list.get(0).isEmpty() && numbs.size() == 2) {
                            if (list.get(0).equals(list.get(1))) {
                                for (int row = startRow; row < startRow + 3; row++) {
                                    for (int col = startCol; col < startCol + 3; col++) {
                                        if ((row == list.get(0).get(0).get(0) && col == list.get(0).get(0).get(1)) || (row == list.get(0).get(1).get(0) && col == list.get(0).get(1).get(1))) {
                                            matrixlist[row][col].retainAll(numbs);
                                            System.out.println("Here" + matrixlist[row][col]);
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            }
        }
    }

    public static void three_cells(int[][] matrix, ArrayList<Integer>[][] matrixlist) {

        for (int i = 0; i < matrixlist.length; i++) {
            for (int j = 0; j < matrixlist[i].length; j++) {
                if (matrix[i][j] == 0) {
                    if (matrixlist[i][j].size() == 3) {
                        List<List<Integer>> list2 = new ArrayList<>();
                        list2.add(matrixlist[i][j]);
                        int num1 = 1;
                        int num2 = 1;

                        for (int k = 0; k < 9; k++) {
                            if (matrix[i][k] == 0) {
                                if (matrixlist[i][j].equals(matrixlist[i][k]) && k != j) {
                                    num1++;
                                }
                            }
                        }

                        if (num1 == 3) {
                            for (int k = 0; k < 9; k++) {
                                if (matrix[i][k] == 0) {
                                    for (int a = 0; a < 9; a++) {
                                        if (matrix[i][a] == 0) {
                                            if (!matrixlist[i][a].equals(list2.get(0))) {
                                                matrixlist[i][a].removeAll(list2.get(0));
                                                if (matrixlist[i][a].size() == 1) {
                                                    matrix[i][a] = matrixlist[i][a].get(0);
                                                    num1 = 1;
                                                    solveSudoku(matrix, matrixlist);

                                                }

                                            }

                                        }
                                    }
                                }
                            }
                        }

                        for (int k = 0; k < 9; k++) {
                            if (matrix[k][j] == 0) {
                                if (matrixlist[i][j].equals(matrixlist[k][j]) && k != i) {
                                    num2++;
                                }
                            }
                        }

                        if (num2 == 3) {
                            for (int a = 0; a < 9; a++) {
                                if (matrix[a][j] == 0) {
                                    if (!matrixlist[a][j].equals(list2.get(0))) {
                                        matrixlist[a][j].removeAll(list2.get(0));
                                        if (matrixlist[a][j].size() == 1) {
                                            matrix[a][j] = matrixlist[a][j].get(0);
                                            solveSudoku(matrix, matrixlist);
                                        }
                                    }

                                }
                            }
                        }

                    }
                }
            }

        }


    }

    public static void justtwo_colrow(int[][] matrix, ArrayList<Integer>[][] matrixlist) {

        for (int i = 0; i < matrixlist.length; i++) {
            for (int j = 0; j < matrixlist[i].length; j++) {
                if (matrix[i][j] == 0) {
                    if (matrixlist[i][j].size() == 2) {
                        List<List<Integer>> list2 = new ArrayList<>();
                        list2.add(matrixlist[i][j]);

                        for (int k = 0; k < 9; k++) {
                            if (matrix[i][k] == 0) {
                                if (matrixlist[i][j].equals(matrixlist[i][k]) && k != j) {
                                    for (int a = 0; a < 9; a++) {
                                        if (matrix[i][a] == 0) {
                                            if (!matrixlist[i][a].equals(list2.get(0))) {
                                                matrixlist[i][a].removeAll(list2.get(0));
                                                if (matrixlist[i][a].size() == 1) {
                                                    matrix[i][a] = matrixlist[i][a].get(0);
                                                    solveSudoku(matrix, matrixlist);

                                                }

                                            }

                                        }
                                    }
                                }
                            }
                        }


                        for (int k = 0; k < 9; k++) {
                            if (matrix[k][j] == 0) {
                                if (matrixlist[i][j].equals(matrixlist[k][j]) && k != i) {
                                    for (int a = 0; a < 9; a++) {
                                        if (matrix[a][j] == 0) {
                                            if (!matrixlist[a][j].equals(list2.get(0))) {
                                                matrixlist[a][j].removeAll(list2.get(0));
                                                if (matrixlist[a][j].size() == 1) {
                                                    matrix[a][j] = matrixlist[a][j].get(0);
                                                    solveSudoku(matrix, matrixlist);
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }

    }


    public static void justtwo_3x3(int[][] matrix, ArrayList<Integer>[][] matrixlist) {
        for (int i = 0; i < matrixlist.length; i++) {
            for (int j = 0; j < matrixlist[i].length; j++) {

                if (matrix[i][j] == 0) {
                    int startRow = i - i % 3;
                    int startCol = j - j % 3;
                    if (matrixlist[i][j].size() == 2) {
                        List<List<Integer>> list = new ArrayList<>();
                        list.add(matrixlist[i][j]);
                        for (int row = startRow; row < startRow + 3; row++) {
                            for (int col = startCol; col < startCol + 3; col++) {
                                if (matrix[row][col] == 0) {
                                    if (list.get(0).equals(matrixlist[row][col]) && ((row != i && col == j) || (row == i && col != j))) {
                                        for (row = startRow; row < startRow + 3; row++) {
                                            for (col = startCol; col < startCol + 3; col++) {
                                                if (matrix[row][col] == 0) {
                                                    if (!list.isEmpty() && !list.get(0).equals(matrixlist[row][col])) {

                                                        matrixlist[row][col].removeAll(list.get(0));


                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }

                        }


                    }


                }

            }
        }
    }

    public static void putNumbers(int[][] matrix, ArrayList<Integer>[][] matrixlist) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }

        boolean changed = false;
        for (int i = 0; i < matrixlist.length; i++) {
            for (int j = 0; j < matrixlist[i].length; j++) {
                if (matrix[i][j] == 0) {
                    ArrayList<Integer> tempNumbers = new ArrayList<>(numbers);

                    for (int k = 0; k < matrixlist.length; k++) {
                        tempNumbers.remove((Integer) matrix[k][j]);
                    }
                    for (int k = 0; k < matrixlist[i].length; k++) {
                        tempNumbers.remove((Integer) matrix[i][k]);
                    }
                    int startRow = i - i % 3;
                    int startCol = j - j % 3;
                    for (int row = startRow; row < startRow + 3; row++) {
                        for (int col = startCol; col < startCol + 3; col++) {
                            tempNumbers.remove((Integer) matrix[row][col]);
                        }
                    }

                    System.out.println("(" + i + ", " + j + "): " + tempNumbers);
                    if (tempNumbers.size() == 1) {
                        matrix[i][j] = tempNumbers.get(0);
                        changed = true;
                    } else {
                        matrixlist[i][j] = tempNumbers;
                    }
                }
            }
        }

        if (changed) {

            solveSudoku(matrix, matrixlist); // Değişiklik yapıldıysa tekrar dene
        }
    }


    public static void rowcolList(int[][] matrix, ArrayList<Integer>[][] matrixlist) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                List<List<Integer>> list2 = new ArrayList<>();
                if (matrix[i][j] == 0) {
                    list2.add(new ArrayList<>(matrixlist[i][j]));
                    for (int k = 0; k < 9; k++) {
                        if (matrix[i][k] == 0 && k != j) {
                            list2.add(matrixlist[i][k]);
                        }
                    }


                    for (int x = 1; x < list2.size(); x++) {
                        list2.get(0).removeAll(list2.get(x));
                    }


                    int startRow = i - i % 3;
                    int startCol = j - j % 3;
                    for (int row = startRow; row < startRow + 3; row++) {
                        for (int col = startCol; col < startCol + 3; col++) {
                            if (!list2.isEmpty() && list2.get(0).size() == 1) {
                                if (matrix[row][col] == list2.get(0).get(0)) {
                                    list2.clear();
                                }
                            }
                        }
                    }
                    if (!list2.isEmpty() && list2.get(0).size() == 1) {
                        matrix[i][j] = list2.get(0).get(0);
                        putNumbers(matrix, matrixlist);
                    }

                    list2.clear();
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                List<List<Integer>> list2 = new ArrayList<>();
                if (matrix[i][j] == 0) {
                    list2.add(new ArrayList<>(matrixlist[i][j]));
                    for (int k = 0; k < 9; k++) {
                        if (matrix[k][j] == 0 && k != i) {
                            list2.add(matrixlist[k][j]);
                        }
                    }

                    System.out.println(list2);
                    for (int x = 1; x < list2.size(); x++) {
                        list2.get(0).removeAll(list2.get(x));
                    }
                    System.out.println(list2);

                    int startRow = i - i % 3;
                    int startCol = j - j % 3;
                    for (int row = startRow; row < startRow + 3; row++) {
                        for (int col = startCol; col < startCol + 3; col++) {
                            if (!list2.isEmpty() && list2.get(0).size() == 1) {
                                if (matrix[row][col] == list2.get(0).get(0)) {
                                    list2.clear();
                                }
                            }
                        }
                    }
                    if (!list2.isEmpty() && list2.get(0).size() == 1) {
                        matrix[i][j] = list2.get(0).get(0);
                        putNumbers(matrix, matrixlist);
                    }

                    list2.clear();
                }
            }
        }
    }


    public static void cleanMatrix(List<List<Integer>> matrix) {
        Set<Integer> seen = new HashSet<>();

        for (List<Integer> row : matrix) {
            for (int i = 0; i < row.size(); i++) {
                int num = row.get(i);
                if (seen.contains(num)) {
                    for (List<Integer> otherRow : matrix) {
                        if (otherRow != row && otherRow.contains(num)) {
                            otherRow.set(otherRow.indexOf(num), 0);
                        }
                    }
                    row.set(i, 0);
                } else {
                    seen.add(num);
                }
            }
        }
        for (List<Integer> row : matrix) {
            row.removeAll(List.of(0));
        }
    }

    public static void placeonlist(List<List<Integer>> list2, List<List<Integer>> cordinats, int[][] matrix) {

        for (int i = 0; i < list2.size(); i++) {
            List<Integer> lists = list2.get(i);
            List<Integer> cordins = cordinats.get(i);
            if (lists.size() == 1) {

                matrix[cordins.get(0)][cordins.get(1)] = lists.get(0);
            }
        }
    }
}










