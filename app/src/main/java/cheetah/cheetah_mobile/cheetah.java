package cheetah.cheetah_mobile;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cheetah.cheetah_GUI.CheetahTextField;
import cheetah.cheetah_GUI.ViewGraphics;
import cheetah.cheetah_GUI.CheetahImageButton;
import cheetah.cheetah_LGC.GameMode;
import cheetah.cheetah_LGC.GameResult;
import cheetah.cheetah_MGR.FileManager;
import cheetah.cheetah_MGR.Globals;
import cheetah.cheetah_MGR.Messenger;

public class cheetah extends Activity {

    private GameMode    GM;
    private int         chosenGameType = 0;
    private GameResult  bestGame = new GameResult();
    private Handler     handler;
    private Thread      time;
    private FileManager FM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        GM = new GameMode(0);
        FM = new FileManager();
        FM.CreateFile(getApplicationContext(), "CMD.dat");
        String[] tmpRes = FM.ReadResultFromFile(getApplicationContext(), "CMD.dat");
        try {
            bestGame.setQuestionCounter(Integer.parseInt(tmpRes[0]));
            bestGame.setCorrectAnswerCounter(Integer.parseInt(tmpRes[1]));
            bestGame.setPointsTotal(Integer.parseInt(tmpRes[2]));
        }
        catch (NumberFormatException ex) {
            Messenger.showException("EXCEPTION:", ex);
        }

        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if(msg.what == 1) {
                    try {
                        ((TextView)findViewById(R.id.txtTimeLeft)).setText("Time left: " + Integer.toString(GM.gameTimeLeft()));
                    }
                    catch (NullPointerException ex) {
                        Messenger.showException("EXCEPTION in handler:", ex);
                    }
                }
                else if(msg.what == 2) {
                    GM.endGame();
                    setContentView(R.layout.result_layout);
                    ((TextView)findViewById(R.id.txtGM_ModeValue)).setText(getGameTypeString(GM.getGameType()));
                    ((TextView)findViewById(R.id.txtGM_PointsValue)).setText(Integer.toString(GM.getCurrentPoints()));
                    ((TextView)findViewById(R.id.txtGM_AnswersValue)).setText(Integer.toString(GM.getCorrectAnswers()) +
                                                                        "/" + Integer.toString(GM.getTotalQuestions()));
                    this.sendEmptyMessageDelayed(3, 1000);
                    if(GM.getGameResult().isBiggerThan(bestGame)) {
                        bestGame.setQuestionCounter(GM.getTotalQuestions());
                        bestGame.setCorrectAnswerCounter(GM.getCorrectAnswers());
                        bestGame.setPointsTotal(GM.getCurrentPoints());
                        FM.WriteResultToFile(getApplicationContext(), "CMD.dat", bestGame);
                    }
                }
                else if(msg.what == 3) {
                    try {
                        ((Button)findViewById(R.id.btnTryAgain)).setEnabled(true);
                    }
                    catch(NullPointerException ex) {
                        Messenger.showException("EXCEPTION:", ex);
                    }
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cheetah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // FIST PAGE BUTTONS
    public void onClickNewGame(View view) {
        setContentView(R.layout.modes_select_layout);
        updateSecondPageButtonsText();
    }

    public void updateSecondPageButtonsText() {
        CheetahImageButton btnTmp;

        btnTmp = (CheetahImageButton) findViewById(R.id.btnModePlus);
        btnTmp.setText("VERY EASY", 0);
        btnTmp.setText("Addition", 1);
        btnTmp.setText("I'm too young to die", 2);

        btnTmp = (CheetahImageButton) findViewById(R.id.btnModeMinus);
        btnTmp.setText("EASY", 0);
        btnTmp.setText("Subtraction", 1);
        btnTmp.setText("Hey, not too rough", 2);

        btnTmp = (CheetahImageButton) findViewById(R.id.btnModeMult);
        btnTmp.setText("NORMAL", 0);
        btnTmp.setText("Multiplication", 1);
        btnTmp.setText("Hurt me plenty", 2);

        btnTmp = (CheetahImageButton) findViewById(R.id.btnModeDiv);
        btnTmp.setText("HARD", 0);
        btnTmp.setText("Division", 1);
        btnTmp.setText("Ultra-Violence", 2);

        btnTmp = (CheetahImageButton) findViewById(R.id.btnModeALL);
        btnTmp.setText("ULTRA HARD", 0);
        btnTmp.setText("All-In-One", 1);
        btnTmp.setText("Nightmare!", 2);
    }

    public void onClickShowMyBest(View view) {
        setContentView(R.layout.record_layout);
        ((TextView)findViewById(R.id.txtRESULT_AnswersValue)).setText(Integer.toString(bestGame.getCorrectAnswerCounter())
                                                                    + "/" + Integer.toString(bestGame.getQuestionCounter()));
        ((TextView)findViewById(R.id.txtRESULT_PointsValue)).setText(Integer.toString(bestGame.getPointsTotal()));
    }

    public void onClickShowAbout(View view) {
        setContentView(R.layout.about_layout);
    }

    public void onClickClose(View view) {
        System.exit(0);
    }

    // SECOND PAGE BUTTONS
    public void onClickBackToMain(View view) {
        setContentView(R.layout.main_layout);
    }

    public void onClickModeSelect(View view) {
        findViewById(R.id.btnModePlus).setBackgroundResource(R.drawable.button_select_state_enabled);
        findViewById(R.id.btnModeMinus).setBackgroundResource(R.drawable.button_select_state_enabled);
        findViewById(R.id.btnModeMult).setBackgroundResource(R.drawable.button_select_state_enabled);
        findViewById(R.id.btnModeDiv).setBackgroundResource(R.drawable.button_select_state_enabled);
        findViewById(R.id.btnModeALL).setBackgroundResource(R.drawable.button_select_state_enabled);

        if(view == findViewById(R.id.btnModePlus))      chosenGameType = Globals.OPERATION_PLUS;
        else if(view == findViewById(R.id.btnModeMinus))chosenGameType = Globals.OPERATION_MINUS;
        else if(view == findViewById(R.id.btnModeMult)) chosenGameType = Globals.OPERATION_MULTIPLY;
        else if(view == findViewById(R.id.btnModeDiv))  chosenGameType = Globals.OPERATION_DIVIDE;
        else chosenGameType = 0;

        view.setBackgroundResource(R.drawable.button_select_state_selected);
        findViewById(R.id.btnGo).setEnabled(true);
    }

    synchronized public void onClickStartGame(View view) {
        Globals.drawAlpha = 0;

        try {
            GM = new GameMode(chosenGameType);
            GM.genNextTask();
            setContentView(R.layout.gameplay_layout);
            showCurrentTask();
        }
        catch (OutOfMemoryError ex) {
            Messenger.showError("ERROR:", ex);
        }

        time = new Thread(new Runnable() {
           public void run() {
               while(GM.gameTimeLeft() > 0) {
                   try {
                       GM.gameTimeDecrease();
                       if(GM.gameTimeLeft() == 0) {
                           handler.sendEmptyMessage(2);
                       }
                       else {
                           handler.sendEmptyMessage(1);
                           Thread.sleep(1000);
                       }
                   }
                   catch (InterruptedException ex) {
                       Messenger.showException("EXCEPTION in timer:", ex);
                   }
               }
           }
        });
        time.start();
    }

    // THIRD PAGE BUTTONS
    public void onClickBackToSelect(View view) {
        GM.endGame();
        setContentView(R.layout.modes_select_layout);
        updateSecondPageButtonsText();
    }

    public void onClickAddDigit(View view) {
        Button tmpBtn = (Button)view;
        String tmpBtnText = tmpBtn.getText().toString();
        int digitValue = Integer.parseInt(tmpBtnText);

        TextView txtResult = (TextView)findViewById(R.id.txtResultValue);
        String tmpResText = txtResult.getText().toString();

        if((digitValue != 0 || tmpResText.length() != 0) && tmpResText.length() <= 7) {
            txtResult.setText(tmpResText + Integer.toString(digitValue));
        }
    }

    public void showCurrentTask() {
        ((TextView)findViewById(R.id.txtFirstValue)).setText(Integer.toString(GM.getCurrentFirstVal()));
        ((TextView)findViewById(R.id.txtSecondValue)).setText(Integer.toString(GM.getCurrentSecondVal()));
        ((TextView)findViewById(R.id.txtPointsEarned)).setText("Points: " + Integer.toString(GM.getCurrentPoints()));
        ((TextView)findViewById(R.id.txtTimeLeft)).setText("Time left: " + Integer.toString(GM.gameTimeLeft()));

        switch(GM.getCurrentTaskType()) {
            case Globals.OPERATION_PLUS:
                ((TextView)findViewById(R.id.txtCurrOperation)).setText(R.string.actionPlus);
                break;
            case Globals.OPERATION_MINUS:
                ((TextView)findViewById(R.id.txtCurrOperation)).setText(R.string.actionMinus);
                break;
            case Globals.OPERATION_MULTIPLY:
                ((TextView)findViewById(R.id.txtCurrOperation)).setText(R.string.actionMult);
                break;
            case Globals.OPERATION_DIVIDE:
                ((TextView)findViewById(R.id.txtCurrOperation)).setText(R.string.actionDiv);
                break;
            default:
                ((TextView)findViewById(R.id.txtCurrOperation)).setText("Unknown operation :(");
                break;
        }
    }

    public void onClickGetNextTask(View view) {
        int answer = 0;

        try {
            answer = Integer.parseInt(((CheetahTextField)findViewById(R.id.txtResultValue)).getText().toString());
        }
        catch (NumberFormatException ex) {
            answer = 0;
        }

        if(GM.checkCurrentTask(answer) == 1) {
            ((ViewGraphics)findViewById(R.id.view_graphics)).throwGraphics("CORRECT", 0, 255, 0);
        }
        else {
            ((ViewGraphics)findViewById(R.id.view_graphics)).throwGraphics("WRONG", 255, 0, 0);
        }

        GM.genNextTask();
        findViewById(R.id.view_graphics).invalidate();
        ((CheetahTextField)findViewById(R.id.txtResultValue)).setText("");
        showCurrentTask();
    }

    // OTHER
    private String getGameTypeString(int v) {
        String s = "";
        switch(v) {
            case 0:
                s = "all-in-one";
                break;
            case 1:
                s = "addition";
                break;
            case 2:
                s = "subtraction";
                break;
            case 3:
                s = "multiplication";
                break;
            case 4:
                s = "division";
                break;
        }
        return s;
    }
}
