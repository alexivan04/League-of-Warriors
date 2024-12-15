public class Test {
    public static void main(String[] args) throws InvalidGridSize {
        Game game = new Game();
        game.username = "marcel@yahoo.com";
        game.password = "6K7GUxjsAc";
        game.useKeyboardInput = false;
        game.characterChoice = 0;
        game.attackChoice = 2;
        game.run();
        game.map = Grid.generateFixedGrid();
        game.map.setPlayer(game.currCharacter);
        game.map.getPlayer().mana = 100000;
        game.map.getPlayer().health = 100000;
        game.map.goEast();
        game.makeAction();
        game.map.goEast();
        game.makeAction();
        game.map.goEast();
        game.makeAction();
        game.map.goEast();
        game.makeAction();
        game.map.goSouth();
        game.makeAction();
        game.map.goSouth();
        game.makeAction();
        game.map.goSouth();
        game.makeAction();
        game.map.goSouth();
        game.makeAction();
        game.gameEnded = true;
    }
}