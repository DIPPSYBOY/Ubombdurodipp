/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;


public class Player extends GameObject implements Movable {

    private Direction direction;
    private boolean moveRequested = false;
    private  int RangeBombs=1 ;
    private int Bombs = 1;
    private int lives;

    public Player(Game game, Position position, int lives) {
        super(game, position);
        this.direction = Direction.DOWN;
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public Direction getDirection() {
        return direction;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
            setModified(true);
        }
        moveRequested = true;
    }
    @Override
    public boolean canMove(Direction direction) {
        boolean ok ;
        if (game.getGrid().get(direction.nextPosition(getPosition()))!=null){
            ok=   game.getGrid().get(direction.nextPosition(getPosition())).toString()=="DoorNextOpened"||
                    game.getGrid().get(direction.nextPosition(getPosition())).toString()=="DoorPrevOpened";
        }else{
            ok=false ;
        }
        //return(  ok ||  (game.getGrid().is_touching_Princess(direction.nextPosition(getPosition()))|| game.getGrid().isEmpty(direction.nextPosition(getPosition() )) || (game.getGrid().Translate_Box(direction, getPosition()))|| game.getGrid().isBonus(direction.nextPosition(getPosition() ))
                //|| game.getGrid().isKey(direction.nextPosition(getPosition() )) || game.getGrid().isHeart(direction.nextPosition(getPosition())))  && game.getGrid().isInside(direction.nextPosition(getPosition() ))
                //|| (game.getGrid().get(direction.nextPosition(getPosition()))!=null &&(game.getGrid().get(direction.nextPosition(getPosition())).toString()=="Bonus_bomb_range_dec" &&RangeBombs>0))
                //|| (game.getGrid().get(direction.nextPosition(getPosition()))!=null &&(game.getGrid().get(direction.nextPosition(getPosition())).toString()=="Bonus_bomb_nb_dec" &&Bombs>0)) )
                //;
        return ( ok || game.getGrid().isEmpty(direction.nextPosition(getPosition() ))
                || (game.getGrid().get(direction.nextPosition(getPosition()))!=null &&(game.getGrid().get(direction.nextPosition(getPosition())).toString()=="Bonus_bomb_range_dec" &&RangeBombs>0))
                || (game.getGrid().get(direction.nextPosition(getPosition()))!=null &&(game.getGrid().get(direction.nextPosition(getPosition())).toString()=="Bonus_bomb_nb_dec" &&Bombs>0)));
    }
    /*
    public final boolean canMove(Direction direction) {
        return true;
    }
*/
    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    public void doMove(Direction direction) {
        // Check if we need to pick something up
        Position nextPos = direction.nextPosition(getPosition());
        setPosition(nextPos);
    }

    @Override
    public boolean isWalkable(Player player) {
        return false;
    }

    @Override
    public void explode() {
    }

    // Example of methods to define by the player
    public void takeDoor(int gotoLevel) {}
    public void takeKey() {}


    public boolean isWinner() {
        return false;
    }
}
