import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

import controller.ImageProcessorController;
import controller.ImageProcessorControllerGUI;
import controller.ImageProcessorControllerImplV2;
import model.ImageProcessorModelImplV2;
import model.ImageProcessorModelV2;
import view.ImageGUIView;
import view.ImageGUIViewImpl;
import view.ImageProcessorViewImplV2;
import view.ImageProcessorViewV2;

/**
 * The main class.
 */
public class MainV3 {
  /**
   * The main method.
   *
   * @param args arguments
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
      } else if (args[i].equalsIgnoreCase("-text")) {
        controller = new ImageProcessorControllerImplV2(model, view);
        controllerInitialized = true;
        break;
      }
    }
    if (!controllerInitialized) {
      ImageGUIView guiView = new ImageGUIViewImpl();
      controller = new ImageProcessorControllerGUI(model, guiView);
    }
    controller.run();
  }
}

