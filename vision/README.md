# Vision

Vision processing code.


## Installing Dependencies

The `vision/install-deps.py` file can install required Python packages.
It assumes there's a Python 3.7+ install available.
Generally it's best to set up a "virtual environment" first.
(https://docs.python.org/3/library/venv.html)

Run with `vision/install-deps.py samples` to install packages and assets
required to run the code in vision/.

The "samples" option will download a .zip containing sample Vision images
of the hub, green-illuminated so the retro-reflective tape is lit up.
The files are added under the vision/assets/ folder.
The test code in vision/tests expects these files to be present.

`frc8089_utils.py` is a utility module used by install-deps.py.
It provides a way of both importing, and downloading (and installing via pip)
if necessary, python packages.


## Vision Green Code

The existing vision processing code is just an experiment, partial proof
of concept.  The goal was to make use of the vision sample files provide
by FRC, to verify that it was simple to make use of "pytest" to do automated
testing of some Python OpenCV-based image processing code.

To run the tests (after installing dependencies), you should
be able to use `py -m pytest` in the project root folder.  It should
also be possible to use just `pytest` but for the moment that appears to
have some issue with finding the vision package.  No idea why.

To run the vision code manually, try `py -m vision.green`.  This will
run the `__main__` part of vision/green.py which will walk through each
sample vision file (in `assets/2022VisionSampleImages`), performing
some basic conversion, thresholding, contour detection, and filtering,
to attempt to identify the green-lit tape on the hub.  The results
are rendered with the detected regions outlined in red, and with some
statistics written onto the image.
