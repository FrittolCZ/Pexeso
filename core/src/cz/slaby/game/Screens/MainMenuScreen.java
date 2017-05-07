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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import cz.slaby.game.Pexeso;


public class MainMenuScreen implements Screen {

    private SpriteBatch batch;
    private Stage mainMenuStage;
    private int time = 0;

    public MainMenuScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        Button btn1, btn2, btn3, btn4, btnPlus, btnMinus;
        final Label timeLbl;
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        mainMenuStage = new Stage();
        Table table = new Table();
        Table timeSelector = new Table();
        Stack stack = new Stack();
        Container titleWrap = new Container();

        Texture btn1Up = new Texture(Gdx.files.internal("buttons/1-obtiznost-up.png"));
        Texture btn1Down = new Texture(Gdx.files.internal("buttons/1-obtiznost-down.png"));
        Texture btn2Up = new Texture(Gdx.files.internal("buttons/2-obtiznost-up.png"));
        Texture btn2Down = new Texture(Gdx.files.internal("buttons/2-obtiznost-down.png"));
        Texture btn3Up = new Texture(Gdx.files.internal("buttons/3-obtiznost-up.png"));
        Texture btn3Down = new Texture(Gdx.files.internal("buttons/3-obtiznost-down.png"));
        Texture btn4Up = new Texture(Gdx.files.internal("buttons/4-obtiznost-up.png"));
        Texture btn4Down = new Texture(Gdx.files.internal("buttons/4-obtiznost-down.png"));
        Texture titleText = new Texture(Gdx.files.internal("vyber-obtiznost-text.png"));
        Texture btnPlusUp = new Texture(Gdx.files.internal("buttons/plus.png"));
        Texture btnMinusUp = new Texture(Gdx.files.internal("buttons/minus.png"));

        Image background = new Image(new TextureRegion(Pexeso.background, width, height * 5 / 6));
        Image title = new Image(titleText);
        title.setScaling(Scaling.fillX);


        Label.LabelStyle timeLblStyle = new Label.LabelStyle(createFont(20), new Color(253 / 255f, 26 / 255f, 20 / 255f, 1f));
        Pixmap labelColor = new Pixmap((int) (width / 11.4f * 3.6f), (int) (width / 11.4f * 0.7f), Pixmap.Format.RGB888);
        labelColor.setColor(Color.WHITE);
        labelColor.fill();
        Image back = new Image(new Texture(labelColor));

        Button.ButtonStyle btn1Style = new Button.ButtonStyle();
        btn1Style.up = new TextureRegionDrawable(new TextureRegion(btn1Up));
        btn1Style.down = new TextureRegionDrawable(new TextureRegion(btn1Down));

        Button.ButtonStyle btn2Style = new Button.ButtonStyle();
        btn2Style.up = new TextureRegionDrawable(new TextureRegion(btn2Up));
        btn2Style.down = new TextureRegionDrawable(new TextureRegion(btn2Down));

        Button.ButtonStyle btn3Style = new Button.ButtonStyle();
        btn3Style.up = new TextureRegionDrawable(new TextureRegion(btn3Up));
        btn3Style.down = new TextureRegionDrawable(new TextureRegion(btn3Down));

        Button.ButtonStyle btn4Style = new Button.ButtonStyle();
        btn4Style.up = new TextureRegionDrawable(new TextureRegion(btn4Up));
        btn4Style.down = new TextureRegionDrawable(new TextureRegion(btn4Down));

        Button.ButtonStyle btnPlusStyle = new Button.ButtonStyle();
        btnPlusStyle.up = new TextureRegionDrawable(new TextureRegion(btnPlusUp));
        btnPlusStyle.down = new TextureRegionDrawable(new TextureRegion(btnPlusUp));

        Button.ButtonStyle btnMinusStyle = new Button.ButtonStyle();
        btnMinusStyle.up = new TextureRegionDrawable(new TextureRegion(btnMinusUp));
        btnMinusStyle.down = new TextureRegionDrawable(new TextureRegion(btnMinusUp));

        timeLbl = new Label(formatTime(time), timeLblStyle);
        timeLbl.setAlignment(Align.center);

        btn1 = new Button(btn1Style);
        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeScreen(6, time);
            }
        });
        btn2 = new Button(btn2Style);
        btn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeScreen(10, time);
            }
        });
        btn3 = new Button(btn3Style);
        btn3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeScreen(15, time);
            }
        });
        btn4 = new Button(btn4Style);
        btn4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeScreen(21, time);
            }
        });
        btnPlus = new Button(btnPlusStyle);
        btnPlus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (time < 600) {
                    time += 30;
                    timeLbl.setText(formatTime(time));
                }
            }
        });
        btnMinus = new Button(btnMinusStyle);
        btnMinus.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (time > 0) {
                    time -= 30;
                    timeLbl.setText(formatTime(time));
                }
            }
        });


        timeSelector.row().height(width / 11.4f * 0.8f);
        timeSelector.add(btnMinus).left().width(width / 11.4f * 0.6f);
        timeSelector.add(timeLbl).center().width(width / 11.4f * 2.2f);
        timeSelector.add(btnPlus).right().width(width / 11.4f * 0.6f);
        //timeSelector.debug();

        stack.add(back);
        stack.add(timeSelector);

        titleWrap.width(width * 3 / 4).height(height / 6);
        titleWrap.setActor(title);

        //table.debug();
        table.setFillParent(true);
        table.row().height(height / 6).width(width);
        table.add(titleWrap).colspan(2);
        table.row().width(width / 3.081f).height(width / 3.081f).padTop(width / 11.4f * 2).padBottom(width / 17.1f);
        table.add(btn1).right().padRight(width / 17.1f);
        table.add(btn2).left().padLeft(width / 17.1f);
        table.row().width(width / 3.081f).height(width / 3.081f).padTop(width / 17.1f).padBottom(width / 17.1f);
        table.add(btn3).right().padRight(width / 17.1f);
        table.add(btn4).left().padLeft(width / 17.1f);
        table.row().expand();
        table.add(stack).colspan(2).bottom().right().width(width / 11.4f * 3.6f).height(width / 11.4f * 0.7f).pad(width / 11.4f);

        table.top();
        mainMenuStage.addActor(background);
        mainMenuStage.addActor(table);
        Gdx.input.setInputProcessor(mainMenuStage);
    }

    private void changeScreen(int tileCount, float time) {
        PexSelectScreen screen = (PexSelectScreen) Pexeso.screens.get(Pexeso.PEX_SELECT);
        screen.setTileCount(tileCount);
        screen.setTime(time);
        ((Game) Gdx.app.getApplicationListener()).setScreen(screen);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(253 / 255f, 26 / 255f, 20 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        mainMenuStage.draw();
        //Gdx.app.log("Click", "Density: " + Gdx.graphics.getDensity());
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

    private String formatTime(int time) {
        int min = time / 60;
        int sec = time % 60;
        String formatedTime = String.format("%02d:%02d", min, sec);
        return formatedTime;
    }

    private BitmapFont createFont(float dp) {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = Pexeso.parameter;
        FreeTypeFontGenerator generator = Pexeso.generator;
        int fontSize = (int) (dp * Gdx.graphics.getDensity());
        parameter.size = fontSize;
        return generator.generateFont(parameter);
    }
}
