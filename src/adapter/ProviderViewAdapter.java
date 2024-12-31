package adapter;

/*public class ProviderViewAdapter extends JFrame implements TTSwingViewInterface {

  private final ThreeTriosView target;
  private final TTControllerInterface adapteeController;
  private final List<ViewFeatures> listeners = new ArrayList<ViewFeatures>();

  public ProviderViewAdapter(ThreeTriosView target, TTControllerInterface adapteeController) {
    this.target = target;
    this.adapteeController = adapteeController;
  }

  @Override
  public void addClickListener() {
    //this.addMouseListener(new MouseAdapter() {});
  }

  @Override
  public void refresh() {
    this.target.refresh();
  }

  @Override
  public void setCurrentSelectedCard(PlayingCard card) {
    // TODO: this prob doesn't work
    this.adapteeController.playerSelectedCard(card.getCardColor().toString(), card);
  }

  @Override
  public void showMessage(String message) {
    // TODO: idk if it's alright to do this; the target.showError() method customizes it to errors
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
  public void addListener(ViewFeatures listener) {
    this.listeners.add(listener);
  }
}
*/