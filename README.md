# League of Warriors
## Ivan Alexandru-Marian - 322CC
## Description
League of Warriors is an action-packed game where players can choose their warriors, engage in battles, and compete to become the ultimate champion. The game features different character classes such as Mage, Rogue, and Warrior, each with unique abilities and attributes.

## Features

- **Character Professions**: Choose from Warrior, Mage, and Rogue, each with unique abilities and attributes.
- **Grid Navigation**: Move your character across the grid in four directions (North, South, East, West).
- **Battles**: Engage in battles with enemies using normal attacks or special abilities.
- **Sanctuaries**: Regenerate health and mana at sanctuaries.
- **Portals**: Complete levels by reaching portals.

### Controls

- **W**: Move North
- **A**: Move West
- **S**: Move South
- **D**: Move East
- **Q**: Quit Game

## Code Structure

- [`src/Account.java`](src/Account.java ): Represents a player's account with credentials, favorite games, and characters.
- [`src/Character.java`](src/Character.java ): Abstract class for characters with common attributes and methods.
- [`src/Warrior.java`](src/Warrior.java ), [`src/Mage.java`](src/Mage.java ), [`src/Rogue.java`](src/Rogue.java ): Specific character classes with unique abilities.
- [`src/Grid.java`](src/Grid.java ): Manages the grid and player movements.
- [`src/JsonInput.java`](src/JsonInput.java ): Handles reading and writing account data from/to JSON files.
- [`src/Game.java`](src/Game.java ): Main game logic, including running the game and handling user input.
- [`src/Cell.java`](src/Cell.java ): Represents a cell in the grid.
- [`src/CellEntityType.java`](src/CellEntityType.java ): Enum for different types of entities in a cell.
- [`src/Spell.java`](src/Spell.java ): Abstract class for spells with damage and mana cost.
- [`src/Fire.java`](src/Fire.java ), [`src/Ice.java`](src/Ice.java ), [`src/Earth.java`](src/Earth.java ): Specific spell classes with unique properties.
- [`src/Enemy.java`](src/Enemy.java ): Represents an enemy entity with random abilities.
- [`src/ImpossibleMove.java`](src/ImpossibleMove.java ), [`src/InvalidGridSize.java`](src/InvalidGridSize.java ): Custom exceptions for invalid moves and grid sizes.

## Game Functionality

- **Login System**: Players log in using their email and password stored in [`accounts.json`](accounts.json ).
- **Character Selection**: Players choose a character from their account to play.
- **Grid-Based Movement**: Players navigate a grid, encountering enemies, sanctuaries, and portals.
- **Combat System**: Players can attack enemies or use abilities during battles.
- **Level Progression**: Players complete levels by reaching portals and gain experience to level up.


### Development time

- **Planning**: 2 hours
- **Coding**: 8 hours
- **Testing**: 2 hours
- **Documentation**: 30 minutes