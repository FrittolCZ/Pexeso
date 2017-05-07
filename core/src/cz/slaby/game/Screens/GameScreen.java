package cz.slaby.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import cz.slaby.game.Pexeso;

public class GameScreen implements Screen, GestureDetector.GestureListener {

    private SpriteBatch batch;
    private GameStage gameStage;

    public static Texture pexBack;

    private float time;

    private boolean timedGame = false;

    private boolean pause = true;

    Label timeLbl;

    /**
     * @param batch
     * @param set     - Pictures from selected set
     * @param pexBack - Selected back
     * @param time    - time for the game. If time is zero, game will last till every pair wasnt found
     */
    public GameScreen(SpriteBatch batch, ArrayList<TextureRegion> set, Texture pexBack, float time) {
        this.batch = batch;
        if (time != 0) {
            timedGame = true;
        }
        this.time = time;
        this.pexBack = pexBack;
        gameStage = new GameStage(set);
    }


    @Override
    public void show() {
        GestureDetector gd = new GestureDetector(this);
        Gdx.input.setInputProcessor(gd);

        Label.LabelStyle timeLblStyle = new Label.LabelStyle(createFont(20), new Color(253 / 255f, 26 / 255f, 20 / 255f, 1f));
        Pixmap labelColor = new Pixmap((int) (Pexeso.WIDTH / 11.4f * 3.2f), (int) (Pexeso.WIDTH / 11.4f * 0.8f), Pixmap.Format.RGB888);
        labelColor.setColor(Color.WHITE);
        labelColor.fill();
        Image back = new Image(new Texture(labelColor));
        timeLblStyle.background = back.getDrawable();
        timeLbl = new Label(formatTime((int) time), timeLblStyle);
        timeLbl.setAlignment(Align.center);

        Table wrapper = new Table();
        wrapper.setFillParent(true);
        wrapper.row().expand();
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
        ((Game) Gdx.app.getApplicationListener()).setScreen(new EndGameScreen(batch, gameStage.getPieceFound(), gameStage.getPieceToFound()));
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
            if (!hitedPex.isTurned() && !hitedPex.isFound() && gameStage.hittedCount() < 2) {
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
