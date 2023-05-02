package controller;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

import controller.commandv1.BlueComponent;
import controller.commandv1.BrightenCommand;
import controller.commandv1.GreenComponent;
import controller.commandv1.HorizontalCommand;
import controller.commandv1.ICommand;
import controller.commandv1.IntensityComponent;
import controller.commandv1.LoadCommand;
import controller.commandv1.LumaComponent;
import controller.commandv1.RedComponent;
import controller.commandv1.SaveCommand;
import controller.commandv1.ValueComponent;
import controller.commandv1.VerticalCommand;
import model.ImageProcessorModel;
import view.ImageProcessorView;

/**
 * This class represents the controller impl, which implements the ImageProcessor controller.
 * This class runs the image processor from start til finish.
 */
public class ImageProcessorControllerImpl implements ImageProcessorController {
  private final Readable in;
  private final ImageProcessorModel model;
  private final ImageProcessorView view;
  private final Map<String, Supplier<ICommand>> commands;

  /**
   * The constructor of an ImageProcessorControllerImpl.
   *
   * @param in    the given input stream
   * @param model the given model
   * @param view  the given view
   */
  public ImageProcessorControllerImpl(Readable in, ImageProcessorModel model,
                                      ImageProcessorView view) {
    if (model == null || view == null || in == null) {
      throw new IllegalArgumentException("Inputs cannot be null");
    }
    this.in = in;
    this.model = model;
    this.view = view;
    this.commands = new HashMap<>();
    this.commands.put("red-component", () -> new RedComponent());
    this.commands.put("green-component", () -> new GreenComponent());
    this.commands.put("blue-component", () -> new BlueComponent());
    this.commands.put("value-component", () -> new ValueComponent());
    this.commands.put("intensity-component", () -> new IntensityComponent());
    this.commands.put("luma-component", () -> new LumaComponent());
    this.commands.put("load", () -> new LoadCommand());
    this.commands.put("save", () -> new SaveCommand());
    this.commands.put("horizontal-flip", () -> new HorizontalCommand());
    this.commands.put("vertical-flip", () -> new VerticalCommand());
    this.commands.put("brighten", () -> new BrightenCommand());
  }

  /**
   * The constructor of an ImageProcessorControllerImpl.
   *
   * @param model the given model
   * @param view  the given view
   */
  public ImageProcessorControllerImpl(ImageProcessorModel model, ImageProcessorView view) {
    this(new InputStreamReader(System.in), model, view);
  }

  @Override
  public void run() {
    Scanner sc = new Scanner(this.in);
    boolean quit = false;
    this.welcomeMessage();
    while (!quit && sc.hasNext()) {
      String userInstruction = sc.next();
      if (userInstruction.equals("q")) {
        quit = true;
      }
      else {
        processCommands(sc, userInstruction);
      }
    }
    this.farewellMessage();
  }

  /**
   * This method is used to process the commands that the user might input. The method then uses
   * the inputs to run the designated functions objects.
   * @param sc the given scanner
   * @param userInstruction the given user command
   */
  protected void processCommands(Scanner sc, String userInstruction) {
    Supplier<ICommand> supplier = this.commands.getOrDefault(userInstruction, null);
    if (supplier != null) {
      ICommand cmd = supplier.get();
      cmd.runCommand(sc, this.view, this.model);
    }
    else {
      writeMessage("Invalid command.");
    }
  }

  /**
   * Outputs a message into the view.
   *
   * @param message the message to be outputted
   */
  protected void writeMessage(String message) {
    this.view.renderMessage(message + "\n");
  }

  /**
   * Prints the menu with all the possible commands to be run.
   */
  protected void printMenu() {
    writeMessage("load image-path image-name");
    writeMessage("Load an image from the specified path and refer it to henceforth in the " +
            "program by the given image name.");
    writeMessage("save image-path image-name");
    writeMessage("Save the image with the given name to the specified path");
    writeMessage("(component-name)-component image-name dest-image-name");
    writeMessage("Create a greyscale image with the given component of the image with the " +
            "given name, and refer to it henceforth in the program by the given destination name.");
    writeMessage("Supported components:");
    writeMessage("red, green, blue, value, intensity, luma");
    writeMessage("horizontal-flip image-name dest-image-name");
    writeMessage("Flip an image horizontally to create a new image, referred to henceforth by the" +
            " given destination name.");
    writeMessage("vertical-flip image-name dest-image-name");
    writeMessage("Flip an image vertically to create a new image, referred to henceforth by the " +
            "given destination name.");
    writeMessage("brighten increment image-name dest-image-name");
    writeMessage("brighten the image by the given increment to create a new image, referred to " +
            "henceforth by the given destination name. The increment may be positive " +
            "(brightening) or negative (darkening)");
  }

  /**
   * The starting message outputted at the beginning of the program.
   */
  protected void welcomeMessage() {
    writeMessage("Welcome to the Image Processing Program! Here are a list of supported commands:");
    printMenu();
  }

  /**
   * The message displayed after quitting the image processor.
   */
  protected void farewellMessage() {
    writeMessage("Quitting the program.");
  }
}
