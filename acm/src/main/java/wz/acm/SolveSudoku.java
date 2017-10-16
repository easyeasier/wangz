package wz.acm;

/**
 * Created by wangz on 17-6-16.
 */
public class SolveSudoku {
    public static void main(String[] args) {
        char[][] board = {
                {'.','.','9','7','4','8','.','.','.'},
                {'7','.','.','.','.','.','.','.','.'},
                {'.','2','.','1','.','9','.','.','.'},
                {'.','.','7','.','.','.','2','4','.'},
                {'.','6','4','.','1','.','5','9','.'},
                {'.','9','8','.','.','.','3','.','.'},
                {'.','.','.','8','.','3','.','2','.'},
                {'.','.','.','.','.','.','.','.','6'},
                {'.','.','.','2','7','5','9','.','.'},
        };

        System.out.println("给定数独==================");
        for(int row =0;row<9;row++){
            for(int column = 0;column<9;column++){
                System.out.print(board[row][column] + "  ");
            }
            System.out.println();
        }

        if(solveSudoku(board)){
            System.out.println();
            System.out.println("完整数独=================");
            for(int row =0;row<9;row++){
                for(int column = 0;column<9;column++){
                    System.out.print(board[row][column] + "  ");
                }
                System.out.println();
            }
        }
    }

    /**
     * 紧跟上题isValidSudoku
     * 思路：
     * 1. 穷举法，对每一个待定字段进行1-9测试isValidSudoku
     * 2. 如果当前可行，填充当前节点，对新的数独进行递归校验
     * 3. 如果校验成功，重复1.2知道全部填充完成，如果不成功，返回上一层方法栈，重复1.2
     * @param board
     */
    public static boolean solveSudoku(char[][] board) {
        if (board == null || board.length == 0 || board.length != board[0].length) {
            return false;
        }

        int len = board.length;
        for (int row = 0; row < len; row++) {
            for (int column = 0; column < len; column++) {
                char target = board[row][column];
                if (target == '.') {
                    for (char k = '1'; k < '1' + len; k++) {
                        if (isValidSudoku(board, row, column, k)) {
                            board[row][column] = k;
                            if(solveSudoku(board)){
                                return true;
                            }
                            board[row][column] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidSudoku(char[][] board, int row, int column, char target) {
        int len = board.length;
        int blockLength = (int) Math.sqrt(len);
        int blockRowStart = row / blockLength * blockLength;   // 0 1 2   3 4   : 4/3 = 1*3 =3
        int blockColumnStart = column / blockLength * blockLength;   // 0 1 2   3 4   : 4/3 = 1*3 =3
        for (int i = 0; i < len; i++) {
            if (i != column && board[row][i] == target) {
                return false;
            }

            if (i != row && board[i][column] == target) {
                return false;
            }
        }

        for (int i = blockRowStart; i < blockRowStart + blockLength; i++) {
            for (int j = blockColumnStart; j < blockLength + blockColumnStart; j++) {
                if (i == row && j == column) {
                    continue;
                } else if (board[i][j] == target) {
                    return false;
                }
            }
        }

        return true;
    }
}
