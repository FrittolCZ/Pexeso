package slaby.cz.game.GameClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Random;


public class GameStage extends Stage {
    private Table tab;
    private PexSprite pexArray[][];
    float height, width;
    float pieceSize;
    ArrayList<PexSprite> hitted;
    float timer = 0;
    int pieceFound = 0;

    public GameStage() {
        super(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        tab = new Table();
        this.height = Gdx.graphics.getHeight();
        this.width = Gdx.graphics.getWidth();
        loadPexArray();
        shufPex();
        loadTab();
        //tab.debug();
        tab.setFillParent(true);
        this.addActor(tab);
        hitted = new ArrayList<PexSprite>();
    }

    public void addHitted(PexSprite hitted) {
        this.hitted.add(hitted);
    }

    public int hittedCount() {
        return hitted.size();
    }

    public int getPieceFound()
    {
        return pieceFound;
    }

    private void shufPex() {
        Random r = new Random();
        for (int i = 0; i < pexArray.length; i++) {
            for (int j = 0; j < pexArray[i].length; j++) {
                int iPos = r.nextInt(pexArray.length);
                int jPos = r.nextInt(pexArray[i].length);
                PexSprite tmp = pexArray[i][j];
                pexArray[i][j] = pexArray[iPos][jPos];
                pexArray[iPos][jPos] = tmp;
            }
        }
    }

    private void loadPexArray() {
        FileHandle f = Gdx.files.internal("pexeso/animals/");
        FileHandle[] files = f.list();
        Texture tex;
        PexSprite pex;
        int pexCount = files.length * 2; // počet dílků pexesa
        int inLine = getInLine(pexCount); // počet dílků v jedné řadě
        pexArray = new PexSprite[pexCount / inLine][inLine];
        pieceSize = Math.min(height / (pexCount / inLine), width / inLine); // velikost jednoho dílku
        int y = -1;
        for (int i = 0; i < pexCount; i++) {
            if (i % inLine == 0) y++;
            tex = new Texture(files[i / 2]);
            pex = new PexSprite(tex, i / 2);
            try {
                pexArray[y][i % inLine] = pex;
            } catch (Exception e) {
                Gdx.app.log("chyba", "asdf", e);
            }
        }
    }

    private void loadTab() {
        for (int i = 0; i < pexArray.length; i++) {
            for (int j = 0; j < pexArray[i].length; j++) {
                tab.add(pexArray[i][j]).pad(10).size(pieceSize - 20);
            }
            tab.row();
        }
        tab.center();
    }

    private int getInLine(int pexCount) {
        int maxi, i, j;
        i = 1;
        j = pexCount;
        maxi = i;
        while (i < j) {
            i++;
            if (pexCount % i == 0) {
                j = pexCount / i;
                maxi = i;
            }
        }
        return maxi;
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        if (hitted.size() == 2) {
            timer += delta;
            if (timer >= 2) {
                PexSprite hitOne = hitted.get(0);
                PexSprite hitTwo = hitted.get(1);
                if (hitOne.getId() == hitTwo.getId()) {
                    hitOne.setFound();
                    hitTwo.setFound();
                    pieceFound++;
                } else {
                    hitOne.turn();
                    hitTwo.turn();
                }
                hitted.clear();
                timer = 0;
            }
        }
    }
}
