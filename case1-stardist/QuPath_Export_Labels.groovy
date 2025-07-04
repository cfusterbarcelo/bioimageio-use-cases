/**
 * QuPath script to export labeled images corresponding to nucleus annotations to train StarDist.
 * Written for QuPath v0.6.0.
 */

// Create a labeled image that assigns integer labels to all unclassified annotations.
// Note that the 'parent' annotation created before running StarDist should have a classification 
// to make sure that it is not included here.
def imageData = getCurrentImageData()
def labelServer = new LabeledImageServer.Builder(imageData)
    .useInstanceLabels()
    .useFilter(a -> a.isAnnotation() && !a.classification)
    .build()


// Specify the folder where to save the results
def outputDir = buildPathInProject("export")
mkdirs(outputDir)

// Write the image
// For very large images, .ome.tif or .ome.zarr may be needed
def name = getCurrentImageNameWithoutExtension()
def path = buildFilePath(outputDir, name + ".tif") 
writeImage(labelServer, path)
println "Image written successfully to " + path
