package com.mycompany.g9_01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class G9_01 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = -1, ascii = 0, win, lose, tie, GF, GA;
        char ch;
        String str;
        FIFA fifa = new FIFA();

        while (n != 5) {
            System.out.printf("%n=== 主選單 ===%n1.查看小組賽賽程%n2.輸入比賽結果%n3.查看小組賽積分表%n4.查看淘汰賽對陣%n5.離開系統%n請輸入數字(1-5):");
            n = sc.nextInt();

            //主選單設定只可輸入數字1-5，其他數字皆會顯示輸入錯誤
            if (n > 5 || n <= 0) {
                System.out.println("輸入錯誤，請輸入 1-5 之間的數字。");
            } else if (n == 1) {
                System.out.print("請輸入想知道的小組的賽程(A-L):");
                str = sc.next();
                
                //查看小組賽程
                if (str.length() == 1) {
                    ch = Character.toUpperCase(str.charAt(0));
                    if (ch >= 'A' && ch <= 'L') {
                        ascii = ch - 'A' + 1;
                        fifa.competitionSchedule(ascii);
                    } else {
                        System.out.println("⚠️ 請輸入 A-L 之間的小組代號！");
                    }
                } else {
                    System.out.println("⚠️ 請只輸入一個英文字母！");
                }

                //輸入比賽結果
            } else if (n == 2) {
                sc.nextLine();
                System.out.print("請輸入國家:");
                str = sc.next();
                System.out.print("請輸入勝場:");
                win = sc.nextInt();
                System.out.print("請輸入敗場:");
                lose = sc.nextInt();
                System.out.print("請輸入平手場次:");
                tie = sc.nextInt();
                System.out.print("請輸入進球數:");
                GF = sc.nextInt();
                System.out.print("請輸入失球數:");
                GA = sc.nextInt();

                if (fifa.inputCompetitionResults(str, win, lose, tie, GF, GA)) {
                    System.out.println("數據更新成功！");
                } else {
                    System.out.println("⚠️ 更新失敗：找不到該球隊，請檢查名稱是否完全正確。");
                }
                
                //查看小組積分表
            } else if (n == 3) {
                System.out.print("(未使用功能4前，不顯示排名)請輸入想知道的小組的積分表(A-L):");
                str = sc.next();
                if (str.length() == 1) {
                    ch = str.toUpperCase().charAt(0);
                    ascii = ch - 64;
                    fifa.showTeamsScore(ascii);
                }
                
                //進入淘汰賽對陣
            } else if (n == 4) {
                fifa.showEliminationMatch();
                System.out.println("\n[系統提示] 所有小組名次已計算/手動設定完畢。");
            }
        }
        System.out.println("謝謝使用，系統已離開。");
    }
}

class FIFA {

    private String[] teams;
    private int[][] scores = new int[8][48];

    // 建構子
    public FIFA() {
        this.teams = new String[]{
            "捷克", "墨西哥", "南非", "南韓",
            "加拿大", "波士尼亞與赫塞哥維納", "卡達", "瑞士",
            "巴西", "海地", "摩洛哥", "蘇格蘭",
            "澳洲", "巴拉圭", "土耳其", "美國",
            "庫拉索", "厄夫多", "德國", "象牙海岸",
            "荷蘭", "日本", "瑞典", "突尼西亞",
            "比利時", "埃及", "伊朗", "紐西蘭",
            "佛得角", "沙烏地阿拉伯", "西班牙", "烏拉圭",
            "法國", "挪威", "塞內加爾", "伊拉克",
            "阿爾及利亞", "阿根廷", "奧地利", "約旦",
            "哥倫比亞", "剛果民主共和國", "葡萄牙", "烏茲別克",
            "克羅埃西亞", "英格蘭", "迦納", "巴拿馬"
        };

        //建立初始值
        scores[0][0] = 2;
        scores[1][0] = 1;
        scores[2][0] = 0;
        scores[3][0] = 5;
        scores[4][0] = 3;
        scores[5][0] = 2;
        scores[6][0] = 6; // 捷克
        scores[0][1] = 1;
        scores[1][1] = 1;
        scores[2][1] = 1;
        scores[3][1] = 3;
        scores[4][1] = 3;
        scores[5][1] = 0;
        scores[6][1] = 4; // 墨西哥
        scores[0][2] = 1;
        scores[1][2] = 1;
        scores[2][2] = 1;
        scores[3][2] = 3;
        scores[4][2] = 3;
        scores[5][2] = 0;
        scores[6][2] = 4; // 南非
        scores[0][3] = 0;
        scores[1][3] = 1;
        scores[2][3] = 2;
        scores[3][3] = 2;
        scores[4][3] = 4;
        scores[5][3] = -2;
        scores[6][3] = 2; // 南韓

        // --- B 組 ---
        scores[0][4] = 2;
        scores[1][4] = 0;
        scores[2][4] = 1;
        scores[3][4] = 6;
        scores[4][4] = 2;
        scores[5][4] = 4;
        scores[6][4] = 7; // 加拿大
        scores[0][5] = 1;
        scores[1][5] = 1;
        scores[2][5] = 1;
        scores[3][5] = 4;
        scores[4][5] = 4;
        scores[5][5] = 0;
        scores[6][5] = 4; // 波士尼亞
        scores[0][6] = 1;
        scores[1][6] = 2;
        scores[2][6] = 0;
        scores[3][6] = 3;
        scores[4][6] = 5;
        scores[5][6] = -2;
        scores[6][6] = 3; // 卡達
        scores[0][7] = 0;
        scores[1][7] = 1;
        scores[2][7] = 2;
        scores[3][7] = 2;
        scores[4][7] = 4;
        scores[5][7] = -2;
        scores[6][7] = 2; // 瑞士

        // --- C 組 ---
        scores[0][8] = 3;
        scores[1][8] = 0;
        scores[2][8] = 0;
        scores[3][8] = 8;
        scores[4][8] = 1;
        scores[5][8] = 7;
        scores[6][8] = 9; // 巴西
        scores[0][9] = 1;
        scores[1][9] = 1;
        scores[2][9] = 1;
        scores[3][9] = 4;
        scores[4][9] = 5;
        scores[5][9] = -1;
        scores[6][9] = 4; // 海地
        scores[0][10] = 1;
        scores[1][10] = 2;
        scores[2][10] = 0;
        scores[3][10] = 3;
        scores[4][10] = 6;
        scores[5][10] = -3;
        scores[6][10] = 3;// 摩洛哥
        scores[0][11] = 0;
        scores[1][11] = 2;
        scores[2][11] = 1;
        scores[3][11] = 2;
        scores[4][11] = 5;
        scores[5][11] = -3;
        scores[6][11] = 1;// 蘇格蘭

        // --- D 組 ---
        scores[0][12] = 2;
        scores[1][12] = 1;
        scores[2][12] = 0;
        scores[3][12] = 5;
        scores[4][12] = 2;
        scores[5][12] = 3;
        scores[6][12] = 6;// 澳洲
        scores[0][13] = 2;
        scores[1][13] = 1;
        scores[2][13] = 0;
        scores[3][13] = 4;
        scores[4][13] = 3;
        scores[5][13] = 1;
        scores[6][13] = 6;// 巴拉圭
        scores[0][14] = 1;
        scores[1][14] = 2;
        scores[2][14] = 0;
        scores[3][14] = 3;
        scores[4][14] = 5;
        scores[5][14] = -2;
        scores[6][14] = 3;// 土耳其
        scores[0][15] = 1;
        scores[1][15] = 2;
        scores[2][15] = 0;
        scores[3][15] = 2;
        scores[4][15] = 4;
        scores[5][15] = -2;
        scores[6][15] = 3;// 美國

        // --- E 組 ---
        scores[0][16] = 2;
        scores[1][16] = 0;
        scores[2][16] = 1;
        scores[3][16] = 5;
        scores[4][16] = 2;
        scores[5][16] = 3;
        scores[6][16] = 7;// 庫拉索
        scores[0][17] = 1;
        scores[1][17] = 1;
        scores[2][17] = 1;
        scores[3][17] = 4;
        scores[4][17] = 4;
        scores[5][17] = 0;
        scores[6][17] = 4;// 厄夫多
        scores[0][18] = 1;
        scores[1][18] = 1;
        scores[2][18] = 1;
        scores[3][18] = 3;
        scores[4][18] = 4;
        scores[5][18] = -1;
        scores[6][18] = 4;// 德國
        scores[0][19] = 0;
        scores[1][19] = 2;
        scores[2][19] = 1;
        scores[3][19] = 2;
        scores[4][19] = 4;
        scores[5][19] = -2;
        scores[6][19] = 1;// 象牙海岸

        // --- F 組 ---
        scores[0][20] = 2;
        scores[1][20] = 0;
        scores[2][20] = 1;
        scores[3][20] = 5;
        scores[4][20] = 1;
        scores[5][20] = 4;
        scores[6][20] = 7;// 荷蘭
        scores[0][21] = 1;
        scores[1][21] = 1;
        scores[2][21] = 1;
        scores[3][21] = 3;
        scores[4][21] = 3;
        scores[5][21] = 0;
        scores[6][21] = 4;// 日本
        scores[0][22] = 1;
        scores[1][22] = 1;
        scores[2][22] = 1;
        scores[3][22] = 4;
        scores[4][22] = 4;
        scores[5][22] = 0;
        scores[6][22] = 4;// 瑞典
        scores[0][23] = 0;
        scores[1][23] = 2;
        scores[2][23] = 1;
        scores[3][23] = 1;
        scores[4][23] = 5;
        scores[5][23] = -4;
        scores[6][23] = 1;// 突尼西亞

        // --- G 組 ---
        scores[0][24] = 3;
        scores[1][24] = 0;
        scores[2][24] = 0;
        scores[3][24] = 7;
        scores[4][24] = 0;
        scores[5][24] = 7;
        scores[6][24] = 9;// 比利時
        scores[0][25] = 1;
        scores[1][25] = 1;
        scores[2][25] = 1;
        scores[3][25] = 3;
        scores[4][25] = 4;
        scores[5][25] = -1;
        scores[6][25] = 4;// 埃及
        scores[0][26] = 1;
        scores[1][26] = 2;
        scores[2][26] = 0;
        scores[3][26] = 2;
        scores[4][26] = 5;
        scores[5][26] = -3;
        scores[6][26] = 3;// 伊朗
        scores[0][27] = 0;
        scores[1][27] = 2;
        scores[2][27] = 1;
        scores[3][27] = 1;
        scores[4][27] = 4;
        scores[5][27] = -3;
        scores[6][27] = 1;// 紐西蘭

        // --- H 組 ---
        scores[0][28] = 0;
        scores[1][28] = 2;
        scores[2][28] = 1;
        scores[3][28] = 2;
        scores[4][28] = 5;
        scores[5][28] = -3;
        scores[6][28] = 1;// 佛得角
        scores[0][29] = 1;
        scores[1][29] = 2;
        scores[2][29] = 0;
        scores[3][29] = 3;
        scores[4][29] = 6;
        scores[5][29] = -3;
        scores[6][29] = 3;// 沙烏地阿拉伯
        scores[0][30] = 2;
        scores[1][30] = 0;
        scores[2][30] = 1;
        scores[3][30] = 6;
        scores[4][30] = 2;
        scores[5][30] = 4;
        scores[6][30] = 7;// 西班牙
        scores[0][31] = 2;
        scores[1][31] = 0;
        scores[2][31] = 1;
        scores[3][31] = 5;
        scores[4][31] = 3;
        scores[5][31] = 2;
        scores[6][31] = 7;// 烏拉圭

        // --- I 組 ---
        scores[0][32] = 2;
        scores[1][32] = 1;
        scores[2][32] = 0;
        scores[3][32] = 6;
        scores[4][32] = 3;
        scores[5][32] = 3;
        scores[6][32] = 6;// 法國
        scores[0][33] = 2;
        scores[1][33] = 1;
        scores[2][33] = 0;
        scores[3][33] = 5;
        scores[4][33] = 2;
        scores[5][33] = 3;
        scores[6][33] = 6;// 挪威
        scores[0][34] = 0;
        scores[1][34] = 2;
        scores[2][34] = 1;
        scores[3][34] = 2;
        scores[4][34] = 5;
        scores[5][34] = -3;
        scores[6][34] = 1;// 塞內加爾
        scores[0][35] = 0;
        scores[1][35] = 2;
        scores[2][35] = 1;
        scores[3][35] = 1;
        scores[4][35] = 4;
        scores[5][35] = -3;
        scores[6][35] = 1;// 伊拉克

        // --- J 組 ---
        scores[0][36] = 1;
        scores[1][36] = 2;
        scores[2][36] = 0;
        scores[3][36] = 3;
        scores[4][36] = 5;
        scores[5][36] = -2;
        scores[6][36] = 3;// 阿爾及利亞
        scores[0][37] = 2;
        scores[1][37] = 0;
        scores[2][37] = 1;
        scores[3][37] = 6;
        scores[4][37] = 2;
        scores[5][37] = 4;
        scores[6][37] = 7;// 阿根廷
        scores[0][38] = 2;
        scores[1][38] = 1;
        scores[2][38] = 0;
        scores[3][38] = 4;
        scores[4][38] = 3;
        scores[5][38] = 1;
        scores[6][38] = 6;// 奧地利
        scores[0][39] = 0;
        scores[1][39] = 2;
        scores[2][39] = 1;
        scores[3][39] = 2;
        scores[4][39] = 5;
        scores[5][39] = -3;
        scores[6][39] = 1;// 約旦

        // --- K 組 ---
        scores[0][40] = 2;
        scores[1][40] = 1;
        scores[2][40] = 0;
        scores[3][40] = 5;
        scores[4][40] = 3;
        scores[5][40] = 2;
        scores[6][40] = 6;// 哥倫比亞
        scores[0][41] = 0;
        scores[1][41] = 2;
        scores[2][41] = 1;
        scores[3][41] = 2;
        scores[4][41] = 6;
        scores[5][41] = -4;
        scores[6][41] = 1;// 剛果民主共和國
        scores[0][42] = 2;
        scores[1][42] = 0;
        scores[2][42] = 1;
        scores[3][42] = 6;
        scores[4][42] = 2;
        scores[5][42] = 4;
        scores[6][42] = 7;// 葡萄牙
        scores[0][43] = 1;
        scores[1][43] = 2;
        scores[2][43] = 0;
        scores[3][43] = 3;
        scores[4][43] = 5;
        scores[5][43] = -2;
        scores[6][43] = 3;// 烏茲別克

        // --- L 組 ---
        scores[0][44] = 2;
        scores[1][44] = 0;
        scores[2][44] = 1;
        scores[3][44] = 4;
        scores[4][44] = 1;
        scores[5][44] = 3;
        scores[6][44] = 7;// 克羅埃西亞
        scores[0][45] = 2;
        scores[1][45] = 0;
        scores[2][45] = 1;
        scores[3][45] = 5;
        scores[4][45] = 2;
        scores[5][45] = 3;
        scores[6][45] = 7;// 英格蘭
        scores[0][46] = 1;
        scores[1][46] = 2;
        scores[2][46] = 0;
        scores[3][46] = 2;
        scores[4][46] = 4;
        scores[5][46] = -2;
        scores[6][46] = 3;// 迦納
        scores[0][47] = 0;
        scores[1][47] = 3;
        scores[2][47] = 0;
        scores[3][47] = 1;
        scores[4][47] = 5;
        scores[5][47] = -4;
        scores[6][47] = 0;// 巴拿馬
    }

    // 查詢小組賽賽程
    public void competitionSchedule(int ascii) {
        int startIndex = (ascii - 1) * 4;
        String team1 = teams[startIndex];
        String team2 = teams[startIndex + 1];
        String team3 = teams[startIndex + 2];
        String team4 = teams[startIndex + 3];

        char group = (char) ('A' + ascii - 1);

        System.out.println("\n===== " + group + " 組賽程 =====");

        System.out.println("\n【第一輪】");
        System.out.println(team1 + " VS " + team2);
        System.out.println(team3 + " VS " + team4);

        System.out.println("\n【第二輪】");
        System.out.println(team1 + " VS " + team3);
        System.out.println(team2 + " VS " + team4);

        System.out.println("\n【第三輪】");
        System.out.println(team1 + " VS " + team4);
        System.out.println(team2 + " VS " + team3);
    }

    // 輸入比賽積分
    public boolean inputCompetitionResults(String teamname, int win, int lose, int tie, int GF, int GA) {

        teamname = teamname.trim().replaceAll("\\s+", "");

        for (int i = 0; i < 48; i++) {

            String dbName = this.teams[i].trim().replaceAll("\\s+", "");

            if (dbName.equals(teamname)) {

                //將使用者輸入的資料輸入二維陣列
                this.scores[0][i] = win;
                this.scores[1][i] = lose;
                this.scores[2][i] = tie;
                this.scores[3][i] = GF;
                this.scores[4][i] = GA;
                this.scores[5][i] = GF - GA;
                this.scores[6][i] = win * 3 + tie;
                return true;
            }
        }
        return false;
    }

    // 查詢小組積分
    public void showTeamsScore(int ascii) {
        int startIndex = (ascii - 1) * 4;
        System.out.printf("%-24s %-4s %-4s %-4s %-6s %-6s %-6s %-6s %-10s%n",
                "隊伍名稱", "勝", "敗", "平", "進球", "失球", "淨勝", "積分", "小組排名");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = startIndex; i < startIndex + 4; i++) {
            String displayName = this.teams[i];
            if (displayName.length() > 8) {
                displayName = displayName.substring(0, 7) + "..";
            }

            String rankText;

            if (this.scores[7][i] == 0) {
                rankText = "尚未計算";
            } else {
                rankText = "第" + this.scores[7][i] + "名";
            }
            System.out.printf("%-24s %-5d %-5d %-5d %-7d %-7d %-7d %-7d %s%n",
                    displayName,
                    this.scores[0][i],
                    this.scores[1][i],
                    this.scores[2][i],
                    this.scores[3][i],
                    this.scores[4][i],
                    this.scores[5][i],
                    this.scores[6][i],
                    rankText
            );
        }

    }

    // 顯示淘汰賽對陣（計算所有小組排名）
    public void showEliminationMatch() {
        for (int i = 1; i <= 12; i++) {
            int j = (i - 1) * 4;
            compareRanking(this.teams[j], this.teams[j + 1], this.teams[j + 2], this.teams[j + 3]);
        }

        System.out.println("\n[系統提示] 淘汰賽對陣抽籤/對局生成完畢。");
        ArrayList<String> qualifiedTeams = new ArrayList<>();
        ArrayList<Integer> thirdPlaceIndexes = new ArrayList<>();

        for (int i = 0; i < 48; i++) {
            if (this.scores[7][i] == 1 || this.scores[7][i] == 2) {
                qualifiedTeams.add(this.teams[i]);
            } else if (this.scores[7][i] == 3) {
                thirdPlaceIndexes.add(i); // 記錄小組第三名的隊伍索引
            }
        }

        //抓出每小組第三的前八名
        for (int i = 0; i < thirdPlaceIndexes.size() - 1; i++) {
            for (int j = 0; j < thirdPlaceIndexes.size() - i - 1; j++) {
                int idx1 = thirdPlaceIndexes.get(j);
                int idx2 = thirdPlaceIndexes.get(j + 1);
                boolean swap = false;

                if (this.scores[6][idx2] > this.scores[6][idx1]) {
                    swap = true;
                } else if (this.scores[6][idx2] == this.scores[6][idx1]) {
                    if (this.scores[5][idx2] > this.scores[5][idx1]) {
                        swap = true;
                    } else if (this.scores[5][idx2] == this.scores[5][idx1]) {
                        if (this.scores[3][idx2] > this.scores[3][idx1]) {
                            swap = true;
                        }
                    }
                }

                if (swap) {
                    int temp = thirdPlaceIndexes.get(j);
                    thirdPlaceIndexes.set(j, thirdPlaceIndexes.get(j + 1));
                    thirdPlaceIndexes.set(j + 1, temp);
                }
            }
        }

        if (thirdPlaceIndexes.size() < 8) {
            System.out.println("⚠️ 錯誤：第三名隊伍不足 8 隊，無法產生完整淘汰賽名單！");
            return;
        }

        for (int i = 0; i < 8; i++) {
            int approvedIdx = thirdPlaceIndexes.get(i);
            qualifiedTeams.add(this.teams[approvedIdx]);
        }

        System.out.print("\n本次進入淘汰賽的隊伍有:");
        for (int i = 0; i < qualifiedTeams.size(); i++) {
            System.out.print(qualifiedTeams.get(i));
            if (i < qualifiedTeams.size() - 1) {
                System.out.print(", "); // 隊伍之間用逗號隔開
            }
        }
    }

    // 比較小組內名次
    private void compareRanking(String a, String b, String c, String d) {
        int index = 0;
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> arr = new ArrayList<>();

        // 1. 找到這一組第一隊的 index
        for (int i = 0; i < 48; i++) {
            if (a.equals(this.teams[i])) {
                index = i;
                arr.add(this.scores[6][i]);
                arr.add(this.scores[6][i + 1]);
                arr.add(this.scores[6][i + 2]);
                arr.add(this.scores[6][i + 3]);
                break;
            }
        }

        int[] groupTeams = {index, index + 1, index + 2, index + 3};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3 - i; j++) {
                int t1 = groupTeams[j];
                int t2 = groupTeams[j + 1];
                boolean swap = false;

                if (this.scores[6][t2] > this.scores[6][t1]) {
                    swap = true;
                } else if (this.scores[6][t2] == this.scores[6][t1]) {
                    if (this.scores[5][t2] > this.scores[5][t1]) {
                        swap = true;
                    } else if (this.scores[5][t2] == this.scores[5][t1]) {
                        if (this.scores[3][t2] > this.scores[3][t1]) {
                            swap = true;
                        }
                    }
                }

                if (swap) {
                    int temp = groupTeams[j];
                    groupTeams[j] = groupTeams[j + 1];
                    groupTeams[j + 1] = temp;
                }
            }
        }

        for (int rank = 0; rank < 4; rank++) {
            this.scores[7][groupTeams[rank]] = rank + 1;
        }

        if (isScoreEquals(arr, 0, 3)) {
            char groupLetter = (char) ('A' + (index / 4));
            boolean[] hasAsked = new boolean[4];

            for (int i = 0; i < 4; i++) {
                if (hasAsked[i]) {
                    continue;
                }

                int targetScore = arr.get(i);
                ArrayList<Integer> sameScoreTeamIndexes = scoreFindTeams(targetScore, index);

                if (sameScoreTeamIndexes.size() > 1) {

                    int higherCount = 0;
                    for (int j = 0; j < 4; j++) {
                        if (arr.get(j) > targetScore) {
                            higherCount++;
                        }
                    }
                    int minRank = higherCount + 1;
                    int maxRank = higherCount + sameScoreTeamIndexes.size();

                    System.out.println("\n⚠️ 偵測到 " + groupLetter + " 組有積分相同狀況！");
                    System.out.println("----------------------------------------");
                    System.out.println("以下球隊在後台積分同為 " + targetScore + " 分：");
                    for (int teamIdx : sameScoreTeamIndexes) {
                        System.out.println("- " + this.teams[teamIdx] + " (淨勝球: " + this.scores[5][teamIdx] + ", 進球數: " + this.scores[3][teamIdx] + ")");
                    }
                    System.out.println("[系統提示] 請為這幾隊設定排名，可選的名次範圍為 (" + minRank + "-" + maxRank + ")");

                    ArrayList<Integer> usedRanks = new ArrayList<>();
                    for (int teamIdx : sameScoreTeamIndexes) {
                        int rank;
                        while (true) {
                            System.out.print("請輸入 [" + this.teams[teamIdx] + "] 的最終名次 (" + minRank + "-" + maxRank + "): ");

                            if (!sc.hasNextInt()) {
                                System.out.println("⚠️ 請輸入數字！");
                                sc.next();
                                continue;
                            }

                            rank = sc.nextInt();

                            if (rank < minRank || rank > maxRank) {
                                System.out.println("⚠️ 名次超出範圍，請重新輸入！");
                                continue;
                            }

                            if (usedRanks.contains(rank)) {
                                System.out.println("⚠️ 該名次已被使用，請重新輸入！");
                                continue;
                            }

                            break;
                        }
                        usedRanks.add(rank);
                        this.scores[7][teamIdx] = rank;
                        hasAsked[teamIdx - index] = true;
                    }
                }
            }
        }
    }

    // 撈隊伍索引值
    private ArrayList<Integer> scoreFindTeams(int score, int index) {
        ArrayList<Integer> teamIndex = new ArrayList<>();
        for (int i = index; i < index + 4; i++) {
            if (score == this.scores[6][i]) {
                teamIndex.add(i);
            }
        }
        return teamIndex;
    }

    // 比較分數有無相等 有則回傳true
    private boolean isScoreEquals(ArrayList<Integer> arr, int start, int end) {
        if (start >= end) {
            return false;
        }
        for (int i = start + 1; i <= end; i++) {
            if (arr.get(start).equals(arr.get(i))) {
                return true;
            }
        }
        return isScoreEquals(arr, start + 1, end);
    }
}
