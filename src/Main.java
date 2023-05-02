import controller.ImageProcessorController;
import controller.ImageProcessorControllerImpl;
import model.ImageProcessorModel;
import model.ImageProcessorModelImpl;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

/**
 * The main class. Instructions are given when the program runs.
 */
public class Main {
  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    ImageProcessorModel model = new ImageProcessorModelImpl();
    ImageProcessorView view = new ImageProcessorViewImpl(model);
    ImageProcessorController controller = new ImageProcessorControllerImpl(model, view);
    controller.run();
  }
}
