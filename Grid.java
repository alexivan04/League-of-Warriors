public class Grid extends ArrayList<ArrayList<Cell>> {
    int rows, columns;
    Character player;
    Cell playerCell;

    static Grid generateGrid(int rows, int columns) {
        Grid grid = new Grid(rows, columns);
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 0; j < columns; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            grid.add(row);
        }
        return grid;
    }

    void goNorth() {
        if (playerCell.getX() > 0) {
            playerCell.setX(playerCell.getX() - 1);
        }
    }

    void goSouth() {
        if (playerCell.getX() < rows - 1) {
            playerCell.setX(playerCell.getX() + 1);
        }
    }

    void goWest() {
        if (playerCell.getY() > 0) {
            playerCell.setY(playerCell.getY() - 1);
        }
    }

    void goEast() {
        if (playerCell.getY() < columns - 1) {
            playerCell.setY(playerCell.getY() + 1);
        }
    }
}

class Cell {
    int x, y;
    CellEntityType type;
    boolean isVisited;

    public Cell(int x, int y, CellEntityType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.isVisited = false;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public CellEntityType getType() {
        return type;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void printCell() {
        System.out.print("(" + x + ", " + y + ")");
    }

}