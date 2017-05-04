package cz.slaby.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import cz.slaby.game.Pexeso;

public class EndGameScreen implements Screen {

    private SpriteBatch batch;
    private Stage stage;
    private int score;
    private Skin skin;

    public EndGameScreen(SpriteBatch batch, int score) {
        this.batch = batch;
        this.score = score;
        this.skin = Pexeso.skin;
    }

    @Override
    public void show() {
        stage = new Stage();
        Label skore = new Label("Skore: " + score, skin);
        stage.addActor(skore);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f, 0.8f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        batch.begin();
        stage.draw();
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

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
}
