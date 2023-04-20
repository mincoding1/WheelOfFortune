import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Wheel {

    public static void main(String[] args) throws IOException {
        ArrayList<StringBuilder> strs = new ArrayList<>();
        int[][] map = new int[5][50];
        String userdata = "";
        int n = 0;

        BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
        //BufferedReader br = new BufferedReader(new FileReader("input2.txt"));

        int n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            String temp;
            temp = br.readLine();
            strs.add(new StringBuilder(temp));
        }
        userdata = br.readLine();
        br.close();


        int conCnt = 0;
        int[] ffirst = { 0, 0, 0, 0, 0 };

        int sum = 0;
        int[] chance = { -1, -1, -1, -1, -1 };


        //하나씩 처리
        //26글자 for 돌면서 퀴즈 참석자가 하나씩 시도를 하는 것
        for (int i = 0; i < 26; i++) {

            //1. 2000 달러 찬스를 얻었는지 검사
            for (int y = 0; y < strs.size(); y++) {
                if (chance[y] != -1) {
                    for (int x = 0; x < strs.get(chance[y]).length(); x++) {
                        if (map[chance[y]][x] == 0 && strs.get(chance[y]).charAt(x) == userdata.charAt(i)) {
                            //획득 성공시 2000달러를 얻는다.
                            sum += 2000;
                            break;
                        }
                    }
                    chance[y] = -1;
                }
            }


            int flag = 0;
            int passCnt = 0;

            //2중 for 돌면서 정답 문자열을 하나씩 검사해서
            //퀴즈참가자가 던진 문자가 존재하는지 검사
            for (int y = 0; y < strs.size(); y++) {
                for (int x = 0; x < strs.get(y).length(); x++) {
                    if (map[y][x] == 1) continue;

                    //만약 퀴즈참석자가 요청한 문자가,
                    //정답문자열과 동일하다면
                    if (strs.get(y).charAt(x) == userdata.charAt(i)) {

                        //Let's First 점수인지 확인한다.
                        if (ffirst[y] == 0 && x == 0) {
                            sum += 1000;
                            ffirst[y] = 1;
                            chance[y] = y;
                        } else if (ffirst[y] == 0 && x != 0) {
                            ffirst[y] = 1;
                        }

                        //used배열
                        map[y][x] = 1;

                        //해당 문자를 _로 바꿔버린다.
                        strs.get(y).setCharAt(x, '_');
                        flag = 1;

                        //동일한 문자 개수를 Counting한다.
                        passCnt++;
                    }
                }
            }


            if (flag == 1) {
                conCnt++;
                sum += (conCnt * 100) * passCnt;
            } else {
                conCnt = 0;
                for (int t = 0; t < 5; t++) chance[t] = -1;
            }
        }

        System.out.println("$" + sum);
    }
}
