package cheetah.cheetah_LGC;

import cheetah.cheetah_MGR.Globals;
import cheetah.cheetah_MGR.Messenger;

public class GameMode {

    private int           gameType;
    private int           gameTimeLeft;
    private int[]         min_max_LO, min_max_HI;
    private int[]         currentTask;
    private GameResult    result;
    private GameGenerator generator;

    public GameMode(int type) {

        // from 0 to 4
        setGameType(type);
        gameTimeLeft = 60;

        try {
            // [0] - first operand; [1] - second operand; [2] - operation type; [3] - result
            currentTask   = new int[4];
            min_max_LO    = new int[2];
            min_max_LO[0] = 1;
            min_max_LO[1] = 29;
            min_max_HI    = new int[2];
            min_max_HI[0] = 1;
            min_max_HI[1] = 99;
            result        = new GameResult();
            generator     = new GameGenerator();
        }
        catch (OutOfMemoryError ex) {
            // I hope we'll never been here
            Messenger.showError("ERROR:", ex);
        }

        // set to 0
        resetTask();
    }

    public void endGame() {
        gameTimeLeft = 0;
    }

    public void gameTimeDecrease() {
        if(gameTimeLeft > 0)
            gameTimeLeft--;
    }

    public int gameTimeLeft() {
        return gameTimeLeft;
    }

    public void genNextTask() {

        int tmpFirst  = 0;
        int tmpSecond = 0;

        int thisTurnGameType = gameType;
        if(thisTurnGameType == 0) {
            thisTurnGameType = generator.generateValue(1, 4);
        }

        if(thisTurnGameType == 1 || thisTurnGameType == 2) {
            tmpFirst  = generator.generateValue(min_max_HI[0], min_max_HI[1]);
            tmpSecond = generator.generateValue(min_max_HI[0], min_max_HI[1]);
        }
        else {
            tmpFirst  = generator.generateValue(min_max_LO[0], min_max_LO[1]);
            tmpSecond = generator.generateValue(min_max_LO[0], min_max_LO[1]);
        }

        currentTask[2] = thisTurnGameType;
        switch(thisTurnGameType) {
            case Globals.OPERATION_PLUS:
                currentTask[0] = tmpFirst;
                currentTask[1] = tmpSecond;
                currentTask[3] = tmpFirst + tmpSecond;
                break;
            case Globals.OPERATION_MINUS:
                currentTask[0] = (tmpFirst > tmpSecond) ? tmpFirst : tmpSecond;
                currentTask[1] = (tmpFirst > tmpSecond) ? tmpSecond : tmpFirst;
                currentTask[3] = Math.abs(tmpFirst - tmpSecond);
                break;
            case Globals.OPERATION_MULTIPLY:
                currentTask[0] = tmpFirst;
                currentTask[1] = tmpSecond;
                currentTask[3] = tmpFirst * tmpSecond;
                break;
            case Globals.OPERATION_DIVIDE:
                currentTask[0] = tmpFirst * tmpSecond;
                currentTask[1] = tmpSecond;
                currentTask[3] = tmpFirst;
                break;
            default:
                resetTask();
                break;
        }
    }

    private void resetTask() {
        for(int i = 0; i < currentTask.length; i++) {
            currentTask[i] = 0;
        }
    }

    private int calculateBonus() {
        int bonus = 0;
        int typeBonus = (gameType == 0) ? 40 : 0;
        int calculationWeight = (currentTask[0] > currentTask[1]) ? currentTask[0] : currentTask[1];
            calculationWeight = (calculationWeight > currentTask[3]) ? calculationWeight : currentTask[3];
        switch(currentTask[2]) {
            case Globals.OPERATION_PLUS:
                bonus = 100;
                break;
            case Globals.OPERATION_MINUS:
                bonus = 100;
                break;
            case Globals.OPERATION_MULTIPLY:
                bonus = 130;
                break;
            case Globals.OPERATION_DIVIDE:
                bonus = 200;
                break;
            default:
                bonus = 0;
                break;
        }
        return bonus + typeBonus + calculationWeight;
    }

    public int checkCurrentTask(int answer) {
        int res = (answer == currentTask[3]) ? 1 : 0;
        result.increaseQuestionCounter();
        result.increaseCorrectAnswerCounter(res);
        result.increasePointsTotal(res * calculateBonus());
        return res;
    }

    public void setGameType(int type) {
        // 0 - all operations
        // 1 - only plus
        // 2 - only minus
        // 3 - only multiply
        // 4 - only divide
        if(type >= 0) {
            gameType = (type < 5) ? type : 0;
        }
        else {
            gameType = 0;
        }
    }

    public int getGameType() {
        return gameType;
    }

    public int getCurrentFirstVal() {
        return currentTask[0];
    }

    public int getCurrentSecondVal() {
        return currentTask[1];
    }

    public int getCurrentTaskType() {
        return currentTask[2];
    }

    public int getCurrentTaskResult() {
        return currentTask[3];
    }

    public int getCurrentPoints() {
        return result.getPointsTotal();
    }

    public int getCorrectAnswers() {
        return result.getCorrectAnswerCounter();
    }

    public int getTotalQuestions() {
        return result.getQuestionCounter();
    }
}
