//////////////////////// PART 1 ////////////////////////

// Create image
var image = new SimpleImage("hilton.jpg");

// Get width of a third of the image
var third = image.getWidth() / 3;

// For each pixel in image
for (var pixel of image.values()) {
    // Get x-coordinate of pixel
    var x = pixel.getX();
    if (x < third) {
        // If pixel is from left third, set red to 255
        pixel.setRed(255);
    } else if (x > third && x < third + third) {
        // Else if pixel is from middle third, set green to 255
        pixel.setGreen(255);
    } else {
        // Else set blue to 255
        pixel.setBlue(255);
    }
}

print(image);


//////////////////////// PART 2 ////////////////////////

function swapRedGreen(img) {
    // For each pixel in image
    for (var px of img.values()) {
        // Swap red and green values
        px.setGreen(px.getRed());
        px.setRed(px.getGreen());
    }
    
    img.setSize(200,200);
    print(img);
}

var img = new SimpleImage("eastereggs.jpg");
swapRedGreen(img);


//////////////////////// PART 3 ////////////////////////

var devil = new SimpleImage("duke_blue_devil.png");

for (var pix of devil.values()) {
    // If pixel is less than total of white 
    if (pix.getRed()+pix.getGreen()+pixel.getBlue()<765) {
        // Change to yellow
        pix.setRed(255);
        pix.setGreen(255);
        pix.setBlue(0);
    }
}

print(devil);