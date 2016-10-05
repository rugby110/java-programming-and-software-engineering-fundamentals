// Make a yellow square that is 200 pixels wide and 200 pixels high

// Create image
var image = new SimpleImage(200, 200);
// Make each pixel in image yellow
for (var pixel of image.values()) {
    pixel.setRed(255);
    pixel.setGreen(255)
    pixel.setBlue(0);
}
print(image);


// What if you wanted to make your image magenta instead of yellow?

for (var pixel of image.values()) {
    pixel.setRed(255);
    pixel.setGreen(0)
    pixel.setBlue(255);
}
print(image);