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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Scaling;

import cz.slaby.game.Pexeso;

public class EndGameScreen implements Screen {

    private SpriteBatch batch;
    private Stage stage;
    private int score;
    private int position;

    public EndGameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setResults(int score, int maxScore) {
        this.score = score;
        if (score >= (maxScore * 0.8)) {
            position = 1;
        } else if (score >= (maxScore * 0.6)) {
            position = 2;
        } else {
            position = 3;
        }

    }

    @Override
    public void show() {
        stage = new Stage();
        Table table = new Table();
        Container titleWrap = new Container();
        Stack results = new Stack();
        Table values = new Table();

        Table btnWrapper = new Table();

        Texture resultWindow = new Texture(Gdx.files.internal("vysledky-okno-01.png"));
        Texture titleText = new Texture(Gdx.files.internal("vysledky-text.png"));
        NinePatch patchUp = new NinePatch(new Texture(Gdx.files.internal("buttons/btnUp.png")), 4, 4, 4, 4);
        NinePatch patchDown = new NinePatch(new Texture(Gdx.files.internal("buttons/btnDown.png")), 4, 4, 4, 4);

        Image title = new Image(titleText);
        Image window = new Image(resultWindow);

        title.setScaling(Scaling.fillX);
        window.setScaling(Scaling.fillX);

        Label.LabelStyle lblStyle = new Label.LabelStyle(createFont(42), new Color(0, 0, 0, 1));
        Label scoreLbl = new Label(String.valueOf(score), lblStyle);
        Label positionLbl = new Label(String.valueOf(position), lblStyle);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new NinePatchDrawable(patchUp);
        style.down = new NinePatchDrawable(patchDown);
        style.font = createFont(20);
        style.fontColor = new Color(253 / 255f, 26 / 255f, 20 / 255f, 1f);

        TextButton btnRepeat = new TextButton("Hrát znovu", style);
        btnRepeat.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen screen = (GameScreen) Pexeso.screens.get(Pexeso.GAME);
                screen.resetGame();
                ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
            }
        });
        TextButton btnMenu = new TextButton("Menu", style);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(Pexeso.screens.get(Pexeso.MAIN_MENU));
            }
        });

        btnWrapper.row().expand().width(200).bottom().pad(Pexeso.WIDTH / 11.4f);
        btnWrapper.add(btnRepeat).left();
        btnWrapper.add(btnMenu).right();

        values.top();
        values.add(positionLbl).expand().bottom();
        values.row();
        values.add(scoreLbl).expand();

        results.add(window);
        results.add(values);


        titleWrap.width(Pexeso.WIDTH / 11.4f * 4.7f).height(Pexeso.HEIGHT / 6);
        titleWrap.setActor(title);

        table.top();
        table.setFillParent(true);
        table.row().height(Pexeso.HEIGHT / 6).width(Pexeso.WIDTH);
        table.add(titleWrap);
        table.row().height(Pexeso.WIDTH / 11.4f * 9.3f).width(Pexeso.WIDTH / 11.4f * 9.3f).padTop(Pexeso.WIDTH / 11.4f * 0.9f);
        table.add(results);
        table.row().fill().expand();
        table.add(btnWrapper);

        //Label skore = new Label("Skore: " + score, skin);
        stage.addActor(table);

        InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if (keycode == Input.Keys.BACK)
                    ((Game) Gdx.app.getApplicationListener()).setScreen(Pexeso.screens.get(Pexeso.MAIN_MENU));
                return false;
            }
        };

        InputMultiplexer multiplexer = new InputMultiplexer(stage, backProcessor);
        Gdx.input.setInputProcessor(multiplexer);
        Gdx.input.setCatchBackKey(true);

        Gdx.input.setInputProcessor(multiplexer);
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
