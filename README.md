# HW6
HW6 Joseph Kim &amp; Jason Gao

------- DESIGN EXPLANATION PT 3 (FOR HW6) -------

We changed methods in CommonImageUtils such that the
previous readImage and writeImage don't change, but
the part of the method that converts IImages to
BufferedImages and BufferedImages to IImages is their
own method, and added tests to CommonImageUtilTest
accordingly.

For our Histogram class, we created a class that took
in the dimensions of the histogram, and output the
lines required to draw the histogram for each component:
getRedLines, getBlueLines, getGreenLines, and
getIntensityLines. We were able to reuse the
ComponentGetter function object made way back in the
first image processor assignment to abstract lots
of functionality in the histogram code, and we're
proud about that :D

The general design between the Controller and the View
is that the View sends certain events to all the
listeners of the view (which are added using the 
addListener method in View), and the controller uses
the methods provided by the view to edit the view
accordingly to the event that it received.

For testing controller and view, we figured that it
wouldn't be plausible to test the view due to being
unable to access various Java Swing elements, so we test
most of the functionality in the controller tests.

In order to allow our controller to write PPM files,
we had to add a method to PPMUtil that takes in an
IImage and a destination, and writes it to that
destination (Something we lost points off for in
the first part of the assignment). While we were
at it, we also refactored ImageProcessorViewImpl
to use the new method.

------- DESIGN EXPLANATION PT 2 (FOR HW5) -------

Most of the difficult design decisions for this
homework were related with the Controller class:
the Model and View worked very well with extensions.

We moved many classes in the model package
to their own packages to reduce clutter of code.

We changed the javadocs of some interfaces/
classes to more accurately describe its functionality

We changed ImageProcessorControllerImpl to use the
Command Design pattern, and created many function objects.

For SaveCommandV2 and LoadCommandV2 function objects, we
ran into a problem where we wanted to use the code from
the previous SaveCommand and LoadCommand to continue
supporting PPM files, but the
LoadCommand prints a specific message that's both
important to the original design, while also
incompatible with the message we needed to print
(The message in question was "Given file is not a
PPM file."). To handle this, we decided to modify
SaveCommand to be extendable by using a protected
method (to prevent the scanner from reading more
than one line) and had SaveCommandV2 extend
SaveCommand. We did not do that for LoadCommand,
due to the aforementioned message rendering issue,
but still decided to make LoadCommand extendable
as the code was looking very messy, and we wanted
to make sure that if we had to extend it again,
we wouldn't have to have the developer deal with that
messy code.

Another issue we encountered was with the command design
pattern, which resulted in us having to give the view
to each of the commands and call renderMessage on
the views, rather than using the renderMessage command
in the controller class. This we did not find a workaround
for and left as is.

Many of the original tests were modified to allow
testing of new classes without repetition of code.

As according to Professor Vido's instruction,
any tests revolving around writing/reading JPG
files just make sure they properly construct an
image, rather than checking if the image is
what we expect.

For the command line arguments:

When an invalid command line argument is given
when the program runs, the program ignores it and
gives an interactive menu.

The command line returns a message if the file for the
arguments is not a valid file, and does not bring up
the interactive menu.

(Note: When running ComonImageUtilTest, a 
FileNotFoundException shows up. We are not sure why,
because in the trace it's surrounded by a try/catch
statement of IOException. The tests pass so we presume
it's just a weird issue.)

------- DESIGN EXPLANATION -------

We first decided that the model should be handling
the majority of the functionality of the code. To do
this, we first represented image pixels as their own
class, and later realized it also made sense to
represent images themselves as their own class (in order
to not duplicate code that checks whether a 2D list of
pixels is a valid image.)

We decided that the view should handle both outputting
to the console and writing to files, and that the
controller should handle inputs from the console,
and reading from files.

A roadblock we encountered midway through was dealing
with the fact that reading from and writing to
files is difficult to test. To deal with that, we
decided to use the ImageUtil class to handle reading
from files, and generating the PPM string for writing
to files.

------- DESIGN -------

MODEL:

IPixel represents a single pixel in an image,
and contains a red, green, and blue component.
The interface has methods to get its own red, green,
and blue values, along with its value, intensity, and luma.
Red, green, and blue are each represented as integers
between 0 and 255, while intensity and luma are rounded
to the nearest integer.

PixelImpl is an implementation of IPixel. Notably, it
overrides .equals() and .hashCode().

IImage represents an image, and contains a 2D list of
IPixels. It is able to get its own width and height,
along with a pixel at a certain position and the
2D list of IPixels (cloned to prevent aliasing).

ImageImpl is an implementation of IImage. Like PixelImpl,
it overrides .equals() and .hashCode().

ImageProcessorModelReadOnly is an interface that has
the one non-mutating method of the model: saveImage.

ImageProcessorModel extends the ReadOnly version of the
model and has methods for each command the user is able
to input. Notably, it has a single getComponent command
which handles all component getting commands.

ComponentGetter is a function object interface, used by
getComponent in the model.

GetBlue, GetGreen, GetIntensity, GetLuma, GetRed, and
GetValue all implement ComponentGetter, and each take
in an IPixel and return their respective component of
that pixel.

IColorTransformation represents a general color
transformation done on a pixel. GreyscaleTransformation
applies a greyscale, while SepiaTransformation applies
a sepia effect.

IFilter represents a general filter done on an IIMage.
GaussianBlurFilter represents a blur, while SharpenFilter
represents a sharpen.

VIEW:

ImageProcessorView has 2 methods: toPPM and
renderMessage. renderMessage renders a message
to the given Appendable, while toPPM writes a given
image name to a given file destination, with the help
of ImageUtil. ImageProcessorView takes in a model and
an optional Appendable. If there is no Appendable given,
by default it will be System.out.

ImageProcessorViewImpl is an implementation of
ImageProcessorView.

CONTROLLER:

ImageProcessorController is the controller, implemented
by ImageProcessorControllerImpl. It takes in a model,
a view, and an optional Readable. If there is no
Readable given, by default it will be
InputStreamReader(System.in).

SOURCE FOR IMAGE
https://commons.wikimedia.org/wiki/File:Rick_Astley-cropped.jpg
Minesweeper is a screenshot from me beating hard mode at
https://minesweeperonline.com