'''
Problem Statement:

Your task is to build a multi-layer autoencoder using PyTorch to compress the images into a lower-dimensional representation and then reconstruct them. The model should use the Adam optimizer and Mean Squared Error (MSE) loss. The goal is to minimize the reconstruction loss and generate an accurate representation of the input data.
Steps to Complete the Exercise:

    Build the Multi-Layer Autoencoder:
        Encoder: Multiple dense layers reducing dimensionality.
        Decoder: Symmetric layers reconstructing the image.
    Train the Model: Use Mean Squared Error (MSE) loss and Adam optimizer to train the autoencoder.
    
    
    the ANSI diagram for the multi-layer autoencoder
    Input Layer (28x28 pixels)  
          |
      [Flatten Layer] (784)
          |
      [Dense Layer 512] - Linear, ReLU
          |
      [Dense Layer 256] - Linear, ReLU
          |
      [Dense Layer 128] - Linear, ReLU
          |
      [Dense Layer 64]  <-- Latent Representation (Bottleneck)
          |
      [Dense Layer 128] - Linear, ReLU
          |
      [Dense Layer 256] - Linear, ReLU
          |
      [Dense Layer 512] - Linear, ReLU
          |
      [Dense Layer 784] - Sigmoid (Reconstructs 28x28 image)
          |
      [Unflatten Layer] (Reshape to 28x28)
          |
      Output (Reconstructed Image)


'''