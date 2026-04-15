/**
 * Groovy script to apply Stardist using a model from bioimage.io in QuPath v0.6.
 * 
 * It requires installing the StarDist extension in QuPath.
 * For more information, see https://qupath.readthedocs.io/en/0.6/docs/deep/stardist.html
 */


import static qupath.lib.gui.scripting.QPEx.*
import qupath.ext.stardist.*

// Specify the path to the model
var pathModel = buildPathInProject('chatty-frog')

// Customize how StarDist will operate
var stardist = StarDistBioimageIo.builder(pathModel)
    .createAnnotations()  // Create annotations (which are editable); remove to create detections
    .threshold(0.4)       // Specify probability threshold (lower to detect more objects)
    .build()

// Get the current image
var imageData = getCurrentImageData()

// If the image is small, and we have no objects, create a rectangle around everything
if (getAllObjectsWithoutRoot().isEmpty() && Math.max(imageData.server.width, imageData.server.height) <= 2048) {
    println "Creating a full image annotation"
    createFullImageAnnotation(true)
    // Assign a classification so we can distinguish our region from detected nuclei
    getSelectedObject().classification = "Region*"
}

// Run detection for the selected objects
var pathObjects = getSelectedObjects().findAll {it.isAnnotation()}
if (pathObjects.isEmpty()) {
    println "At least one annotation needs to be selected!"
}
stardist.detectObjects(imageData, pathObjects)
stardist.close()
println 'Done!'
