package cz.slaby.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import cz.slaby.game.GUI.CheckImage;
import cz.slaby.game.GUI.PagedScrollPane;


public class PexSelectScreen implements Screen {

    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private Label file;
    private Image image;
    private Texture texture;
    private CheckImage checkImage;
    private ArrayList<ArrayList<Texture>> pexesoSets;
    private PagedScrollPane pagedScrollPane;
    private ScrollPane scroll;
    private Table container;

    public PexSelectScreen(SpriteBatch batch) {
        this.batch = batch;
        this.skin = cz.slaby.game.Pexeso.skin;
    }

    @Override
    public void show() {
        stage = new Stage();
        container = new Table();
        pexesoSets = new ArrayList<ArrayList<Texture>>();
        try {
            loadSets();
        } catch (Exception e) {
            Gdx.app.log("PexLoad", "err", e);
        }

        pagedScrollPane = new PagedScrollPane();
        pagedScrollPane.setFlingTime(0.1f);
        pagedScrollPane.setPageSpacing(25);
        int c = 0;
        float size = Gdx.graphics.getWidth() / 4 - 20;
        for (ArrayList<Texture> set : pexesoSets) {
            Table page = new Table();
            for (Texture pexTex : set) {
                if (c != 0 && c % 4 == 0) page.row();
                page.add(new CheckImage(pexTex)).pad(10).size(size);
                c++;
            }
            pagedScrollPane.addPage(page);
        }

        //file = new Label("file", skin);
        /*TextButton btn = new TextButton("Text", skin);
        btn.setWidth(400f);
        btn.setHeight(100f);
        btn.getLabel().setFontScale(2f);
        btn.setPosition(Gdx.graphics.getWidth() / 2 - 200f, Gdx.graphics.getHeight() / 2 - 100f);

        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                cz.slaby.game.Pexeso.galleryOpener.getGalleryImagePath();
            }
        });*/

        //Načíst defaultní pexesa
        //Načíst pexesa uložená v interní paměti aplikace (pexesa přidaná uživatelem)

        pagedScrollPane.setFillParent(true);
        container.add(pagedScrollPane);
        container.setFillParent(true);
        stage.addActor(container);
        Gdx.input.setInputProcessor(stage);
    }

    private void loadSets() {
        FileHandle pexesosDir = null;
        FileHandle[] internalSets;
        try {
            pexesosDir = Gdx.files.internal("pexeso/");
        } catch (Exception e) {
            Gdx.app.log("OpenFile", "Chyba při pokusu načíst pexesa", e);
        }
        if (pexesosDir != null) {
            internalSets = pexesosDir.list(); // všechny složky v adresáři pexeso
            for (FileHandle set : internalSets) // pro každý adresář
            {
                FileHandle[] setImgs = set.list(); // načti všechny soubotry
                ArrayList<Texture> imgSet = new ArrayList<Texture>(); // vytvoř nový list
                for (FileHandle img : setImgs) {
                    imgSet.add(new Texture(img)); // z každého souboru vytvoř texturu a ulož ji do listu
                }
                pexesoSets.add(imgSet); // potom vlož nový list do listu všechn pexes
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
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
        batch.dispose();
    }
}

