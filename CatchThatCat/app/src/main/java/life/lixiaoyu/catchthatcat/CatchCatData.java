package life.lixiaoyu.catchthatcat;

import android.util.Log;

import java.util.Random;

public class CatchCatData {
    private static final String TAG = "CatchCatData";

    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_BLOCKED = 1;
    public static final int TYPE_CAT = 9;

    public static final int RESULT_CONTINUE = 0;
    public static final int RESULT_WIN = 1;
    public static final int RESULT_LOSE = 2;

    private int[][] gameData;
    private int catPositionJ;   //猫的横向坐标
    private int catPositionI;   //猫的纵向坐标

    private boolean isGameOver = false;   //用于判断游戏是否结束的标志，true表示结束，false表示继续游戏
    private boolean isWinner = false;     //用于判断玩家是否赢了的标志，true表示玩家赢（猫被困住），false表示玩家输（猫逃脱）
    private int[] distances = new int[6];  //用来记录猫到边缘的六个方向上的距离，-1表示不可达，从左上方顺时针开始计算

    public CatchCatData() {
        gameData = new int[11][11];
        initData();
    }

    private void initData() {
        //设置猫所在的位置
        gameData[5][5] = TYPE_CAT;
        catPositionJ = 5;
        catPositionI = 5;
        //随机生成8个障碍

        int blockNum = 0;
        while (blockNum < 8) {
            Random random = new Random();
            int randomI = random.nextInt(9) + 1;
            int randomJ = random.nextInt(9) + 1;
            if (gameData[randomI][randomJ] == TYPE_EMPTY) {
                gameData[randomI][randomJ] = TYPE_BLOCKED;
            }
            blockNum++;
        }
    }

    public int getN() {
        return 11;
    }

    public int[][] getGameData() {
        return gameData;
    }

    public void setEmpty(int i, int j) {
        gameData[i][j] = TYPE_EMPTY;
    }

    public void setBlocked(int i, int j) {
        gameData[i][j] = TYPE_BLOCKED;
    }

    public void setCat(int i, int j) {
        gameData[i][j] = TYPE_CAT;
    }

    public boolean isBlockedInPosition(int i, int j) {
        return gameData[i][j] == TYPE_BLOCKED;
    }

    public boolean isEmptyInPosition(int i, int j) {
        return gameData[i][j] == TYPE_EMPTY;
    }

    public boolean isCatInPosition(int i, int j) {
        return gameData[i][j] == TYPE_CAT;
    }

    /**
     * 猫逃离
     */
    public void catEscape() {
        calculateDistances();
        //判断猫是否被围住
        if (isCatTrapped()) {
            isGameOver = true;
            isWinner = true;
            return;
        }
        //从六个方向的距离中选出可达边缘的最短距离
        int minDis = 5;
        for (int i = 0; i < 6; i++) {
            if (distances[i] >= 0 && distances[i] <= minDis) {
                minDis = distances[i];
            }
        }
        Log.d(TAG, "catEscape: 最短距离====>" + minDis);
        //随机选择一个最短距离的方向，作为猫走的方向
        Random random = new Random();
        int randomIndex;
        do {
            randomIndex = random.nextInt(6);
        } while (distances[randomIndex] != minDis);
        resetCatPosition(randomIndex);
    }

    /**
     * 判断猫是否已经被完全围住
     *
     * @return
     */
    private boolean isCatTrapped() {
        for (int i = 0; i < 6; i++) {
            if (distances[i] != -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 重新设置猫的位置，让猫走一步
     *
     * @param direction
     */
    private void resetCatPosition(int direction) {
        setEmpty(catPositionI, catPositionJ);
        switch (direction) {
            case 0:
                if (catPositionI % 2 == 1) {
                    catPositionJ = catPositionJ + 1;
                }
                catPositionI = catPositionI - 1;
                break;
            case 1:
                catPositionJ++;
                break;
            case 2:
                if (catPositionI % 2 == 1) {
                    catPositionJ = catPositionJ + 1;
                }
                catPositionI = catPositionI + 1;
                break;
            case 3:
                if (catPositionI % 2 == 0) {
                    catPositionJ = catPositionJ - 1;
                }
                catPositionI = catPositionI + 1;
                break;
            case 4:
                catPositionJ--;
                break;
            case 5:
                if (catPositionI % 2 == 0) {
                    catPositionJ = catPositionJ - 1;
                }
                catPositionI = catPositionI - 1;
                break;
        }
        setCat(catPositionI, catPositionJ);
        if(isCatEscaped()){
            isGameOver = true;
            isWinner = false;
        }
    }

    /**
     * 判断猫是否逃到边缘
     * @return
     */
    private boolean isCatEscaped() {
        return isInEdge(catPositionI,catPositionJ);
    }

    /**
     * 计算猫到边缘的六个方向的距离
     */
    private void calculateDistances() {
        distances[0] = calculateDistance0();
        distances[1] = calculateDistance1();
        distances[2] = calculateDistance2();
        distances[3] = calculateDistance3();
        distances[4] = calculateDistance4();
        distances[5] = calculateDistance5();
    }

    private int calculateDistance0() {
        int distance = 0;
        int i, j;
        if (catPositionI % 2 == 1) {
            i = catPositionI - 1;
            j = catPositionJ + 1;
        } else {
            i = catPositionI - 1;
            j = catPositionJ;
        }

        while (!isOutOfEdge(i, j)) {
            if (gameData[i][j] == TYPE_BLOCKED) {
                distance = -1;
                break;
            } else if (gameData[i][j] == TYPE_EMPTY) {
                distance++;
            }

            if (i % 2 == 1) {
                j = j + 1;
            }
            i = i - 1;
        }
        Log.d(TAG, "calculateDistances: ------0------->" + distance);
        return distance;
    }

    private int calculateDistance1() {
        int distance = 0;
        int i = catPositionI;
        int j = catPositionJ + 1;

        while (!isOutOfEdge(i, j)) {
            if (gameData[i][j] == TYPE_BLOCKED) {
                distance = -1;
                break;
            } else if (gameData[i][j] == TYPE_EMPTY) {
                distance++;
            }
            j = j + 1;
        }
        Log.d(TAG, "calculateDistances: ------1------->" + distance);
        return distance;
    }

    private int calculateDistance2() {
        int distance = 0;
        int i, j;
        if (catPositionI % 2 == 1) {
            i = catPositionI + 1;
            j = catPositionJ + 1;
        } else {
            i = catPositionI + 1;
            j = catPositionJ;
        }

        while (!isOutOfEdge(i, j)) {
            if (gameData[i][j] == TYPE_BLOCKED) {
                distance = -1;
                break;
            } else if (gameData[i][j] == TYPE_EMPTY) {
                distance++;
            }

            if (i % 2 == 1) {
                j = j + 1;
            }
            i = i + 1;
        }
        Log.d(TAG, "calculateDistances: ------2------->" + distance);
        return distance;
    }

    private int calculateDistance3() {
        int distance = 0;
        int i, j;
        if (catPositionI % 2 == 0) {
            i = catPositionI + 1;
            j = catPositionJ - 1;
        } else {
            i = catPositionI + 1;
            j = catPositionJ;
        }

        while (!isOutOfEdge(i, j)) {
            if (gameData[i][j] == TYPE_BLOCKED) {
                distance = -1;
                break;
            } else if (gameData[i][j] == TYPE_EMPTY) {
                distance++;
            }

            if (i % 2 == 0) {
                j = j - 1;
            }
            i = i + 1;
        }
        Log.d(TAG, "calculateDistances: ------3------->" + distance);
        return distance;
    }

    private int calculateDistance4() {
        int distance = 0;
        int i = catPositionI;
        int j = catPositionJ - 1;

        while (!isOutOfEdge(i, j)) {
            if (gameData[i][j] == TYPE_BLOCKED) {
                distance = -1;
                break;
            } else if (gameData[i][j] == TYPE_EMPTY) {
                distance++;
            }
            j = j - 1;
        }
        Log.d(TAG, "calculateDistances: ------4------->" + distance);
        return distance;
    }

    private int calculateDistance5() {
        int distance = 0;
        int i, j;
        if (catPositionI % 2 == 0) {
            i = catPositionI - 1;
            j = catPositionJ - 1;
        } else {
            i = catPositionI - 1;
            j = catPositionJ;
        }

        while (!isOutOfEdge(i, j)) {
            if (gameData[i][j] == TYPE_BLOCKED) {
                distance = -1;
                break;
            } else if (gameData[i][j] == TYPE_EMPTY) {
                distance++;
            }

            if (i % 2 == 0) {
                j = j - 1;
            }
            i = i - 1;
        }
        Log.d(TAG, "calculateDistances: ------5------->" + distance);
        return distance;
    }

    private boolean isInEdge(int i, int j) {
        return i == 0 || i == 10 || j == 0 || j == 10;
    }

    private boolean isOutOfEdge(int i, int j) {
        return i < 0 || i > 10 || j < 0 || j > 10;
    }

    public int checkGameResult(){
        if(!isGameOver){
            return RESULT_CONTINUE;
        }else{
            if(isWinner){
                return RESULT_WIN;
            }else{
                return RESULT_LOSE;
            }
        }
    }
}
