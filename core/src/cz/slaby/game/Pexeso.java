package cz.slaby.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


import java.util.ArrayList;

import cz.slaby.game.Screens.*;

public class Pexeso extends Game {

    public static final int MAIN_MENU = 0, PEX_SELECT = 1, GAME = 2, END_GAME = 3;
    public static int WIDTH, HEIGHT;
    private static SpriteBatch batch;
    public static OpenGallery galleryOpener;
    public static FreeTypeFontGenerator generator;
    public static FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public static ArrayList<Screen> screens;
    public static Texture background;

    public Pexeso(OpenGallery galleryOpener) {
        Pexeso.galleryOpener = galleryOpener;
        screens = new ArrayList<Screen>();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        background = new Texture(Gdx.files.internal("background.png"));
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/ae24126d.pfb"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters += "ěščřžýáíéĚŠČŘŽÝÁÍÉ";
        screens.add(new MainMenuScreen(batch));
        screens.add(new PexSelectScreen(batch));
        //screens.add(new GameScreen(batch));
        //screens.add(new EndGameScreen(batch));

        setScreen(screens.get(MAIN_MENU));
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
