import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerImplV2;
import model.ImageProcessorModelImplV2;
import model.ImageProcessorModelV2;
import view.ImageProcessorViewImplV2;
import view.ImageProcessorViewV2;

/**
 * The main class. Instructions are given when the program runs.
 */
public class MainV2 {
  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {

    Scanner sc;
    StringReader reader;
    ImageProcessorModelV2 model = new ImageProcessorModelImplV2();
    ImageProcessorViewV2 view = new ImageProcessorViewImplV2(model);
    ImageProcessorController controller = null;
    String commands = "";
    boolean controllerInitialized = false;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equalsIgnoreCase("-file") && i != args.length - 1) {
        try {
          sc = new Scanner(new FileInputStream(args[i + 1]));
          while (sc.hasNext()) {
            commands += sc.next() + "\n ";
          }
          if (commands.equals("")) {
            controller = new ImageProcessorControllerImplV2(model, view);
          } else {
            reader = new StringReader(commands);
            controller = new ImageProcessorControllerImplV2(reader, model, view);
          }
          controllerInitialized = true;
          break;
        } catch (FileNotFoundException e) {
          System.out.println("File " + args[1] + " not found!");
          return;
        }
      }
    }
    if (!controllerInitialized) {
      controller = new ImageProcessorControllerImplV2(model, view);
    }
    controller.run();
  }
}
