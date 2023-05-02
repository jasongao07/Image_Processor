package controller;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

import controller.commandv2.BlurCommand;
import controller.commandv2.GreyscaleCommand;
import controller.commandv2.ICommandV2;
import controller.commandv2.LoadCommandV2;
import controller.commandv2.SaveCommandV2;
import controller.commandv2.SepiaCommand;
import controller.commandv2.SharpenCommand;
import model.ImageProcessorModelV2;
import view.ImageProcessorViewV2;

/**
 * This class represents the second variation of the controllerImpl, that extends the previous
 * controllerimpl. This new class includes new commands that the user can input, and runs the
 * specific methods associated with the commands when given. Like the previous controller, this
 * version of the controller also runs the image processor from start to finish.
 */
public class ImageProcessorControllerImplV2 extends ImageProcessorControllerImpl {
  private final ImageProcessorModelV2 model;
  private final ImageProcessorViewV2 view;
  private final Map<String, Supplier<ICommandV2>> commands;

  /**
   * The constructor of an ImageProcessorControllerImpl.
   *
   * @param in    the given input stream
   * @param model the given model
   * @param view  the given view
   */
  public ImageProcessorControllerImplV2(Readable in, ImageProcessorModelV2 model,
                                      ImageProcessorViewV2 view) {
    super(in, model, view);
    this.model = model;
    this.view = view;
    this.commands = new HashMap<>();
    this.commands.put("greyscale", () -> new GreyscaleCommand());
    this.commands.put("sepia", () -> new SepiaCommand());
    this.commands.put("blur", () -> new BlurCommand());
    this.commands.put("sharpen", () -> new SharpenCommand());
    this.commands.put("save", () -> new SaveCommandV2());
    this.commands.put("load", () -> new LoadCommandV2());
  }

  /**
   * The constructor of an ImageProcessorControllerImpl.
   *
   * @param model the given model
   * @param view  the given view
   */
  public ImageProcessorControllerImplV2(ImageProcessorModelV2 model, ImageProcessorViewV2 view) {
    this(new InputStreamReader(System.in), model, view);
  }

  @Override
  protected void processCommands(Scanner sc, String userInstruction) {
    Supplier<ICommandV2> supplier = this.commands.getOrDefault(userInstruction, null);
    try {
      if (supplier != null) {
        ICommandV2 cmd = supplier.get();
        cmd.runCommand(sc, this.view, this.model);
      } else {
        super.processCommands(sc, userInstruction);
      }
    }
    catch (NoSuchElementException e) {
      writeMessage("Scanner ran out of inputs");
    }
  }

  @Override
  protected void printMenu() {
    super.printMenu();
    writeMessage("blur image-name dest-image-name");
    writeMessage("blurs an image using Gaussian blur to create a new image, referred to " +
            "henceforth by the given destination name.");
    writeMessage("sharpen image-name dest-image-name");
    writeMessage("Sharpens an image to create a new image, referred to henceforth by the" +
            " given destination name.");
    writeMessage("sepia image-name dest-image-name");
    writeMessage("Transforms an image by using a Sepia transformation to create a new image, " +
            "referred to henceforth by the given destination name.");
    writeMessage("greyscale image-name dest-image-name");
    writeMessage("Transforms an image by using a greyscale transformation to create a new image, " +
            "referred to henceforth by the given destination name.");
  }
}
