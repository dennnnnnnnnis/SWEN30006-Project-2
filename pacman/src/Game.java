// PacMan.java
// Simple PacMan implementation
package src;

import ch.aplu.jgamegrid.*;
import src.map.grid.Grid;
import src.utility.GameCallback;

import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

public class Game extends GameGrid
{
  private final static int nbHorzCells = 20;
  private final static int nbVertCells = 11;
  protected PacManGameGrid grid;

  protected PacActor pacActor = new PacActor(this);

  private ArrayList<Monster> monsters = new ArrayList<Monster>();

  private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();
  private ArrayList<Actor> iceCubes = new ArrayList<Actor>();
  private ArrayList<Actor> goldPieces = new ArrayList<Actor>();
  private ArrayList<Actor> whitePortal = new ArrayList<Actor>();
  private ArrayList<Actor> yellowPortal = new ArrayList<Actor>();
  private ArrayList<Actor> darkPortal = new ArrayList<Actor>();
  private ArrayList<Actor> grayPortal = new ArrayList<Actor>();
  private GameCallback gameCallback;
  private Properties properties;
  private int seed = 30006;

  public Game(GameCallback gameCallback, Properties properties, ArrayList<String> maps)
  {
    //Setup game
    super(nbHorzCells, nbVertCells, 20, false);
    grid = new PacManGameGrid(nbHorzCells, nbVertCells, maps.get(0));
    maps.remove(0);
    this.gameCallback = gameCallback;
    this.properties = properties;
    setSimulationPeriod(100);
    setTitle("[PacMan in the Multiverse]");

    //Setup for auto test
    pacActor.setAuto(Boolean.parseBoolean(properties.getProperty("PacMan.isAuto")));

    GGBackground bg = getBg();
    drawGrid(bg);

    //Setup Random seeds
    setupActorLocations();
    seed = Integer.parseInt(properties.getProperty("seed"));
    for (Monster monster: monsters){
      monster.setSeed(seed);
    }
    pacActor.setSeed(seed);

    addKeyRepeatListener(pacActor);
    setKeyRepeatPeriod(150);
    pacActor.setSlowDown(3);
    for (Monster monster: monsters){
      monster.setSlowDown(3);
      if (monster.getType() == MonsterType.TX5){
        monster.stopMoving(5);
      }
    }



    //Run the game
    doRun();
    show();
    // Loop to look for collision in the application thread
    // This makes it improbable that we miss a hit
    boolean hasPacmanBeenHit = false;
    boolean hasPacmanEatAllPills;
    setupPillAndItemsLocations();
    int maxPillsAndItems = countPillsAndItems();
    
    do {
      for (Monster monster: monsters){
        if (monster.getLocation().equals(pacActor.getLocation())){
          hasPacmanBeenHit = true;
        }
      }
      hasPacmanEatAllPills = pacActor.getNbPills() >= maxPillsAndItems;
      delay(10);
    } while(!hasPacmanBeenHit && !hasPacmanEatAllPills);
    delay(120);

    Location loc = pacActor.getLocation();
    for (Monster monster: monsters){
      monster.setStopMoving(true);
    }
    pacActor.removeSelf();

    String title = "";
    if (hasPacmanBeenHit) {
      bg.setPaintColor(Color.red);
      title = "GAME OVER";
      addActor(new Actor("sprites/explosion3.gif"), loc);
    } else if (hasPacmanEatAllPills) {
      bg.setPaintColor(Color.yellow);
      title = "YOU WIN";
      if (maps.size() != 0){
        new Game(gameCallback, properties, maps);
      }
    }
    setTitle(title);
    gameCallback.endOfGame(title);

    doPause();
    hide();
  }

  public GameCallback getGameCallback() {
    return gameCallback;
  }

  private void setupActorLocations() { //
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 5) { // Pacman
          addActor(pacActor, location);
        } else if (a == 6) { // Troll
          Monster troll = new Monster(this, MonsterType.Troll);
          monsters.add(troll);
          addActor(troll, location, Location.NORTH);
        } else if (a == 7) { // TX5
          Monster tx5 = new Monster(this, MonsterType.TX5);
          monsters.add(tx5);
          addActor(tx5, location, Location.NORTH);
        }
      }
    }
  }

  private int countPillsAndItems() {
    int pillsAndItemsCount = 0;
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 1) { // Pill
          pillsAndItemsCount++;
        } else if (a == 3) { // Gold
          pillsAndItemsCount++;
        }
      }
    }

    return pillsAndItemsCount;
  }

  public ArrayList<Location> getPillAndItemLocations() {
    return pillAndItemLocations;
  }

  private void setupPillAndItemsLocations() {
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a == 1) {
          pillAndItemLocations.add(location);
        }
        if (a == 3) {
          pillAndItemLocations.add(location);
        }
        if (a == 4) {
          pillAndItemLocations.add(location);
        }
      }
    }

  }

  private void drawGrid(GGBackground bg)
  {
    bg.clear(Color.gray);
    bg.setPaintColor(Color.white);
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        bg.setPaintColor(Color.white);
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a > 0)
          bg.fillCell(location, Color.lightGray);
        if (a == 1) { // Pill
          putPill(bg, location);
        } else if (a == 3) { // Gold
          putGold(bg, location);
        } else if (a == 4) {
          putIce(bg, location);
        } else if (a == 8) {
          putWhiteTile(bg, location);
        } else if (a == 9) {
          putYellowTile(bg, location);
        } else if (a == 10) {
          putDarkTile(bg, location);
        } else if (a == 11) {
          putGrayTile(bg, location);
        }
      }
    }

  }

  private void putPill(GGBackground bg, Location location){
    bg.fillCircle(toPoint(location), 5);
  }

  private void putGold(GGBackground bg, Location location){
    bg.setPaintColor(Color.yellow);
    bg.fillCircle(toPoint(location), 5);
    Actor gold = new Actor("sprites/gold.png");
    this.goldPieces.add(gold);
    addActor(gold, location);
  }

  private void putIce(GGBackground bg, Location location){
    bg.setPaintColor(Color.blue);
    bg.fillCircle(toPoint(location), 5);
    Actor ice = new Actor("sprites/ice.png");
    this.iceCubes.add(ice);
    addActor(ice, location);
  }

  private void putWhiteTile(GGBackground bg, Location location){
    bg.setPaintColor(Color.white);
    bg.fillCircle(toPoint(location), 5);
    Actor whitePortal = new Actor("data/i_portalWhiteTile.png");
    this.whitePortal.add(whitePortal);
    addActor(whitePortal, location);
  }

  private void putYellowTile(GGBackground bg, Location location){
    bg.setPaintColor(Color.white);
    bg.fillCircle(toPoint(location), 5);
    Actor yellowPortals = new Actor("data/j_portalYellowTile.png");
    this.yellowPortal.add(yellowPortals);
    addActor(yellowPortals, location);
  }

  private void putDarkTile(GGBackground bg, Location location){
    bg.setPaintColor(Color.white);
    bg.fillCircle(toPoint(location), 5);
    Actor darkPortals = new Actor("data/k_portalDarkGoldTile.png");
    this.darkPortal.add(darkPortals);
    addActor(darkPortals, location);
  }

  private void putGrayTile(GGBackground bg, Location location){
    bg.setPaintColor(Color.white);
    bg.fillCircle(toPoint(location), 5);
    Actor grayPortals = new Actor("data/l_portalDarkGrayTile.png");
    this.grayPortal.add(grayPortals);
    addActor(grayPortals, location);
  }

  public void removeItem(String type,Location location){
    if(type.equals("gold")){
      for (Actor item : this.goldPieces){
        if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
          item.hide();
        }
      }
    }else if(type.equals("ice")){
      for (Actor item : this.iceCubes){
        if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
          item.hide();
        }
      }
    }
  }

  public Location jumpItem(Location location){
    boolean isPortal = false;
    for (Actor item : this.whitePortal){
      if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
        isPortal = true;
      }
    }
    if (isPortal) {
      for (Actor item : this.whitePortal) {
        if (location.getX() != item.getLocation().getX() || location.getY() != item.getLocation().getY()) {
          location.x = item.getLocation().getX();
          location.y = item.getLocation().getY();
          break;
        }
      }
      return location;
    }

    for (Actor item : this.grayPortal){
      if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
        isPortal = true;
      }
    }
    if (isPortal) {
      for (Actor item : this.grayPortal) {
        if (location.getX() != item.getLocation().getX() || location.getY() != item.getLocation().getY()) {
          location.x = item.getLocation().getX();
          location.y = item.getLocation().getY();
          break;
        }
      }
      return location;
    }

    for (Actor item : this.yellowPortal){
      if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
        isPortal = true;
      }
    }
    if (isPortal) {
      for (Actor item : this.yellowPortal) {
        if (location.getX() != item.getLocation().getX() || location.getY() != item.getLocation().getY()) {
          location.x = item.getLocation().getX();
          location.y = item.getLocation().getY();
          break;
        }
      }
      return location;
    }

    for (Actor item : this.darkPortal){
      if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
        isPortal = true;
      }
    }
    if (isPortal) {
      for (Actor item : this.darkPortal) {
        if (location.getX() != item.getLocation().getX() || location.getY() != item.getLocation().getY()) {
          location.x = item.getLocation().getX();
          location.y = item.getLocation().getY();
          break;
        }
      }
      return location;
    }

    return location;
  }

  public int getNumHorzCells(){
    return this.nbHorzCells;
  }
  public int getNumVertCells(){
    return this.nbVertCells;
  }
}
