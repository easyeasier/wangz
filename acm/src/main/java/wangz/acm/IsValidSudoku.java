package wangz.acm;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangz on 17-6-16.
 */
public class IsValidSudoku {
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
        System.out.println(isValidSudou(board));
    }

    public static boolean isValidSudou(char[][] board){
        if(board == null || board.length == 0 || board.length != board[0].length){
            return false;
        }

        int len = board.length;
        char c;
        Set<String> sets = new HashSet<>();
        for(int i=0; i<len ;i++){
            for(int j=0; j< len;j++){
                c = board[i][j];
                if(c == '.'){
                    continue;
                }else if(c > '9' || c<'0'){
                    return false;
                }else if(!sets.add(c+" in row "+i)
                        || !sets.add(c+" in column " + j)
                        || !sets.add(c + " in block " +i/3+"-"+j/3)){
                    System.out.println("第"+(i+1)+"行"+",第"+(j+1)+"列错误");
                    return false;
                }
            }
        }

        return true;
    }
}
