package cz.slaby.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


import cz.slaby.game.Screens.*;

public class Pexeso extends Game {

    private static SpriteBatch batch;
    public static Skin skin;
    public static OpenGallery galleryOpener;
    public static FreeTypeFontGenerator generator;
    public static FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public Pexeso(OpenGallery galleryOpener)
    {
        Pexeso.galleryOpener = galleryOpener;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/ae24126d.pfb"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters += "ěščřžýáíéĚŠČŘŽÝÁÍÉ";
        setScreen(new MainMenuScreen(batch));
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
