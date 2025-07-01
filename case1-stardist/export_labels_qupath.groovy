def imageData = getCurrentImageData()

def labelServer = new LabeledImageServer.Builder(imageData)
    .backgroundLabel(0, 0) // Specify background label (usually 0 or 255)
    .useInstanceLabels()
    .useAnnotations()
    .multichannelOutput(false)
    .build()


// specify the folder where to save the results
def outputDir = '/home/alan/Documents/github/imaging/use-cases/use-cases/case2/images_for_qupath/labels'
mkdirs(outputDir)
def name = GeneralTools.stripExtension(imageData.getServer().getMetadata().getName())
def path = buildFilePath(outputDir, name + ".tif")
 
writeImage(labelServer, path)
println "Image written successfully to " + path