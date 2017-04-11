package poker;

public interface PlayerInterface {
	public boolean canOpen();
	public void discardCards(int[] cardsToDiscard);
	public String call();
	public int bet();
	public boolean compareHands(HandOfCards hand);
}
