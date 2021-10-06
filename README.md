# The-Easy-Animator
A visual editor that accepts an input text file representing a set of motions and produces a real-time 2D representation of the animation

Below is an outline of our animator model, explaining how we structured it and what were our design
 choices. We are also underlining changes made from the previous assignment.


Our main implementation of the model **AnimatorModelImpl** (which implements **IAnimatorModel**) has
 the following fields:
- private **currentTick** int: representing the current time(frame) of the animation
- A private final **shapes** HashMap of **IShapes**, with Strings as keys: stores the shapes which 
are currently part of the animation, along with their unique shape name (used as key).
- A private final **commands** HashMap of List of **Description**, with Strings as keys: stores the
 descriptions (motion commands) associated with each shape and matches them to their respective 
 unique shape name (used as key).
- Private **xBounds**, **yBounds**, **widthBounds**, **heightBounds** int variables to determine the
top, bottom, left, and right boundaries of the animation.

## Classes and interfaces:
### Model:
- **IReadableShape**: An interface that represents the read only version of a shape in an animation.
 Does not allow the mutation of the shape.
- **IShape**: An interface that represents a shape in an animation.
- **AbstractShape**: An abstract class that implements **IShape** and constists of all common 
methods across different shapes in the animation.
- **Rectangle**: A class that represents a Rectangle shape in an animation. It extends
 AbstractShape.
- **Ellipse**: A class that represents an ellipse shape in an animation. It extends AbstractShape.
- **Position2D**: A class that represents a 2D position with an x and a y coordinate.
- **Description**: A class that represents a motion/movement command for a certain shape within the
animation. Stores information, such as the start and end time of the command, as well as the 
beginning and ending height, width, color, and position of the shape referenced in the command.
- **IAnimatorModel**: An interface for creating and using animations.
- **AnimatorModelImpl**: A class implementing **IAnimatorModel** which measures time in ticks
 (frames). Class invariants can be found in the JavaDoc comments of the class.
 - **AnimatorBuilderImpl**: A class used to 'build' (construct) a model, including methods like 
 declaring a new shape, adding a new motion, or setting the bounds of the model. This class is defined
 within the **AnimatorModelImpl** class. 
 - **Plus**: A class that represents a plus sign shape in an animation. It extends AbstractShape.
### Main:
- **Excellence**: is the Main class of the project, and holds the main method.
- **BubbleSortAnimation**: is a class which includes a main method that can be used to run our 
 Bubble Sort Animation (which is generated programmatically using the **BubbleSortAlgorithm** class).

### Views:
- **IAnimationView**: An interface used to display the Animator Model based on the various 
implementations of this view.
- **TextualView**: A class that implements **IAnimationView** and constructs a textual
 view of the Animator Model. It lists all shapes initialized within the model, as well as any 
 motions associated with them.
- **SVGView**: implements **IAnimationView** and constructs a text view of the model by producing a string
representing the animation in SVG format.
- **VisualAnimationView**: implements **IAnimationView** and constructs a GUI using the java swing
library to represent the animation. The **VisualAnimationPanel** class consists of utilities used
within the Visual Animation View.
- **InteractiveViewFrame** and **InteractiveViewPanel** both implement **IAnimationView** and are used
 to design an interactive view, which reuses the features of the visual view but adds extra 
 functionalities (start, pause, resume, restart, enable/disable looping, increase/decrease speed， 
 outline mode, discrete mode, slow motion mode).
- **ViewFactory**: a class that uses the factory pattern to construct IViews.

### Controller:
- **IAnimationController**: An interface representing the controller, which is able to read the commands
provided by the user and translate them into actual commands for the model.
- **AnimationControllerImpl**: An abstract class consisting of commonly used methods across different
 controller implementations of this project. It implements **IAnimationController**.
- **NormalAnimationController**: The basic implementation of the controller for this project that is 
 able to read and understand arguments provided by the user. It extends **AnimationControllerImpl**.
- **BubbleSortController**: A controller that extends **AnimationControllerImpl** (i.e. includes all
 functionalities of the main controller), but adds support for out Bubble Sort Animation which is
 generated using **BubbleSortAlgorithm**. This controller works in the same way as the normal controller,
 with the only difference being the input. Instead of accepting a file input, it accepts an integer
 and generates bars (rectangles) with random height values. The number of bars is equal to the integer
 provided. The animation generated sorts this list of bars based on their height (smallest to highest).
 It traverses the list and performs the swaps needed based on the bubble sort algorithm.

## Design Choices
#### Model Package
1. We were happy with our previous version of our animation model worked, so we did not implement any
 major changes to the model package in this assignment.
#### View Package
1. We added the **InteractiveAnimationPanel** and **InteractiveAnimationFrame** to add support for a
 new interactive view. This is a new implementation of an **IAnimationView** that consists of all
 functionalities of the visual view, but adds extra features (support for start, pause, resume, restart,
 enable/disable looping, increase/decrease animation speed).
2. We added support for this new interactive view by adding a new 'ViewType' enum in our **ViewFactory**
 class.
3. Our previous versions of the **SVGView** and **TextualView** would create a new output each time
they were initialized. Based on feedback from Assignment 6, we changed the SVGView and TextualView 
constructors to receive an appendable object from the controller. The controller now determines what 
stream is connected to the appendable which can be either System.out or a file. The view classes now 
only append to this object.
4. To accommodate the change listed above, we added a 'flush()' method in **ITextView** which 
flushes the produced result from the view into the appendable.

### Controller Package
1. We decided that we need a new controller to be able to generate and run our Bubble Sort Animation
which is programmatically generated. Thus, we stored all commonly used methods across different 
controller implementations of this project in the abstract class **AnimationControllerImpl**.
The **NormalAnimationController** extends this class and constructs a controller with the basic 
functionalities. The **BubbleSortController** also extends **AnimationControllerImpl** (i.e. includes 
all functionalities of the main controller), but adds support for out Bubble Sort Animation which is
generated using **BubbleSortAlgorithm**. This controller works in the same way as the normal controller,
with the only difference being the input. Instead of accepting a file input, it accepts an integer
and generates bars (rectangles) with random height values. The number of bars is equal to the integer
provided. The animation generated sorts this list of bars based on their height (smallest to highest).
It traverses the list and performs the swaps needed based on the bubble sort algorithm.
2. The **Excellence** class which holds our main method, calls the **NormalAnimationController**.
3. The **BubbleSortAnimation** class holds a main method that is used to generate and run the bubble 
sort animation. It simply calls the **BubbleSortController**. The arguments provided to run the 
BubbleSortAnimation have the same structure as in the NormalController, with the exception of using
an integer input instead of a file name. For example, running "-in 5 -view visual -speed 10" creates
an animation of 5 bars with random heights and positions and then sorts them using bubble sort.

#### The process of whole Animator is:
1. The controller add shapes to model.
2. The controller add descriptions (motion commands) to model.
3. The controller set the current tick to model (beginning from 0).
4. The controller makes the model run to the state of the current tick.
5. Controller gets the LinkedHashMap of current state, which includes all information need to
draw a picture.
6. Controller appends this map to view.
7. View get the picture of current tick and show it on the screen.
8. As time pass by, the controller set the new current tick which equals the last tick + 1. 
(So the speed is controlled by the controller).
9. And get map again and then append and show it.
10. This process will generate an animation which can be displayed in text, SVG format, or visually
using a GUI.

If user wants to do any interaction during the animation, the controller will 
append the command of interaction to the model, and the model will do some change to 
the map of shapes according to the command (this method is not designed yet because we don't 
know the format of interaction), and then give the edited map of shapes to controller 
and then show the picture in the view.

If user want to do any interaction after the animation finishes, the endTick of model
will extend and more description will be allowed to add.

####New change:
1. We add a new shape, Plus, which will represent a plus sign in our animation.
2. We add a new command argument, -slow, which allows the user to give a txt represent the 
slow motion interval for animation. The format of slow txt should be like "10 100". The first 
argument is the begin time of a slow motion, and the second one is the end time of a slow motion.
We allow the user to give multi slow motion by writing one slow motion per row, like:
10 100
150 200
3. We add new function, outline mode, for interactive view. There is a button in interactive frame.
If user click it, the animation will only show the outline of shapes in animation. When users click
it again, the animation will return to normal mode.  This button allows the user to change between
fill mode and outline mode easily.
4. We add new function, discrete mode, for interactive view. It allows the users to only watch
the key tick of animation. Users can change the mode between discrete mode and continue mode(the 
normal mode) by clicking "discrete" button.

