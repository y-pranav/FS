'''
Problem Statement

In this notebook, you will implement a Variational Autoencoder (VAE) using PyTorch.
Tasks:

    Implement the Encoder network to produce mean and log-variance.
    Implement the Reparameterization Trick.
    Implement the Decoder network to reconstruct images.
    Implement the VAE loss function.
    Train the VAE using the Adam optimizer.
    Generate and visualize new images from random latent vectors.
    
import torch
import torch.nn as nn
import torch.optim as optim
import torch.nn.functional as F
from torchvision import datasets, transforms
from torch.utils.data import DataLoader
import matplotlib.pyplot as plt
from PIL import Image


                         +-------------------------+
                         |        Input x          |
                         |    (Flattened Image)    |
                         +-------------------------+
                                     |
                                     v
                         +-------------------------+
                         |        Encoder          |
                         |  Linear(784 → 400)      |
                         |         ReLU            |
                         +-------------------------+
                                     |
                                     v
                  +--------------------------------------+
                  |      Mean (μ)       |    Log-Var    |
                  |  Linear(400 → 20)   | Linear(400 → 20) |
                  +--------------------------------------+
                                     |  |
                                     |  |
                                     v  v
                         +-------------------------+
                         |  Compute std = exp(0.5 * logvar) |
                         |  Sample eps ~ N(0,1)           |
                         |  Compute z = μ + eps * std     |
                         +-------------------------+
                                     |
                                     v
                         +-------------------------+
                         |        Decoder          |
                         |  Linear(20 → 400)       |
                         |         ReLU            |
                         |  Linear(400 → 784)      |
                         |       Sigmoid          |
                         +-------------------------+
                                     |
                                     v
                         +-------------------------+
                         |   Reconstructed Image   |
                         +-------------------------+
                                     |
                                     v
                         +-------------------------+
                         |        Loss Function    |
                         |  Reconstruction Loss    |
                         |  Binary Cross-Entropy   |
                         |                         |
                         |   KL Divergence Loss    |
                         | D_KL(q(z|x) || p(z))    |
                         +-------------------------+



'''
