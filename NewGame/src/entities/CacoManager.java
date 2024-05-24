/*
 * package entities;
 * 
 * import static utilz.Constants.EnemyConstant.CACO_HEIGHT; import static
 * utilz.Constants.EnemyConstant.CACO_HEIGHT_DEFAULT; import static
 * utilz.Constants.EnemyConstant.CACO_WIDTH; import static
 * utilz.Constants.EnemyConstant.CACO_WIDTH_DEFAULT;
 * 
 * import java.awt.Graphics; import java.awt.image.BufferedImage; import
 * java.util.ArrayList;
 * 
 * import gamestates.Playing; import utilz.LoadSave;
 * 
 * public class CacoManager { private Playing playing; private BufferedImage[][]
 * cacoArr; private ArrayList<Caco> cacos = new ArrayList<>();
 * 
 * public CacoManager(Playing playing) { this.playing = playing;
 * loadEnemyImgs(); addEnemies(); }
 * 
 * private void addEnemies() { cacos = LoadSave.GetCacos();
 * System.out.println("size of cacos: " + cacos.size()); }
 * 
 * public void update(int[][] lvlData, Player player) { for (Caco c : cacos) {
 * c.update(lvlData, player); } }
 * 
 * public void draw(Graphics g, int xLvOffset) { drawCaco(g, xLvOffset); }
 * 
 * public void drawCaco(Graphics g, int xLvOffset) { for (Caco c : cacos) {
 * g.drawImage(cacoArr[c.getEnemyState()][c.getAniIndex()], (int)
 * c.getHitbox().x - xLvOffset, (int) c.getHitbox().y, CACO_WIDTH, CACO_HEIGHT,
 * null); } }
 * 
 * private void loadEnemyImgs() { cacoArr = new BufferedImage[4][8];
 * BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.CACO_SPRITE); for (int
 * j = 0; j < cacoArr.length; j++) { for (int i = 0; i < cacoArr[j].length; i++)
 * { cacoArr[j][i] = temp.getSubimage(i * CACO_WIDTH_DEFAULT, j *
 * CACO_HEIGHT_DEFAULT, CACO_WIDTH_DEFAULT, CACO_HEIGHT_DEFAULT); } } }
 * 
 * }
 */