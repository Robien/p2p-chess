package lo23.data;

public class ResumeGame extends Invitation {

    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ResumeGame(PublicProfile host, PublicProfile guest, Game game) {
        super(host, guest);
        this.game = game;
    }
}
