interface Battle {
    public void recieveDamage(int damage);
    public void getDamage();

}

enum CellEntityType {
    PLAYER,
    VOID,
    ENEMY,
    SANCTUARY,
    PORTAL
}