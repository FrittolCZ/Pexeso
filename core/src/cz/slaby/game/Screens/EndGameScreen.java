package cz.slaby.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Scaling;

import cz.slaby.game.Pexeso;

public class EndGameScreen implements Screen {

    private SpriteBatch batch;
    private Stage stage;
    private int score;

    public EndGameScreen(SpriteBatch batch, int score) {
        this.batch = batch;
        this.score = score;
    }

    @Override
    public void show() {
        stage = new Stage();
        Table table = new Table();
        Container titleWrap = new Container();
        Stack results = new Stack();
        Table values = new Table();

        Texture resultWindow = new Texture(Gdx.files.internal("vysledky-okno-01.png"));
        Texture titleText = new Texture(Gdx.files.internal("vysledky-text.png"));

        Image title = new Image(titleText);
        Image window = new Image(resultWindow);

        title.setScaling(Scaling.fillX);
        window.setScaling(Scaling.fillX);

        Label.LabelStyle lblStyle = new Label.LabelStyle(createFont(42), new Color(0, 0, 0, 1));
        Label scoreLbl = new Label(String.valueOf(score), lblStyle);
        Label positionLbl = new Label("1", lblStyle);

        values.top();
        values.add(positionLbl).expand().bottom();
        values.row();
        values.add(scoreLbl).expand();

        results.add(window);
        results.add(values);


        titleWrap.width(Pexeso.WIDTH / 11.4f * 4.7f).height(Pexeso.HEIGHT / 6);
        titleWrap.setActor(title);

        //table.debug();
        table.top();
        table.setFillParent(true);
        table.row().height(Pexeso.HEIGHT / 6).width(Pexeso.WIDTH);
        table.add(titleWrap);
        table.row().height(Pexeso.WIDTH / 11.4f * 9.3f).width(Pexeso.WIDTH / 11.4f * 9.3f).padTop(Pexeso.WIDTH / 11.4f * 0.9f);
        table.add(results);

        //Label skore = new Label("Skore: " + score, skin);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(253 / 255f, 26 / 255f, 20 / 255f, 1);
        //Gdx.gl.glClearColor(1, 1, 1, 1);
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

    private BitmapFont createFont(float dp) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = Pexeso.parameter;
        FreeTypeFontGenerator generator = Pexeso.generator;
        int fontSize = (int) (dp * Gdx.graphics.getDensity());
        parameter.size = fontSize;
        return generator.generateFont(parameter);
    }
}
