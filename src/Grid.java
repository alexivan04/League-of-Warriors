import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private final int rows;
    private final int columns;
    private Character player;
    private Cell playerCell;

    static int sanctuariesMax = 5;
    static int sanctuariesMin = 2;
    static int enemiesMax = 10;
    static int enemiesMin = 4;

    private Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    // set a random cell to type
    static private Cell setRandomCellType(Grid grid, CellEntityType type) {
        int x, y;
        Random rand = new Random();
        do {
            x = rand.nextInt(grid.rows - 1);
            y = rand.nextInt(grid.columns - 1);
//            System.out.println("Curr: x - " + x + ", y - " + y + ", type: " + grid.get(x).get(y).getType().toString());
        } while(grid.get(x).get(y).getType() != CellEntityType.VOID);

        grid.get(x).get(y).setType(type);
        return grid.get(x).get(y);
    }

    static Grid generateGrid(int rows, int columns) throws InvalidGridSize{
        if (rows > 10 || columns > 10) {
            throw new InvalidGridSize("rows: " + rows + ", columns: " + columns);
        }

        Random rand = new Random();
        Grid grid = new Grid(rows, columns);

        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            for (int j = 0; j < columns; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            grid.add(row);
        }

        // randomise number of enemies and sanctuaries
        int enemiesCnt = rand.nextInt(enemiesMax - enemiesMin + 1) + enemiesMin;
        int sanctuariesCnt = rand.nextInt(sanctuariesMax - sanctuariesMin) + sanctuariesMin;

        // one player
        grid.playerCell = setRandomCellType(grid, CellEntityType.PLAYER);
        for(int i = 0; i < enemiesCnt; i++)
            setRandomCellType(grid, CellEntityType.ENEMY);

        for(int i = 0; i < sanctuariesCnt; i++)
            setRandomCellType(grid, CellEntityType.SANCTUARY);

        // one player
        setRandomCellType(grid, CellEntityType.PORTAL);
        return grid;
    }

    static Grid generateFixedGrid() throws InvalidGridSize {
        int rows = 5;
        int columns = 5;

        Grid grid = new Grid(rows, columns);

        // Define the grid layout
        CellEntityType[][] layout = {
                {CellEntityType.PLAYER, CellEntityType.VOID, CellEntityType.VOID, CellEntityType.SANCTUARY, CellEntityType.VOID},
                {CellEntityType.VOID, CellEntityType.VOID, CellEntityType.VOID, CellEntityType.SANCTUARY, CellEntityType.VOID},
                {CellEntityType.SANCTUARY, CellEntityType.VOID, CellEntityType.VOID, CellEntityType.VOID, CellEntityType.VOID},
                {CellEntityType.VOID, CellEntityType.VOID, CellEntityType.VOID, CellEntityType.VOID, CellEntityType.ENEMY},
                {CellEntityType.VOID, CellEntityType.VOID, CellEntityType.VOID, CellEntityType.SANCTUARY, CellEntityType.PORTAL},
        };

        // Populate the grid
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                row.add(new Cell(i, j, layout[i][j]));
            }
            grid.add(row);
        }
        grid.playerCell = grid.get(0).get(0);
        return grid;
    }

    public void setPlayer (Character player) {
        this.player = player;
    }

    public Character getPlayer (){
        return player;
    }

    public void setPlayerCell (Cell playerCell) {
        this.playerCell = playerCell;
    }

    public Cell getPlayerCell () {
        return playerCell;
    }

    public void goNorth() throws ImpossibleMove {
        if (playerCell.getX() > 0) {
            CellEntityType currType = get(playerCell.getX()).get(playerCell.getY()).getType();
            get(playerCell.getX()).get(playerCell.getY()).setType(CellEntityType.VOID);
            get(playerCell.getX()).get(playerCell.getY()).setVisited(true);
            playerCell = get(playerCell.getX() - 1).get(playerCell.getY());
        } else {
            throw new ImpossibleMove("Reached top of map");
        }
    }

    public void goSouth() throws ImpossibleMove {
        if (playerCell.getX() < rows - 1) {
            CellEntityType currType = get(playerCell.getX()).get(playerCell.getY()).getType();
            get(playerCell.getX()).get(playerCell.getY()).setType(CellEntityType.VOID);
            get(playerCell.getX()).get(playerCell.getY()).setVisited(true);
            playerCell = get(playerCell.getX() + 1).get(playerCell.getY());
        } else {
            throw new ImpossibleMove("Reached bottom of map");
        }
    }

    public void goWest() throws ImpossibleMove {
        if (playerCell.getY() > 0) {
            CellEntityType currType = get(playerCell.getX()).get(playerCell.getY()).getType();
            get(playerCell.getX()).get(playerCell.getY()).setType(CellEntityType.VOID);
            get(playerCell.getX()).get(playerCell.getY()).setVisited(true);
            playerCell = get(playerCell.getX()).get(playerCell.getY() - 1);
        } else {
            throw new ImpossibleMove("Reached left side of map");
        }
    }

    public void goEast() throws ImpossibleMove {
        if (playerCell.getY() < columns - 1) {
            CellEntityType currType = get(playerCell.getX()).get(playerCell.getY()).getType();
            get(playerCell.getX()).get(playerCell.getY()).setType(CellEntityType.VOID);
            get(playerCell.getX()).get(playerCell.getY()).setVisited(true);
            playerCell = get(playerCell.getX()).get(playerCell.getY() + 1);
        } else {
            throw new ImpossibleMove("Reached right side of map");
        }
    }


    public void printGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (playerCell.getX() == i && playerCell.getY() == j) {
                    System.out.print("P ");
                } else if (!get(i).get(j).isVisited()) {
                    System.out.print("N ");
                } else {
                    switch (get(i).get(j).getType()) {
                        case VOID:
                            System.out.print("V ");
                            break;
                        case ENEMY:
                            System.out.print("E ");
                            break;
                        case SANCTUARY:
                            System.out.print("S ");
                            break;
                        case PORTAL:
                            System.out.print("F ");
                            break;
                    }
                }
            }
            System.out.println();
        }
    }

}