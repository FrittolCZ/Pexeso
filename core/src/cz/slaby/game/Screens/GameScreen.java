package cz.slaby.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import cz.slaby.game.GameClasses.GameStage;
import cz.slaby.game.GameClasses.PexSprite;
import cz.slaby.game.Pexeso;

public class GameScreen implements Screen, GestureDetector.GestureListener {

    private SpriteBatch batch;
    private GameStage gameStage;

    public static Texture pexBack;

    private float timeOrigin;
    private float time;

    private boolean timedGame = false;

    private boolean pause = true;

    private ArrayList<TextureRegion> set;

    private Label timeLbl, exitLbl;

    private boolean exit = false;
    private float exitTimer = 0;


    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setGame(ArrayList<TextureRegion> set, Texture pexBack, float time) {
        if (time != 0) {
            timedGame = true;
        }
        this.time = time;
        this.timeOrigin = time;
        this.pexBack = pexBack;
        this.set = set;
        gameStage = new GameStage(set);
    }

    public void resetGame() {
        this.time = timeOrigin;
        gameStage = new GameStage(set);
    }


    @Override
    public void show() {
        GestureDetector gd = new GestureDetector(this);
        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if (keycode == Input.Keys.BACK)
                    if (exit) {
                        exitLbl.setVisible(false);
                        exit = false;
                        ((Game) Gdx.app.getApplicationListener()).setScreen(Pexeso.screens.get(Pexeso.MAIN_MENU));
                    } else {
                        exitLbl.setVisible(true);
                        exit = true;
                    }
                return false;
            }
        };

        InputMultiplexer multiplexer = new InputMultiplexer(gd, backProcessor);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);


        Label.LabelStyle timeLblStyle = new Label.LabelStyle(createFont(20), new Color(253 / 255f, 26 / 255f, 20 / 255f, 1f));
        Pixmap labelColor = new Pixmap((int) (Pexeso.WIDTH / 11.4f * 3.2f), (int) (Pexeso.WIDTH / 11.4f * 0.8f), Pixmap.Format.RGB888);
        labelColor.setColor(Color.WHITE);
        labelColor.fill();
        Image back = new Image(new Texture(labelColor));

        timeLblStyle.background = back.getDrawable();
        timeLbl = new Label(formatTime((int) time), timeLblStyle);
        timeLbl.setAlignment(Align.center);

        Label.LabelStyle exitLblStyle = new Label.LabelStyle(createFont(18), Color.WHITE);
        labelColor = new Pixmap((int) (Pexeso.WIDTH / 11.4f * 3.2f), (int) (Pexeso.WIDTH / 11.4f * 0.8f), Pixmap.Format.RGBA8888);
        labelColor.setColor(new Color(0, 0, 0, 0.5f));
        labelColor.fill();
        back = new Image(new Texture(labelColor));

        exitLblStyle.background = back.getDrawable();
        exitLbl = new Label("Stiskněte zpět pro ukončení hry", exitLblStyle);
        exitLbl.setAlignment(Align.center);
        exitLbl.setVisible(false);

        Table wrapper = new Table();
        wrapper.setFillParent(true);
        wrapper.row().expand();
        wrapper.add(exitLbl).bottom().left().pad(10).width(exitLbl.getWidth() + 20);
        wrapper.add(timeLbl).bottom().right().pad(10).width(Pexeso.WIDTH / 11.4f * 3.2f).height(Pexeso.WIDTH / 11.4f * 0.8f);


        gameStage.addActor(wrapper);

    }

    private String formatTime(int time) {
        int min = time / 60;
        int sec = time % 60;
        String formatedTime = String.format("%02d:%02d", min, sec);
        return formatedTime;
    }

    private BitmapFont createFont(float dp) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = Pexeso.parameter;
        FreeTypeFontGenerator generator = Pexeso.generator;
        parameter.size = (int) (dp * Gdx.graphics.getDensity());
        return generator.generateFont(parameter);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if (exit) {
            exitTimer += delta;
            if (exitTimer >= 2f) {
                exit = false;
                exitLbl.setVisible(false);
                exitTimer = 0;
            }
        }

        if (gameStage.gameOver()) {
            endGame();
        }
        if (timedGame) {
            if (!pause) {
                time -= delta;
            }

            if (time <= 0) {
                endGame();
            }
        }
        timeLbl.setText(formatTime((int) time));

        gameStage.act(delta);

        batch.begin();
        gameStage.draw();
        batch.end();

    }

    private void endGame() {
        EndGameScreen screen = (EndGameScreen) Pexeso.screens.get(Pexeso.END_GAME);
        screen.setResults(gameStage.getPieceFound(), gameStage.getPieceToFound());
        ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
    }

    @Override
    public void resize(int width, int height) {
        gameStage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector3 pos = new Vector3(x, y, 0);
        gameStage.getCamera().unproject(pos);
        Actor hited = gameStage.hit(pos.x, pos.y, true);
        if (hited instanceof cz.slaby.game.GameClasses.PexSprite) {
            pause = false;
            cz.slaby.game.GameClasses.PexSprite hitedPex = (cz.slaby.game.GameClasses.PexSprite) hited;
            if (!hitedPex.isTurned() && !hitedPex.isFound() && !hitedPex.isTurning() && gameStage.hittedCount() < 2) {
                hitedPex.startTurning();
                gameStage.addHitted(hitedPex);
            }
            //
        }
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
