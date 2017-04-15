package slaby.cz.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import slaby.cz.game.Screens.*;

public class Pexeso extends Game {

    private static SpriteBatch batch;
    public static Skin skin;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new GameScreen(batch, 20));
        skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
