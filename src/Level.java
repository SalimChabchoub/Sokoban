public class Level {
    static final int Vide = 0;
    static final int  Mur= 0;
    static final int joueur = 0;
    static final int Cible = 0;
    static final int Box = 0;

    int[][] board;
    int l, c;

    String name;

    public Level(){
        board = new int[1][1];
        l = c = 0;
    }

    private int adjust(int c,int i ){
        while(c<=i)
            c*=2;
        return c;
    }

    private void resizeBoard(int l , int c){
        int oldL = board.length;
        int oldC = board[0].length;

        if ((oldL <= l) || (oldC <=c)){
            int newL = adjust(oldL,l);
            int newC = adjust(oldC,c);
        }
    }
    public void fixName(String nm){

    }

    public void emptyCell(int i, int j){

    }

    public void  addWall(int i,int j){

    }

    public void addPlayer(int i,int j){

    }

    public void addBox(int i,int j){

    }

    public void addTarget(int i,int j){

    }

    public int lines(){
        return 0;
    }

    public int columns(){
        return 0;
    }

    public String name(){
        return name;
    }

    public boolean isEmpty(int i,int j){
        return false;
    }

    public boolean hasWall(int i,int j){
        return false;
    }

    public boolean hasTarget(int i,int j){
        return false;
    }

    public boolean hasPlayer(int i,int j){
        return false;
    }

    public boolean hasBox(int i,int j){
        return false;
    }

}
