package model.service.factory;

import controller.GameState;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Coordinates;

public abstract class FXTileFactory extends TileFactory {

  private static final DoubleProperty requestedTileSize;
  private static final double DEFAULT_GRID_LENGTH = 400;
  // Is DoubleProperty in case the grid size changes in future updates.
  private static final DoubleProperty gridLengthInPixels;

  static {
    gridLengthInPixels = new SimpleDoubleProperty(DEFAULT_GRID_LENGTH);
    requestedTileSize = new SimpleDoubleProperty();
    requestedTileSize.bind(gridLengthInPixels.divide(GameState.gridSizeProperty()));
  }

  private final ShapeFactory shapeFactory = new ShapeFactory();

  public static double getRequestedTileSize() {
    return requestedTileSize.get();
  }

  public static void setRequestedTileSize(double requestedTileSize) {
    FXTileFactory.requestedTileSize.set(requestedTileSize);
  }

  public static DoubleProperty requestedTileSizeProperty() {
    return requestedTileSize;
  }

  public static double getGridLengthInPixels() {
    return gridLengthInPixels.get();
  }

  public static void setGridLengthInPixels(double gridLengthInPixels) {
    FXTileFactory.gridLengthInPixels.set(gridLengthInPixels);
  }

  public static DoubleProperty gridLengthInPixelsProperty() {
    return gridLengthInPixels;
  }

  public Shape createShape(TileShape tileShape, Coordinates coordinates) {
    switch (tileShape) {
      case SQUARE:
        return shapeFactory.newRectangle(coordinates.getRow(), coordinates.getCol());
      case CIRCLE:
        return null;
      default:
        return null;
    }
  }

  public Text createText(String id) {
    Text text = new Text(id);
    text.setFont(Font.font(getRequestedTileSize() / 2));
    text.setFill(Color.WHITE);
    text.setMouseTransparent(true);
    return text;
  }
  //
  // // TODO: change method name
  // public void bla(ObservableList<Node> children, Tile tile) {
  //   switch (((FXTile) tile).getShapeType()) {
  //     case SQUARE: {
  //       children.add(shapeFactory
  //           .newRectangle(tile.getCoordinates().getRow(), tile.getCoordinates().getCol()));
  //       break;
  //     }
  //     case CIRCLE: {
  //       children.add(shapeFactory
  //           .newCircle(tile.getCoordinates().getRow(), tile.getCoordinates().getCol()));
  //       break;
  //     }
  //   }
  // }

  public enum TileShape {
    SQUARE, CIRCLE
  }

  class ShapeFactory {

    Rectangle newRectangle(int row, int col) {
      Rectangle tmp = new Rectangle(getRequestedTileSize() - 4, getRequestedTileSize() - 4);
      tmp.setArcHeight(getRequestedTileSize() / 10);
      tmp.setArcWidth(getRequestedTileSize() / 10);
      return tmp;
    }


    public Node newCircle(int row, int col) {
//      TODO: implement
      return null;
    }
  }

}


