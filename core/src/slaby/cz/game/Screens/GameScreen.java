package slaby.cz.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

import slaby.cz.game.GameClasses.GameStage;
import slaby.cz.game.GameClasses.PexSprite;

public class GameScreen implements Screen, GestureDetector.GestureListener {

    private SpriteBatch batch;
    private GameStage gameStage;

    public static Texture pexBack = new Texture(Gdx.files.internal("pexBacks/back1.jpg"));

    private float time;

    public GameScreen(SpriteBatch batch, float time) {
        this.batch = batch;
        this.time = time;
    }

    @Override
    public void show() {
        gameStage = new GameStage();

        GestureDetector gd = new GestureDetector(this);
        Gdx.input.setInputProcessor(gd);
    }


    @Override
    public void render(float delta) {

        time -= delta;

        Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(time <= 0)
        {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new EndGameScreen(batch, gameStage.getPieceFound()));
        }


        gameStage.act();

        batch.begin();
        gameStage.draw();
        batch.end();

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
        if (hited instanceof PexSprite) {
            PexSprite hitedPex = (PexSprite) hited;
            if (!hitedPex.isTurned() && !hitedPex.isFound() && gameStage.hittedCount() < 2) {
                hitedPex.turn();
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
