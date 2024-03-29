package model.tiles;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import model.Coordinates;
import model.Tile;
import model.service.factory.FXTileFactory;
import model.service.factory.FXTileFactory.TileShape;
import model.service.factory.TileFactory;
import model.service.factory.TileFactory.TileClassification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class FXTile extends StackPane implements Tile {

  //TODO: refactor styleclasses into constants that are defined in factories
  private static final Logger logger = LogManager.getLogger(Tile.class);
  final TileFactory tileFactory;
  final Coordinates initialCoordinates;
  private final Coordinates currentCoordinates;
  private final BooleanProperty inInitialPosition;
  Text text;
  private String id = "John Doe";
  private TileClassification tilesClassification;
  private TileShape tilesShape;
  Shape shape;

  // Because I defined the toString method in shape
  // when it is printed the String defined in toString goes
  // on the screen

  FXTile(TileFactory tileFactory) {
    this.tileFactory = tileFactory;
    initialCoordinates = new Coordinates(0, 0);
    currentCoordinates = new Coordinates(0, 0);
    inInitialPosition = new SimpleBooleanProperty(true);
  }

  void assembleFXTileBase() {
    logger.traceEntry("assembling FXTileBase");
    assembleTileBase(tileFactory);
    setTranslateX(getCoordinates().getCol() * FXTileFactory.getRequestedTileSize());
    setTranslateY(getCoordinates().getRow() * FXTileFactory.getRequestedTileSize());
    logger.traceExit("done assembling FXTileBase");
  }

  @Override
  abstract public void assembleTile();

  public void updateStyle(String newStyle) {
    getStyleClass().add(newStyle);
  }

  public String toString() {
    // If any Tile object is printed to screen this shows up
    return "The " + tilesClassification + " has a top speed of " +
        " and an attack power of ";
  }

  @Override
  public TileClassification getType() {
    return this.tilesClassification;
  }

  @Override
  public void setType(TileClassification tilesClassification) {
    this.tilesClassification = tilesClassification;
  }

  @Override
  public String getID() {
    return this.id;
  }

  @Override
  public void setID(String id) {
    this.id = id;
  }

  @Override
  public Coordinates getCoordinates() {
    return currentCoordinates;
  }

  @Override
  public void setCoordinates(Coordinates newCoordinates) {
    currentCoordinates.setRow(newCoordinates.getRow());
    currentCoordinates.setCol(newCoordinates.getCol());
  }

  @Override
  public void setInitialCoordinates(Coordinates newCoordinates) {
    initialCoordinates.setRow(newCoordinates.getRow());
    initialCoordinates.setCol(newCoordinates.getCol());
  }

  // @Override
  public boolean isInRightPosition() {
    return isInInitialPosition();
    //  isInInitialPositionManualCheck
  }

  @Override
  public void swapWith(Tile emptyTile) {
    this.currentCoordinates.swapWith(emptyTile.getCoordinates());
  }

  TileShape getShapeType() {
    return tilesShape;
  }

  void setShapeType(TileShape tilesShape) {
    this.tilesShape = tilesShape;
  }

  private boolean isInInitialPosition() {
    return inInitialPosition.get();
  }

  BooleanProperty inInitialPositionProperty() {
    return inInitialPosition;
  }

  public synchronized boolean refreshInInitialPosition() {
    this.inInitialPosition.set(currentCoordinates.equals(initialCoordinates));
    return isInInitialPosition();
  }
}