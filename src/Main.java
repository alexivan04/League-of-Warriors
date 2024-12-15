public class Main {
    public static void main(String[] args) throws InvalidGridSize {
        Game game = new Game();
        while(!game.gameEnded) {
            if(game.username == null || game.password == null) {
                do {
                    System.out.print("Username: ");
                    game.username = game.input.nextLine();
                    System.out.print("Password: ");
                    game.password = game.input.nextLine();
                    game.run();
                } while (!game.loggedIn);
            }
            else game.run();
            game.map = Grid.generateGrid(6, 6);
            game.map.setPlayer(game.currCharacter);
            while (!game.gameEnded) {
                game.chooseNextAction();
                if(game.gameEnded)
                    break;
                game.makeAction();
            }
        }
    }
}