package Modele;

import java.util.Observable;

public abstract class Case extends Observable {
    public Point p;
    public Entite entite;
    public Case(Point p){
        this.p = p;
    }
    public void move(Direction d,int H, int L){
        switch (d){
            case UP :
                this.p.y = ((p.y-1)+H)%(H);
                //y = ((y-1)+H)%(H);
                break;
            case DOWN:
                this.p.y =(p.y+1)%(H);
                break;
            case LEFT:
                this.p.x = ((p.x-1)+L)%(L);
                break;
            case RIGHT:
                this.p.x = (p.x+1)%(L);
        }

    }

    public abstract boolean entrer ();
    public void quitter(){
        this.entite=null;
    }
}
