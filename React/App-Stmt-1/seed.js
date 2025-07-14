require('dotenv').config();
const mongoose = require('mongoose');
const Product = require('./models/Product');

const categories = ['Electronics', 'Books', 'Clothing', 'Home', 'Toys'];

function getRandomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

async function seedProducts() {
  await mongoose.connect(process.env.MONGODB_URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true
  });

  await Product.deleteMany({});

  const products = [];
  for (let i = 1; i <= 100; i++) {
    products.push({
      name: `Product ${i}`,
      price: getRandomInt(10, 500),
      category: categories[getRandomInt(0, categories.length - 1)],
      inStock: Math.random() < 0.7
    });
  }

  await Product.insertMany(products);
  console.log('Seeded 100 products');
  mongoose.disconnect();
}

seedProducts();
